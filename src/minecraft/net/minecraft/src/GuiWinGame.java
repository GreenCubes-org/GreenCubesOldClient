// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;
import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, World, EntityClientPlayerMP, Packet9Respawn, 
//            NetClientHandler, Session, FontRenderer, Tessellator, 
//            RenderEngine, GuiButton

public class GuiWinGame extends GuiScreen {

	private int field_41046_a;
	private List field_41044_b;
	private int field_41045_c[];
	private int field_41042_d;
	private float field_41043_e;

	public GuiWinGame() {
		field_41046_a = 0;
		field_41042_d = 0;
		field_41043_e = 0.5F;
	}

	@Override
	public void updateScreen() {
		field_41046_a++;
		float f = (field_41042_d + height + height + 24) / field_41043_e;
		if(field_41046_a > f) {
			func_41041_e();
		}
	}

	@Override
	protected void keyTyped(char c, int i) {
		if(i == 1) {
			func_41041_e();
		}
	}

	private void func_41041_e() {
		if(mc.theWorld.multiplayerWorld) {
			EntityClientPlayerMP entityclientplayermp = (EntityClientPlayerMP) mc.thePlayer;
			entityclientplayermp.sendInventoryChanged();
			entityclientplayermp.sendQueue.addToSendQueue(new Packet9Respawn((byte) entityclientplayermp.dimension, (byte) mc.theWorld.difficultySetting, mc.theWorld.getWorldSeed(), mc.theWorld.field_35472_c, 0));
		} else {
			mc.displayGuiScreen(null);
			mc.respawn(mc.theWorld.multiplayerWorld, 0, true);
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}

	@Override
	public void initGui() {
		if(field_41044_b != null) {
			return;
		}
		field_41044_b = new ArrayList();
		try {
			String s = "";
			String s1 = "\247f\247k\247a\247b";
			BufferedReader bufferedreader = new BufferedReader(new InputStreamReader((net.minecraft.src.GuiWinGame.class).getResourceAsStream("/title/win.txt"), Charset.forName("UTF-8")));
			Random random = new Random(0x7bf7d3L);
			while((s = bufferedreader.readLine()) != null) {
				String s2;
				String s3;
				for(s = s.replaceAll("PLAYERNAME", mc.session.username); s.indexOf(s1) >= 0; s = (new StringBuilder()).append(s2).append("\247f\247k").append("XXXXXXXX".substring(0, random.nextInt(4) + 3)).append(s3).toString()) {
					int i = s.indexOf(s1);
					s2 = s.substring(0, i);
					s3 = s.substring(i + s1.length());
				}

				if(s.length() == 0) {
					field_41044_b.add(s);
				}
				field_41044_b.add(s);
			}
			for(int j = 0; j < 16; j++) {
				field_41044_b.add("");
			}

			bufferedreader = new BufferedReader(new InputStreamReader((net.minecraft.src.GuiWinGame.class).getResourceAsStream("/title/credits.txt"), Charset.forName("UTF-8")));
			while((s = bufferedreader.readLine()) != null) {
				s = s.replaceAll("PLAYERNAME", mc.session.username);
				s = s.replaceAll("\t", "    ");
				if(s.length() == 0) {
					field_41044_b.add(s);
				}
				field_41044_b.add(s);
			}
			field_41045_c = new int[field_41044_b.size()];
			char c = '\u0112';
			fontRenderer.FONT_HEIGHT = 12;
			for(int k = 0; k < field_41044_b.size(); k++) {
				field_41042_d += field_41045_c[k] = fontRenderer.splitStringWidth((String) field_41044_b.get(k), c);
			}

			fontRenderer.FONT_HEIGHT = 8;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
	}

	private void func_41040_b(int i, int j, float f) {
		Tessellator tessellator = Tessellator.instance;
		GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("%blur%/gui/background.png"));
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
		int k = width;
		float f1 = 0.0F - (field_41046_a + f) * 0.5F * field_41043_e;
		float f2 = height - (field_41046_a + f) * 0.5F * field_41043_e;
		float f3 = 0.015625F;
		float f4 = ((field_41046_a + f) - 0.0F) * 0.02F;
		float f5 = (field_41042_d + height + height + 24) / field_41043_e;
		float f6 = (f5 - 20F - (field_41046_a + f)) * 0.005F;
		if(f6 < f4) {
			f4 = f6;
		}
		if(f4 > 1.0F) {
			f4 = 1.0F;
		}
		f4 *= f4;
		f4 = (f4 * 96F) / 255F;
		tessellator.setColorOpaque_F(f4, f4, f4);
		tessellator.addVertexWithUV(0.0D, height, zLevel, 0.0D, f1 * f3);
		tessellator.addVertexWithUV(k, height, zLevel, k * f3, f1 * f3);
		tessellator.addVertexWithUV(k, 0.0D, zLevel, k * f3, f2 * f3);
		tessellator.addVertexWithUV(0.0D, 0.0D, zLevel, 0.0D, f2 * f3);
		tessellator.draw();
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		func_41040_b(i, j, f);
		Tessellator tessellator = Tessellator.instance;
		char c = '\u0112';
		int k = width / 2 - c / 2;
		int l = height + 50;
		float f1 = -(field_41046_a + f) * field_41043_e;
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, f1, 0.0F);
		GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/title/mclogo.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(k + 0, l + 0, 0, 0, 155, 44);
		drawTexturedModalRect(k + 155, l + 0, 0, 45, 155, 44);
		tessellator.setColorOpaque_I(0xffffff);
		fontRenderer.FONT_HEIGHT = 12;
		int i1 = l + 200;
		for(int j1 = 0; j1 < field_41044_b.size(); j1++) {
			int l1 = field_41045_c[j1];
			if(j1 == field_41044_b.size() - 1) {
				float f2 = (i1 + f1) - (height / 2 - l1 / 2);
				if(f2 < 0.0F) {
					GL11.glTranslatef(0.0F, -f2, 0.0F);
				}
			}
			if(i1 + f1 + l1 + 8F > 0.0F && i1 + f1 < height) {
				String s = (String) field_41044_b.get(j1);
				if(s.startsWith("[C]")) {
					fontRenderer.drawStringWithShadow(s.substring(3), k + (c - fontRenderer.getStringWidth(s.substring(3))) / 2, i1, 0xffffff);
				} else {
					fontRenderer.field_41064_c.setSeed(j1 * 0xfca99533L + (field_41046_a / 4));
					fontRenderer.func_40609_a(s, k + 1, i1 + 1, c, 0xffffff, true);
					fontRenderer.field_41064_c.setSeed(j1 * 0xfca99533L + (field_41046_a / 4));
					fontRenderer.func_40609_a(s, k, i1, c, 0xffffff, false);
				}
			}
			i1 += l1;
		}

		fontRenderer.FONT_HEIGHT = 8;
		GL11.glPopMatrix();
		GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("%blur%/misc/vignette.png"));
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glBlendFunc(0, 769);
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
		int k1 = width;
		int i2 = height;
		tessellator.addVertexWithUV(0.0D, i2, zLevel, 0.0D, 1.0D);
		tessellator.addVertexWithUV(k1, i2, zLevel, 1.0D, 1.0D);
		tessellator.addVertexWithUV(k1, 0.0D, zLevel, 1.0D, 0.0D);
		tessellator.addVertexWithUV(0.0D, 0.0D, zLevel, 0.0D, 0.0D);
		tessellator.draw();
		GL11.glDisable(3042 /*GL_BLEND*/);
		super.drawScreen(i, j, f);
	}
}
