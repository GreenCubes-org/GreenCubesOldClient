package net.minecraft.src;

public class BlockAppleTreeLeaves extends BlockLeavesBase {

	public boolean blockType;
	public static int leaves = ModLoader.addOverride("/terrain.png", "/gc_images/appletreeleaves.png");
	public static int flower = ModLoader.addOverride("/terrain.png", "/gc_images/appletreeflower.png");
	public static int flower2 = ModLoader.addOverride("/terrain.png", "/gc_images/appletreeflower2.png");
	public static int apple = ModLoader.addOverride("/terrain.png", "/gc_images/appletreeapple.png");
	public static int apple2 = ModLoader.addOverride("/terrain.png", "/gc_images/appletreeapple2.png");
	public static int apple3 = ModLoader.addOverride("/terrain.png", "/gc_images/appletreeapple3.png");
	public static int apple4 = ModLoader.addOverride("/terrain.png", "/gc_images/appletreeapple4.png");

	protected BlockAppleTreeLeaves(int i, int j) {
		super(i, j, (new Material(MapColor.foliageColor)).setBurning().setNoPushMobility(), true);

	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		j = (j & 0xF0) >> 4;
		switch(j) {
		case 0:
			return leaves;
		case 1:
			return leaves;
		case 2:
			return flower;
		case 3:
			return flower2;
		case 4:
			return apple;
		case 5:
			return apple2;
		case 6:
			return apple3;
		case 7:
			return apple3;
		}
		return apple3;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

}
