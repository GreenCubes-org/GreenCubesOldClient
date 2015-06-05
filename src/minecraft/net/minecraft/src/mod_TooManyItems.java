package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class mod_TooManyItems extends BaseMod {
	public static final String COPYRIGHT = "All of TooManyItems except for thesmall portion excerpted from the original Minecraft game is copyright 2011Marglyph. TooManyItems is free for personal use only. Do not redistributeTooManyItems, including in mod packs, and do not use TooManyItems' sourcecode or graphics in your own mods.";
	private long lastInvUpdate = 0L;

	public mod_TooManyItems() {
		TMIConfig.isModloaderEnabled = true;
		ModLoader.SetInGameHook(this, true, true);
		TMIUtils.loadPreferences(TMIConfig.getInstance());
	}

	public boolean onTickInGame(float paramFloat, Minecraft paramMinecraft) {
		TMIUtils.replaceCustomItems();

		long l = System.currentTimeMillis();
		if(l - this.lastInvUpdate > 250L) {
			TMIUtils.updateUnlimitedItems();
			this.lastInvUpdate = l;
		}
		return true;
	}

	public String Version() {
		return getVersion();
	}

	@Override
	public String getVersion() {
		return "12w17a 2012-04-26";
	}

	@Override
	public void load() {
	}
}