// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            BlockFlower, World, WorldGenTaiga2, WorldGenForest, 
//            WorldGenTrees, WorldGenBigTree, WorldGenerator

public class BlockSapling extends BlockFlower {

	protected BlockSapling(int i, int j) {
		super(i, j);
		float f = 0.4F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		if(world.multiplayerWorld) {
			return;
		}
		super.updateTick(world, i, j, k, random);
		if(world.getBlockLightValue(i, j + 1, k) >= 9 && random.nextInt(7) == 0) {
			int l = world.getBlockMetadata(i, j, k);
			if((l & 8) == 0) {
				world.setBlockMetadataWithNotify(i, j, k, l | 8);
			} else {
				growTree(world, i, j, k, random);
			}
		}
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		j &= 3;
		if(j == 1) {
			return 63;
		}
		if(j == 2) {
			return 79;
		}
		if(j == 3) {
			return 30;
		} else {
			return super.getBlockTextureFromSideAndMetadata(i, j);
		}
	}

	public void growTree(World world, int i, int j, int k, Random random) {
		int l = world.getBlockMetadata(i, j, k) & 3;
		world.setBlock(i, j, k, 0);
		Object obj = null;
		boolean flag = false;
		if(l == 1) {
			obj = new WorldGenTaiga2(true);
		} else if(l == 2) {
			obj = new WorldGenForest(true);
		}
		if(i == 3) {
			j = 0;

			do {
				if(j < -1) {
					break;
				}

				k = 0;

				do {
					if(k < -1) {
						break;
					}

					if(func_50076_f(world, i + j, j, k + k, 3) && func_50076_f(world, i + j + 1, j, k + k, 3) && func_50076_f(world, i + j, j, k + k + 1, 3) && func_50076_f(world, i + j + 1, j, k + k + 1, 3)) {
						obj = new WorldGenHugeTrees(true, 10 + random.nextInt(20), 3, 3);
						flag = true;
						break;
					}

					k--;
				} while(true);

				if(obj != null) {
					break;
				}

				j--;
			} while(true);

			if(obj == null) {
				j = k = 0;
				obj = new WorldGenTrees(true, 4 + random.nextInt(7), 3, 3, false);
			}
		} else {
			obj = new WorldGenTrees(true);
			if(random.nextInt(10) == 0) {
				obj = new WorldGenBigTree(true);
			}
		}
		if(!((WorldGenerator) (obj)).generate(world, random, i, j, k)) {
			world.setBlockAndMetadata(i, j, k, blockID, l);
		}
	}

	public boolean func_50076_f(World par1World, int par2, int par3, int par4, int par5) {
		return par1World.getBlockId(par2, par3, par4) == blockID && (par1World.getBlockMetadata(par2, par3, par4) & 3) == par5;
	}

	@Override
	protected int damageDropped(int i) {
		return i & 3;
	}
}
