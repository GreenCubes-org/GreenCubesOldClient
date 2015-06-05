package net.minecraft.src;

public class BlockStepWool extends Block implements IBlockMadeOf {

	public final boolean isUp;

	public BlockStepWool(int i, Material material, boolean up) {
		super(i, 64, material);
		if(up)
			setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
		else
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		opaqueCubeLookup[i] = false;
		setLightOpacity(0);
		isUp = up;
		setHardness(Block.wool.blockHardness);
		setResistance(Block.wool.blockResistance);
		setStepSound(Block.wool.stepSound);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		if(j == 0) {
			return 64;
		} else {
			j = ~(j & 0xf);
			return 113 + ((j & 8) >> 3) + (j & 7) * 16;
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	protected int damageDropped(int i) {
		return i;
	}

	@Override
	public Block getBlockMadeOf() {
		return Block.wool;
	}

	@Override
	protected void init() {
		if(Item.itemsList[blockID] == null)
			new ItemStepWool(blockID - 256);
		super.init();
	}
}