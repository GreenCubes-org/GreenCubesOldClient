// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            Block, Material, IBlockAccess, ItemStack, 
//            World

public class BlockStep extends Block {

	public static final String field_22037_a[] = {"stone", "sand", "wood", "cobble", "brick", "smoothStoneBrick"};
	private boolean blockType;

	public BlockStep(int i, boolean flag) {
		super(i, 6, Material.rock);
		blockType = flag;
		if(!flag) {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		} else {
			opaqueCubeLookup[i] = true;
		}
		setLightOpacity(255);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		if(j == 0) {
			return i > 1 ? 5 : 6;
		}
		if(j == 1) {
			if(i == 0) {
				return 208;
			}
			return i != 1 ? 192 : 176;
		}
		if(j == 2) {
			return 4;
		}
		if(j == 3) {
			return 16;
		}
		if(j == 4) {
			return Block.brick.blockIndexInTexture;
		}
		if(j == 5) {
			return Block.stoneBrick.blockIndexInTexture;
		} else {
			return 6;
		}
	}

	@Override
	public int getBlockTextureFromSide(int i) {
		return getBlockTextureFromSideAndMetadata(i, 0);
	}

	@Override
	public boolean isOpaqueCube() {
		return blockType;
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return Block.stairSingle.blockID;
	}

	@Override
	public int quantityDropped(Random random) {
		return !blockType ? 1 : 2;
	}

	@Override
	protected int damageDropped(int i) {
		return i;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return blockType;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		if(blockType || this instanceof BlockStepUp)
			return super.shouldSideBeRendered(iblockaccess, i, j, k, l);
		if(l == 1)
			return true;
		if(l == 2 || l == 3 || l == 4 || l == 5) {
			int blockType = iblockaccess.getBlockId(i, j, k);
			Block block = Block.blocksList[blockType];
			if(block == null)
				return true;
			if(block instanceof BlockStep && !(block instanceof BlockStepUp))
				return false;
			if(block instanceof BlockGreenStepBananaLog)
				return true;
			if(block instanceof BlockGreenStep)
				return ((BlockGreenStep) block).isUp || !((BlockGreenStep) block).original.isOpaqueCube();
			if(block instanceof BlockStairs) {
				boolean up = iblockaccess.getBlockMetadata(i, j, k) >> 2 == 1;
				return up || !((BlockStairs) block).modelBlock.isOpaqueCube();
			}
		}
		return super.shouldSideBeRendered(iblockaccess, i, j, k, l);
	}

	@Override
	protected ItemStack func_41049_c_(int i) {
		return new ItemStack(Block.stairSingle.blockID, 1, i);
	}

	@Override
	protected void init() {
		if(Item.itemsList[blockID] == null)
			new ItemSlab(blockID - 256);
		super.init();
	}

}
