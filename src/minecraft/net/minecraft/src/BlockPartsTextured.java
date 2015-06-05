package net.minecraft.src;

public class BlockPartsTextured extends BlockParts {

	public BlockPartsTextured(int i, Block source, int texture) {
		super(i, source, 0);
		this.blockIndexInTexture = texture;
	}
	
	@Override
	public int getBlockTextureFromSide(int i) {
		return this.blockIndexInTexture;
	}

}
