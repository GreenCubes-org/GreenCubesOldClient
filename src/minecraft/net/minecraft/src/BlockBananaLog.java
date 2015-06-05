package net.minecraft.src;

public class BlockBananaLog extends Block {

	public final static int side = ModLoader.addOverride("/terrain.png", "/gc_images/bananalogside.png");
	public final static int side1 = ModLoader.addOverride("/terrain.png", "/gc_images/bananalogside1.png");
	public final static int top = ModLoader.addOverride("/terrain.png", "/gc_images/bananalogtop.png");
	public final static int slice = ModLoader.addOverride("/terrain.png", "/gc_images/bananalogslice.png");

	public BlockBananaLog(int i) {
		super(i, side, Material.wood);
		setBlockBounds(0.125F, 0.0F, 0.125F, 1 - 0.125F, 1.0F, 1 - 0.125F);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		if((j & 1) == 0) {
			if(i == 4 || i == 5)
				return side;
			if(i == 2 || i == 3)
				return side1;
			return top;
		} else {
			if(i == 5 || i == 2)
				return side;
			if(i == 3 || i == 4)
				return side1;
			return top;
		}
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
