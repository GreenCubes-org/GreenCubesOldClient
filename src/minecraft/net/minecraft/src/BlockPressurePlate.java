// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;
import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            Block, World, EnumMobType, AxisAlignedBB, 
//            EntityLiving, EntityPlayer, IBlockAccess, Material, 
//            Entity

public class BlockPressurePlate extends Block {

	private EnumMobType triggerMobType;

	protected BlockPressurePlate(int i, int j, EnumMobType enummobtype, Material material) {
		super(i, j, material);
		triggerMobType = enummobtype;
		setTickOnLoad(true);
		float f = 0.0625F;
		setBlockBounds(f, 0.0F, f, 1.0F - f, 0.03125F, 1.0F - f);
	}

	@Override
	public int tickRate() {
		return 20;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return null;
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
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return super.canPlaceBlockAt(world, i, j, k) && BlockTorch.canStayOnSide(world, i, j, k, BlockFace.TOP);
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		if(!BlockTorch.canStayOnSide(world, i, j, k, BlockFace.TOP)) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockWithNotify(i, j, k, 0);
		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		if(world.multiplayerWorld)
			return;
		if(world.getBlockMetadata(i, j, k) != 0)
			setStateIfMobInteractsWithPlate(world, i, j, k);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		if(world.multiplayerWorld)
			return;
		if(world.getBlockMetadata(i, j, k) != 1)
			setStateIfMobInteractsWithPlate(world, i, j, k);
	}

	private void setStateIfMobInteractsWithPlate(World world, int i, int j, int k) {
		boolean flag = world.getBlockMetadata(i, j, k) == 1;
		boolean flag1 = false;
		float f = 0.125F;
		List list = null;
		if(triggerMobType == EnumMobType.everything)
			list = world.getEntitiesWithinAABBExcludingEntity((Entity) null, AxisAlignedBB.getBoundingBoxFromPool(i + f, j, k + f, (i + 1) - f, j + 0.25D, (k + 1) - f));
		if(triggerMobType == EnumMobType.mobs)
			list = world.getEntitiesWithinAABB(net.minecraft.src.EntityLiving.class, AxisAlignedBB.getBoundingBoxFromPool(i + f, j, k + f, (i + 1) - f, j + 0.25D, (k + 1) - f));
		if(triggerMobType == EnumMobType.players)
			list = world.getEntitiesWithinAABB(net.minecraft.src.EntityPlayer.class, AxisAlignedBB.getBoundingBoxFromPool(i + f, j, k + f, (i + 1) - f, j + 0.25D, (k + 1) - f));
		if(list.size() > 0)
			flag1 = true;
		if(flag1 && !flag) {
			world.setBlockMetadataWithNotify(i, j, k, 1);
			world.notifyBlocksOfNeighborChange(i, j, k, blockID);
			world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
			world.markBlocksDirty(i, j, k, i, j, k);
			world.playSoundEffect(i + 0.5D, j + 0.10000000000000001D, k + 0.5D, "random.click", 0.3F, 0.6F);
		}
		if(!flag1 && flag) {
			world.setBlockMetadataWithNotify(i, j, k, 0);
			world.notifyBlocksOfNeighborChange(i, j, k, blockID);
			world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
			world.markBlocksDirty(i, j, k, i, j, k);
			world.playSoundEffect(i + 0.5D, j + 0.10000000000000001D, k + 0.5D, "random.click", 0.3F, 0.5F);
		}
		if(flag1)
			world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
	}

	@Override
	public void onBlockRemoval(World world, int i, int j, int k) {
		int l = world.getBlockMetadata(i, j, k);
		if(l > 0) {
			world.notifyBlocksOfNeighborChange(i, j, k, blockID);
			world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
		}
		super.onBlockRemoval(world, i, j, k);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k) {
		boolean flag = iblockaccess.getBlockMetadata(i, j, k) == 1;
		float f = 0.0625F;
		if(flag)
			setBlockBounds(f, 0.0F, f, 1.0F - f, 0.03125F, 1.0F - f);
		else
			setBlockBounds(f, 0.0F, f, 1.0F - f, 0.0625F, 1.0F - f);
	}

	@Override
	public boolean isPoweringTo(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		return iblockaccess.getBlockMetadata(i, j, k) > 0;
	}

	@Override
	public boolean isIndirectlyPoweringTo(World world, int i, int j, int k, int l) {
		return l == 1 && world.getBlockMetadata(i, j, k) != 0;
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public void setBlockBoundsForItemRender() {
		float f = 0.5F;
		float f1 = 0.125F;
		float f2 = 0.5F;
		setBlockBounds(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
	}

	@Override
	public int getMobilityFlag() {
		return 1;
	}
}
