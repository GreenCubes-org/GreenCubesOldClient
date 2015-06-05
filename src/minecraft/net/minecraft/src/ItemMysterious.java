package net.minecraft.src;

public class ItemMysterious extends Item {

	public ItemMysterious(int i) {
		super(i);
		setMaxStackSize(1);
	}

	@Override
	public String getTranslatedName(ItemStack itemstack) {
		return new StringBuilder().append(StringTranslate.getInstance().translateNamedKey(getUntranslatedName(itemstack))).toString().trim() + " #" + itemstack.itemDamage;
	}

}
