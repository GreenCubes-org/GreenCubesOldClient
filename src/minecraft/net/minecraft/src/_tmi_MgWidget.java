package net.minecraft.src;

public class _tmi_MgWidget {
	public static final String COPYRIGHT = "All of TooManyItems except for thesmall portion excerpted from the original Minecraft game is copyright 2011Marglyph. TooManyItems is free for personal use only. Do not redistributeTooManyItems, including in mod packs, and do not use TooManyItems' sourcecode or graphics in your own mods.";
	public int x;
	public int y;
	public int z;
	public int width;
	public int height;
	public boolean show;
	public boolean mouseOver;

	public _tmi_MgWidget(int i, int j) {
		this(i, j, 0, 0, 0);
	}

	public _tmi_MgWidget(int i, int j, int k) {
		this(i, j, k, 0, 0);
	}

	public _tmi_MgWidget(int i, int j, int k, int l, int i1) {
		show = true;
		mouseOver = false;
		x = i;
		y = j;
		z = k;
		width = l;
		height = i1;
	}

	public static _tmi_MgZOrder getComparator() {
		return new _tmi_MgZOrder();
	}

	public void draw(_tmi_MgCanvas _ptmi_mgcanvas, int i, int j) {
	}

	public boolean click(int i, int j, int k) {
		return true;
	}

	public boolean contains(int i, int j) {
		return show && i >= x && i <= x + width && j >= y && j <= y + height;
	}

	public void position(int i, int j, int k, int l, int i1) {
		x = i;
		y = j;
		z = k;
		width = l;
		height = i1;
		resize();
	}

	public void resize() {
	}
}
