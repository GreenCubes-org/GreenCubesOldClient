// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            Tessellator, FontRenderer

public class Gui {

	protected float zLevel;

	public Gui() {
		zLevel = 0.0F;
	}

	protected void func_27100_a(int i, int j, int k, int l) {
		if(j < i) {
			int i1 = i;
			i = j;
			j = i1;
		}
		drawRect(i, k, j + 1, k + 1, l);
	}

	protected void func_27099_b(int i, int j, int k, int l) {
		if(k < j) {
			int i1 = j;
			j = k;
			k = i1;
		}
		drawRect(i, j + 1, i + 1, k, l);
	}

	protected void drawRect(int i, int j, int k, int l, int i1) {
		if(i < k) {
			int j1 = i;
			i = k;
			k = j1;
		}
		if(j < l) {
			int k1 = j;
			j = l;
			l = k1;
		}
		float f = (i1 >> 24 & 0xff) / 255F;
		float f1 = (i1 >> 16 & 0xff) / 255F;
		float f2 = (i1 >> 8 & 0xff) / 255F;
		float f3 = (i1 & 0xff) / 255F;
		Tessellator tessellator = Tessellator.instance;
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(f1, f2, f3, f);
		tessellator.startDrawingQuads();
		tessellator.addVertex(i, l, 0.0D);
		tessellator.addVertex(k, l, 0.0D);
		tessellator.addVertex(k, j, 0.0D);
		tessellator.addVertex(i, j, 0.0D);
		tessellator.draw();
		GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
		GL11.glDisable(3042 /*GL_BLEND*/);
	}

	protected void drawRectSimple(int i, int j, int k, int l, int i1) {
		if(i < k) {
			int j1 = i;
			i = k;
			k = j1;
		}
		if(j < l) {
			int k1 = j;
			j = l;
			l = k1;
		}
		float f = (i1 >> 24 & 0xff) / 255F;
		float f1 = (i1 >> 16 & 0xff) / 255F;
		float f2 = (i1 >> 8 & 0xff) / 255F;
		float f3 = (i1 & 0xff) / 255F;
		Tessellator tessellator = Tessellator.instance;
		GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
		GL11.glColor4f(f1, f2, f3, f);
		tessellator.startDrawingQuads();
		tessellator.addVertex(i, l, 0.0D);
		tessellator.addVertex(k, l, 0.0D);
		tessellator.addVertex(k, j, 0.0D);
		tessellator.addVertex(i, j, 0.0D);
		tessellator.draw();
		GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
	}

	protected void drawGradientRect(int x1, int y1, int x2, int y2, int color1, int color2) {
		float f = (color1 >> 24 & 0xff) / 255F;
		float f1 = (color1 >> 16 & 0xff) / 255F;
		float f2 = (color1 >> 8 & 0xff) / 255F;
		float f3 = (color1 & 0xff) / 255F;
		float f4 = (color2 >> 24 & 0xff) / 255F;
		float f5 = (color2 >> 16 & 0xff) / 255F;
		float f6 = (color2 >> 8 & 0xff) / 255F;
		float f7 = (color2 & 0xff) / 255F;
		GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
		GL11.glBlendFunc(770, 771);
		GL11.glShadeModel(7425 /*GL_SMOOTH*/);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(f1, f2, f3, f);
		tessellator.addVertex(x2, y1, zLevel);
		tessellator.addVertex(x1, y1, zLevel);
		tessellator.setColorRGBA_F(f5, f6, f7, f4);
		tessellator.addVertex(x1, y2, zLevel);
		tessellator.addVertex(x2, y2, zLevel);
		tessellator.draw();
		GL11.glShadeModel(7424 /*GL_FLAT*/);
		GL11.glDisable(3042 /*GL_BLEND*/);
		GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
		GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
	}

	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k) {
		fontrenderer.drawStringWithShadow(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}

	public void drawString(FontRenderer fontrenderer, String s, int i, int j, int k) {
		fontrenderer.drawStringWithShadow(s, i, j, k);
	}

	public void drawTexturedModalRect(int i, int j, int k, int l, int i1, int j1) {
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(i + 0, j + j1, zLevel, (k + 0) * f, (l + j1) * f1);
		tessellator.addVertexWithUV(i + i1, j + j1, zLevel, (k + i1) * f, (l + j1) * f1);
		tessellator.addVertexWithUV(i + i1, j + 0, zLevel, (k + i1) * f, (l + 0) * f1);
		tessellator.addVertexWithUV(i + 0, j + 0, zLevel, (k + 0) * f, (l + 0) * f1);
		tessellator.draw();
	}
}
