package net.minecraft.src;

import java.util.EnumMap;
import java.util.Map;

import org.greencubes.util.AbstractFace;

public enum BlockFace implements AbstractFace {

	NORTH(-1, 0, 0), EAST(0, 0, -1), SOUTH(1, 0, 0), WEST(0, 0, 1), TOP(0, 1, 0), DOWN(0, -1, 0), NORTH_EAST(NORTH, EAST), NORTH_WEST(NORTH, WEST), SOUTH_EAST(SOUTH, EAST), SOUTH_WEST(SOUTH, WEST), SELF(0, 0, 0);

	private final int modX;
	private final int modY;
	private final int modZ;
	private final boolean ortogonal;

	private static final Map<BlockFace, BlockFace> opposite = new EnumMap<BlockFace, BlockFace>(BlockFace.class);
	private static final BlockFace[] roundFaces = new BlockFace[]{NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST};

	public static final BlockFace[] sideFaces = new BlockFace[]{NORTH, EAST, SOUTH, WEST};

	private BlockFace(int modX, int modY, int modZ) {
		this.modX = modX;
		this.modY = modY;
		this.modZ = modZ;
		ortogonal = true;
	}

	private BlockFace(BlockFace face1, BlockFace face2) {
		this.modX = face1.getModX() + face2.getModX();
		this.modY = face1.getModY() + face2.getModY();
		this.modZ = face1.getModZ() + face2.getModZ();
		ortogonal = false;
	}

	public static BlockFace getBlockFaceByMod(int x, int y, int z) {
		for(BlockFace face : BlockFace.values())
			if(face.modX == x && face.modY == y && face.modZ == z)
				return face;
		return null;
	}

	public static BlockFace getOrtoBlockFaceByMod(int x, int z) {
		for(BlockFace face : BlockFace.values())
			if(face.ortogonal && face.modX == x && face.modZ == z)
				return face;
		return null;
	}

	public boolean ort() {
		return ortogonal;
	}

	@Override
	public int getModX() {
		return this.modX;
	}

	@Override
	public int getModY() {
		return this.modY;
	}

	@Override
	public int getModZ() {
		return this.modZ;
	}

	public BlockFace getOpposite() {
		return opposite.get(this);
	}

	public static BlockFace getFaceById(int face) {
		switch(face) {
		case 0:
			return DOWN;
		case 1:
			return TOP;
		case 2:
			return EAST;
		case 3:
			return WEST;
		case 4:
			return NORTH;
		case 5:
			return SOUTH;
		default:
			return SELF;
		}
	}

	public int getId() {
		return getFaceId(this);
	}

	public static int getFaceId(BlockFace face) {
		switch(face) {
		case DOWN:
			return 0;
		case TOP:
			return 1;
		case EAST:
			return 2;
		case WEST:
			return 3;
		case NORTH:
			return 4;
		case SOUTH:
			return 5;
		case SELF:
		default:
			return -1;
		}
	}

	public static BlockFace getDirection(Entity entity) {
		float rot = (entity.rotationYaw - 90.0F) % 360.0F;
		if(rot < 0.0F)
			rot += 360.0F;
		if(0.0D <= rot && rot < 22.5D)
			return NORTH;
		if(22.5D <= rot && rot < 67.5D)
			return NORTH_EAST;
		if(67.5D <= rot && rot < 112.5D)
			return EAST;
		if(112.5D <= rot && rot < 157.5D)
			return SOUTH_EAST;
		if(157.5D <= rot && rot < 202.5D)
			return SOUTH;
		if(202.5D <= rot && rot < 247.5D)
			return SOUTH_WEST;
		if(247.5D <= rot && rot < 292.5D)
			return WEST;
		if(292.5D <= rot && rot < 337.5D)
			return NORTH_WEST;
		if(337.5D <= rot && rot < 360.0D)
			return NORTH;
		return null;
	}

	public static BlockFace getDirectionOrt(Entity entity) {
		float rot = (entity.rotationYaw - 90.0F) % 360.0F;
		if(rot < 0.0F)
			rot += 360.0F;
		if(0.0D <= rot && rot < 55D)
			return NORTH;
		if(55D <= rot && rot < 130D)
			return EAST;
		if(130D <= rot && rot < 225D)
			return SOUTH;
		if(225D <= rot && rot < 315D)
			return WEST;
		if(315D <= rot && rot < 360.0D)
			return NORTH;
		return null;
	}

	public static BlockFace simpleDirectionToFace(int data) {
		switch(data) {
		case 0:
			return BlockFace.WEST;
		case 1:
			return BlockFace.NORTH;
		case 2:
			return BlockFace.EAST;
		case 3:
			return BlockFace.SOUTH;
		}
		return null;
	}

	public BlockFace clockwise() {
		return clockwise(this);
	}

	public BlockFace counterClockwise() {
		return counterClockwise(this);
	}

	public static BlockFace[] getRoundFaces() {
		return roundFaces;
	}

	public static BlockFace clockwise(BlockFace face) {
		switch(face) {
		case NORTH:
			return NORTH_EAST;
		case NORTH_EAST:
			return EAST;
		case EAST:
			return SOUTH_EAST;
		case SOUTH_EAST:
			return SOUTH;
		case SOUTH:
			return SOUTH_WEST;
		case SOUTH_WEST:
			return WEST;
		case WEST:
			return NORTH_WEST;
		case NORTH_WEST:
			return NORTH;
		case TOP:
			return TOP;
		case DOWN:
			return DOWN;
		case SELF:
			return SELF;
		}
		return null;
	}

	public static BlockFace counterClockwise(BlockFace face) {
		switch(face) {
		case NORTH:
			return NORTH_WEST;
		case NORTH_WEST:
			return WEST;
		case WEST:
			return SOUTH_WEST;
		case SOUTH_WEST:
			return SOUTH;
		case SOUTH:
			return SOUTH_EAST;
		case SOUTH_EAST:
			return EAST;
		case EAST:
			return NORTH_EAST;
		case NORTH_EAST:
			return NORTH;
		case TOP:
			return TOP;
		case DOWN:
			return DOWN;
		case SELF:
			return SELF;
		}
		return null;
	}

	static {
		opposite.put(WEST, EAST);
		opposite.put(EAST, WEST);
		opposite.put(SOUTH, NORTH);
		opposite.put(NORTH, SOUTH);
		opposite.put(TOP, DOWN);
		opposite.put(DOWN, TOP);
		opposite.put(SELF, SELF);
		opposite.put(NORTH_EAST, SOUTH_WEST);
		opposite.put(SOUTH_WEST, NORTH_EAST);
		opposite.put(NORTH_WEST, SOUTH_EAST);
		opposite.put(SOUTH_EAST, NORTH_WEST);
	}
}
