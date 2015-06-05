// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            Block, Material, World, IBlockAccess, 
//            AxisAlignedBB, EntityPlayer

public class BlockLever extends Block {

	protected BlockLever(int i, int j) {
		super(i, j, Material.circuits);
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
		return 12;
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, int i, int j, int k, int l) {
		if(l == 1 && world.isBlockNormalCube(i, j - 1, k)) {
			return true;
		}
		if(l == 2 && world.isBlockNormalCube(i, j, k + 1)) {
			return true;
		}
		if(l == 3 && world.isBlockNormalCube(i, j, k - 1)) {
			return true;
		}
		if(l == 4 && world.isBlockNormalCube(i + 1, j, k)) {
			return true;
		}
		return l == 5 && world.isBlockNormalCube(i - 1, j, k);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		if(world.isBlockNormalCube(i - 1, j, k)) {
			return true;
		}
		if(world.isBlockNormalCube(i + 1, j, k)) {
			return true;
		}
		if(world.isBlockNormalCube(i, j, k - 1)) {
			return true;
		}
		if(world.isBlockNormalCube(i, j, k + 1)) {
			return true;
		}
		return world.isBlockNormalCube(i, j - 1, k);
	}

	@Override
	public void onBlockPlaced(World world, int i, int j, int k, int l) {
		int i1 = world.getBlockMetadata(i, j, k);
		int j1 = i1 & 8;
		i1 &= 7;
		i1 = -1;
		if(l == 1 && world.isBlockNormalCube(i, j - 1, k)) {
			i1 = 5 + world.rand.nextInt(2);
		}
		if(l == 2 && world.isBlockNormalCube(i, j, k + 1)) {
			i1 = 4;
		}
		if(l == 3 && world.isBlockNormalCube(i, j, k - 1)) {
			i1 = 3;
		}
		if(l == 4 && world.isBlockNormalCube(i + 1, j, k)) {
			i1 = 2;
		}
		if(l == 5 && world.isBlockNormalCube(i - 1, j, k)) {
			i1 = 1;
		}
		if(i1 == -1) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockWithNotify(i, j, k, 0);
			return;
		} else {
			world.setBlockMetadataWithNotify(i, j, k, i1 + j1);
			return;
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		if(checkIfAttachedToBlock(world, i, j, k)) {
			int i1 = world.getBlockMetadata(i, j, k) & 7;
			boolean flag = false;
			if(!world.isBlockNormalCube(i - 1, j, k) && i1 == 1) {
				flag = true;
			}
			if(!world.isBlockNormalCube(i + 1, j, k) && i1 == 2) {
				flag = true;
			}
			if(!world.isBlockNormalCube(i, j, k - 1) && i1 == 3) {
				flag = true;
			}
			if(!world.isBlockNormalCube(i, j, k + 1) && i1 == 4) {
				flag = true;
			}
			if(!world.isBlockNormalCube(i, j - 1, k) && i1 == 5) {
				flag = true;
			}
			if(!world.isBlockNormalCube(i, j - 1, k) && i1 == 6) {
				flag = true;
			}
			if(flag) {
				dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
				world.setBlockWithNotify(i, j, k, 0);
			}
		}
	}

	private boolean checkIfAttachedToBlock(World world, int i, int j, int k) {
		if(!canPlaceBlockAt(world, i, j, k)) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockWithNotify(i, j, k, 0);
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k) {
		int l = iblockaccess.getBlockMetadata(i, j, k) & 7;
		float f = 0.1875F;
		if(l == 1) {
			setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
		} else if(l == 2) {
			setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
		} else if(l == 3) {
			setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
		} else if(l == 4) {
			setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
		} else {
			float f1 = 0.25F;
			setBlockBounds(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, 0.6F, 0.5F + f1);
		}
	}

	@Override
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer, int face) {
		blockActivated(world, i, j, k, entityplayer);
	}

	@Override
	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		if(world.multiplayerWorld) {
			return true;
		}
		int l = world.getBlockMetadata(i, j, k);
		int i1 = l & 7;
		int j1 = 8 - (l & 8);
		world.setBlockMetadataWithNotify(i, j, k, i1 + j1);
		world.markBlocksDirty(i, j, k, i, j, k);
		world.playSoundEffect(i + 0.5D, j + 0.5D, k + 0.5D, "random.click", 0.3F, j1 <= 0 ? 0.5F : 0.6F);
		world.notifyBlocksOfNeighborChange(i, j, k, blockID);
		if(i1 == 1) {
			world.notifyBlocksOfNeighborChange(i - 1, j, k, blockID);
		} else if(i1 == 2) {
			world.notifyBlocksOfNeighborChange(i + 1, j, k, blockID);
		} else if(i1 == 3) {
			world.notifyBlocksOfNeighborChange(i, j, k - 1, blockID);
		} else if(i1 == 4) {
			world.notifyBlocksOfNeighborChange(i, j, k + 1, blockID);
		} else {
			world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
		}
		return true;
	}

	@Override
	public void onBlockRemoval(World world, int i, int j, int k) {
		int l = world.getBlockMetadata(i, j, k);
		if((l & 8) > 0) {
			world.notifyBlocksOfNeighborChange(i, j, k, blockID);
			int i1 = l & 7;
			if(i1 == 1) {
				world.notifyBlocksOfNeighborChange(i - 1, j, k, blockID);
			} else if(i1 == 2) {
				world.notifyBlocksOfNeighborChange(i + 1, j, k, blockID);
			} else if(i1 == 3) {
				world.notifyBlocksOfNeighborChange(i, j, k - 1, blockID);
			} else if(i1 == 4) {
				world.notifyBlocksOfNeighborChange(i, j, k + 1, blockID);
			} else {
				world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
			}
		}
		super.onBlockRemoval(world, i, j, k);
	}

	@Override
	public boolean isPoweringTo(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		return (iblockaccess.getBlockMetadata(i, j, k) & 8) > 0;
	}

	@Override
	public boolean isIndirectlyPoweringTo(World world, int i, int j, int k, int l) {
		int i1 = world.getBlockMetadata(i, j, k);
		if((i1 & 8) == 0) {
			return false;
		}
		int j1 = i1 & 7;
		if(j1 == 6 && l == 1) {
			return true;
		}
		if(j1 == 5 && l == 1) {
			return true;
		}
		if(j1 == 4 && l == 2) {
			return true;
		}
		if(j1 == 3 && l == 3) {
			return true;
		}
		if(j1 == 2 && l == 4) {
			return true;
		}
		return j1 == 1 && l == 5;
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}
}
