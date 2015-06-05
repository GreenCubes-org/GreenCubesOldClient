package net.minecraft.src;

import java.util.Random;

/**
 * GreenCubes
 * @author Feyola
 *
 */
public class BlockGreenFlowers extends Block {

	public static int tulip = ModLoader.addOverride("/terrain.png", "/gc_images/tulip.png");
	public static int lavender = ModLoader.addOverride("/terrain.png", "/gc_images/lavender.png");
	public static int vasilek = ModLoader.addOverride("/terrain.png", "/gc_images/vasilek.png");
	public static int chamomile = ModLoader.addOverride("/terrain.png", "/gc_images/chamomile.png");

	protected BlockGreenFlowers(int i) {
		super(i, Material.plants);
		setTickOnLoad(true);
		float f = 0.3F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3F, 0.5F + f);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) // j - дата, i - сторона 0,1 - верх и низ.
	{
		if(j == 3) {
			return chamomile;
		}
		if(j == 2) {
			return vasilek;
		}
		if(j == 1) {
			return lavender;
		}
		if(j == 0) {
			return tulip;
		}
		return tulip;
	}

	@Override
	public int getBlockTextureFromSide(int i) {
		return getBlockTextureFromSideAndMetadata(i, 0);
	}

	@Override
	protected int damageDropped(int i) {
		return i;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return super.canPlaceBlockAt(world, i, j, k) && canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k));
	}

	protected boolean canThisPlantGrowOnThisBlockID(int i) {
		return i == Block.grass.blockID || i == Block.dirt.blockID || i == Block.tilledField.blockID;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		super.onNeighborBlockChange(world, i, j, k, l);
		checkFlowerChange(world, i, j, k);
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		checkFlowerChange(world, i, j, k);
	}

	protected final void checkFlowerChange(World world, int i, int j, int k) {
		if(!canBlockStay(world, i, j, k)) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockWithNotify(i, j, k, 0);
		}
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		return (world.getFullBlockLightValue(i, j, k) >= 8 || world.canBlockSeeTheSky(i, j, k)) && canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k));
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

	@Override
	protected void init() {
		if(Item.itemsList[blockID] == null)
			new ItemGreenFlowers(blockID - 256);
		super.init();
	}

}
