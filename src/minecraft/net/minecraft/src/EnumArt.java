// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

public enum EnumArt {
	Kebab("Kebab", 16, 16, 0, 0, "/art/kz.png"), Aztec("Aztec", 16, 16, 16, 0, "/art/kz.png"), Alban("Alban", 16, 16, 32, 0, "/art/kz.png"), Aztec2("Aztec2", 16, 16, 48, 0, "/art/kz.png"), Bomb("Bomb", 16, 16, 64, 0, "/art/kz.png"), Plant("Plant", 16, 16, 80, 0, "/art/kz.png"), Wasteland("Wasteland", 16, 16, 96, 0, "/art/kz.png"), Pool("Pool", 32, 16, 0, 32, "/art/kz.png"), Courbet("Courbet", 32, 16, 32, 32, "/art/kz.png"), Sea("Sea", 32, 16, 64, 32, "/art/kz.png"), Sunset("Sunset", 32, 16, 96, 32, "/art/kz.png"), Creebet("Creebet", 32, 16, 128, 32, "/art/kz.png"), Wanderer("Wanderer", 16, 32, 0, 64, "/art/kz.png"), Graham("Graham", 16, 32, 16, 64, "/art/kz.png"), Match("Match", 32, 32, 0, 128, "/art/kz.png"), Bust("Bust", 32, 32, 32, 128, "/art/kz.png"), Stage("Stage", 32, 32, 64, 128, "/art/kz.png"), Void("Void", 32, 32, 96, 128, "/art/kz.png"), SkullAndRoses("SkullAndRoses", 32, 32, 128, 128, "/art/kz.png"), Fighters("Fighters", 64, 32, 0, 96, "/art/kz.png"), Pointer("Pointer", 64, 64, 0, 192, "/art/kz.png"), Pigscene("Pigscene", 64, 64, 64, 192, "/art/kz.png"), BurningSkull("BurningSkull", 64, 64, 128, 192, "/art/kz.png"), Skeleton("Skeleton", 64, 48, 192, 64, "/art/kz.png"), DonkeyKong("DonkeyKong", 64, 48, 192, 112, "/art/kz.png"),

	MK_1("MK_1", 64, 64, 0, 0, "/art/mk.png"), MK_2("MK_2", 64, 64, 64, 0, "/art/mk.png"), MK_3("MK_3", 64, 48, 128, 0, "/art/mk.png"), MK_4("MK_4", 64, 32, 0, 64, "/art/mk.png"), MK_5("MK_5", 16, 16, 64, 64, "/art/mk.png"), MK_6("MK_6", 16, 16, 80, 64, "/art/mk.png"), MK_7("MK_7", 16, 16, 96, 64, "/art/mk.png"), MK_8("MK_8", 32, 16, 64, 80, "/art/mk.png"), MK_9("MK_9", 32, 16, 64, 96, "/art/mk.png"), MK_10("MK_10", 16, 32, 0, 96, "/art/mk.png"), MK_11("MK_11", 16, 32, 16, 96, "/art/mk.png"), MK_12("MK_12", 32, 32, 32, 96, "/art/mk.png"), ;

	public static final int maxArtTitleLength = "SkullAndRoses".length();
	public final String title;
	public final int sizeX;
	public final int sizeY;
	public final int offsetX;
	public final int offsetY;
	public final String texture;

	private EnumArt(String s1, int j, int k, int l, int i1, String file) {
		title = s1;
		sizeX = j;
		sizeY = k;
		offsetX = l;
		offsetY = i1;
		this.texture = file;
	}
}
