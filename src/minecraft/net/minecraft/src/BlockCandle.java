package net.minecraft.src;

import java.util.Random;

public class BlockCandle extends Block {

	public BlockCandle(int i, int j) {
		super(i, j, Material.circuits);
		setBlockBounds(0.4f, 0, 0.4f, 0.6f, 0.8f, 0.6f);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return null;
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
		return 2;
	}

	public static boolean canStayOnSide(World world, int x, int y, int z, BlockFace face) {
		if(face == BlockFace.TOP) {
			Block block = Block.blocksList[world.getBlockId(x, y - 1, z)];
			if(block == null)
				return false;
			if(block instanceof BlockFence)
				return true;
			if(!block.blockMaterial.getIsOpaque())
				return false;
			if(block.isOpaqueCube())
				return true;
			if(block instanceof BlockGreenStep)
				return ((BlockGreenStep) block).isUp;
			if(block instanceof BlockColumn)
				return true;
		}
		return false;
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, int i, int j, int k, int l) {
		return canStayOnSide(world, i, j, k, BlockFace.getFaceById(l));
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		if(!canStayOnSide(world, i, j, k, BlockFace.TOP)) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockWithNotify(i, j, k, 0);
		}
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		double d = i + 0.5F;
		double d1 = j + 0.8F;
		double d2 = k + 0.5F;
		double d3 = 0.2199999988079071D;
		double d4 = 0.27000001072883606D;
		//world.spawnParticle("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("flame", d, d1, d2, 0.0D, 0.0D, 0.0D);
	}
}
