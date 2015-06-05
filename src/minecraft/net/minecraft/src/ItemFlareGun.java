package net.minecraft.src;

import org.greencubes.items.unique.DecorItemQuality;

public class ItemFlareGun extends Item {

	public ItemFlareGun(int i) {
		super(i);
		setMaxStackSize(1);
		setFull3D();
	}

	@Override
	public boolean noDrop() {
		return true;
	}

	@Override
	public DecorItemQuality getDecorQuality() {
		return DecorItemQuality.NORMAL;
	}

	@Override
	public float getRenderShift() {
		return 0.22f;
	}

}
