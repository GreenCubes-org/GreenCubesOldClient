// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            BlockBreakable, Material

public class BlockGlass extends BlockBreakable {

	public BlockGlass(int i, int j, Material material, boolean flag) {
		super(i, j, material, flag);
		blockGlassType = 1;
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	public int getRenderBlockPass() {
		return 0;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		int i1 = iblockaccess.getBlockId(i, j, k);
		if(i1 == blockID || (Block.blocksList[i1] != null && Block.blocksList[i1] instanceof BlockGlass)) {
			return false;
		} else {
			return super.shouldSideBeRendered(iblockaccess, i, j, k, l);
		}
	}
}
