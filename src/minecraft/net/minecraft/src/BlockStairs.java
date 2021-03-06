// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            Block, World, EntityLiving, MathHelper, 
//            IBlockAccess, AxisAlignedBB, EntityPlayer, Entity, 
//            Vec3D

public class BlockStairs extends Block implements IBlockMadeOf {

	private static final int[][] field_72159_a = new int[][]{{2, 6}, {3, 7}, {2, 3}, {6, 7}, {0, 4}, {1, 5}, {0, 1}, {4, 5}};
	private static final int[] field_82545_b = new int[]{1, -1, 0, 0};
	private static final int[] field_82546_c = new int[]{0, 0, 1, -1};

	public final Block modelBlock;
	protected boolean field_72156_cr = false;
	protected int field_72160_cs = 0;
	protected int data = 0;

	protected BlockStairs(int i, Block block) {
		super(i, block.blockIndexInTexture, block.blockMaterial);
		modelBlock = block;
		this.blockGlassType = block.blockGlassType;
		setHardness(block.blockHardness);
		setResistance(block.blockResistance / 3F);
		setStepSound(block.stepSound);
		setLightOpacity(0);
	}

	public BlockStairs(int i, Block block, int data) {
		this(i, block);
		this.data = data;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		if(this.field_72156_cr) {
			this.setBlockBounds(0.5F * (this.field_72160_cs % 2), 0.5F * (this.field_72160_cs / 2 % 2), 0.5F * (this.field_72160_cs / 4 % 2), 0.5F + 0.5F * (this.field_72160_cs % 2), 0.5F + 0.5F * (this.field_72160_cs / 2 % 2), 0.5F + 0.5F * (this.field_72160_cs / 4 % 2));
		} else {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
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
		return 10;
	}

	/*@Override
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int x, int y, int z, int i) {
		//0 ������ �������
		int x2 = x; //���������� ������������� �����
		int y2 = y;
		int z2 = z;
		if(i == 0)
			y2++;
		else if(i == 1)
			y2--;
		else if(i == 2)
			z2++;
		else if(i == 3)
			z2--;
		else if(i == 4)
			x2++;
		else if(i == 5)
			x2--;
		Block near = Block.blocksList[iblockaccess.getBlockId(x, y, z)];
		if(near == null)
			return true;
		if(near instanceof BlockStairs) {
			int m1 = iblockaccess.getBlockMetadata(x2, y2, z2);
			int m2 = iblockaccess.getBlockMetadata(x, y, z);
			if((m1 & 7) != (m2 & 7) && (m1 & 4) == (m2 & 4))
				return (blockGlassType == 0 || near.blockGlassType != blockGlassType) && !near.isOpaqueCube();
		}
		return super.shouldSideBeRendered(iblockaccess, x, y, z, i);
	}*/

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		modelBlock.randomDisplayTick(world, i, j, k, random);
	}

