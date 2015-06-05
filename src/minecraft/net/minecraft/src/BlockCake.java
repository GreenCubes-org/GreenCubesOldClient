// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            Block, Material, IBlockAccess, World, 
//            AxisAlignedBB, EntityPlayer, FoodStats

public class BlockCake extends Block {
	
	private static float WIDTH_MIN = (1 - 0.875f) * 0.5f;
	private static float WIDTH_MAX = (1 + 0.875f) * 0.5f;
	private static float HEIGHT = 0.5f;

	protected BlockCake(int i, int j) {
		super(i, j, Material.cakeMaterial);
		setTickOnLoad(true);
		//setBlockBounds(WIDTH_MIN, 0, WIDTH_MIN, WIDTH_MAX, HEIGHT, WIDTH_MAX);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k) {
		int l = iblockaccess.getBlockMetadata(i, j, k);
		float f = 0.0625F;
		float f1 = (1 + l * 2) / 16F;
		float f2 = 0.5F;
		setBlockBounds(f1, 0.0F, f, 1.0F - f, f2, 1.0F - f);
		//setBlockBounds(WIDTH_MIN, 0, WIDTH_MIN, WIDTH_MAX, HEIGHT, WIDTH_MAX);
	}

	@Override
	public void setBlockBoundsForItemRender() {
		float f = 0.0625F;
		float f1 = 0.5F;
		setBlockBounds(f, 0.0F, f, 1.0F - f, f1, 1.0F - f);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		/*int l = world.getBlockMetadata(i, j, k);
		float f = 0.0625F;
		float f1 = (1 + l * 2) / 16F;
		float f2 = 0.5F;
		return AxisAlignedBB.getBoundingBoxFromPool(i + f1, j, k + f, (i + 1) - f, (j + f2) - f, (k + 1) - f);*/
		return AxisAlignedBB.getBoundingBox(i + WIDTH_MIN, j + 0, k + WIDTH_MIN, i + WIDTH_MAX, j + HEIGHT, k + WIDTH_MAX);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
		/*int l = world.getBlockMetadata(i, j, k);
		float f = 0.0625F;
		float f1 = (1 + l * 2) / 16F;
		float f2 = 0.5F;
		return AxisAlignedBB.getBoundingBoxFromPool(i + f1, j, k + f, (i + 1) - f, j + f2, (k + 1) - f);*/
		return AxisAlignedBB.getBoundingBox(i + WIDTH_MIN, j + 0, k + WIDTH_MIN, i + WIDTH_MAX, j + HEIGHT, k + WIDTH_MAX);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		if(i == 1) {
			return blockIndexInTexture;
		}
		if(i == 0) {
			return blockIndexInTexture + 3;
		}
		if(j > 0 && i == 4) {
			return blockIndexInTexture + 2;
		} else {
			return blockIndexInTexture + 1;
		}
	}

	@Override
	public int getBlockTextureFromSide(int i) {
		if(i == 1) {
			return blockIndexInTexture;
		}
		if(i == 0) {
			return blockIndexInTexture + 3;
		} else {
			return blockIndexInTexture + 1;
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
	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		if(world.multiplayerWorld)
			return true;
		eatCakeSlice(world, i, j, k, entityplayer);
		return true;
	}

	@Override
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer, int face) {
		if(world.multiplayerWorld)
			return; // GreenCubes
		eatCakeSlice(world, i, j, k, entityplayer);
	}

	private void eatCakeSlice(World world, int i, int j, int k, EntityPlayer entityplayer) {
		if(entityplayer.func_35197_b(false)) {
			entityplayer.getFoodStats().addStats(2, 0.1F);
			int l = world.getBlockMetadata(i, j, k) + 1;
			if(l >= 6) {
				world.setBlockWithNotify(i, j, k, 0);
			} else {
				world.setBlockMetadataWithNotify(i, j, k, l);
				world.markBlockAsNeedsUpdate(i, j, k);
			}
		}
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
		return world.getBlockMaterial(i, j - 1, k).isSolid();
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return 0;
	}
}
