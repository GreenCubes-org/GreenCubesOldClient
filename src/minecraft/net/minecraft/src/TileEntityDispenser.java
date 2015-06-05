// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            TileEntity, IInventory, ItemStack, NBTTagCompound, 
//            NBTTagList, World, EntityPlayer

public class TileEntityDispenser extends TileEntity implements IInventory {

	private ItemStack dispenserContents[];
	private Random dispenserRandom;
	private String name = "Раздатчик";

	public TileEntityDispenser() {
		dispenserContents = new ItemStack[9];
		dispenserRandom = new Random();
	}

	@Override
	public int getSizeInventory() {
		return 9;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return dispenserContents[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(dispenserContents[i] != null) {
			if(dispenserContents[i].stackSize <= j) {
				ItemStack itemstack = dispenserContents[i];
				dispenserContents[i] = null;
				onInventoryChanged();
				return itemstack;
			}
			ItemStack itemstack1 = dispenserContents[i].splitStack(j);
			if(dispenserContents[i].stackSize == 0) {
				dispenserContents[i] = null;
			}
			onInventoryChanged();
			return itemstack1;
		} else {
			return null;
		}
	}

	public ItemStack getRandomStackFromInventory() {
		int i = -1;
		int j = 1;
		for(int k = 0; k < dispenserContents.length; k++) {
			if(dispenserContents[k] != null && dispenserRandom.nextInt(j++) == 0) {
				i = k;
			}
		}

		if(i >= 0) {
			return decrStackSize(i, 1);
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		dispenserContents[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
		onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		dispenserContents = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.size(); i++) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.get(i);
			int j = nbttagcompound1.getByte("Slot") & 0xff;
			if(j >= 0 && j < dispenserContents.length) {
				dispenserContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < dispenserContents.length; i++) {
			if(dispenserContents[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				dispenserContents[i].writeToNBT(nbttagcompound1);
				nbttaglist.setTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);
	}

	@Override
	public int getInventoryStackLimit() {
		return 32768;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		}
		return entityplayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64D;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}
}
