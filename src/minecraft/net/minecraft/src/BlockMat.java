package net.minecraft.src;

public class BlockMat extends Block {

	public static final int tex2 = ModLoader.addOverride("/terrain.png", "/gc_images/mat.png");
	public static final int tex1 = ModLoader.addOverride("/terrain.png", "/gc_images/mat2.png");

	public BlockMat(int i) {
		super(i, tex1, Material.wood);
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 1F - 0.0625F, 0.0625F, 1F - 0.0625F);
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
	public int getBlockTextureFromSideAndMetadata(int i, int j) {// j - дата, i - сторона 0,1 - верх и низ.
		if(i == 1 || i == 0) {
			if(j == 1) {
				return tex2;
			}
			return tex1;
		}
		if(j == 0) {
			if(i == 5)
				return tex1;
			if(i == 2)
				return tex2;
			if(i == 3)
				return tex2;
			if(i == 4)
				return tex1;
		}
		if(j == 1) {
			if(i == 4)
				return tex1;
			if(i == 3)
				return tex2;
			if(i == 2)
				return tex2;
			if(i == 5)
				return tex1;
		}
		return tex1;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
		int l = MathHelper.floor_double(((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		if(l == 0) {
			world.setBlockMetadataWithNotify(i, j, k, 1);
		}
		if(l == 1) {
			world.setBlockMetadataWithNotify(i, j, k, 0);
		}
		if(l == 2) {
			world.setBlockMetadataWithNotify(i, j, k, 1);
		}
		if(l == 3) {
			world.setBlockMetadataWithNotify(i, j, k, 0);
		}
	}
}
