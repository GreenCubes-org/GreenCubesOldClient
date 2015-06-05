package net.minecraft.src;

public class BlockPlanks extends Block {

	public BlockPlanks(int id) {
		super(id, 4, Material.wood);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int s, int d) {
		if(d == 0)
			return 4;
		if(d == 1)
			return GreenTextures.jungleplanks;
		if(d == 2)
			return GreenTextures.baoplanks;
		if(d == 3)
			return GreenTextures.palmplanks;
		return 4;
	}
}
