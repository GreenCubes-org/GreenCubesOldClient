package net.minecraft.src;

public class BlockHalfBlockGrass extends BlockHalfBlock {

	public BlockHalfBlockGrass(int i, Block original) {
		super(i, original);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		if(i == 1) {
			return 0;
		}
		return 3;
	}

	@Override
	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		if(l == 1) {
			return 0;
		}
		if(l == 0) {
			return 2;
		}
		Material material = iblockaccess.getBlockMaterial(i, j + 1, k);
		return material != Material.snow && material != Material.craftedSnow ? 3 : 68;
	}

	@Override
	public int getBlockColor() {
		double d = 0.5D;
		double d1 = 1.0D;
		return ColorizerGrass.getGrassColor(d, d1);
	}

	@Override
	public int getRenderColor(int i) {
		return getBlockColor();
	}

	@Override
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		int i = 0;
		int j = 0;
		int k = 0;
		for(int l = -2; l <= 2; l++) {
			for(int i1 = -2; i1 <= 2; i1++) {
				int j1 = par1IBlockAccess.getBiomeAt(par2 + i1, par3, par4 + l).getBiomeGrassColor();
				i += (j1 & 0xff0000) >> 16;
				j += (j1 & 0xff00) >> 8;
				k += j1 & 0xff;
			}
		}
		return (i / 25 & 0xff) << 16 | (j / 25 & 0xff) << 8 | k / 25 & 0xff;
	}
}
