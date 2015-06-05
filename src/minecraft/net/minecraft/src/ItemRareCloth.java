package net.minecraft.src;

import org.greencubes.items.unique.DecorItemQuality;
import org.greencubes.items.unique.DecorItemStatus;

public class ItemRareCloth extends ItemCloth {

	public DecorItemQuality quality;
	public DecorItemStatus status;
	
	public ItemRareCloth(int i, int j, int k, DecorItemQuality quality) {
		this(i, j, k, quality, null);
	}

	public ItemRareCloth(int i, int j, int k, DecorItemQuality quality, DecorItemStatus status) {
		super(i, j, k);
		this.quality = quality;
		this.status = status;
	}

	@Override
	public boolean noDrop() {
		return true;
	}

	@Override
	public DecorItemQuality getDecorQuality() {
		return quality;
	}
	
	@Override
	public DecorItemStatus getDecorStatus() {
		return status;
	}

}
