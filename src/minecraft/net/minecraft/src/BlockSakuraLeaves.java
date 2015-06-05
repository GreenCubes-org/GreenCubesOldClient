package net.minecraft.src;

public class BlockSakuraLeaves extends BlockLeavesBase {

	public BlockSakuraLeaves(int i, int j) {
		super(i, j, Material.leaves, true);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		return blockIndexInTexture;
	}

	@Override
	public int getRenderColor(int i) {
		return ColorizerFoliage.getFoliageColorPine();
	}

	@Override
	public int colorMultiplier(IBlockAccess iblockaccess, int i, int j, int k) {
		return ColorizerFoliage.getFoliageColorPine();
	}
}
