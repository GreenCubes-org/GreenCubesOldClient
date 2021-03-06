package net.minecraft.src;

public abstract class BlockOnWall extends Block {
	
	public BlockOnWall(int i, Material material) {
		super(i, material);
	}

	public BlockOnWall(int i, int j, Material material) {
		super(i, j, material);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return null;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	public static BlockFace getAttachedSide(int data) {
		switch(data & 7) {
		case 1:
			return BlockFace.NORTH;
		case 2:
			return BlockFace.SOUTH;
		case 3:
			return BlockFace.EAST;
		case 4:
			return BlockFace.WEST;
		case 5:
			return BlockFace.TOP;
		case 6:
			return BlockFace.DOWN;
		}
		return BlockFace.NORTH; // NPE workaround
	}
	
	public static int getData(BlockFace side) {
		switch(side) {
		case NORTH:
			return 1;
		case SOUTH:
			return 2;
		case EAST:
			return 3;
		case WEST:
			return 4;
		case TOP:
			return 5;
		case DOWN:
			return 6;
		default:
			return 1;
		}
	}
}
