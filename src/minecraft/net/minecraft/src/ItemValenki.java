package net.minecraft.src;

import java.util.List;

import org.greencubes.items.unique.DecorItemQuality;

public class ItemValenki extends ItemCloth {

	protected final int bonus;

	public ItemValenki(int i, int j, int bonus) {
		super(i, j, 3);
		this.bonus = bonus;
		this.setMaxDamage(0);
	}
	
	@Override
	public boolean noDrop() {
		return true;
	}

	@Override
	public DecorItemQuality getDecorQuality() {
		return DecorItemQuality.IMPROOVED;
	}

	@Override
	public void appendAttributes(ItemStack itemstack, List<String> list) {
		int bonus = this.bonus;
		if(itemstack.nbtData != null && itemstack.nbtData.hasKey("SnowBoost"))
			bonus = itemstack.nbtData.getInteger("SnowBoost");
		if(bonus > 0)
			list.add("\247rffaaffff+" + bonus + " � �������� ������������ �� �����");
	}

}