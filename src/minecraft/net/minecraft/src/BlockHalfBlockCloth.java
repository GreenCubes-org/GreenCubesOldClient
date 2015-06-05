package net.minecraft.src;

public class BlockHalfBlockCloth extends BlockHalfBlock {

	private final int texture;

	public BlockHalfBlockCloth(int i, int data) {
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
}