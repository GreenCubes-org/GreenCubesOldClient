package net.minecraft.src;

import java.util.List;

public class ItemSlabUp extends ItemSlab {

	public ItemSlabUp(int i) {
		super(i);

	}

	@Override
	public void appendDescription(ItemStack itemstack, List list) {
		list.add("\2477(Верхний)");
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) {
		int i = itemstack.getItemDamage();
		if(i < 0 || i >= BlockStep.field_22037_a.length) {
			i = 0;
		}
		return (new StringBuilder()).append(super.getItemName()).append(".").append(BlockStep.field_22037_a[i]).toString();
	}
}
