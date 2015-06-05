package net.minecraft.src;

import java.util.List;

public class ItemDurabilityMod extends Item {

	public ItemDurabilityMod(int i) {
		super(i);
	}
	
	@Override
	public void appendDescription(ItemStack itemstack, List<String> list) {
		if(itemstack.nbtData != null && itemstack.nbtData.hasKey("DurAdd"))
			list.add("\247rffaaffff+" + itemstack.nbtData.getInteger("DurAdd") + " прочности");
		if(itemstack.nbtData != null && itemstack.nbtData.hasKey("Durability"))
			list.add("\247rffaaffff+" + (int) (itemstack.nbtData.getFloat("Durability") * 100) + "% прочности");
	}

}
