package net.minecraft.src;

import java.util.List;

public class ItemPremiumCoupon extends ItemCoupon {

	public ItemPremiumCoupon(int i) {
		super(i);
	}

	@Override
	public String getTranslatedName(ItemStack itemstack) {
		int damage = itemstack.getItemDamage();
		switch(damage) {
		case 7:
			return "Купон на премиум, 7 дней";
		case 14:
			return "Купон на премиум, 14 дней";
		case 31:
			return "Купон на премиум, 31 день";
		case 62:
			return "Купон на премиум, 62 дня";
		}
		return super.getTranslatedName(itemstack);
	}

	@Override
	public void appendDescription(ItemStack itemstack, List list) {
		int damage = itemstack.getItemDamage();
		list.add("\247rffd2d2d2Активирует премиум-аккаунт на " + damage + " дней");
	}

}
