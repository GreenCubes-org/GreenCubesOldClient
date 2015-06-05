package net.minecraft.src;

public class ItemArmorRepairable extends ItemArmor {

	public ItemArmorRepairable(int id, EnumArmorMaterial material, int texture, int slot, int toFix) {
		super(id, material, texture, slot, toFix);
	}
	
	@Override
	public boolean isUnbreakable() {
		return true;
	}

	@Override
	public boolean noDrop() {
		return true;
	}

}
