// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;

// Referenced classes of package net.minecraft.src:
//            PlayerHelper, SPCObjectHit

public abstract class SPCPlugin {

	public PlayerHelper ph;

	public SPCPlugin() {
	}

	public void setPlayerHelper(PlayerHelper playerhelper) {
		ph = playerhelper;
	}

	public abstract String getVersion();

	public abstract String getName();

	public boolean handleCommand(String as[]) {
		return false;
	}

	public void handleLeftClick(SPCObjectHit spcobjecthit) {
	}

	public void handleRightClick(SPCObjectHit spcobjecthit) {
	}

	public void handleLeftButtonDown(SPCObjectHit spcobjecthit) {
	}

	public void handleRightButtonDown(SPCObjectHit spcobjecthit) {
	}

	public void handleCUIEvent(String s, String as[]) {
	}

	public void atUpdate() {
	}

	public List getCommands() {
		return null;
	}

	public String[] getHelp(String s) {
		return null;
	}
}
