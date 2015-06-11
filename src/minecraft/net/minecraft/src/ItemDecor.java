package net.minecraft.src;

import org.greencubes.items.unique.DecorItemQuality;

public class ItemDecor extends Item {
	
	protected DecorItemQuality quality;

	public ItemDecor(int i) {
		super(i);
	}
	
	public ItemDecor setDecor(DecorItemQuality q) {
		this.quality = q;
		return this;
	}
	
	@Override
	public boolean noDrop() {
		return this.quality != null;
	}
	
	@Override
	public DecorItemQuality getDecorQuality() {
		return this.quality;
	}

}
