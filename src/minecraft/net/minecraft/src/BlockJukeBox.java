// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            BlockContainer, Material, World, TileEntityRecordPlayer, 
//            EntityItem, ItemStack, EntityPlayer, TileEntity

public class BlockJukeBox extends BlockContainer {

	protected BlockJukeBox(int i, int j) {
		super(i, j, Material.wood);
	}

	@Override
	public int getBlockTextureFromSide(int i) {
		return blockIndexInTexture + (i != 1 ? 0 : 1);
	}

	@Override
	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		if(world.getBlockMetadata(i, j, k) == 0) {
			return false;
		} else {
			func_28038_b_(world, i, j, k);
			return true;
		}
	}

	public void ejectRecord(World world, int i, int j, int k, int l) {
		if(world.multiplayerWorld) {
			return;
		}
		TileEntityRecordPlayer tileentityrecordplayer = (TileEntityRecordPlayer) world.getBlockTileEntity(i, j, k);
		if(tileentityrecordplayer == null) {
			return;
		} else {
			tileentityrecordplayer.record = l;
			tileentityrecordplayer.onInventoryChanged();
			world.setBlockMetadataWithNotify(i, j, k, 1);
			return;
		}
	}

	public void func_28038_b_(World world, int i, int j, int k) {
		if(world.multiplayerWorld) {
			return;
		}
		TileEntityRecordPlayer tileentityrecordplayer = (TileEntityRecordPlayer) world.getBlockTileEntity(i, j, k);
		if(tileentityrecordplayer == null) {
			return;
		}
		int l = tileentityrecordplayer.record;
		if(l == 0) {
			return;
		} else {
			world.playAuxSFX(1005, i, j, k, 0);
			world.playRecord(null, i, j, k);
			tileentityrecordplayer.record = 0;
			tileentityrecordplayer.onInventoryChanged();
			world.setBlockMetadataWithNotify(i, j, k, 0);
			int i1 = l;
			float f = 0.7F;
			double d = (world.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
			double d1 = (world.rand.nextFloat() * f) + (1.0F - f) * 0.20000000000000001D + 0.59999999999999998D;
			double d2 = (world.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
			EntityItem entityitem = new EntityItem(world, i + d, j + d1, k + d2, new ItemStack(i1, 1, 0));
			entityitem.delayBeforeCanPickup = 10;
			world.entityJoinedWorld(entityitem);
			return;
		}
	}

	@Override
	public void onBlockRemoval(World world, int i, int j, int k) {
		func_28038_b_(world, i, j, k);
		super.onBlockRemoval(world, i, j, k);
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int i, int j, int k, int l, float f, int i1) {
		if(world.multiplayerWorld) {
			return;
		} else {
			super.dropBlockAsItemWithChance(world, i, j, k, l, f, 0);
			return;
		}
	}

	@Override
	public TileEntity getBlockEntity() {
		return new TileEntityRecordPlayer();
	}
}
