// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            Block, Material, World, AxisAlignedBB, 
//            Vec3D, MovingObjectPosition

public class BlockTorch extends Block {

	protected BlockTorch(int i, int j) {
		super(i, j, Material.circuits);
		setTickOnLoad(true);
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
	public int getRenderType() {
		return 2;
	}

	public static boolean canStayOnSide(World world, int x, int y, int z, BlockFace face) {
		if(face == BlockFace.TOP) {
			Block block = Block.blocksList[world.getBlockId(x, y - 1, z)];
			if(block == null)
				return false;
			if(block instanceof BlockFence)
				return true;
			if(!block.blockMaterial.getIsOpaque())
				return false;
			if(block.isOpaqueCube())
				return true;
			if(block instanceof BlockGreenStep)
				return ((BlockGreenStep) block).isUp;
			if(block instanceof BlockColumn)
				return true;
		} else if(face == BlockFace.WEST || face == BlockFace.SOUTH || face == BlockFace.EAST || face == BlockFace.NORTH) {
			BlockFace oppositeFace = face.getOpposite();
			Block block = Block.blocksList[world.getBlockId(x + oppositeFace.getModX(), y, z + oppositeFace.getModZ())];
			if(block == null)
				return false;
			if(!block.blockMaterial.getIsOpaque())
				return false;
			if(block.isOpaqueCube())
				return true;
			if(block instanceof BlockHalfBlock) {
				int data = world.getBlockMetadata(x + oppositeFace.getModX(), y, z + oppositeFace.getModZ());
				return face == BlockHalfBlock.getDirectionByData(data);
			}
		}
		return false;
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, int i, int j, int k, int l) {
		return canStayOnSide(world, i, j, k, BlockFace.getFaceById(l));
	}

	@Override
	public void onBlockPlaced(World world, int i, int j, int k, int l) {
		BlockFace face = BlockFace.getFaceById(l);
		byte data = 5;
		if(face == BlockFace.TOP)
			data = 5;
		else if(face == BlockFace.EAST)
			data = 4;
		else if(face == BlockFace.WEST)
			data = 3;
		else if(face == BlockFace.NORTH)
			data = 2;
		else if(face == BlockFace.SOUTH)
			data = 1;
		world.setBlockMetadataWithNotify(i, j, k, data);
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		super.updateTick(world, i, j, k, random);
		if(world.getBlockMetadata(i, j, k) == 0)
			onBlockAdded(world, i, j, k);
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		if(canStayOnSide(world, i, j, k, BlockFace.SOUTH))
			world.setBlockMetadataWithNotify(i, j, k, 1);
		else if(canStayOnSide(world, i, j, k, BlockFace.NORTH))
			world.setBlockMetadataWithNotify(i, j, k, 2);
		else if(canStayOnSide(world, i, j, k, BlockFace.WEST))
			world.setBlockMetadataWithNotify(i, j, k, 3);
		else if(canStayOnSide(world, i, j, k, BlockFace.EAST))
			world.setBlockMetadataWithNotify(i, j, k, 4);
		else if(canStayOnSide(world, i, j, k, BlockFace.TOP))
			world.setBlockMetadataWithNotify(i, j, k, 5);
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		int i1 = world.getBlockMetadata(i, j, k);
		boolean flag = false;
		if(i1 == 1 && !canStayOnSide(world, i, j, k, BlockFace.SOUTH))
			flag = true;
		if(i1 == 2 && !canStayOnSide(world, i, j, k, BlockFace.NORTH))
			flag = true;
		if(i1 == 3 && !canStayOnSide(world, i, j, k, BlockFace.WEST))
			flag = true;
		if(i1 == 4 && !canStayOnSide(world, i, j, k, BlockFace.EAST))
			flag = true;
		if(i1 == 5 && !canStayOnSide(world, i, j, k, BlockFace.TOP))
			flag = true;
		if(flag) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockWithNotify(i, j, k, 0);
		}
	}

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1) {
		int l = world.getBlockMetadata(i, j, k) & 7;
		float f = 0.15F;
		if(l == 1) {
			setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
		} else if(l == 2) {
			setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
		} else if(l == 3) {
			setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
		} else if(l == 4) {
			setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
		} else {
			float f1 = 0.1F;
			setBlockBounds(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, 0.6F, 0.5F + f1);
		}
		return super.collisionRayTrace(world, i, j, k, vec3d, vec3d1);
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		int l = world.getBlockMetadata(i, j, k);
		double d = i + 0.5F;
		double d1 = j + 0.7F;
		double d2 = k + 0.5F;
		double d3 = 0.2199999988079071D;
		double d4 = 0.27000001072883606D;
		if(l == 1) {
			world.spawnParticle("smoke", d - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", d - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
		} else if(l == 2) {
			world.spawnParticle("smoke", d + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", d + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
		} else if(l == 3) {
			world.spawnParticle("smoke", d, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", d, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
		} else if(l == 4) {
			world.spawnParticle("smoke", d, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", d, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
		} else {
			world.spawnParticle("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", d, d1, d2, 0.0D, 0.0D, 0.0D);
		}
	}

}
