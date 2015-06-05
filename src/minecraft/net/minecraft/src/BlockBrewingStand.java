// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            BlockContainer, Material, TileEntityBrewingStand, World, 
//            EntityPlayer, ItemStack, EntityItem, Item, 
//            TileEntity, AxisAlignedBB

public class BlockBrewingStand extends BlockContainer {

	private Random field_40214_a;

	public BlockBrewingStand(int i) {
		super(i, Material.iron);
		field_40214_a = new Random();
		blockIndexInTexture = 157;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return 25;
	}

	@Override
	public TileEntity getBlockEntity() {
		return new TileEntityBrewingStand();
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void getCollidingBoundingBoxes(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, ArrayList arraylist) {
		setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 0.875F, 0.5625F);
		super.getCollidingBoundingBoxes(world, i, j, k, axisalignedbb, arraylist);
		setBlockBoundsForItemRender();
		super.getCollidingBoundingBoxes(world, i, j, k, axisalignedbb, arraylist);
	}

	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
	}

	@Override
	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		if(world.multiplayerWorld) {
			return true;
		}
		TileEntityBrewingStand tileentitybrewingstand = (TileEntityBrewingStand) world.getBlockTileEntity(i, j, k);
		if(tileentitybrewingstand != null) {
			entityplayer.func_40180_a(tileentitybrewingstand);
		}
		return true;
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		double d = i + 0.4F + random.nextFloat() * 0.2F;
		double d1 = j + 0.7F + random.nextFloat() * 0.3F;
		double d2 = k + 0.4F + random.nextFloat() * 0.2F;
		world.spawnParticle("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public void onBlockRemoval(World world, int i, int j, int k) {
		TileEntity tileentity = world.getBlockTileEntity(i, j, k);
		if(tileentity != null && (tileentity instanceof TileEntityBrewingStand)) {
			TileEntityBrewingStand tileentitybrewingstand = (TileEntityBrewingStand) tileentity;
			label0: for(int l = 0; l < tileentitybrewingstand.getSizeInventory(); l++) {
				ItemStack itemstack = tileentitybrewingstand.getStackInSlot(l);
				if(itemstack == null) {
					continue;
				}
				float f = field_40214_a.nextFloat() * 0.8F + 0.1F;
				float f1 = field_40214_a.nextFloat() * 0.8F + 0.1F;
				float f2 = field_40214_a.nextFloat() * 0.8F + 0.1F;
				do {
					if(itemstack.stackSize <= 0) {
						continue label0;
					}
					int i1 = field_40214_a.nextInt(21) + 10;
					if(i1 > itemstack.stackSize) {
						i1 = itemstack.stackSize;
					}
					itemstack.stackSize -= i1;
					EntityItem entityitem = new EntityItem(world, i + f, j + f1, k + f2, new ItemStack(itemstack.itemID, i1, itemstack.getItemDamage()));
					float f3 = 0.05F;
					entityitem.motionX = (float) field_40214_a.nextGaussian() * f3;
					entityitem.motionY = (float) field_40214_a.nextGaussian() * f3 + 0.2F;
					entityitem.motionZ = (float) field_40214_a.nextGaussian() * f3;
					world.entityJoinedWorld(entityitem);
				} while(true);
			}

		}
		super.onBlockRemoval(world, i, j, k);
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return Item.brewingStand.shiftedIndex;
	}
}
