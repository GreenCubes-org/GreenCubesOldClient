package net.minecraft.src;

public class BlockWheatStack extends Block {

	public static int up = ModLoader.addOverride("/terrain.png", "/gc_images/wheatstacktop.png");
	public static int side = ModLoader.addOverride("/terrain.png", "/gc_images/wheatstackside.png");

	public BlockWheatStack(int i) {
		super(i, Material.leaves);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		//return AxisAlignedBB.getBoundingBoxFromPool(i + 0.0625, j, k + 0.0625, i + 1 - 0.0625, j + 0.4, k + 1 - 0.0625);
		return null;

	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {// j - дата, i - сторона 0,1 - верх и низ.
		if(i == 1 || i == 0)
			return up;
		return side;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		entity.motionX *= 0.05;
		entity.motionZ *= 0.05;
		entity.motionY *= 0.3;
	}
}
