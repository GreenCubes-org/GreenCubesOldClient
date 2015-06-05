package net.minecraft.src;

public class BlockPartsGrass extends BlockParts {

	public BlockPartsGrass(int i, Block source) {
		super(i, source);
	}

	public BlockPartsGrass(int i, Block source, int sourceMetadata) {
		super(i, source, sourceMetadata);
	}

	@Override
	public int getBlockColor() {
		return source.getBlockColor();
	}

	@Override
	public int getRenderColor(int i) {
		return source.getRenderColor(i);
	}

	@Override
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		return source.colorMultiplier(blockAccess, x, y, z);
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		return Block.blockStepGrass.getBlockTextureFromSideAndMetadata(i, 0);
	}

	@Override
	public int getBlockTextureFromSide(int i) {
		return Block.blockStepGrass.getBlockTextureFromSideAndMetadata(i, 0);
	}
}
