// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            BlockStone, Block

public class BlockObsidian extends BlockStone {

	public BlockObsidian(int i, int j) {
		super(i, j);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return Block.obsidian.blockID;
	}
}
