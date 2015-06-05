// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import org.lwjgl.input.Keyboard;

// Referenced classes of package net.minecraft.src:
//            GuiChat, StringTranslate, GuiButton, EntityPlayerSP, 
//            EntityClientPlayerMP, Packet19EntityAction, NetClientHandler

public class GuiSleepMP extends GuiChat {

	public GuiSleepMP() {
		super(false);
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		StringTranslate stringtranslate = StringTranslate.getInstance();
		controlList.add(new GuiButton(1, width / 2 - 100, height - 40, stringtranslate.translateKey("multiplayer.stopSleeping")));
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void keyTyped(char c, int i) {
		if(i == 1)
			func_22115_j();
		else
			super.keyTyped(c, i);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		super.drawScreen(i, j, f);
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if(guibutton.id == 1) {
			func_22115_j();
		} else {
			super.actionPerformed(guibutton);
		}
	}

	private void func_22115_j() {
		if(mc.thePlayer instanceof EntityClientPlayerMP) {
			NetClientHandler netclienthandler = ((EntityClientPlayerMP) mc.thePlayer).sendQueue;
			netclienthandler.addToSendQueue(new Packet19EntityAction(mc.thePlayer, 3));
		}
	}
}
