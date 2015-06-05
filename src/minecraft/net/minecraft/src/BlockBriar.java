package net.minecraft.src;

import java.util.Random;

public class BlockBriar extends BlockFlower {

	public static int briar1 = ModLoader.addOverride("/terrain.png", "/gc_images/briar1.png");
	public static int briar2 = ModLoader.addOverride("/terrain.png", "/gc_images/briar2.png");
	public static int briar3 = ModLoader.addOverride("/terrain.png", "/gc_images/briar3.png");
	public static int briar4 = ModLoader.addOverride("/terrain.png", "/gc_images/briar4.png");
	public static int briar5 = ModLoader.addOverride("/terrain.png", "/gc_images/briar5.png");

	public boolean berry;

	public BlockBriar(int i, boolean berry) {
		super(i, briar3);
		this.berry = berry;
		float f = 0.4F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {// j - дата, i - сторона 0,1 - верх и низ.
		switch(j) {
		case 0:
			return briar1;
		case 1:
			return briar1;
		case 2:
			return briar2;
		case 3:
			return briar2;
		case 4:
			return briar2;
		case 5:
			return briar3;
		case 6:
			return briar3;
		case 7:
			return briar3;
		case 8:
			return briar3;
		case 9:
			return briar4;
		case 10:
			if(berry)
				return briar4;
			return briar5;
		case 11:
			if(berry)
				return briar4;
			return briar5;
		case 12:
			return briar5;
		case 13:
			return briar5;
		case 14:
			return briar5;
		case 15:
			return briar5;
		}
		return briar3;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		if(world.getBlockMetadata(i, j, k) >= 5) {
			entity.motionX *= 0.3;
			entity.motionZ *= 0.3;
		}
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return 0;
	}

	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer entityplayer, int face) {
		ItemStack item = entityplayer.getCurrentEquippedItem();
		if(item != null && !item.isBroken() && item.itemID == Item.shears.shiftedIndex) {
			item.damageItem(1, entityplayer);
			world.playAuxSFX(2001, x, y, z, blockID + world.getBlockMetadata(x, y, z) << 12);
			world.setBlockWithNotify(x, y, z, 0);
		}
	}
}
