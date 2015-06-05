package net.minecraft.src;

public class BlockLiana extends Block {

	public BlockLiana(int i, int j) {
		super(i, j, Material.vine);
	}

	@Override
	public int getBlockColor() {
		double d = 0.5D;
		double d1 = 1.0D;
		return ColorizerFoliage.getFoliageColor(d, d1);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return super.canPlaceBlockAt(world, i, j, k) && canThisPlantGrowOnThisBlockID(world.getBlockId(i, j + 1, k));
	}

	protected boolean canThisPlantGrowOnThisBlockID(int i) {
		return i == Block.blockLiana.blockID || i == Block.leaves.blockID || i == Block.blockAppleTreeLeaves.blockID || i == Block.blockAppleTreeLeavesPlayer.blockID || i == Block.blockBananaLeaves.blockID || i == Block.blockLeavesBao.blockID || i == Block.blockLeavesPalm.blockID || i == Block.blockSakuraLeaves.blockID || i == Block.blockSakuraFlowers.blockID || i == Block.blockSakuraFlowersDense.blockID;
	}

	@Override
	public int getRenderColor(int i) {
		return ColorizerFoliage.getFoliageColorBasic();
	}

	@Override
	public int colorMultiplier(IBlockAccess iblockaccess, int i, int j, int k) {
		int l = iblockaccess.getBlockMetadata(i, j, k);
		int c1 = 0;
		int c2 = 0;
		int c3 = 0;
		for(l = -2; l <= 2; l++) {
			for(int i1 = -2; i1 <= 2; i1++) {
				int j1 = iblockaccess.getBiomeAt(i + i1, j, k + l).getBiomeFoliageColor();
				c1 += (j1 & 0xff0000) >> 16;
				c2 += (j1 & 0xff00) >> 8;
				c3 += j1 & 0xff;
			}
		}
		return (c1 / 25 & 0xff) << 16 | (c2 / 25 & 0xff) << 8 | c3 / 25 & 0xff;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return null;
	}

	@Override
	public int getRenderType() {
		return 1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

}
