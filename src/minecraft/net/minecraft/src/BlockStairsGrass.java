package net.minecraft.src;

public class BlockStairsGrass extends BlockStairs {

	public BlockStairsGrass(int i, Block block) {
		super(i, block);
	}

	public BlockStairsGrass(int i, Block block, int data) {
		super(i, block, data);
	}
	
	@Override
	public int getBlockColor() {
		return modelBlock.getBlockColor();
	}

	@Override
	public int getRenderColor(int i) {
		return modelBlock.getRenderColor(i);
	}

	@Override
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		return modelBlock.colorMultiplier(blockAccess, x, y, z);
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		return Block.blockStepGrass.getBlockTextureFromSideAndMetadata(i, data);
	}

	@Override
	public int getBlockTextureFromSide(int i) {
		return Block.blockStepGrass.getBlockTextureFromSideAndMetadata(i, data);
	}
}
