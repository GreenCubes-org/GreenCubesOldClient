// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            Block, Material, AxisAlignedBB, IBlockAccess, 
//            World

public class BlockFence extends Block {

	public BlockFence(int i, int j) {
		super(i, j, Material.wood);
	}

	public BlockFence(int i, int j, Material material) {
		super(i, j, material);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return super.canPlaceBlockAt(world, i, j, k);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		boolean flag = canAttachTo(world, i, j, k - 1);
		boolean flag1 = canAttachTo(world, i, j, k + 1);
		boolean flag2 = canAttachTo(world, i - 1, j, k);
		boolean flag3 = canAttachTo(world, i + 1, j, k);
		float f = 0.375F;
		float f1 = 0.625F;
		float f2 = 0.375F;
		float f3 = 0.625F;
		if(flag) {
			f2 = 0.0F;
		}
		if(flag1) {
			f3 = 1.0F;
		}
		if(flag2) {
			f = 0.0F;
		}
		if(flag3) {
			f1 = 1.0F;
		}
		return AxisAlignedBB.getBoundingBoxFromPool(i + f, j, k + f2, i + f1, j + 1.5F, k + f3);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k) {
		boolean flag = canAttachTo(iblockaccess, i, j, k - 1);
		boolean flag1 = canAttachTo(iblockaccess, i, j, k + 1);
		boolean flag2 = canAttachTo(iblockaccess, i - 1, j, k);
		boolean flag3 = canAttachTo(iblockaccess, i + 1, j, k);
		float f = 0.375F;
		float f1 = 0.625F;
		float f2 = 0.375F;
		float f3 = 0.625F;
		if(flag) {
			f2 = 0.0F;
		}
		if(flag1) {
			f3 = 1.0F;
		}
		if(flag2) {
			f = 0.0F;
		}
		if(flag3) {
			f1 = 1.0F;
		}
		setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return 11;
	}

	public boolean canAttachTo(IBlockAccess iblockaccess, int i, int j, int k) {
		int l = iblockaccess.getBlockId(i, j, k);
		if(l == blockID || (blocksList[l] != null && blocksList[l] instanceof BlockFenceGate))
			return true;
		Block block = Block.blocksList[l];
		if(block != null && block.blockMaterial.getIsOpaque() && block.renderAsNormalBlock())
			return block.blockMaterial != Material.pumpkin;
		else
			return false;
	}
}
