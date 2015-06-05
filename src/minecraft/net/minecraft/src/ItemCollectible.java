package net.minecraft.src;

import org.greencubes.items.unique.DecorItemQuality;

public class ItemCollectible extends Item {
	
	private String[] names;
	private int[] icons;
	private DecorItemQuality quality;

	public ItemCollectible(int id, String[] names, int[] icons, DecorItemQuality quality) {
		super(id);
		this.names = names;
		this.icons = icons;
		this.quality = quality;
	}
	
	@Override
	public int getIconFromDamage(int i) {
		if(i >= icons.length)
			return icons[icons.length - 1];
		return icons[i];
	}
	
	@Override
	public String getItemNameIS(ItemStack itemstack) {
		if(itemstack.getItemDamage() >= names.length)
			return "item." + names[names.length - 1];
		return "item." + names[itemstack.getItemDamage()];
	}

	@Override
	public DecorItemQuality getDecorQuality() {
		return quality;
	}

	@Override
	public boolean noDrop() {
		return true;
	}

}