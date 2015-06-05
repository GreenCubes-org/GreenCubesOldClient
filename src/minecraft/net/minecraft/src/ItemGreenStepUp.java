package net.minecraft.src;

import java.util.List;

public class ItemGreenStepUp extends ItemBlock {

	public ItemGreenStepUp(int i) {
		super(i);
	}

	@Override
	public void appendDescription(ItemStack itemstack, List list) {
		list.add("\2477(Верхний)");
	}
}
