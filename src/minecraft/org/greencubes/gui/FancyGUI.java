package org.greencubes.gui;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import com.jme3.math.ColorRGBA;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.Tessellator;

public class FancyGUI {
	
	private static FancyGUI instance;
	private static Tessellator tesselator = Tessellator.instance;
	
	private final RenderEngine renderEngine;
	
	private int textureWidth = 1024;
	private int textureHeight = 512;
	private int textureId;
	private float texturePixelWidth;
	private float texturePixelHeight;
	private ColorRGBA color = ColorRGBA.White;
	private float scale = 1f;
	
	public NinePartInfo inventoryWindowNPI = new NinePartInfo(5, 1, 5, 5, 5, 1, 5, 1, 5, 1, 5, 5);
	public NinePartInfo itemDescriptionNPI = new NinePartInfo(4, 2, 4, 4, 4, 2, 4, 2, 4, 2, 4, 4);
	public NinePartInfo inventoryPanelNPI = new NinePartInfo(3, 1, 3, 3, 3, 1, 3, 1, 3, 1, 3, 3);
	public NinePartInfo butonNPI = new NinePartInfo(4, 40, 4, 4, 4, 40, 4, 31, 4, 40, 4, 4);
	public NinePartInfo inputFieldNPI = new NinePartInfo(4, 1, 4, 4, 4, 1, 4, 1, 4, 1, 4, 4);
	
