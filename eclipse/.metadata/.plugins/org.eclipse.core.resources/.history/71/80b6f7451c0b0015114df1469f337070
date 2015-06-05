package net.minecraft.src;

public class BlockHalfBlockTextured extends BlockHalfBlock {

	public final int side;
	public final int up;
	public final int tex;

	public BlockHalfBlockTextured(int i, int upTexture, int sideTexture, Block original) {
		super(i, original);
		this.side = sideTexture;
		this.up = upTexture;
		this.tex = original.blockIndexInTexture;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		j = j & 3;
		if(j == 2 || j == 3) {
			if(i == 0 || i == 1)
				return up;
			if(i == 5 || i == 4)
				return side;
			return tex;
		}
		if(j == 0 || j == 1) {
			if(i == 0 || i == 1)
				return side;
			if(i == 5 || i == 4)
				return tex;
			return side;
		}
		return tex;
	}

}
