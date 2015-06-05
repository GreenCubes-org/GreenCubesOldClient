// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, StringTranslate, GuiButton, GuiMainMenu

public class GuiDisconnected extends GuiScreen {

	private String errorMessage;
	private String errorDetail;
	private String addInfo;

	public GuiDisconnected(String s, String s1, Object aobj[]) {
		this(s, s1, aobj, null);
	}

	public GuiDisconnected(String s, String s1, Object aobj[], String addInfo) {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		errorMessage = stringtranslate.translateKey(s);
		if(aobj != null) {
			errorDetail = stringtranslate.translateKeyFormat(s1, aobj);
		} else {
			errorDetail = stringtranslate.translateKey(s1);
		}
		this.addInfo = addInfo;
	}

	@Override
	public void updateScreen() {
	}

	@Override
	protected void keyTyped(char c, int i) {
	}

	@Override
	public void initGui() {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		controlList.clear();
		controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.translateKey("gui.toMenu")));
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if(guibutton.id == 0) {
			mc.displayGuiScreen(new GuiMainMenu());
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		drawCenteredString(fontRenderer, errorMessage, width / 2, height / 2 - 50, 0xffffff);
		drawCenteredString(fontRenderer, errorDetail, width / 2, height / 2 - 10, 0xffffff);
		if(addInfo != null)
			drawCenteredString(fontRenderer, addInfo, width / 2, height / 2, 0xffffff);
		super.drawScreen(i, j, f);
	}
}
