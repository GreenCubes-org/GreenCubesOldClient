// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, StringTranslate, EnumOptions, GuiSmallButton, 
//            GameSettings, World, WorldInfo, StatCollector, 
//            GuiSlider, GuiButton, GuiVideoSettings, GuiControls

public class GuiOptions extends GuiScreen {

	private GuiScreen parentScreen;
	protected String screenTitle;
	private GameSettings options;
	private static EnumOptions relevantOptions[];

	public GuiOptions(GuiScreen guiscreen, GameSettings gamesettings) {
		screenTitle = "Настройки"; // GreenCubes
		parentScreen = guiscreen;
		options = gamesettings;
	}

	@Override
	public void initGui() {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		screenTitle = stringtranslate.translateKey("options.title");
		int i = 0;
		EnumOptions aenumoptions[] = relevantOptions;
		int j = aenumoptions.length;
		for(int k = 0; k < j; k++) {
			EnumOptions enumoptions = aenumoptions[k];
			if(!enumoptions.getEnumFloat()) {
				GuiSmallButton guismallbutton = new GuiSmallButton(enumoptions.returnEnumOrdinal(), (width / 2 - 155) + (i % 2) * 160, height / 6 + 24 * (i >> 1), enumoptions, options.getKeyBinding(enumoptions));
				if(enumoptions == EnumOptions.DIFFICULTY && mc.theWorld != null && mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
					guismallbutton.enabled = false;
					guismallbutton.displayString = (new StringBuilder()).append(StatCollector.translateToLocal("options.difficulty")).append(": ").append(StatCollector.translateToLocal("options.difficulty.hardcore")).toString();
				}
				controlList.add(guismallbutton);
			} else {
				controlList.add(new GuiSlider(enumoptions.returnEnumOrdinal(), (width / 2 - 155) + (i % 2) * 160, height / 6 + 24 * (i >> 1), enumoptions, options.getKeyBinding(enumoptions), options.getOptionFloatValue(enumoptions)));
			}
			i++;
		}

		controlList.add(new GuiButton(101, width / 2 - 100, height / 6 + 96 + 12, stringtranslate.translateKey("options.video")));
		controlList.add(new GuiButton(100, width / 2 - 100, height / 6 + 120 + 12, stringtranslate.translateKey("options.controls")));
		controlList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, stringtranslate.translateKey("gui.done")));
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if(!guibutton.enabled) {
			return;
		}
		if(guibutton.id < 100 && (guibutton instanceof GuiSmallButton)) {
			options.setOptionValue(((GuiSmallButton) guibutton).returnEnumOptions(), 1);
			guibutton.displayString = options.getKeyBinding(EnumOptions.getEnumOptions(guibutton.id));
		}
		if(guibutton.id == 101) {
			mc.gameSettings.saveOptions();
			mc.displayGuiScreen(new GuiVideoSettings(this, options));
		}
		if(guibutton.id == 100) {
			mc.gameSettings.saveOptions();
			mc.displayGuiScreen(new GuiControls(this, options));
		}
		if(guibutton.id == 200) {
			mc.gameSettings.saveOptions();
			mc.displayGuiScreen(parentScreen);
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		drawCenteredString(fontRenderer, screenTitle, width / 2, 20, 0xffffff);
		// GreenCubes start
		fontRenderer.drawString("\2472Green", 8, height - 16, (0x00) << 16 | (0x77) << 8 | (0x04));
		fontRenderer.drawString("Cubes", 31, height - 16, (0xee) << 16 | (0xee) << 8 | (0xee));
		// GreenCubes end
		super.drawScreen(i, j, f);
	}

	static {
		relevantOptions = (new EnumOptions[]{EnumOptions.MUSIC, EnumOptions.SOUND, EnumOptions.INVERT_MOUSE, EnumOptions.SENSITIVITY, EnumOptions.FOV, EnumOptions.DIFFICULTY});
	}
}
