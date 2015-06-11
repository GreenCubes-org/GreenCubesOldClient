package net.minecraft.src;

import org.greencubes.items.unique.DecorItemQuality;

public class ItemPackedNamedItem extends ItemGiftBagNy {

	protected static final String[] packedNames = new String[]{"cherryStick",
		"diamondPickaxe", "diamondShovel", "diamondAxe", "decorBow", "ironPickaxe", "ivyStick", "diamondSword",
		"bow.cherry", "bone"};

	protected ItemPackedNamedItem(int i) {
		super(i);
	}

	@Override
	public DecorItemQuality getDecorQuality() {
		return DecorItemQuality.NAMED;
	}

	@Override
	public boolean noDrop() {
		return true;
	}

	@Override
	public int getIconFromDamage(int i) {
		if(i == 0 || i == 6 || i == 9)
			return GreenTextures.packedstick;
		return iconIndex;
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) {
		int i = MathHelper.func_41084_a(itemstack.getItemDamage(), 0, packedNames.length - 1);
		return (new StringBuilder()).append(super.getItemName()).append(".").append(packedNames[i]).toString();
	}

}
