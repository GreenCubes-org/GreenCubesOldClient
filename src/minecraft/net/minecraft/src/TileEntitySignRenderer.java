// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.NVOcclusionQuery;

// Referenced classes of package net.minecraft.src:
//            TileEntitySpecialRenderer, SignModel, TileEntitySign, Block, 
//            ModelRenderer, FontRenderer, TileEntity

public class TileEntitySignRenderer extends TileEntitySpecialRenderer {

	private SignModel signModel;
	private boolean compileText = false;

	public TileEntitySignRenderer() {
		signModel = new SignModel();
	}

	public void renderTileEntitySignAt(TileEntitySign tileentitysign, double d, double d1, double d2, float f) {
		Profiler.startSection("Render sign");
		Profiler.startSection("Model");
		Block block = tileentitysign.getBlockType();
		GL11.glPushMatrix();
		float f1 = 0.6666667F;
		if(block == Block.signPost || block == Block.blockWhiteSign) {
			GL11.glTranslatef((float) d + 0.5F, (float) d1 + 0.75F * f1, (float) d2 + 0.5F);
			float f2 = (tileentitysign.getBlockMetadata() * 360) / 16F;
			GL11.glRotatef(-f2, 0.0F, 1.0F, 0.0F);
			signModel.signStick.showModel = true;
		} else {
			int i = tileentitysign.getBlockMetadata();
			float f3 = 0.0F;
			if(i == 2) {
				f3 = 180F;
			}
			if(i == 4) {
				f3 = 90F;
			}
			if(i == 5) {
				f3 = -90F;
			}
			GL11.glTranslatef((float) d + 0.5F, (float) d1 + 0.75F * f1, (float) d2 + 0.5F);
			GL11.glRotatef(-f3, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);
			signModel.signStick.showModel = false;
		}
		if(block instanceof BlockWhiteSign)
			bindTextureByName("/gc_images/whitesign.png");
		else
			bindTextureByName("/item/sign.png");
		GL11.glPushMatrix();
		GL11.glScalef(f1, -f1, -f1);
		signModel.renderSign();
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		Profiler.endSection();
		Profiler.endSection();
	}

	@Override
	public void secondPass(TileEntity tileentity, double d, double d1, double d2, float f) {
		TileEntitySign tileentitysign = (TileEntitySign) tileentity;
		re: do {
			for(int k = 0; k < tileentitysign.signText.length; k++) {
				if(tileentitysign.signText[k] != null && tileentitysign.signText[k].length() > 0)
					break re;
			}
			return;
		} while(false);
		if(!Minecraft.theMinecraft.gameSettings.fancyGraphics && d * d + d1 * d1 + d2 * d2 > 400)
			return;
		Profiler.startSection("Render sign");
		Profiler.startSection("Text");
		Block block = tileentitysign.getBlockType();
		GL11.glPushMatrix();
		float f1 = 0.6666667F;
		if(block == Block.signPost || block == Block.blockWhiteSign) {
			GL11.glTranslatef((float) d + 0.5F, (float) d1 + 0.75F * f1, (float) d2 + 0.5F);
			float f2 = (tileentitysign.getBlockMetadata() * 360) / 16F;
			GL11.glRotatef(-f2, 0.0F, 1.0F, 0.0F);
		} else {
			int i = tileentitysign.getBlockMetadata();
			float f3 = 0.0F;
			if(i == 2)
				f3 = 180F;
			else if(i == 4)
				f3 = 90F;
			else if(i == 5)
				f3 = -90F;
			GL11.glTranslatef((float) d + 0.5F, (float) d1 + 0.75F * f1, (float) d2 + 0.5F);
			GL11.glRotatef(-f3, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);
		}
		if(compileText && tileentitysign.compiled && tileentitysign.lineBeingEdited == -1) {
			GL11.glCallList(tileentitysign.textRenderList);
		} else {
			if(compileText && !(Minecraft.theMinecraft.currentScreen instanceof GuiEditSign)) {
				tileentitysign.textRenderList = GLAllocation.generateDisplayLists(1);
				GL11.glNewList(tileentitysign.textRenderList, GL11.GL_COMPILE_AND_EXECUTE);
			}
			float f4 = 0.01666667F * f1;
			GL11.glTranslatef(0.0F, 0.5F * f1, 0.07F * f1);
			GL11.glScalef(f4, -f4, f4);
			GL11.glNormal3f(0.0F, 0.0F, -1F * f4);
			GL11.glDepthMask(false);
			FontRenderer fontrenderer = getFontRenderer();
			//int j = 0;
			for(int k = 0; k < tileentitysign.signText.length; k++) {
				String s = tileentitysign.signText[k];
				if(k == tileentitysign.lineBeingEdited) {
					s = (new StringBuilder()).append("> ").append(s).append(" <").toString();
					fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, k * 10 - tileentitysign.signText.length * 5, 0);
				} else {
					if(s == null || s.length() == 0)
						continue;
					fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, k * 10 - tileentitysign.signText.length * 5, 0);
				}
			}
			GL11.glDepthMask(true);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			if(compileText && !(Minecraft.theMinecraft.currentScreen instanceof GuiEditSign)) {
				tileentitysign.compiled = true;
				GL11.glEndList();
			}
		}
		GL11.glPopMatrix();
		Profiler.endSection();
		Profiler.endSection();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		renderTileEntitySignAt((TileEntitySign) tileentity, d, d1, d2, f);
	}
}