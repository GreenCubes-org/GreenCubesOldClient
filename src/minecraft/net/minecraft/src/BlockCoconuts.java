package net.minecraft.src;

public class BlockCoconuts extends Block {

	public static int top = ModLoader.addOverride("/terrain.png", "/gc_images/coconutstop.png");
	public static int top2 = ModLoader.addOverride("/terrain.png", "/gc_images/coconutstop2.png");
	public static int side = ModLoader.addOverride("/terrain.png", "/gc_images/coconutsside.png");

	public BlockCoconuts(int i) {
		super(i, 20, Material.plants);
	}

	@Override
	public int getRenderType() {
		return 33;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int s, int d) {
		if(s == 1) {
			if(d == 2 || d == 3)
				return top2;
			return top;
		}
		return side;
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
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return null;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		int data = world.getBlockMetadata(x, y, z) & 0x3;
		switch(data) {
		case 0:
			return AxisAlignedBB.getBoundingBoxFromPool((double) x + 0.125f, (double) y + 0.50f, z, (double) x + 0.8125f, (double) y + 1f, (double) z + 0.75f);
		case 1:
			return AxisAlignedBB.getBoundingBoxFromPool((double) x + 0.125f, (double) y + 0.50f, (double) z + 0.25f, (double) x + 0.8125f, (double) y + 1f, (double) z + 1.0f);
		case 2:
			return AxisAlignedBB.getBoundingBoxFromPool((double) x + 0.25f, (double) y + 0.50f, (double) z + 0.125f, (double) x + 1.0f, (double) y + 1f, (double) z + 0.8125f);
		case 3:
			return AxisAlignedBB.getBoundingBoxFromPool(x, (double) y + 0.50f, (double) z + 0.125f, (double) x + 0.75f, (double) y + 1f, (double) z + 0.8125f);
		}
		return null;
	}

}
