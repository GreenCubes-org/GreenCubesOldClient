package net.minecraft.src;

import java.util.List;

public class ItemScrollCoord extends ItemCoupon {

	public ItemScrollCoord(int i) {
		super(i);
	}
	
	@Override
	public void appendAttributes(ItemStack itemstack, List<String> list) {
		if(itemstack.nbtData != null && itemstack.nbtData.hasKey("Target")) {
			int[] coords = itemstack.nbtData.getIntArray("Target");
			list.add("\247bКоординаты: " + coords[0] + ", " + coords[1] + ", " + coords[2]);
			if(itemstack.nbtData.hasKey("LocDescr"))
				list.add("\2477Описание: " + itemstack.nbtData.getString("LocDescr"));
			if(itemstack.nbtData.hasKey("Author"))
				list.add("\2477Автор: " + itemstack.nbtData.getString("Author"));
			if(itemstack.getItemDamage() == 1)
				list.add("\2474\247iЭтот свиток защищён");
		}
	}

}
