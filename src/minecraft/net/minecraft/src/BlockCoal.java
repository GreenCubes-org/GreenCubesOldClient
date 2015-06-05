package net.minecraft.src;

public class BlockCoal extends Block {

	public static int coal1 = ModLoader.addOverride("/terrain.png", "/gc_images/coalblock1.png");
	public static int coal2 = ModLoader.addOverride("/terrain.png", "/gc_images/coalblock2.png");

	public BlockCoal(int i) {
		super(i, coal1, Material.rock);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) // j - дата, i - сторона 0,1 - верх и низ.
	{
		switch(j) {
		case 0:
			return coal1;
		case 1:
			return coal2;
		}
		return coal1;
	}
}
