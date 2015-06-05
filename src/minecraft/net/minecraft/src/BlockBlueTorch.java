package net.minecraft.src;

import java.util.Random;

public class BlockBlueTorch extends BlockTorch {

	public BlockBlueTorch(int i, int j) {
		super(i, j);
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if(random.nextInt(3) != 0)
			return;
		int l = world.getBlockMetadata(i, j, k);
		double d = i + 0.5F;
		double d1 = j + 0.65F;
		double d2 = k + 0.5F;
		double d3 = 0.2199999988079071D;
		double d4 = 0.27000001072883606D;
		if(l == 1) {
			world.spawnParticle("bluedust", d - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
		} else if(l == 2) {
			world.spawnParticle("bluedust", d + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
		} else if(l == 3) {
			world.spawnParticle("bluedust", d, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
		} else if(l == 4) {
			world.spawnParticle("bluedust", d, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
		} else {
			world.spawnParticle("bluedust", d, d1, d2, 0.0D, 0.0D, 0.0D);
		}
	}
}
