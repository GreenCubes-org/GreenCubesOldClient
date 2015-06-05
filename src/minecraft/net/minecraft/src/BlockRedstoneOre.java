// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            Block, Material, World, Item, 
//            ItemStack, EntityPlayer, Entity

public class BlockRedstoneOre extends Block {

	private boolean glowing;

	public BlockRedstoneOre(int i, int j, boolean flag) {
		super(i, j, Material.rock);
		if(flag) {
			setTickOnLoad(true);
		}
		glowing = flag;
	}

	@Override
	public int tickRate() {
		return 30;
	}

	@Override
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer, int face) {
		func_320_h(world, i, j, k);
		super.onBlockClicked(world, i, j, k, entityplayer, face);
	}

	@Override
	public void onEntityWalking(World world, int i, int j, int k, Entity entity) {
		func_320_h(world, i, j, k);
		super.onEntityWalking(world, i, j, k, entity);
	}

	@Override
	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		func_320_h(world, i, j, k);
		return super.blockActivated(world, i, j, k, entityplayer);
	}

	private void func_320_h(World world, int i, int j, int k) {
		if(world.multiplayerWorld)
			return;
		func_319_i(world, i, j, k);
		if(blockID == Block.oreRedstone.blockID) {
			world.setBlockWithNotify(i, j, k, Block.oreRedstoneGlowing.blockID);
		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		if(blockID == Block.oreRedstoneGlowing.blockID) {
			world.setBlockWithNotify(i, j, k, Block.oreRedstone.blockID);
		}
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return Item.redstone.shiftedIndex;
	}

	@Override
	public int func_40198_a(int i, Random random) {
		return quantityDropped(random) + random.nextInt(i + 1);
	}

	@Override
	public int quantityDropped(Random random) {
		return 4 + random.nextInt(2);
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if(glowing)
			func_319_i(world, i, j, k);
	}

	private void func_319_i(World world, int i, int j, int k) {
		Random random = world.rand;
		double d = 0.0625D;
		for(int l = 0; l < 6; l++) {
			double d1 = i + random.nextFloat();
			double d2 = j + random.nextFloat();
			double d3 = k + random.nextFloat();
			if(l == 0 && !world.isBlockOpaqueCube(i, j + 1, k)) {
				d2 = (j + 1) + d;
			}
			if(l == 1 && !world.isBlockOpaqueCube(i, j - 1, k)) {
				d2 = (j + 0) - d;
			}
			if(l == 2 && !world.isBlockOpaqueCube(i, j, k + 1)) {
				d3 = (k + 1) + d;
			}
			if(l == 3 && !world.isBlockOpaqueCube(i, j, k - 1)) {
				d3 = (k + 0) - d;
			}
			if(l == 4 && !world.isBlockOpaqueCube(i + 1, j, k)) {
				d1 = (i + 1) + d;
			}
			if(l == 5 && !world.isBlockOpaqueCube(i - 1, j, k)) {
				d1 = (i + 0) - d;
			}
			if(d1 < i || d1 > (i + 1) || d2 < 0.0D || d2 > (j + 1) || d3 < k || d3 > (k + 1)) {
				world.spawnParticle("reddust", d1, d2, d3, 0.0D, 0.0D, 0.0D);
			}
		}

	}

	@Override
	protected ItemStack func_41049_c_(int i) {
		return new ItemStack(Block.oreRedstone);
	}
}
