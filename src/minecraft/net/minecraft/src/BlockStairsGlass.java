package net.minecraft.src;

public class BlockStairsGlass extends BlockStairs {

	public BlockStairsGlass(int i, Block block) {
		super(i, block);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		j &= 7;
		if(i == 1) {
			if(j == 0 || j == 1)
				return GreenTextures.glassHalfTexture;
			return GreenTextures.glassStepTexture;
		}
		return super.getBlockTextureFromSideAndMetadata(i, j);
	}

}
