package net.minecraft.src;

import java.util.Random;

public class BlockPortalSpecial extends BlockPortal {

	public BlockPortalSpecial(int i, int j) {
		super(i, j);
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		entity.motionX *= 0.1;
		entity.motionZ *= 0.1;
		entity.motionY *= 0.1;
	}
}
