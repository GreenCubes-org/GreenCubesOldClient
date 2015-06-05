package net.minecraft.src;

public class BlockStepUp extends BlockStep implements IBlockMadeOf {

	public BlockStepUp(int i) {
		super(i, false);
		setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
		setLightOpacity(0);
		setHardness(Block.stairSingle.blockHardness);
		setResistance(Block.stairSingle.blockResistance);
		setStepSound(Block.stairSingle.stepSound);
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		if(l == 0)
			return true;
		if(l == 2 || l == 3 || l == 4 || l == 5) {
			int blockType = iblockaccess.getBlockId(i, j, k);
			Block block = Block.blocksList[blockType];
			if(block == null)
				return true;
			if(block instanceof BlockStepUp)
				return false;
			if(block instanceof BlockGreenStepBananaLog)
				return true;
			if(block instanceof BlockGreenStep)
				return !((BlockGreenStep) block).isUp || !((BlockGreenStep) block).original.isOpaqueCube();
			if(block instanceof BlockStairs) {
				boolean up = iblockaccess.getBlockMetadata(i, j, k) >> 2 == 1;
				return !up || !((BlockStairs) block).modelBlock.isOpaqueCube();
			}
		}
		return super.shouldSideBeRendered(iblockaccess, i, j, k, l);
	}

	@Override
	public Block getBlockMadeOf() {
		return Block.stairSingle;
	}

	@Override
	protected void init() {
		if(Item.itemsList[blockID] == null)
			new ItemSlabUp(blockID - 256);
		super.init();
	}
}
