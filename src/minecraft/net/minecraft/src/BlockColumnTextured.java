package net.minecraft.src;

public class BlockColumnTextured extends BlockColumn {

	private int texSide;
	private int texTop;

	public BlockColumnTextured(int i, Block source, int top, int side) {
		super(i, source);
		this.texSide = side;
		this.texTop = top;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		if(i == 0 || i == 1)
			return texTop;
		return texSide;
	}

	@Override
	public int getBlockTextureFromSide(int i) {
		if(i == 0 || i == 1)
			return texTop;
		return texSide;
	}

}
