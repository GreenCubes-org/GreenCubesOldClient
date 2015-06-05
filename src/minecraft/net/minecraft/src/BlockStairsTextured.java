package net.minecraft.src;

public class BlockStairsTextured extends BlockStairs {

	private int t;

	public BlockStairsTextured(int i, Block block, int te) {
		super(i, block);
		t = te;
	}

	@Override
	public int getBlockTextureFromSide(int i) {
		return t;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		return t;
	}
}
