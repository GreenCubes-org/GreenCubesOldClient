package net.minecraft.src;

public class SlotTrashcan extends Slot {

	public SlotTrashcan(IInventory iinventory, int slot, int x, int y) {
		super(iinventory, slot, x, y);
	}

	@Override
	public int getBackgroundIconIndex() {
		return GreenTextures.trashcan;
	}
	
	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return !itemstack.noDrop();
	}
}
