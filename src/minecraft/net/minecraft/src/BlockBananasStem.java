package net.minecraft.src;

public class BlockBananasStem extends Block {

	public static int stem = ModLoader.addOverride("/terrain.png", "/gc_images/bananasstem.png");

	public BlockBananasStem(int i) {
		super(i, stem, Material.plants);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int s, int d) {
		return stem;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
		return null;
	}

	@Override
	public boolean canCollideCheck(int i, boolean flag) {
		return false;
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
		return 1;
	}

}
