// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.*;

import org.lwjgl.input.Keyboard;

// Referenced classes of package net.minecraft.src:
//            IntHashMap

public class KeyBinding {

	public static List<KeyBinding> keybindArray = new ArrayList<KeyBinding>();
	public static TIntObjectHashMap<KeyBinding> hash = new TIntObjectHashMap<KeyBinding>();
	public String keyDescription;
	public int keyCode;
	public boolean pressed;
	public int pressTime;

	public static void onTick(int i) {
		KeyBinding keybinding = hash.get(i);
		if(keybinding != null)
			keybinding.pressTime++;
	}

	public static void setKeyBindState(int i, boolean flag) {
		KeyBinding keybinding = hash.get(i);
		if(keybinding != null)
			keybinding.pressed = flag;
		// GreenCubes Improved Chat start
		if(GChat.mc.isMultiplayerWorld()) {
			if(!flag) {
				if(GChat.mc.chat.pressKey(i))
					return;
			} else if((Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) && Keyboard.isKeyDown(15)) {
				if(Keyboard.isKeyDown(42)) {
					GChat.mc.chat.previosTab();
				} else {
					GChat.mc.chat.nextTab();
				}
			}
		}
		// GreenCubes end
	}

	public static void unPressAllKeys() {
		KeyBinding keybinding;
		for(int i = 0; i < keybindArray.size(); ++i)
			keybindArray.get(i).unpressKey();
	}

	public static void resetKeyBindingArrayAndHash() {
		hash.clear();
		KeyBinding keybinding;
		for(int i = 0; i < keybindArray.size(); ++i)
			hash.put(keybindArray.get(i).keyCode, keybindArray.get(i));
	}

	public KeyBinding(String s, int i) {
		pressTime = 0;
		keyDescription = s;
		keyCode = i;
		keybindArray.add(this);
		hash.put(i, this);
	}

	public boolean isPressed() {
		if(pressTime == 0) {
			return false;
		} else {
			pressTime--;
			return true;
		}
	}

	private void unpressKey() {
		pressTime = 0;
		pressed = false;
	}

}
