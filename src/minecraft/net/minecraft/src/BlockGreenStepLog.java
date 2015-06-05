package net.minecraft.src;

public class BlockGreenStepLog extends BlockGreenStep {

	public BlockGreenStepLog(int i, Material material, Block block, boolean up) {
		super(i, 20, block, up);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		if(i == 1 || i == 0) {
			if(j == 0)
				return 21;
			if(j == 1)
				return BlockLog.redwoodTop;
			if(j == 2)
				return BlockLog.birchTop;
			if(j == 3)
				return GreenTextures.sakuralog;
			if(j == 4)
				return BlockLog.jungleTop;
			if(j == 5)
				return GreenTextures.baologtop;
			if(j == 6)
				return GreenTextures.palmlogtop;

			return 21;
		}
		if(j == 6) {
			return GreenTextures.palmlogside;
		}
		if(j == 5) {
			return GreenTextures.baologside;
		}
		if(j == 4) {
			return BlockLog.jungleSide;
		}
		if(j == 3) {
			return GreenTextures.sakuralogside;
		}
		if(j == 2) {
			return 117;
		}
		if(j == 1) {
			return 116;
		}
		return 20;

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
	protected int damageDropped(int i) {
		return i;
	}

	@Override
	protected void init() {
		if(Item.itemsList[blockID] == null)
			new ItemGreenStepLog(blockID - 256);
		super.init();
	}
}
