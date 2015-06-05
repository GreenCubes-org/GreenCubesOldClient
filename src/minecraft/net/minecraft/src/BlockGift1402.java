package net.minecraft.src;

public class BlockGift1402 extends Block {

	public static int top = ModLoader.addOverride("/terrain.png", "/gc_images/gift1402top.png");
	public static int bottom = ModLoader.addOverride("/terrain.png", "/gc_images/gift1402bottom.png");
	public static int side5 = ModLoader.addOverride("/terrain.png", "/gc_images/gift1402side5.png");
	public static int side2 = ModLoader.addOverride("/terrain.png", "/gc_images/gift1402side2.png");
	public static int side3 = ModLoader.addOverride("/terrain.png", "/gc_images/gift1402side3.png");
	public static int side4 = ModLoader.addOverride("/terrain.png", "/gc_images/gift1402side4.png");

	public BlockGift1402(int i, Block original) {
		super(i, Material.wool);
		setHardness(original.blockHardness);
		setResistance(original.blockResistance);
		setStepSound(original.stepSound);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {// j - дата, i - сторона 0,1 - верх и низ.
		if(i == 1)
			return top;
		if(i == 0)
			return bottom;
		if(i == 2)
			return side2;
		if(i == 3)
			return side3;
		if(i == 4)
			return side4;
		if(i == 5)
			return side5;
		return 0;
	}

}
