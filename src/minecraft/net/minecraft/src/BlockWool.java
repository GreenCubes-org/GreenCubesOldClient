// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            Block, Material

public class BlockWool extends Block {

	public BlockWool() {
		super(35, 64, Material.wool);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		if(j == 0) {
			return blockIndexInTexture;
		} else {
			j = ~(j & 0xf);
			return 113 + ((j & 8) >> 3) + (j & 7) * 16;
		}
	}

	@Override
	protected int damageDropped(int i) {
		return i;
	}

	public static int getBlockFromDye(int i) {
		return ~i & 0xf;
	}

	public static int getDyeFromBlock(int i) {
		return ~i & 0xf;
	}

	@Override
	protected void init() {
		if(Item.itemsList[blockID] == null)
			new ItemWool(blockID - 256);
		super.init();
	}
}
