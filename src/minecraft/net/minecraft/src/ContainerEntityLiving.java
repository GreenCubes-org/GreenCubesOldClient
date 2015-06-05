package net.minecraft.src;

public class ContainerEntityLiving extends Container {

	public EntityLivingInventory inv;

	public ContainerEntityLiving(EntityLiving entityLiving) {
		inv = new EntityLivingInventory(entityLiving);
		addSlot(new Slot(inv, 0, 5, 25));
		addSlot(new Slot(inv, 4, 33, 25));
		addSlot(new Slot(inv, 3, 56, 25));
		addSlot(new Slot(inv, 2, 79, 25));
		addSlot(new Slot(inv, 1, 102, 25));
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return false;
	}

}