	@Override
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer, int face) {
		modelBlock.onBlockClicked(world, i, j, k, entityplayer, face);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l) {
		modelBlock.onBlockDestroyedByPlayer(world, i, j, k, l);
	}

	@Override
	public int getMixedBrightnessForBlock(IBlockAccess iblockaccess, int i, int j, int k) {
		return modelBlock.getMixedBrightnessForBlock(iblockaccess, i, j, k);
	}

	@Override
	public float getBlockBrightness(IBlockAccess iblockaccess, int i, int j, int k) {
		return modelBlock.getBlockBrightness(iblockaccess, i, j, k);
	}

	@Override
	public float getExplosionResistance(Entity entity) {
		return modelBlock.getExplosionResistance(entity);
	}

	@Override
	public int getRenderBlockPass() {
		return modelBlock.getRenderBlockPass();
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		return modelBlock.getBlockTextureFromSideAndMetadata(i, data);
	}

	@Override
	public int getBlockTextureFromSide(int i) {
		return modelBlock.getBlockTextureFromSideAndMetadata(i, data);
	}

	@Override
	public int tickRate() {
		return modelBlock.tickRate();
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
		return modelBlock.getSelectedBoundingBoxFromPool(world, i, j, k);
	}

	@Override
	public void velocityToAddToEntity(World world, int i, int j, int k, Entity entity, Vec3D vec3d) {
		modelBlock.velocityToAddToEntity(world, i, j, k, entity, vec3d);
	}

	@Override
	public boolean isCollidable() {
		return modelBlock.isCollidable();
	}

	@Override
	public boolean canCollideCheck(int i, boolean flag) {
		return modelBlock.canCollideCheck(i, flag);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return modelBlock.canPlaceBlockAt(world, i, j, k);
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		onNeighborBlockChange(world, i, j, k, 0);
		modelBlock.onBlockAdded(world, i, j, k);
	}

	@Override
	public void onBlockRemoval(World world, int i, int j, int k) {
		modelBlock.onBlockRemoval(world, i, j, k);
	}

	@Override
	public void onEntityWalking(World world, int i, int j, int k, Entity entity) {
		modelBlock.onEntityWalking(world, i, j, k, entity);
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		modelBlock.updateTick(world, i, j, k, random);
	}

	@Override
	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		return modelBlock.blockActivated(world, i, j, k, entityplayer);
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int i, int j, int k) {
		modelBlock.onBlockDestroyedByExplosion(world, i, j, k);
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
		int data = world.getBlockMetadata(i, j, k) << 3;
		int l = MathHelper.floor_double(((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		if(entityliving.rotationPitch > 0) {
			if(l == 0) {
				world.setBlockMetadataWithNotify(i, j, k, 2 | data);
			}
			if(l == 1) {
				world.setBlockMetadataWithNotify(i, j, k, 1 | data);
			}
			if(l == 2) {
				world.setBlockMetadataWithNotify(i, j, k, 3 | data);
			}
			if(l == 3) {
				world.setBlockMetadataWithNotify(i, j, k, 0 | data);
			}
		} else {
			if(l == 0) {
				world.setBlockMetadataWithNotify(i, j, k, 6 | data);
			}
			if(l == 1) {
				world.setBlockMetadataWithNotify(i, j, k, 5 | data);
			}
			if(l == 2) {
				world.setBlockMetadataWithNotify(i, j, k, 7 | data);
			}
			if(l == 3) {
				world.setBlockMetadataWithNotify(i, j, k, 4 | data);
			}
		}
	}

	@Override
	public Block getBlockMadeOf() {
		return modelBlock;
	}

	public void func_82541_d(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		if((var5 & 4) != 0)
			this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
		else
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	public static boolean isStair(int par0) {
		return par0 > 0 && Block.blocksList[par0] instanceof BlockStairs;
	}

	private boolean canConnect(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		int var6 = par1IBlockAccess.getBlockId(par2, par3, par4);
		return isStair(var6) && (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 7) == par5;
	}

	public boolean func_82542_g(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 7;
		int var6 = var5 & 3;
		float var7 = 0.5F;
		float var8 = 1.0F;

		if((var5 & 4) != 0) {
			var7 = 0.0F;
			var8 = 0.5F;
		}

		float var9 = 0.0F;
		float var10 = 1.0F;
		float var11 = 0.0F;
		float var12 = 0.5F;
		boolean var13 = true;
		int var14;
		int var15;
		int var16;

		if(var6 == 0) {
			var9 = 0.5F;
			var12 = 1.0F;
			var14 = par1IBlockAccess.getBlockId(par2 + 1, par3, par4);
			var15 = par1IBlockAccess.getBlockMetadata(par2 + 1, par3, par4) & 7;

			if(isStair(var14) && (var5 & 4) == (var15 & 4)) {
				var16 = var15 & 3;

				if(var16 == 3 && !this.canConnect(par1IBlockAccess, par2, par3, par4 + 1, var5)) {
					var12 = 0.5F;
					var13 = false;
				} else if(var16 == 2 && !this.canConnect(par1IBlockAccess, par2, par3, par4 - 1, var5)) {
					var11 = 0.5F;
					var13 = false;
				}
			}
		} else if(var6 == 1) {
			var10 = 0.5F;
			var12 = 1.0F;
			var14 = par1IBlockAccess.getBlockId(par2 - 1, par3, par4);
			var15 = par1IBlockAccess.getBlockMetadata(par2 - 1, par3, par4) & 7;

			if(isStair(var14) && (var5 & 4) == (var15 & 4)) {
				var16 = var15 & 3;

				if(var16 == 3 && !this.canConnect(par1IBlockAccess, par2, par3, par4 + 1, var5)) {
					var12 = 0.5F;
					var13 = false;
				} else if(var16 == 2 && !this.canConnect(par1IBlockAccess, par2, par3, par4 - 1, var5)) {
					var11 = 0.5F;
					var13 = false;
				}
			}
		} else if(var6 == 2) {
			var11 = 0.5F;
			var12 = 1.0F;
			var14 = par1IBlockAccess.getBlockId(par2, par3, par4 + 1);
			var15 = par1IBlockAccess.getBlockMetadata(par2, par3, par4 + 1) & 7;

			if(isStair(var14) && (var5 & 4) == (var15 & 4)) {
				var16 = var15 & 3;

				if(var16 == 1 && !this.canConnect(par1IBlockAccess, par2 + 1, par3, par4, var5)) {
					var10 = 0.5F;
					var13 = false;
				} else if(var16 == 0 && !this.canConnect(par1IBlockAccess, par2 - 1, par3, par4, var5)) {
					var9 = 0.5F;
					var13 = false;
				}
			}
		} else if(var6 == 3) {
			var14 = par1IBlockAccess.getBlockId(par2, par3, par4 - 1);
			var15 = par1IBlockAccess.getBlockMetadata(par2, par3, par4 - 1) & 7;

			if(isStair(var14) && (var5 & 4) == (var15 & 4)) {
				var16 = var15 & 3;

				if(var16 == 1 && !this.canConnect(par1IBlockAccess, par2 + 1, par3, par4, var5)) {
					var10 = 0.5F;
					var13 = false;
				} else if(var16 == 0 && !this.canConnect(par1IBlockAccess, par2 - 1, par3, par4, var5)) {
					var9 = 0.5F;
					var13 = false;
				}
			}
		}

		this.setBlockBounds(var9, var7, var11, var10, var8, var12);
		return var13;
	}

	public boolean func_82544_h(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 7;
		int var6 = var5 & 3;
		float var7 = 0.5F;
		float var8 = 1.0F;

		if((var5 & 4) != 0) {
			var7 = 0.0F;
			var8 = 0.5F;
		}

		float var9 = 0.0F;
		float var10 = 0.5F;
		float var11 = 0.5F;
		float var12 = 1.0F;
		boolean var13 = false;
		int var14;
		int var15;
		int var16;

		if(var6 == 0) {
			var14 = par1IBlockAccess.getBlockId(par2 - 1, par3, par4);
			var15 = par1IBlockAccess.getBlockMetadata(par2 - 1, par3, par4) & 7;

			if(isStair(var14) && (var5 & 4) == (var15 & 4)) {
				var16 = var15 & 3;

				if(var16 == 3 && !this.canConnect(par1IBlockAccess, par2, par3, par4 - 1, var5)) {
					var11 = 0.0F;
					var12 = 0.5F;
					var13 = true;
				} else if(var16 == 2 && !this.canConnect(par1IBlockAccess, par2, par3, par4 + 1, var5)) {
					var11 = 0.5F;
					var12 = 1.0F;
					var13 = true;
				}
			}
		} else if(var6 == 1) {
			var14 = par1IBlockAccess.getBlockId(par2 + 1, par3, par4);
			var15 = par1IBlockAccess.getBlockMetadata(par2 + 1, par3, par4) & 7;

			if(isStair(var14) && (var5 & 4) == (var15 & 4)) {
				var9 = 0.5F;
				var10 = 1.0F;
				var16 = var15 & 3;

				if(var16 == 3 && !this.canConnect(par1IBlockAccess, par2, par3, par4 - 1, var5)) {
					var11 = 0.0F;
					var12 = 0.5F;
					var13 = true;
				} else if(var16 == 2 && !this.canConnect(par1IBlockAccess, par2, par3, par4 + 1, var5)) {
					var11 = 0.5F;
					var12 = 1.0F;
					var13 = true;
				}
			}
		} else if(var6 == 2) {
			var14 = par1IBlockAccess.getBlockId(par2, par3, par4 - 1);
			var15 = par1IBlockAccess.getBlockMetadata(par2, par3, par4 - 1) & 7;

			if(isStair(var14) && (var5 & 4) == (var15 & 4)) {
				var11 = 0.0F;
				var12 = 0.5F;
				var16 = var15 & 3;

				if(var16 == 1 && !this.canConnect(par1IBlockAccess, par2 - 1, par3, par4, var5)) {
					var13 = true;
				} else if(var16 == 0 && !this.canConnect(par1IBlockAccess, par2 + 1, par3, par4, var5)) {
					var9 = 0.5F;
					var10 = 1.0F;
					var13 = true;
				}
			}
		} else if(var6 == 3) {
			var14 = par1IBlockAccess.getBlockId(par2, par3, par4 + 1);
			var15 = par1IBlockAccess.getBlockMetadata(par2, par3, par4 + 1) & 7;

			if(isStair(var14) && (var5 & 4) == (var15 & 4)) {
				var16 = var15 & 3;

				if(var16 == 1 && !this.canConnect(par1IBlockAccess, par2 - 1, par3, par4, var5)) {
					var13 = true;
				} else if(var16 == 0 && !this.canConnect(par1IBlockAccess, par2 + 1, par3, par4, var5)) {
					var9 = 0.5F;
					var10 = 1.0F;
					var13 = true;
				}
			}
		}

		if(var13) {
			this.setBlockBounds(var9, var7, var11, var10, var8, var12);
		}

		return var13;
	}

	/**
	 * if the specified block is in the given AABB, add its collision bounding box to the given list
	 */
	@Override
	public void getCollidingBoundingBoxes(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, ArrayList par6List) {
		this.func_82541_d(par1World, par2, par3, par4);
		super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6List);
		boolean var8 = this.func_82542_g(par1World, par2, par3, par4);
		super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6List);

		if(var8 && this.func_82544_h(par1World, par2, par3, par4))
			super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6List);

		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
	 * x, y, z, startVec, endVec
	 */
	@Override
	public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3D par5Vec3, Vec3D par6Vec3) {
		MovingObjectPosition[] var7 = new MovingObjectPosition[8];
		int var8 = par1World.getBlockMetadata(par2, par3, par4) & 7;
		int var9 = var8 & 3;
		boolean var10 = (var8 & 4) == 4;
		int[] var11 = field_72159_a[var9 + (var10 ? 4 : 0)];
		this.field_72156_cr = true;

		for(int var12 = 0; var12 < 8; ++var12) {
			this.field_72160_cs = var12;
			int[] var13 = var11;
			int var14 = var11.length;

			var7[var12] = super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
		}

		for(int var14 = 0; var14 < var11.length; ++var14) {
			int var15 = var11[var14];
			var7[var15] = null;
		}

		MovingObjectPosition var23 = null;
		double var22 = 0.0D;
		MovingObjectPosition[] var25 = var7;

		for(int var17 = 0; var17 < var7.length; ++var17) {
			MovingObjectPosition var18 = var25[var17];

			if(var18 != null) {
				double var19 = var18.hitVec.squareDistanceTo(par6Vec3);

				if(var19 > var22) {
					var23 = var18;
					var22 = var19;
				}
			}
		}

		return var23;
	}
}
