// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import org.lwjgl.input.Keyboard;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, GuiTextField, StringTranslate, GuiButton, 
//            ServerNBTStorage

public class GuiScreenAddServer extends GuiScreen {

	private GuiScreen field_35362_a;
	private GuiTextField field_35360_b;
	private GuiTextField field_35361_c;
	private ServerNBTStorage field_35359_d;

	public GuiScreenAddServer(GuiScreen guiscreen, ServerNBTStorage servernbtstorage) {
		field_35362_a = guiscreen;
		field_35359_d = servernbtstorage;
	}

	@Override
	public void updateScreen() {
		field_35361_c.updateCursorCounter();
		field_35360_b.updateCursorCounter();
	}

	@Override
	public void initGui() {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		controlList.clear();
		controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, stringtranslate.translateKey("addServer.add")));
		controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.translateKey("gui.cancel")));
		field_35361_c = new GuiTextField(fontRenderer, width / 2 - 100, 76, 200, 20);
		field_35361_c.setFocused(true);
		field_35361_c.setMaxStringLength(32);
		field_35361_c.setText(field_35359_d.name);
		field_35360_b = new GuiTextField(fontRenderer, width / 2 - 100, 116, 200, 20);
		field_35360_b.setMaxStringLength(128);
		field_35360_b.setText(field_35359_d.host);
		((GuiButton) controlList.get(0)).enabled = field_35360_b.getText().length() > 0 && field_35361_c.getText().length() > 0;
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if(!guibutton.enabled) {
			return;
		}
		if(guibutton.id == 1) {
			field_35362_a.deleteWorld(false, 0);
		} else if(guibutton.id == 0) {
			field_35359_d.name = field_35361_c.getText();
			field_35359_d.host = field_35360_b.getText();
			field_35362_a.deleteWorld(true, 0);
		}
	}

	@Override
	protected void keyTyped(char c, int i) {
		field_35361_c.textboxKeyTyped(c, i);
		field_35360_b.textboxKeyTyped(c, i);
		if(c == '\t') {
			if(field_35361_c.isFocused()) {
				field_35361_c.setFocused(false);
				field_35360_b.setFocused(true);
			} else {
				field_35361_c.setFocused(true);
				field_35360_b.setFocused(false);
			}
		}
		if(c == '\r') {
			actionPerformed((GuiButton) controlList.get(0));
		}
		((GuiButton) controlList.get(0)).enabled = field_35360_b.getText().length() > 0 && field_35361_c.getText().length() > 0;
		if(((GuiButton) controlList.get(0)).enabled) {
			String s = field_35360_b.getText().trim();
			String as[] = s.split(":");
			if(as.length > 2) {
				((GuiButton) controlList.get(0)).enabled = false;
			}
		}
	}

	@Override
	protected void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		field_35360_b.mouseClicked(i, j, k);
		field_35361_c.mouseClicked(i, j, k);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		drawDefaultBackground();
		drawCenteredString(fontRenderer, stringtranslate.translateKey("addServer.title"), width / 2, (height / 4 - 60) + 20, 0xffffff);
		drawString(fontRenderer, stringtranslate.translateKey("addServer.enterName"), width / 2 - 100, 63, 0xa0a0a0);
		drawString(fontRenderer, stringtranslate.translateKey("addServer.enterIp"), width / 2 - 100, 104, 0xa0a0a0);
		field_35361_c.drawTextBox();
		field_35360_b.drawTextBox();
		super.drawScreen(i, j, f);
	}
}
