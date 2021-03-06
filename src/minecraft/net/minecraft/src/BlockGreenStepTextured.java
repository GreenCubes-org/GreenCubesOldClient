package net.minecraft.src;

public class BlockGreenStepTextured extends BlockGreenStep {

	public final int sideTexture;

	public BlockGreenStepTextured(int i, int upTexture, int sideTexture, Block original, boolean up) {
		super(i, upTexture, original, up);
		this.sideTexture = sideTexture;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		if(l == 0 || l == 1)
			return true;
		int i1 = iblockaccess.getBlockId(i, j, k);
		if(i1 == blockID) {
			return false;
		} else {
			return super.shouldSideBeRendered(iblockaccess, i, j, k, l);
		}
	}
	
	@Override
	public int getBlockTextureFromSide(int i) {
		return i == 0 || i == 1 ? this.blockIndexInTexture : sideTexture;
	}
}
