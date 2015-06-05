// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            Block, Material, World, EntityTNTPrimed, 
//            ItemStack, EntityPlayer, Item

public class BlockTNT extends Block {

	public BlockTNT(int i, int j) {
		super(i, j, Material.tnt);
	}

	@Override
	public int getBlockTextureFromSide(int i) {
		if(i == 0) {
			return blockIndexInTexture + 2;
		}
		if(i == 1) {
			return blockIndexInTexture + 1;
		} else {
			return blockIndexInTexture;
		}
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		super.onBlockAdded(world, i, j, k);
		if(world.isBlockIndirectlyGettingPowered(i, j, k)) {
			onBlockDestroyedByPlayer(world, i, j, k, 1);
			world.setBlockWithNotify(i, j, k, 0);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		if(l > 0 && Block.blocksList[l].canProvidePower() && world.isBlockIndirectlyGettingPowered(i, j, k)) {
			onBlockDestroyedByPlayer(world, i, j, k, 1);
			world.setBlockWithNotify(i, j, k, 0);
		}
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int i, int j, int k) {
		EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, i + 0.5F, j + 0.5F, k + 0.5F);
		entitytntprimed.fuse = world.rand.nextInt(entitytntprimed.fuse / 4) + entitytntprimed.fuse / 8;
		world.entityJoinedWorld(entitytntprimed);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l) {
		if(world.multiplayerWorld) {
			return;
		}
		if((l & 1) == 0) {
			dropBlockAsItem_do(world, i, j, k, new ItemStack(Block.tnt.blockID, 1, 0));
		} else {
			EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, i + 0.5F, j + 0.5F, k + 0.5F);
			world.entityJoinedWorld(entitytntprimed);
			world.playSoundAtEntity(entitytntprimed, "random.fuse", 1.0F, 1.0F);
		}
	}

	@Override
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer, int face) {
		if(entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().itemID == Item.flintAndSteel.shiftedIndex)
			world.setBlockMetadata(i, j, k, 1);
		super.onBlockClicked(world, i, j, k, entityplayer, face);
	}

	@Override
	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		return super.blockActivated(world, i, j, k, entityplayer);
	}

	@Override
	protected ItemStack func_41049_c_(int i) {
		return null;
	}
}
