package net.minecraft.src;

public class BlockColumn extends Block implements IBlockMadeOf {

	private final Block source;
	private final int metadata;

	public BlockColumn(int i, Block source) {
		super(i, source.blockIndexInTexture, source.blockMaterial);
		setHardness(source.blockHardness);
		setResistance(source.blockResistance);
		blockGlassType = source.blockGlassType;
		setBlockBounds(0.125F, 0.0F, 0.125F, 1 - 0.125F, 1.0F, 1 - 0.125F);
		setStepSound(source.stepSound);
		this.source = source;
		this.metadata = 0;
		setRequiresSelfNotify();
	}

	public BlockColumn(int i, Block source, int metadata) {
		super(i, source.blockIndexInTexture, source.blockMaterial);
		setHardness(source.blockHardness);
		setResistance(source.blockResistance);
		blockGlassType = source.blockGlassType;
		setBlockBounds(0.125F, 0.0F, 0.125F, 1 - 0.125F, 1.0F, 1 - 0.125F);
		setStepSound(source.stepSound);
		this.source = source;
		this.metadata = metadata;
		setRequiresSelfNotify();
	}

	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0.125F, 0.0F, 0.125F, 1 - 0.125F, 1.0F, 1 - 0.125F);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		setBlockBoundsBasedOnState(world, i, j, k);
		return super.getCollisionBoundingBoxFromPool(world, i, j, k);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
		setBlockBoundsBasedOnState(world, i, j, k);
		return super.getSelectedBoundingBoxFromPool(world, i, j, k);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		int data = world.getBlockMetadata(x, y, z);
		setBlockBounds(getSideDiff(0, data), 0.0F, getSideDiff(1, data), 1 - getSideDiff(2, data), 1.0F, 1 - getSideDiff(3, data));
	}
	
	public static int getSide(BlockFace face) {
		switch(face) {
		case NORTH:
			return 0;
		case SOUTH:
			return 2;
		case EAST:
			return 1;
		case WEST:
			return 3;
		default:
			return 0;
		}
	}

	public static float getSideDiff(int side, int data) {
		data = (data >> side) & 0x11;
		if(data == 0x11)
			return 0.375F;
		if(data == 0x10)
			return 0.250F;
		if(data == 1)
			return 0;
		return 0.125F;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		return source.getBlockTextureFromSideAndMetadata(i, metadata);
	}

	@Override
	public int getBlockTextureFromSide(int i) {
		return source.getBlockTextureFromSide(i);
	}

	@Override
	public Block getBlockMadeOf() {
		return source;
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
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		if(l == 0 || l == 1) {
			int blockNearID = iblockaccess.getBlockId(i, j, k);
			Block blockNear = blocksList[blockNearID];
			if(blockNear == null)
				return true;
			if(blockNear instanceof BlockColumn) {
				if(!((BlockColumn) blockNear).source.isOpaqueCube())
					if(blockGlassType == 0 || blockNear.blockGlassType != blockGlassType)
						return true;
				int nearData = iblockaccess.getBlockMetadata(i, j, k);
				int currentData = iblockaccess.getBlockMetadata(i, j + (l == 0 ? 1 : -1), k);
				return nearData != currentData;
			}
		}
		return super.shouldSideBeRendered(iblockaccess, i, j, k, l);
	}

	private int nextData(int current) {
		switch(current) {
		case 0:
			return 16;
		case 16:
			return 17;
		case 17:
			return 1;
		case 1:
			return 0;
		}
		return 0;
	}

	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer entityplayer, int l) {
		if(world.multiplayerWorld)
			return;
		if(entityplayer.getCurrentEquippedItem() == null || entityplayer.getCurrentEquippedItem().itemID != blockID)
			return;
		int data = world.getBlockMetadata(x, y, z);
		int side = 0;
		BlockFace face = BlockFace.getDirectionOrt(entityplayer).getOpposite();
		switch(face) {
		case NORTH:
			side = 2;
			break;
		case EAST:
			side = 3;
			break;
		case SOUTH:
			side = 0;
			break;
		case WEST:
			side = 1;
			break;
		default:
			return;
		}
		int current = (data >> side) & 0x11;
		data = data & ~(0x11 << side);
		world.setBlockMetadataWithNotify(x, y, z, data | (nextData(current) << side));
	}
}
