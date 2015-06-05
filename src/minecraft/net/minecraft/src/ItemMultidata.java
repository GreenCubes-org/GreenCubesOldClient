package net.minecraft.src;

public class ItemMultidata extends Item {

	protected String[] names;
	protected int[] textures;
	
	public ItemMultidata(int i, String[] names, int[] textures) {
		super(i);
		this.names = names;
		this.textures = textures;
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) {
		return "item." + names[itemstack.itemDamage % names.length];
	}
	
	@Override
	public int getIconFromDamage(int i) {
		return textures[i % textures.length];
	}

}
