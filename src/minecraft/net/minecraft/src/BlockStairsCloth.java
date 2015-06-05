package net.minecraft.src;

public class BlockStairsCloth extends BlockStairs {

	public int texture;

	public BlockStairsCloth(int i, int data) {
		super(i, Block.wool);
		if(data == 0) {
			texture = 64;
		} else {
			data = ~(data & 0xf);
			texture = 113 + ((data & 8) >> 3) + (data & 7) * 16;
		}
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		return texture;
	}

	@Override
	public int getBlockTextureFromSide(int i) {
		return texture;
	}
}
