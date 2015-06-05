package net.minecraft.src;

import org.greencubes.items.unique.DecorItemQuality;

public class ItemDecorArmor extends ItemArmorRepairable {

	public ItemDecorArmor(int i, EnumArmorMaterial enumarmormaterial, int j, int k, int toFix) {
		super(i, enumarmormaterial, j, k, toFix);
	}

	@Override
	public DecorItemQuality getDecorQuality() {
		return DecorItemQuality.NORMAL_WEAR;
	}

}
