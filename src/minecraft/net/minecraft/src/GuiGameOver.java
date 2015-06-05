// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, World, WorldInfo, GuiButton, 
//            StatCollector, ISaveFormat, ISaveHandler, GuiMainMenu, 
//            EntityPlayerSP

public class GuiGameOver extends GuiScreen {

	public GuiGameOver() {
	}

	@Override
	public void initGui() {
		controlList.clear();
		if(mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
			controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 96, StatCollector.translateToLocal("deathScreen.deleteWorld")));
		} else {
			controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 96, StatCollector.translateToLocal("deathScreen.respawn")));
			if(!mc.isMultiplayerWorld())
				controlList.add(new GuiButton(2, width / 2 - 100, height / 4 + 72, StatCollector.translateToLocal("deathScreen.titleScreen")));
			if(mc.session == null) {
				((GuiButton) controlList.get(1)).enabled = false;
			}
		}
	}

	@Override
	protected void keyTyped(char c, int i) {
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if(guibutton.id == 1) {
			if(mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
				World world = mc.theWorld;
				mc.func_40002_b("Deleting world");
				ISaveFormat isaveformat = mc.getSaveLoader();
				isaveformat.flushCache();
				isaveformat.deleteWorldDirectory(world.func_40479_y().func_40530_d());
				mc.displayGuiScreen(new GuiMainMenu());
			} else {
				mc.thePlayer.respawnPlayer();
				mc.displayGuiScreen(null);
			}
		}
		if(guibutton.id == 2) {
			mc.changeWorld1(null);
			mc.displayGuiScreen(new GuiMainMenu());
		}
	}

	// GreenCubes start
	private int updateCounter = 0;

	@Override
	public void updateScreen() {
		super.updateScreen();
		updateCounter++;
	}

	// GreenCubes end
	@Override
	public void drawScreen(int i, int j, float f) {
		drawGradientRect(0, 0, width, height, 0x60500000, 0xa0803030);
		GL11.glPushMatrix();
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		// GreenCubes start
		if(!mc.theWorld.multiplayerWorld) {
			if(mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
				drawCenteredString(fontRenderer, StatCollector.translateToLocal("deathScreen.title.hardcore"), width / 2 / 2, 30, 0xffffff);
			} else {
				drawCenteredString(fontRenderer, StatCollector.translateToLocal("deathScreen.title"), width / 2 / 2, 30, 0xffffff);
			}
			GL11.glPopMatrix();
			if(mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
				drawCenteredString(fontRenderer, StatCollector.translateToLocal("deathScreen.hardcoreInfo"), width / 2, 144, 0xffffff);
			}
			drawCenteredString(fontRenderer, (new StringBuilder()).append(StatCollector.translateToLocal("deathScreen.score")).append(": \247e").append(mc.thePlayer.getScore()).toString(), width / 2, 100, 0xffffff);
		} else {
			drawCenteredString(fontRenderer, "Вы умерли! :(", width / 2 / 2, 30, 0xffffff);
		}
		GL11.glPopMatrix();
		float f1 = ((updateCounter % 65) + f) / 65F;
		f1 = MathHelper.sin(f1 * 3.141593F * 2.0F) * 0.5F + 0.5F;
		if(f1 > 1)
			f1 = 1;
		fontRenderer.drawStringWithShadow("Green", 8, height - 16, (int) (0x00 * f1) << 16 | (int) (0x77 * f1) << 8 | (int) (0x04 * f1));
		fontRenderer.drawStringWithShadow("Cubes", 31, height - 16, (int) (0xee * f1) << 16 | (int) (0xee * f1) << 8 | (int) (0xee * f1));
		// GreenCubes end
		super.drawScreen(i, j, f);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
