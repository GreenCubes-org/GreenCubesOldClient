package net.minecraft.src;

public class BlockWheat extends Block {

	public int tex1;
	public int tex2;
	
	public BlockWheat(int id, int tex1, int tex2) {
		super(id, tex1, Material.leaves);
		this.tex1 = tex1;
		this.tex2 = tex2;
	}
	
	@Override
	public int getBlockTextureFromSide(int i) {
		return i == 0 || i == 1 ? tex2 : tex1;
	}

}
