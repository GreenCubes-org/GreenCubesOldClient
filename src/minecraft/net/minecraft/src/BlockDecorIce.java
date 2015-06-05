package net.minecraft.src;

import java.util.Random;

public class BlockDecorIce extends BlockIce implements IBlockMadeOf {

	public BlockDecorIce(int i, int j) {
		super(i, j);
		setTickOnLoad(false);
	}
	
	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
	}

	@Override
	public Block getBlockMadeOf() {
		return Block.ice;
	}

}
