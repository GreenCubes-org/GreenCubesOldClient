// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            Block, Material, World, AxisAlignedBB, 
//            EntityLiving, MathHelper, EntityPlayer

public class BlockFenceGate extends Block {

	public BlockFenceGate(int i, int j) {
		super(i, j, Material.wood);
	}

	// GreenCubes start
	public BlockFenceGate(int i, int j, Material m) {
		super(i, j, m);
	}

	// GreenCubes end

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		if(!world.getBlockMaterial(i, j - 1, k).isSolid()) {
			return false;
		} else {
			return super.canPlaceBlockAt(world, i, j, k);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		int l = world.getBlockMetadata(i, j, k);
		if(isFenceGateOpen(l)) {
			return null;
		} else {
			return AxisAlignedBB.getBoundingBoxFromPool(i, j, k, i + 1, j + 1.5F, k + 1);
		}
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
	public int getRenderType() {
		return 21;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
		int l = (MathHelper.floor_double(((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3) % 4;
		world.setBlockMetadataWithNotify(i, j, k, l);
	}

	@Override
	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		int l = world.getBlockMetadata(i, j, k);
		if(isFenceGateOpen(l)) {
			world.setBlockMetadataWithNotify(i, j, k, l & -5);
		} else {
			int i1 = (MathHelper.floor_double(((entityplayer.rotationYaw * 4F) / 360F) + 0.5D) & 3) % 4;
			int j1 = func_35290_f(l);
			if(j1 == (i1 + 2) % 4) {
				l = i1;
			}
			world.setBlockMetadataWithNotify(i, j, k, l | 4);
		}
		world.playAuxSFXAtEntity(entityplayer, 1003, i, j, k, 0);
		return true;
	}

	public static boolean isFenceGateOpen(int i) {
		return (i & 4) != 0;
	}

	public static int func_35290_f(int i) {
		return i & 3;
	}
}
