package net.minecraft.src;

public class BlockWhiteFence extends BlockFence {

	public BlockWhiteFence(int i, int j) {
		super(i, j);
	}

	public BlockWhiteFence(int i, int j, Material material) {
		super(i, j, material);
	}

	@Override
	public int getRenderType() {
		return 37;
	}

}
