package net.minecraft.src;

import org.greencubes.items.unique.DecorItemQuality;

public class ItemArmorColorLeather extends ItemArmor {

	public ItemArmorColorLeather(int id, EnumArmorMaterial material, int texture, int slot) {
		super(id, material, texture, slot, -1);
	}
	
	@Override
	public DecorItemQuality getDecorQuality() {
		return DecorItemQuality.NORMAL_WEAR;
	}

}
