package net.minecraft.src;

import org.greencubes.items.unique.DecorItemStatus;

public class ItemFrozenFlaregun extends ItemFlareGun {

	public ItemFrozenFlaregun(int i) {
		super(i);
	}
	
	@Override
	public DecorItemStatus getDecorStatus() {
		return DecorItemStatus.GENUINE;
	}

}
