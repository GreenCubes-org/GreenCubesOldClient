// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            Block, Material

public class BlockStoneBrick extends Block {

	public static int circle = 213;

	public BlockStoneBrick(int i) {
		super(i, 54, Material.rock);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		switch(j) {
		default:
			return 54;

		case 1: // '\001'
			return 100;

		case 2: // '\002'
			return 101;
		case 3:
			return circle;
		}
	}

	@Override
	protected int damageDropped(int i) {
		return i;
	}
}
