package net.minecraft.src;

import java.util.Random;

public class BlockSakuraFlowers extends BlockLeavesBase {

	public BlockSakuraFlowers(int i, int j) {
		super(i, j, Material.leaves, true);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if(random.nextInt(32) != 0)
			return;
		if(!world.isAirBlock(i, j - 1, k))
			return;
		int l = world.getBlockMetadata(i, j, k);
		if((l & 0x4) == 0) {
			double d = i + random.nextFloat();
			double d1 = j + 0.0F;
			double d2 = k + random.nextFloat();
			world.spawnParticle("sakura", d, d1, d2, 0.0D, 0.0D, 0.0D);
		}
	}
}
