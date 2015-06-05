package net.minecraft.src;

import java.util.Random;

public class BlockBadnick extends Block {

	public static final int top = ModLoader.addOverride("/terrain.png", "/gc_images/badnick_top.png");
	public static final int bottom = ModLoader.addOverride("/terrain.png", "/gc_images/badnick_bottom.png");
	public static final int left = ModLoader.addOverride("/terrain.png", "/gc_images/badnick_left.png");
	public static final int right = ModLoader.addOverride("/terrain.png", "/gc_images/badnick_right.png");
	public static final int face = ModLoader.addOverride("/terrain.png", "/gc_images/badnick_face.png");
	public static final int back = ModLoader.addOverride("/terrain.png", "/gc_images/badnick_back.png");

	public BlockBadnick(int i, Block original) {
		super(i, Material.rock);
		setHardness(original.blockHardness);
		setResistance(original.blockResistance);
		setStepSound(original.stepSound);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {// j - дата, i - сторона 1,0 - верх и низ.
		if(i == 1)
			return top;
		if(i == 0)
			return bottom;
		if(j == 0) {
			if(i == 5)
				return face;
			if(i == 2)
				return left;
			if(i == 3)
				return right;
			if(i == 4)
				return back;
		}
		if(j == 1) {
			if(i == 4)
				return face;
			if(i == 3)
				return left;
			if(i == 2)
				return right;
			if(i == 5)
				return back;
		}
		if(j == 2) {
			if(i == 2)
				return face;
			if(i == 4)
				return left;
			if(i == 5)
				return right;
			if(i == 3)
				return back;
		}
		if(j == 3) {
			if(i == 3)
				return face;
			if(i == 2)
				return back;
			if(i == 4)
				return right;
			if(i == 5)
				return left;
			return bottom;
		}
		return left;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
		int l = MathHelper.floor_double(((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		if(l == 0) {
			world.setBlockMetadataWithNotify(i, j, k, 2);
		}
		if(l == 1) {
			world.setBlockMetadataWithNotify(i, j, k, 0);
		}
		if(l == 2) {
			world.setBlockMetadataWithNotify(i, j, k, 3);
		}
		if(l == 3) {
			world.setBlockMetadataWithNotify(i, j, k, 1);
		}
	}
}
