package org.greencubes.gui;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import com.jme3.math.ColorRGBA;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.Tessellator;

public class FancyGUI {
	
	private static FancyGUI instance;
	private static Tessellator tesselator = Tessellator.instance;
	
	private final RenderEngine renderEngine;
	
	private int textureWidth;
	private int textureHeight;
	private int textureId;
	private float texturePixelWidth;
	private float texturePixelHeight;
	private ColorRGBA color = ColorRGBA.White;
	
	public NinePartInfo inventoryWindowNPI = new NinePartInfo(5, 1, 5, 5, 5, 1, 5, 1, 5, 1, 5, 5);
	public NinePartInfo itemDescriptionNPI = new NinePartInfo(4, 2, 4, 4, 4, 2, 4, 2, 4, 2, 4, 4);
	public NinePartInfo inventoryPanelNPI = new NinePartInfo(3, 1, 3, 3, 3, 1, 3, 1, 3, 1, 3, 3);
	public NinePartInfo butonNPI = new NinePartInfo(4, 40, 4, 4, 4, 40, 4, 31, 4, 40, 4, 4);
	
	private FancyGUI() {
		renderEngine = Minecraft.theMinecraft.renderEngine;
		try {
			BufferedImage interfaceTexture = ModLoader.loadImage(renderEngine, "/gui/interface.png");
			interfaceTexture = ImageUtil.flipVertical(interfaceTexture);
			textureWidth = interfaceTexture.getWidth();
			textureHeight = interfaceTexture.getHeight();
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
	
	public void renderInterface(int x, int y, int w, int h, int srcX, int srcY, int srcW, int srcH) {
		renderEngine.bindTexture(textureId);
		renderImage(x, y, w, h, textureWidth, textureHeight, srcX, srcY, srcW, srcH, color);
	}
	
	public void renderInterfaceNinePart(int x, int y, int w, int h, int srcX, int srcY, NinePartInfo npi) {
		renderInterface(x, y, npi.top_leftWidth, npi.top_height, srcX, srcY, npi.top_leftWidth, npi.top_height);
		if(npi.repeat) {
			renderInterfaceRepeat(x + npi.top_leftWidth, y, w - npi.top_leftWidth - npi.top_rightWidth, npi.top_height, srcX + npi.top_leftWidth, srcY, npi.top_centerWidth, npi.top_height);
		} else {
			renderInterface(x + npi.top_leftWidth, y, w - npi.top_leftWidth - npi.top_rightWidth, npi.top_height, srcX + npi.top_leftWidth, srcY, npi.top_centerWidth, npi.top_height);
		}
		renderInterface(x + w - npi.top_rightWidth, y, npi.top_rightWidth, npi.top_height, srcX + npi.top_leftWidth + npi.top_centerWidth, srcY, npi.top_rightWidth, npi.top_height);
		
		if(npi.repeat) {
			renderInterfaceRepeat(x, y + npi.top_height, npi.center_leftWidth, h - npi.top_height - npi.bottom_height, srcX, srcY + npi.top_height, npi.center_leftWidth, npi.center_height);
			renderInterfaceRepeat(x + npi.center_leftWidth, y + npi.top_height, w - npi.center_leftWidth - npi.center_rightWidth, h - npi.top_height - npi.bottom_height, srcX + npi.center_leftWidth, srcY + npi.top_height, npi.center_centerWidth, npi.center_height);
			renderInterfaceRepeat(x + w - npi.center_rightWidth, y + npi.top_height, npi.center_rightWidth, h - npi.top_height - npi.bottom_height, srcX + npi.center_leftWidth + npi.center_centerWidth, srcY + npi.top_height, npi.center_rightWidth, npi.center_height);
		} else {
			renderInterface(x, y + npi.top_height, npi.center_leftWidth, h - npi.top_height - npi.bottom_height, srcX, srcY + npi.top_height, npi.center_leftWidth, npi.center_height);
			renderInterface(x + npi.center_leftWidth, y + npi.top_height, w - npi.center_leftWidth - npi.center_rightWidth, h - npi.top_height - npi.bottom_height, srcX + npi.center_leftWidth, srcY + npi.top_height, npi.center_centerWidth, npi.center_height);
			renderInterface(x + w - npi.center_rightWidth, y + npi.top_height, npi.center_rightWidth, h - npi.top_height - npi.bottom_height, srcX + npi.center_leftWidth + npi.center_centerWidth, srcY + npi.top_height, npi.center_rightWidth, npi.center_height);
		}
		
		renderInterface(x, y + h - npi.bottom_height, npi.bottom_leftWidth, npi.bottom_height, srcX, srcY + npi.top_height + npi.center_height, npi.bottom_leftWidth, npi.bottom_height);
		if(npi.repeat) {
			renderInterfaceRepeat(x + npi.bottom_leftWidth, y + h - npi.bottom_height, w - npi.bottom_leftWidth - npi.bottom_rightWidth, npi.bottom_height, srcX + npi.bottom_leftWidth, srcY + npi.top_height + npi.center_height, npi.bottom_centerWidth, npi.bottom_height);
		} else {
			renderInterface(x + npi.bottom_leftWidth, y + h - npi.bottom_height, w - npi.bottom_leftWidth - npi.bottom_rightWidth, npi.bottom_height, srcX + npi.bottom_leftWidth, srcY + npi.top_height + npi.center_height, npi.bottom_centerWidth, npi.bottom_height);
		}
		renderInterface(x + w - npi.bottom_rightWidth, y + h - npi.bottom_height, npi.bottom_rightWidth, npi.bottom_height, srcX + npi.bottom_leftWidth + npi.bottom_centerWidth, srcY + npi.top_height + npi.center_height, npi.bottom_rightWidth, npi.bottom_height);
	}
	
	public void renderInterfaceRepeat(int x, int y, int w, int h, int srcX, int srcY, int srcW, int srcH) {
		renderEngine.bindTexture(textureId);
		int endX = x + w;
		int endY = y + h;

		int tileY = y;
		while (tileY < endY) {
			int tileHeight = Math.min(srcH, endY - tileY);
			int tileX = x;
			while (tileX < endX) {
				int tileWidth = Math.min(srcW, endX - tileX);
				renderImage(tileX, tileY, tileWidth, tileHeight, textureWidth, textureHeight, srcX, srcY, tileWidth, tileHeight, color);
				tileX += tileWidth;
			}
			tileY += tileHeight;
		}
	}
	
	private void renderImage(int x, int y, int w, int h, int imageWidth, int imageHeight, int srcX, int srcY, int srcW, int srcH, ColorRGBA color) {
		float startX = (float) srcX / (float) imageWidth;
		float startY = (float) srcY / (float) imageHeight;
		float endX = startX + ((float) srcW / (float) imageWidth);
		float endY = startY + ((float) srcH / (float) imageHeight);
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
