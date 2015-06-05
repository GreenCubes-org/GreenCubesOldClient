// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            Slot, ItemStack, ItemArmor, Item, 
//            Block, ContainerPlayer, IInventory

class SlotCloth extends Slot {

	public static final int[] icons = new int[] {GreenTextures.stub_helmet_decor, GreenTextures.stub_chestplate_decor, GreenTextures.stub_leggins_decor, GreenTextures.stub_boots_decor};
	public final int armorType;
	public final ContainerPlayer inventory;

	public SlotCloth(ContainerPlayer containerplayer, IInventory iinventory, int slot, int x, int y, int armorSlot) {
		super(iinventory, slot, x, y);
		inventory = containerplayer;
		armorType = armorSlot;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		if(itemstack.getItem() instanceof ItemCloth)
			return ((ItemCloth) itemstack.getItem()).slot == armorType;
		if(itemstack.getItem().shiftedIndex == Block.pumpkin.blockID)
			return armorType == 0;
		return false;
	}
	
	@Override
	public int getBackgroundIconIndex() {
		return icons[armorType];
	}
}
