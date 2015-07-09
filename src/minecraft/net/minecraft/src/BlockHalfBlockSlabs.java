package net.minecraft.src;

public class BlockHalfBlockSlabs extends BlockHalfBlock {

	public BlockHalfBlockSlabs(int i, Block original) {
		super(i, original);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		if(j == 2 || j == 3) {
			if(i == 0 || i == 1)
				return 5;
			if(i == 5 || i == 4)
				return GreenTextures.stoneSlabHalfTexture;
			return 6;
		}
		if(j == 0 || j == 1) {
			if(i == 0 || i == 1)
				return GreenTextures.stoneSlabHalfTexture;
			if(i == 5 || i == 4)
				return 6;
			return GreenTextures.stoneSlabHalfTexture;
		}
		return 6;
	}
}
