package net.minecraft.src;

public class BlockGreenStep extends Block implements IBlockMadeOf {

	public final boolean isUp;
	public final Block original;

	public BlockGreenStep(int i, int j, Block original, boolean up) {
		super(i, j, original.blockMaterial);
		this.original = original;
		this.blockGlassType = original.blockGlassType;
		isUp = up;
		if(up)
			setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
		else
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		opaqueCubeLookup[i] = false;
		setLightOpacity(0);
		setHardness(original.blockHardness);
		setResistance(original.blockResistance);
		setStepSound(original.stepSound);
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
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		if(l == 0 && isUp)
			return true;
		if(l == 1 && !isUp)
			return true;
		if(l == 2 || l == 3 || l == 4 || l == 5) {
			int blockType = iblockaccess.getBlockId(i, j, k);
			Block block = Block.blocksList[blockType];
			if(block == null)
				return true;
			if(block instanceof BlockStepUp)
				return !isUp;
			if(block instanceof BlockStep)
				return isUp;
			if(block instanceof BlockGreenStepBananaLog)
				return true;
			if(block instanceof BlockGreenStep) {
				if(isUp != ((BlockGreenStep) block).isUp)
					return true;
				if(((BlockGreenStep) block).original.isOpaqueCube())
					return false;
				if(blockGlassType > 0 && block.blockGlassType == blockGlassType)
					return false;
				return true;
			}
			if(block instanceof BlockStairs) {
				if(!((BlockStairs) block).modelBlock.isOpaqueCube())
					if(blockGlassType == 0 || block.blockGlassType != blockGlassType)
						return true;
				boolean up = iblockaccess.getBlockMetadata(i, j, k) >> 2 == 1;
				return up != isUp;
			}

		}
		return super.shouldSideBeRendered(iblockaccess, i, j, k, l);
	}

	@Override
	public Block getBlockMadeOf() {
		return original;
	}

	@Override
	protected void init() {
		if(isUp && Item.itemsList[blockID] == null)
			new ItemGreenStepUp(blockID - 256);
		super.init();
	}

}
