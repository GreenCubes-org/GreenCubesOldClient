package net.minecraft.src;

import java.util.List;

public class ItemArrow extends Item {
	
	protected final float damageMultipler;
	protected final float accuracy;

	public ItemArrow(int i, float damageMultipler, float accuracy) {
		super(i);
		this.damageMultipler = damageMultipler;
		this.accuracy = accuracy;
	}

	@Override
	public void appendAttributes(ItemStack itemstack, List<String> list) {
		list.add(String.format("\2477Урон: \247f%.1f", damageMultipler));
		list.add(String.format("\2477Точность: \247f%.1f", accuracy));
		super.appendAttributes(itemstack, list);
	}
}
