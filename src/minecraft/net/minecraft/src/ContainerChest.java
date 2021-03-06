// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            Container, IInventory, Slot, ItemStack, 
//            EntityPlayer

public class ContainerChest extends Container {

	private IInventory lowerChestInventory;
	private int numRows;

	public ContainerChest(IInventory iinventory, IInventory iinventory1) {
		lowerChestInventory = iinventory1;
		numRows = (int) Math.ceil((float) iinventory1.getSizeInventory() / (float) 9);
		iinventory1.openChest();
		int i = (numRows - 4) * 18;
		int n = 0;
		full: for(int j = 0; j < numRows; j++) {
			for(int i1 = 0; i1 < 9; i1++) {
				addSlot(new Slot(iinventory1, i1 + j * 9, 8 + i1 * 18, 18 + j * 18));
				if(++n == iinventory1.getSizeInventory())
					break full;
			}

		}

		for(int k = 0; k < 3; k++) {
			for(int j1 = 0; j1 < 9; j1++) {
				Slot s = new Slot(iinventory, j1 + k * 9 + 9, 8 + j1 * 18, 103 + k * 18 + i);
				addSlot(s);
				playerRightSlots.set(9 + k * 9 + j1, s);
			}

		}

		for(int l = 0; l < 9; l++) {
			Slot s = new Slot(iinventory, l, 8 + l * 18, 161 + i);
			addSlot(s);
			playerRightSlots.set(l, s);
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return lowerChestInventory.isUseableByPlayer(entityplayer);
	}

	@Override
	public ItemStack shiftTransfer(int i) {
		ItemStack itemstack = null;
		Slot slot = inventorySlots.get(i);
		if(slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if(i < numRows * 9) {
				if(!putToPlayerInvenoty(itemstack1, false)) {
					return null;
				}
			} else if(!mergeItemStack(itemstack1, 0, numRows * 9, false)) {
				return null;
			}
			if(itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
		}
		return itemstack;
	}

	@Override
	public void onCraftGuiClosed(EntityPlayer entityplayer) {
		super.onCraftGuiClosed(entityplayer);
		lowerChestInventory.closeChest();
	}
}
