package net.minecraft.src;

import java.util.Comparator;

public class _tmi_MgZOrder implements Comparator {
	public static final String COPYRIGHT = "All of TooManyItems except for thesmall portion excerpted from the original Minecraft game is copyright 2011Marglyph. TooManyItems is free for personal use only. Do not redistributeTooManyItems, including in mod packs, and do not use TooManyItems' sourcecode or graphics in your own mods.";

	public _tmi_MgZOrder() {
	}

	public int compare(_tmi_MgWidget _ptmi_mgwidget, _tmi_MgWidget _ptmi_mgwidget1) {
		return _ptmi_mgwidget.z <= _ptmi_mgwidget1.z ? _ptmi_mgwidget.z >= _ptmi_mgwidget1.z ? 0 : -1 : 1;
	}

	@Override
	public int compare(Object obj, Object obj1) {
		return compare((_tmi_MgWidget) obj, (_tmi_MgWidget) obj1);
	}
}
