package net.minecraft.src;

public class BlockHalfBlockWheat extends BlockHalfBlock {

	public int tex1;
	public int tex2;
	
	public BlockHalfBlockWheat(int i, int tex1, int tex2, Block original) {
		super(i, original);
		this.tex1 = tex1;
		this.tex2 = tex2;
	}

	@Override
	public int getBlockTextureFromSide(int i) {
		return i == 0 || i == 1 ? tex2 : tex1;
	}

}
