package net.minecraft.src;

public class EntityLivingInventory implements IInventory {

	private final EntityLiving entity;

	public EntityLivingInventory(EntityLiving el) {
		this.entity = el;
	}

	@Override
	public int getSizeInventory() {
		return 5;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return entity.getEquipment(i);
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
	}

	@Override
	public String getInvName() {
		return null;
	}

	@Override
	public int getInventoryStackLimit() {
		return Short.MAX_VALUE;
	}

	@Override
	public void onInventoryChanged() {
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return false;
	}

	@Override
	public void openChest() {

	}

	@Override
	public void closeChest() {

	}

}
