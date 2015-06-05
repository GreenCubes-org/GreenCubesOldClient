// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            Container, Slot, TileEntityDispenser, ItemStack, 
//            IInventory, EntityPlayer

public class ContainerDispenser extends Container {

	private TileEntityDispenser tileEntityDispenser;

	public ContainerDispenser(IInventory iinventory, TileEntityDispenser tileentitydispenser) {
		tileEntityDispenser = tileentitydispenser;
		for(int i = 0; i < 3; i++) {
			for(int l = 0; l < 3; l++) {
				addSlot(new Slot(tileentitydispenser, l + i * 3, 62 + l * 18, 17 + i * 18));
			}

		}

		for(int j = 0; j < 3; j++) {
			for(int i1 = 0; i1 < 9; i1++) {
				Slot s = new Slot(iinventory, i1 + j * 9 + 9, 8 + i1 * 18, 84 + j * 18);
				addSlot(s);
				playerRightSlots.set(j * 9 + i1 + 9, s);
			}

		}

		for(int k = 0; k < 9; k++) {
			Slot s = new Slot(iinventory, k, 8 + k * 18, 142);
			addSlot(s);
			playerRightSlots.set(k, s);
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return tileEntityDispenser.isUseableByPlayer(entityplayer);
	}

	@Override
	public ItemStack shiftTransfer(int i) {
		ItemStack itemstack = null;
		Slot slot = inventorySlots.get(i);
		if(slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if(i < 9) {
				if(!putToPlayerInvenoty(itemstack1, false)) {
					return null;
				}
			} else if(!mergeItemStack(itemstack1, 0, 9, false)) {
				return null;
			}
			if(itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
			if(itemstack1.stackSize != itemstack.stackSize) {
				slot.onPickupFromSlot(itemstack1);
			} else {
				return null;
			}
		}
		return itemstack;
	}
}
