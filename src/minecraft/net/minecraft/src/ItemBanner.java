package net.minecraft.src;

public class ItemBanner extends ItemMultidata {

	public ItemBanner(int i, String[] names, int[] textures) {
		super(i, names, textures);
	}

	@Override
	public boolean noDrop() {
		return true;
	}

}
