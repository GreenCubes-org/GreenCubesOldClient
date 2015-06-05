package net.minecraft.src;

public class BlockBananas extends Block {

	public static int stem2 = ModLoader.addOverride("/terrain.png", "/gc_images/bananasstem2.png");
	public static int green = ModLoader.addOverride("/terrain.png", "/gc_images/bananasgreen.png");
	public static int yellow = ModLoader.addOverride("/terrain.png", "/gc_images/bananasyellow.png");

	public BlockBananas(int i) {
		super(i, stem2, Material.tallGrass);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int s, int d) {
		if(d == 15)
			return yellow;
		if(d >= 11)
			return green;
		return stem2;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return null;
	}

	@Override
	public void onBlockPlaced(World world, int i, int j, int k, int l) {
		if(!(world.getBlockId(i, j + 1, k) == Block.blockBananaLeaves.blockID && world.getBlockId(i, j - 1, k) == 0)) {
			world.setBlock(i, j, k, 0);
			dropBlockAsItem_do(world, i, j, k, new ItemStack(Item.bananaGreen, 1, 0));
		}
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