	private FancyGUI() {
		renderEngine = Minecraft.theMinecraft.renderEngine;
		try {
			BufferedImage interfaceTexture = ModLoader.loadImage(renderEngine, "/gui/interface.png");
			interfaceTexture = ImageUtil.flipVertical(interfaceTexture);
			// We do not read actual texture size to allow gentle texture packs modifications
			//textureWidth = interfaceTexture.getWidth();
			//textureHeight = interfaceTexture.getHeight();
			texturePixelWidth = 1f / (float) textureWidth;
			texturePixelHeight = 1f / (float) textureHeight;
			textureId = renderEngine.allocateAndSetupTexture(interfaceTexture);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static FancyGUI getInstance() {
		if(instance == null)
			instance = new FancyGUI();
		return instance;
	}
	
	private int getDisplayHeight() {
		return Minecraft.theMinecraft.displayHeight;
	}
	
	public void setColor(ColorRGBA color) {
		this.color = color;
	}
	
	public void enableMode() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
	
	public void disableMode() {
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public void renderScaledTooltip(int x, int y, String[] lines, int screenWidth, int screenHeight, int padding) {
		renderScaledTooltip(x, y, lines, screenWidth, screenHeight, 2, padding);
	}
	
	public void renderScaledTooltip(int x, int y, String[] lines, int screenWidth, int screenHeight, int firstLinePadding, int padding) {
		x += padding;
		y += padding;
		FontRenderer fontRenderer = Minecraft.theMinecraft.fontRenderer;
		FancyGUI.getInstance().enableMode();
		int w = 0;
		for(String s : lines)
			w = Math.max(w, fontRenderer.getStringWidth(s));
		if(lines.length < 2)
			firstLinePadding = 0;
		FancyGUI.getInstance().setScale(0.5f);
		if(x + 6 + w + 8 > screenWidth)
			x -= (x + 6 + w + 8) - screenWidth;
		if(y + 6 + 10 * lines.length + 8 + firstLinePadding > screenHeight) {
			if(y - padding - padding / 2 - (6 + 10 * lines.length + 8 + firstLinePadding) > 0) {
				y -= padding + (6 + 10 * lines.length + 8 + firstLinePadding) + padding / 2;
			} else {
				y -= (y + 6 + 10 * lines.length + 8 + firstLinePadding) - screenHeight;
			}
		}
		FancyGUI.getInstance().renderInterfaceNinePart(x + 6, y + 6, w + 8, 10 * lines.length + 8 + firstLinePadding, 228, 198, FancyGUI.getInstance().itemDescriptionNPI);
		for(int i1 = 0; i1 < lines.length; ++i1) {
			String s = lines[i1];
			fontRenderer.renderString(s, x + 6 + 4, y + 6 + i1 * 10 + 4 + (i1 != 0 ? firstLinePadding : 0) , 0xFFFFFF, false);
		}
		FancyGUI.getInstance().setScale(1f);
		FancyGUI.getInstance().disableMode();
	}
	
	public void renderInterface(float x, float y, float w, float h, int srcX, int srcY, int srcW, int srcH) {
		renderEngine.bindTexture(textureId);
		renderImage(x, y, w, h, srcX, srcY, srcW, srcH, color);
	}
	
	public void renderInterfaceNinePart(float x, float y, float w, float h, int srcX, int srcY, NinePartInfo npi) {
		renderInterface(x, y, npi.top_leftWidth * scale, npi.top_height * scale, srcX, srcY, npi.top_leftWidth, npi.top_height);
		if(npi.repeat) {
			renderInterfaceRepeat(x + npi.top_leftWidth * scale, y, w - npi.top_leftWidth * scale - npi.top_rightWidth * scale, npi.top_height * scale, srcX + npi.top_leftWidth, srcY, npi.top_centerWidth, npi.top_height);
		} else {
			renderInterface(x + npi.top_leftWidth * scale, y, w - npi.top_leftWidth * scale - npi.top_rightWidth  * scale, npi.top_height * scale, srcX + npi.top_leftWidth, srcY, npi.top_centerWidth, npi.top_height);
		}
		renderInterface(x + w - npi.top_rightWidth * scale, y, npi.top_rightWidth * scale, npi.top_height * scale, srcX + npi.top_leftWidth + npi.top_centerWidth, srcY, npi.top_rightWidth, npi.top_height);
		
		if(npi.repeat) {
			renderInterfaceRepeat(x, y + npi.top_height * scale, npi.center_leftWidth * scale, h - npi.top_height * scale - npi.bottom_height * scale, srcX, srcY + npi.top_height, npi.center_leftWidth, npi.center_height);
			renderInterfaceRepeat(x + npi.center_leftWidth * scale, y + npi.top_height * scale, w - npi.center_leftWidth * scale - npi.center_rightWidth * scale, h - npi.top_height * scale - npi.bottom_height * scale, srcX + npi.center_leftWidth, srcY + npi.top_height, npi.center_centerWidth, npi.center_height);
			renderInterfaceRepeat(x + w - npi.center_rightWidth * scale, y + npi.top_height * scale, npi.center_rightWidth * scale, h - npi.top_height * scale - npi.bottom_height * scale, srcX + npi.center_leftWidth + npi.center_centerWidth, srcY + npi.top_height, npi.center_rightWidth, npi.center_height);
		} else {
			renderInterface(x, y + npi.top_height * scale, npi.center_leftWidth * scale, h - npi.top_height * scale - npi.bottom_height * scale, srcX, srcY + npi.top_height, npi.center_leftWidth, npi.center_height);
			renderInterface(x + npi.center_leftWidth * scale, y + npi.top_height * scale, w - npi.center_leftWidth * scale - npi.center_rightWidth * scale, h - npi.top_height * scale - npi.bottom_height * scale, srcX + npi.center_leftWidth, srcY + npi.top_height, npi.center_centerWidth, npi.center_height);
			renderInterface(x + w - npi.center_rightWidth * scale, y + npi.top_height * scale, npi.center_rightWidth * scale, h - npi.top_height * scale - npi.bottom_height * scale, srcX + npi.center_leftWidth + npi.center_centerWidth, srcY + npi.top_height, npi.center_rightWidth, npi.center_height);
		}
		
		renderInterface(x, y + h - npi.bottom_height * scale, npi.bottom_leftWidth * scale, npi.bottom_height * scale, srcX, srcY + npi.top_height + npi.center_height, npi.bottom_leftWidth, npi.bottom_height);
		if(npi.repeat) {
			renderInterfaceRepeat(x + npi.bottom_leftWidth * scale, y + h - npi.bottom_height * scale, w - npi.bottom_leftWidth * scale - npi.bottom_rightWidth * scale, npi.bottom_height * scale, srcX + npi.bottom_leftWidth, srcY + npi.top_height + npi.center_height, npi.bottom_centerWidth, npi.bottom_height);
		} else {
			renderInterface(x + npi.bottom_leftWidth * scale, y + h - npi.bottom_height * scale, w - npi.bottom_leftWidth * scale - npi.bottom_rightWidth * scale, npi.bottom_height * scale, srcX + npi.bottom_leftWidth, srcY + npi.top_height + npi.center_height, npi.bottom_centerWidth, npi.bottom_height);
		}
		renderInterface(x + w - npi.bottom_rightWidth * scale, y + h - npi.bottom_height * scale, npi.bottom_rightWidth * scale, npi.bottom_height * scale, srcX + npi.bottom_leftWidth + npi.bottom_centerWidth, srcY + npi.top_height + npi.center_height, npi.bottom_rightWidth, npi.bottom_height);
	}
	
	public void renderInterfaceRepeat(float x, float y, float w, float h, int srcX, int srcY, int srcW, int srcH) {
		renderEngine.bindTexture(textureId);
		float endX = x + w;
		float endY = y + h;

		float tileY = y;
		while (tileY < endY) {
			float tileHeight = Math.min(srcH, endY - tileY);
			float tileX = x;
			while (tileX < endX) {
				float tileWidth = Math.min(srcW, endX - tileX);
				renderImage(tileX, tileY, tileWidth, tileHeight, srcX, srcY, (int) tileWidth, (int) tileHeight, color);
				tileX += tileWidth;
			}
			tileY += tileHeight;
		}
	}
	
	private void renderImage(float x, float y, float w, float h, int srcX, int srcY, int srcW, int srcH, ColorRGBA color) {
		float startX = (float) srcX * texturePixelWidth;
		float startY = (float) srcY * texturePixelHeight;
		float endX = startX + (float) srcW * texturePixelWidth;
		float endY = startY + (float) srcH * texturePixelHeight;
		float f1 = 1f - startY;
		startY = 1f - endY;
		endY = f1;
		tesselator.startDrawingQuads();
		tesselator.setColorRGBA_F(color.r, color.g, color.b, color.a);
		tesselator.addVertexWithUV(x, y + h, 0d, startX, startY);
		tesselator.addVertexWithUV(x + w, y + h, 0d, endX, startY);
		tesselator.addVertexWithUV(x + w, y, 0d, endX, endY);
		tesselator.addVertexWithUV(x, y, 0d, startX, endY);
		tesselator.draw();
	}
}
