package net.minecraft.src;

import org.greencubes.items.unique.DecorItemQuality;
import org.greencubes.items.unique.DecorItemStatus;

public class ItemClothImmortal extends ItemCloth {

	public ItemClothImmortal(int i, int j, int k) {
		super(i, j, k);
		setMaxDamage(-1);
	}

	@Override
	public boolean noDrop() {
		return true;
	}

	@Override
	public DecorItemQuality getDecorQuality() {
		return DecorItemQuality.NORMAL_WEAR;
	}

	@Override
	public DecorItemStatus getDecorStatus() {
		return DecorItemStatus.IMMORTAL;
	}

}
