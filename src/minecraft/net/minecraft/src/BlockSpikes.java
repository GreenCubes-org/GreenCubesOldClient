package net.minecraft.src;

public class BlockSpikes extends Block {

	public BlockSpikes(int i, int j, Material material) {
		super(i, j, material);
	}

	@Override
	public int getRenderType() {
		return 35;
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

}
