package net.minecraft.src;

import java.util.List;

public class ItemHalfBlock extends ItemBlock {

	public ItemHalfBlock(int i) {
		super(i);
	}

	@Override
	public void appendDescription(ItemStack itemstack, List list) {
		list.add("\2477(Боковой)");
	}

}
