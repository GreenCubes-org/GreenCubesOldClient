// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            Block, Material, World, AxisAlignedBB, 
//            DamageSource, Entity

public class BlockCactus extends Block {

	protected BlockCactus(int i, int j) {
		super(i, j, Material.cactus);
		setTickOnLoad(true);
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		if(world.isAirBlock(i, j + 1, k)) {
			int l;
			for(l = 1; world.getBlockId(i, j - l, k) == blockID; l++) {
			}
			if(l < 3) {
				int i1 = world.getBlockMetadata(i, j, k);
				if(i1 == 15) {
					world.setBlockWithNotify(i, j + 1, k, blockID);
					world.setBlockMetadataWithNotify(i, j, k, 0);
				} else {
					world.setBlockMetadataWithNotify(i, j, k, i1 + 1);
				}
			}
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		float f = 0.0625F;
		return AxisAlignedBB.getBoundingBoxFromPool(i + f, j, k + f, (i + 1) - f, (j + 1) - f, (k + 1) - f);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
		float f = 0.0625F;
		return AxisAlignedBB.getBoundingBoxFromPool(i + f, j, k + f, (i + 1) - f, j + 1, (k + 1) - f);
	}

	@Override
	public int getBlockTextureFromSide(int i) {
		if(i == 1) {
			return blockIndexInTexture - 1;
		}
		if(i == 0) {
			return blockIndexInTexture + 1;
		} else {
			return blockIndexInTexture;
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

	@Override
	public int getRenderType() {
		return 13;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		if(!super.canPlaceBlockAt(world, i, j, k)) {
			return false;
		} else {
			return canBlockStay(world, i, j, k);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		if(!canBlockStay(world, i, j, k)) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockWithNotify(i, j, k, 0);
		}
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		if(world.getBlockMaterial(i - 1, j, k).isSolid()) {
			return false;
		}
		if(world.getBlockMaterial(i + 1, j, k).isSolid()) {
			return false;
		}
		if(world.getBlockMaterial(i, j, k - 1).isSolid()) {
			return false;
		}
		if(world.getBlockMaterial(i, j, k + 1).isSolid()) {
			return false;
		} else {
			int l = world.getBlockId(i, j - 1, k);
			return l == Block.cactus.blockID || l == Block.sand.blockID;
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		entity.attackEntityFrom(DamageSource.cactus, 1);
	}
}
