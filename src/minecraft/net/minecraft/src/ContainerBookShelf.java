package net.minecraft.src;

import net.minecraft.client.Minecraft;

/**
 * GreenCubes
 * Books mod
 * @author Rena
 *
 */
public class ContainerBookShelf extends Container {

	private IInventory lowerChestInventory;
	private int numRows;
	private InventoryPlayer playerInv;
	private Minecraft mc;

	private boolean locked;

	public ContainerBookShelf(Minecraft mc, boolean canEdit, InventoryPlayer iinventory, IInventory iinventory1) {
		this.locked = !canEdit;
		this.mc = mc;
		playerInv = iinventory;
		lowerChestInventory = iinventory1;
		numRows = iinventory1.getSizeInventory() / 9;
		iinventory1.openChest();
		int i = (numRows - 4) * 18;
		for(int j = 0; j < numRows; j++) {
			for(int i1 = 0; i1 < 9; i1++) {
				addSlot(new SlotBook(iinventory1, i1 + j * 9, 8 + i1 * 18, 18 + j * 18));
			}

		}

		for(int k = 0; k < 3; k++) {
			for(int j1 = 0; j1 < 9; j1++) {
				addSlot(new Slot(iinventory, j1 + k * 9 + 9, 8 + j1 * 18, 103 + k * 18 + i));
			}

		}

		for(int l = 0; l < 9; l++) {
			addSlot(new Slot(iinventory, l, 8 + l * 18, 161 + i));
		}

	}

	@Override
	public void onCraftGuiClosed(EntityPlayer entityplayer) {
		super.onCraftGuiClosed(entityplayer);
	}

	@Override
	public ItemStack slotClick(int i, int j, boolean flag, EntityPlayer entityplayer) {
		if(flag)
			return null;
		if(i >= 0) {
			Slot clickedSlot = inventorySlots.get(i);
			if(clickedSlot.inventory == lowerChestInventory && j == 1) {
				ItemStack is = clickedSlot.getStack();
				if(is != null && is.hasNBTData()) {
					NBTTagCompound itemTag = is.getNBTData();
					if(itemTag.hasKey("bookID")) {
						int bookId = itemTag.getInteger("bookID");
						mc.getSendQueue().addToSendQueue(new Packet224AskBook(i, bookId));
					}
				}
				return null;
			}
		}
		ItemStack itemstack = null;
		if(j > 1) {
			return null;
		}
		if(j == 0 || j == 1) {
			InventoryPlayer inventoryplayer = entityplayer.inventory;
			if(i == -999) {
				if(inventoryplayer.getItemStack() != null && i == -999) {
					if(j == 0) {
						entityplayer.dropPlayerItem(inventoryplayer.getItemStack());
						inventoryplayer.setItemStack(null);
					}
					if(j == 1) {
						entityplayer.dropPlayerItem(inventoryplayer.getItemStack().splitStack(1));
						if(inventoryplayer.getItemStack().stackSize == 0) {
							inventoryplayer.setItemStack(null);
						}
					}
				}
			} else if(flag) {
				if(!locked) {
					ItemStack itemstack1 = shiftTransfer(i);
					if(itemstack1 != null) {
						int k = itemstack1.itemID;
						itemstack = itemstack1.copy();
						Slot slot1 = inventorySlots.get(i);
						if(slot1 != null && slot1.getStack() != null && slot1.getStack().itemID == k) {
							returnItemAfterShiftClick(i, j, flag, entityplayer);
						}
					}
				}
			} else {
				if(i < 0) {
					return null;
				}
				Slot slot = inventorySlots.get(i);
				if(slot != null) {
					if(slot.inventory == lowerChestInventory && locked)
						return null;
					slot.onSlotChanged();
					ItemStack itemstack2 = slot.getStack();
					ItemStack itemstack3 = inventoryplayer.getItemStack();
					if(itemstack2 != null) {
						itemstack = itemstack2.copy();
					}
					if(itemstack2 == null) {
						if(itemstack3 != null && slot.isItemValid(itemstack3)) {
							int l = j != 0 ? 1 : itemstack3.stackSize;
							if(l > slot.getSlotStackLimit()) {
								l = slot.getSlotStackLimit();
							}
							slot.putStack(itemstack3.splitStack(l));
							if(itemstack3.stackSize == 0) {
								inventoryplayer.setItemStack(null);
							}
						}
					} else if(itemstack3 == null) {
						int i1 = j != 0 ? (itemstack2.stackSize + 1) / 2 : itemstack2.stackSize;
						ItemStack itemstack5 = slot.decrStackSize(i1);
						inventoryplayer.setItemStack(itemstack5);
						if(itemstack2.stackSize == 0) {
							slot.putStack(null);
						}
						slot.onPickupFromSlot(inventoryplayer.getItemStack());
					} else if(slot.isItemValid(itemstack3)) {
						// GreenCubes: added !ItemStack.canStackItems(itemstack2, itemstack3) || 
						if(!ItemStack.canStackItems(itemstack2, itemstack3) || itemstack2.itemID != itemstack3.itemID || itemstack2.getHasSubtypes() && itemstack2.getItemDamage() != itemstack3.getItemDamage()) {
							if(itemstack3.stackSize <= slot.getSlotStackLimit()) {
								ItemStack itemstack4 = itemstack2;
								slot.putStack(itemstack3);
								inventoryplayer.setItemStack(itemstack4);
							}
						} else {
							int j1 = j != 0 ? 1 : itemstack3.stackSize;
							if(j1 > slot.getSlotStackLimit() - itemstack2.stackSize) {
								j1 = slot.getSlotStackLimit() - itemstack2.stackSize;
							}
							if(j1 > itemstack3.getMaxStackSize() - itemstack2.stackSize) {
								j1 = itemstack3.getMaxStackSize() - itemstack2.stackSize;
							}
							itemstack3.splitStack(j1);
							if(itemstack3.stackSize == 0) {
								inventoryplayer.setItemStack(null);
							}
							itemstack2.stackSize += j1;
						}
					} else
					// GreenCubes added ItemStack.canStackItems(itemstack2, itemstack3) && 
					if(ItemStack.canStackItems(itemstack2, itemstack3) && itemstack2.itemID == itemstack3.itemID && itemstack3.getMaxStackSize() > 1 && (!itemstack2.getHasSubtypes() || itemstack2.getItemDamage() == itemstack3.getItemDamage())) {
						int k1 = itemstack2.stackSize;
						if(k1 > 0 && k1 + itemstack3.stackSize <= itemstack3.getMaxStackSize()) {
							itemstack3.stackSize += k1;
							itemstack2.splitStack(k1);
							if(itemstack2.stackSize == 0) {
								slot.putStack(null);
							}
							slot.onPickupFromSlot(inventoryplayer.getItemStack());
						}
					}
				}
			}
		}
		return itemstack;
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
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return false;
	}
}
