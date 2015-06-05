// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import net.lahwran.WECUIEvent;
import net.lahwran.fevents.EventManager;

// Referenced classes of package net.minecraft.src:
//            SPCPlugin

public class spc_WorldEditCUI extends SPCPlugin {

	public spc_WorldEditCUI() {
	}

	@Override
	public String getVersion() {
		return "v1.0 for GreenCubes";
	}

	@Override
	public String getName() {
		return "WorldEditCUI";
	}

	@Override
	public void handleCUIEvent(String s, String as[]) {
		EventManager.callEvent(new WECUIEvent(s, as));
	}
}
