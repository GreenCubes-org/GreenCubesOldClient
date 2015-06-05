package net.minecraft.src;

public class BlockHalfBlockLog extends BlockHalfBlock {

	public static int oaklogSlice = ModLoader.addOverride("/terrain.png", "/gc_images/oaklogslice.png");
	public static int redwoodSlice = ModLoader.addOverride("/terrain.png", "/gc_images/redwoodlogslice.png");
	public static int birchSlice = ModLoader.addOverride("/terrain.png", "/gc_images/birchlogslice.png");
	public static int jungleSlice = ModLoader.addOverride("/terrain.png", "/gc_images/junglelogslice.png");
	private int type;

	public BlockHalfBlockLog(int i, Material material, int type) {
		super(i, Block.wood);
		this.type = type;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {// j - дата, i - сторона 0,1 - верх и низ.
		if(i == 0 || i == 1) {
			if(type == 0)
				return 21;
			if(type == 1)
				return BlockLog.redwoodTop;
			if(type == 2)
				return BlockLog.birchTop;
			if(type == 3)
				return BlockLog.jungleTop;
			if(type == 4)
				return GreenTextures.sakuralog;
			if(type == 5)
				return GreenTextures.baologtop;
			if(type == 6)
				return GreenTextures.palmlogtop;
		}
		if(j == 0 && i == 4) {
			if(type == 0)
				return oaklogSlice;
			if(type == 1)
				return redwoodSlice;
			if(type == 2)
				return birchSlice;
			if(type == 3)
				return jungleSlice;
			if(type == 4)
				return GreenTextures.sakuralogslice;
			if(type == 5)
				return GreenTextures.baologslice;
			if(type == 6)
				return GreenTextures.palmlogslice;
		}
		if(j == 1 && i == 5) {
			if(type == 0)
				return oaklogSlice;
			if(type == 1)
				return redwoodSlice;
			if(type == 2)
				return birchSlice;
			if(type == 3)
				return jungleSlice;
			if(type == 4)
				return GreenTextures.sakuralogslice;
			if(type == 5)
				return GreenTextures.baologslice;
			if(type == 6)
				return GreenTextures.palmlogslice;
		}
		if(j == 2 && i == 2) {
			if(type == 0)
				return oaklogSlice;
			if(type == 1)
				return redwoodSlice;
			if(type == 2)
				return birchSlice;
			if(type == 3)
				return jungleSlice;
			if(type == 4)
				return GreenTextures.sakuralogslice;
			if(type == 5)
				return GreenTextures.baologslice;
			if(type == 6)
				return GreenTextures.palmlogslice;
		}
		if(j == 3 && i == 3) {
			if(type == 0)
				return oaklogSlice;
			if(type == 1)
				return redwoodSlice;
			if(type == 2)
				return birchSlice;
			if(type == 3)
				return jungleSlice;
			if(type == 4)
				return GreenTextures.sakuralogslice;
			if(type == 5)
				return GreenTextures.baologslice;
			if(type == 6)
				return GreenTextures.palmlogslice;
		}
		if(type == 0)
			return 20;
		if(type == 1)
			return 116;
		if(type == 2)
			return 117;
		if(type == 3)
			return BlockLog.jungleSide;
		if(type == 4)
			return GreenTextures.sakuralogside;
		if(type == 5)
			return GreenTextures.baologside;
		if(type == 6)
			return GreenTextures.palmlogside;
		return 20;
	}
}
