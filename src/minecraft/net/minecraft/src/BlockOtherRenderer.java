package net.minecraft.src;

public class BlockOtherRenderer extends Block {

	protected final int renderType;

	protected BlockOtherRenderer(int i, int j, Material material, int renderer) {
		super(i, j, material);
		this.renderType = renderer;
	}

	@Override
	public int getRenderType() {
		return renderType;
	}

}
