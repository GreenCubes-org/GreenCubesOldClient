package net.minecraft.src;

public class ItemSetBox extends Item {

	protected ItemSetBox(int i) {
		super(i);
		setUsable();
	}
	
	@Override
	public String getTranslatedName(ItemStack itemstack) {
		return StringTranslate.getInstance().translateNamedKey(getUntranslatedName(itemstack)).trim() + " #" + itemstack.getItemDamage();
	}

}
