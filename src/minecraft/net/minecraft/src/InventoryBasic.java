// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;

// Referenced classes of package net.minecraft.src:
//            IInventory, ItemStack, IInvBasic, EntityPlayer

public class InventoryBasic implements IInventory {

	private String inventoryTitle;
	private int slotsCount;
	private ItemStack inventoryContents[];
	private List field_20073_d;

	public InventoryBasic(String s, int i) {
		inventoryTitle = s;
		slotsCount = i;
		inventoryContents = new ItemStack[i];
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inventoryContents[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(inventoryContents[i] != null) {
			if(inventoryContents[i].stackSize <= j) {
				ItemStack itemstack = inventoryContents[i];
				inventoryContents[i] = null;
				onInventoryChanged();
				return itemstack;
			}
			ItemStack itemstack1 = inventoryContents[i].splitStack(j);
			if(inventoryContents[i].stackSize == 0) {
				inventoryContents[i] = null;
			}
			onInventoryChanged();
			return itemstack1;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventoryContents[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
		onInventoryChanged();
	}

	@Override
	public int getSizeInventory() {
		return slotsCount;
	}

	@Override
	public String getInvName() {
		return inventoryTitle;
	}

	@Override
	public int getInventoryStackLimit() {
		return 32768;
	}

	@Override
	public void onInventoryChanged() {
		if(field_20073_d != null) {
			for(int i = 0; i < field_20073_d.size(); i++) {
				((IInvBasic) field_20073_d.get(i)).func_20134_a(this);
			}

		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}
}
