package net.minecraft.src;

public class TMIItemMushroomCap extends ItemBlock {
	public TMIItemMushroomCap(int i) {
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	/**
	 * Returns the metadata of the block which this Item (ItemBlock) can place
	 */
	public int getMetadata(int i) {
		return i;
	}
}
