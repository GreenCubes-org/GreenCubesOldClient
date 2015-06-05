// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, Packet0KeepAlive, NetClientHandler, StringTranslate, 
//            GuiButton

public class GuiDownloadTerrain extends GuiScreen {

	private NetClientHandler netHandler;
	private int updateCounter;

	public GuiDownloadTerrain(NetClientHandler netclienthandler) {
		updateCounter = 0;
		netHandler = netclienthandler;
	}

	@Override
	protected void keyTyped(char c, int i) {
	}

	@Override
	public void initGui() {
		controlList.clear();
	}

	@Override
	public void updateScreen() {
		updateCounter++;
		if(updateCounter % 20 == 0) {
			netHandler.addToSendQueue(new Packet0KeepAlive());
		}
		if(netHandler != null) {
			netHandler.processReadPackets();
		}
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawBackground(0);
		StringTranslate stringtranslate = StringTranslate.getInstance();
		drawCenteredString(fontRenderer, stringtranslate.translateKey("multiplayer.downloadingTerrain"), width / 2, height / 2 - 50, 0xffffff);
		super.drawScreen(i, j, f);
	}
}
