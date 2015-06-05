// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            IInventory, ItemStack, EntityPlayer

public class InventoryCraftResult implements IInventory {

	private ItemStack stackResult[];

	public InventoryCraftResult() {
		stackResult = new ItemStack[1];
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return stackResult[i];
	}

	@Override
	public String getInvName() {
		return "Result";
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(stackResult[i] != null) {
			ItemStack itemstack = stackResult[i];
			stackResult[i] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		stackResult[i] = itemstack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 32768;
	}

	@Override
	public void onInventoryChanged() {
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
