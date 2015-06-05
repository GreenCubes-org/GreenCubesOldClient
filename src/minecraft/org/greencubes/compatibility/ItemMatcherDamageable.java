package org.greencubes.compatibility;

import org.greencubes.nbt.NBTTagCompound;

import net.minecraft.src.ItemStack;

public class ItemMatcherDamageable implements IItemMatcher {
	
	protected final String name;
	public int id;
	
	public ItemMatcherDamageable(String name, int id) {
		this.id = id;
		this.name = name;
	}

	public ItemMatcherDamageable(ItemMatcherSimple matcher) {
		this("custom." + matcher.getName() + ".damageable", matcher.id);
	}

	@Override
	public boolean matches(ItemStack is) {
		return is.itemID == id;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" + getName() + "," + id + "}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 3;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		ItemMatcherDamageable other = (ItemMatcherDamageable) obj;
		if(id != other.id)
			return false;
		return true;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean selectable() {
		return true;
	}
	
	@Override
	public NBTTagCompound toNBTTag(NBTTagCompound target) {
		if(target == null)
			target = new NBTTagCompound();
		target.setByte("type", (byte) IItemMatcher.MATCHER_DAMAGEABLE);
		target.setString("n", name);
		target.setShort("id", (short) id);
		return target;
	}
	
}
