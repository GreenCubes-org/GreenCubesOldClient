// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import net.minecraft.client.Minecraft;

// Referenced classes of package net.minecraft.src:
//            Container, InventoryCrafting, InventoryCraftResult, SlotCrafting, 
//            InventoryPlayer, Slot, SlotArmor, CraftingManager, 
//            IInventory, EntityPlayer, ItemStack

public class ContainerPlayer extends Container {

	public InventoryCrafting craftMatrix;
	public IInventory craftResult;
	public boolean isSinglePlayer;

	public ContainerPlayer(InventoryPlayer inventoryplayer) {
		this(inventoryplayer, true);
	}

	public ContainerPlayer(InventoryPlayer inventoryplayer, boolean flag) {
		craftMatrix = new InventoryCrafting(this, 2, 2);
		craftResult = new InventoryCraftResult();
		isSinglePlayer = flag;
		addSlot(new SlotCrafting(inventoryplayer.player, craftMatrix, craftResult, 0, 152, 34));
		for(int i = 0; i < 2; i++) {
			for(int i1 = 0; i1 < 2; i1++) {
				addSlot(new Slot(craftMatrix, i1 + i * 2, 96 + i1 * 18, 24 + i * 18));
			}

		}

		for(int j = 0; j < 4; j++) {
			int j1 = j;
			addSlot(new SlotArmor(this, inventoryplayer, inventoryplayer.getSizeInventory() - 7 - j, 26, 8 + j * 18, j1));
		}
		
		for(int k = 0; k < 3; k++) {
			for(int k1 = 0; k1 < 9; k1++) {
				Slot s = new Slot(inventoryplayer, k1 + (k + 1) * 9, 8 + k1 * 18, 84 + k * 18);
				addSlot(s);
				playerRightSlots.set(9 + k * 9 + k1, s);
			}

		}

		for(int l = 0; l < 9; l++) {
			Slot s = new Slot(inventoryplayer, l, 8 + l * 18, 142);
			addSlot(s);
			playerRightSlots.set(l, s);
		}
		
		for(int j = 0; j < 4; j++) {
			int j1 = j;
			addSlot(new SlotCloth(this, inventoryplayer, inventoryplayer.getSizeInventory() - 3 - j, 8, 8 + j * 18, j1));
		}
		
		addSlot(new SlotTrashcan(inventoryplayer, inventoryplayer.getSizeInventory() - 2, 152, 62));
		
		addSlot(new SlotArmor(this, inventoryplayer, inventoryplayer.getSizeInventory() - 1, 96, 62, 4));

		onCraftMatrixChanged(craftMatrix);
	}

	@Override
	public void onCraftMatrixChanged(IInventory iinventory) {
		if(!Minecraft.theMinecraft.isMultiplayerWorld())
			craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(craftMatrix));
	}

	@Override
	public void onCraftGuiClosed(EntityPlayer entityplayer) {
		super.onCraftGuiClosed(entityplayer);
		if(Minecraft.theMinecraft.isMultiplayerWorld())
			return;
		for(int i = 0; i < 4; i++) {
			ItemStack itemstack = craftMatrix.getStackInSlot(i);
			if(itemstack != null) {
				entityplayer.dropPlayerItem(itemstack);
				craftMatrix.setInventorySlotContents(i, null);
			}
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public ItemStack shiftTransfer(int i) {
		ItemStack itemstack = null;
		Slot slot = inventorySlots.get(i);
		if(slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if(i == 0) {
				if(!putToPlayerInvenoty(itemstack1, false)) {
					return null;
				}
			} else if(i >= 9 && i < 36) {
				if(!mergeItemStack(itemstack1, 36, 45, false)) {
					return null;
				}
			} else if(i >= 36 && i < 45) {
				if(!mergeItemStack(itemstack1, 9, 36, false)) {
					return null;
				}
			} else if(!putToPlayerInvenoty(itemstack1, false)) {
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
