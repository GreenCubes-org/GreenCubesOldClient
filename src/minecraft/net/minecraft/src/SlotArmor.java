// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            Slot, ItemStack, ItemArmor, Item, 
//            Block, ContainerPlayer, IInventory

class SlotArmor extends Slot {

	public static final int[] icons = new int[] {GreenTextures.stub_helmet, GreenTextures.stub_chestplate, GreenTextures.stub_leggins, GreenTextures.stub_boots, GreenTextures.stub_gloves};
	public final int armorType; /* synthetic field */
	public final ContainerPlayer inventory; /* synthetic field */

	public SlotArmor(ContainerPlayer containerplayer, IInventory iinventory, int slot, int x, int y, int armorSlot) {
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
		Item item = itemstack.getItem();
		if(item instanceof ItemArmor)
			return ((ItemArmor) item).slot == armorType;
		if(armorType == 3 && (item instanceof ItemSpeedBoots || item instanceof ItemValenki))
			return true;
		if(armorType == 4 && item instanceof ItemWearable)
			return ((ItemWearable) item).slot == armorType;
		return false;
	}
	
	@Override
	public int getBackgroundIconIndex() {
		return icons[armorType];
	}
}
