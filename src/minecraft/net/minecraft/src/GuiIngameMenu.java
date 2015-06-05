// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, GuiButton, StatCollector, GuiOptions, 
//            StatList, StatFileWriter, World, GuiMainMenu, 
//            GuiAchievements, GuiStats, MathHelper

public class GuiIngameMenu extends GuiScreen {

	private int updateCounter2;
	private int updateCounter;

	public GuiIngameMenu() {
		updateCounter2 = 0;
		updateCounter = 0;
	}

	@Override
	public void initGui() {
		updateCounter2 = 0;
		controlList.clear();
		byte byte0 = 0;
		// GreenCubes start
		controlList.add(new GuiButton(0, width / 2 - 100, height / 2 + 16, "Настройки"));
		if(mc.isMultiplayerWorld()) {
			controlList.add(new GuiButton(4, width / 2 - 100, height / 2 - 8, "§2Вернуться в игру"));
			controlList.add(new GuiButton(1, width / 2 - 100, height / 2 + 40, "Отключиться"));
		} else {
			controlList.add(new GuiButton(4, width / 2 - 100, height / 2 - 32, "Вернуться в игру"));
			controlList.add(new GuiButton(1, width / 2 - 100, height / 2 + 40, "Сохранить и выйти"));
			controlList.add(new GuiButton(3, width / 2 - 100, height / 2 - 8, "Подключиться к §2Green§fCubes"));
		}
		// GreenCubes end
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if(guibutton.id == 0) {
			mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
		}
		if(guibutton.id == 1) {
			mc.statFileWriter.readStat(StatList.leaveGameStat, 1);
			if(mc.isMultiplayerWorld()) {
				mc.theWorld.sendQuittingDisconnectingPacket();
			}
			mc.changeWorld1(null);
			mc.displayGuiScreen(new GuiMainMenu());
		}
		// GreenCubes start
		if(guibutton.id == 3) {
			mc.displayGuiScreen(new GuiConnecting(mc, "srv1.greencubes.org", 25565));
		}
		// GreenCubes end
		if(guibutton.id == 4) {
			mc.displayGuiScreen(null);
			mc.setIngameFocus();
		}
		if(guibutton.id == 5) {
			mc.displayGuiScreen(new GuiAchievements(mc.statFileWriter));
		}
		if(guibutton.id == 6) {
			mc.displayGuiScreen(new GuiStats(this, mc.statFileWriter));
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		updateCounter++;
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		// GreenCubes start
		float f1 = (updateCounter % 65 + f) / 65.0F;
		f1 = MathHelper.sin(f1 * 3.141593F * 2.0F) * 0.5F + 0.5F;
		if(f1 > 1.0F)
			f1 = 1.0F;
		drawString(fontRenderer, "Green", 8, this.height - 16, (int) (0.0F * f1) << 16 | (int) (126.0F * f1) << 8 | (int) (4.0F * f1));
		drawString(fontRenderer, "Cubes", 31, this.height - 16, (int) (238.0F * f1) << 16 | (int) (238.0F * f1) << 8 | (int) (238.0F * f1));
		drawCenteredString(fontRenderer, "Меню игры", width / 2, height / 2 - 50, 0xffffff);
		// GreenCubes end
		super.drawScreen(i, j, f);
	}
}
