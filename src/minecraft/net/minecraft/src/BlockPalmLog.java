package net.minecraft.src;

public class BlockPalmLog extends Block {

	public static int palmlog = ModLoader.addOverride("/terrain.png", "/gc_images/palmlog.png");
	public static int palmlogside = ModLoader.addOverride("/terrain.png", "/gc_images/palmlogside.png");
	public static int palmlogside1 = ModLoader.addOverride("/terrain.png", "/gc_images/palmlogside1.png");

	public BlockPalmLog(int i) {
		super(i, 10, Material.wood);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		if(j == 0) {
			if(i == 4 || i == 5)
				return palmlogside;
			if(i == 2 || i == 3)
				return palmlogside1;
			return palmlog;
		} else {
			if(i == 5 || i == 2)
				return palmlogside;
			if(i == 3 || i == 4)
				return palmlogside1;
			return palmlog;
		}
	}
}
