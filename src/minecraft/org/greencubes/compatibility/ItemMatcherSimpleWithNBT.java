package org.greencubes.compatibility;

import org.greencubes.nbt.NBTTagCompound;

import net.minecraft.src.ItemStack;

/**
 * Not actually WITH nbt, but ignores it
 * @author Rena
 *
 */
public class ItemMatcherSimpleWithNBT implements IItemMatcher {
	
	protected final String name;
	public int id;
	public int damage;
	
	public ItemMatcherSimpleWithNBT(String name, int id, int damage) {
		this.id = id;
		this.damage = damage;
		this.name = name;
	}
	
	public ItemMatcherSimpleWithNBT(ItemMatcherSimple matcher) {
		this.name = "custom." + matcher.getName() + ".witnbt";
		this.id = matcher.id;
		this.damage = matcher.damage;
	}

	@Override
	public boolean matches(ItemStack is) {
		return is.itemID == id && is.itemDamage == damage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + damage;
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
		ItemMatcherSimpleWithNBT other = (ItemMatcherSimpleWithNBT) obj;
		if(damage != other.damage)
			return false;
		if(id != other.id)
			return false;
		return true;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" + getName() + "," + id + "," + damage + "}";
	}

	@Override
	public boolean selectable() {
		return false;
	}

	@Override
	public NBTTagCompound toNBTTag(NBTTagCompound target) {
		if(target == null)
			target = new NBTTagCompound();
		target.setByte("type", (byte) IItemMatcher.MATCHER_SIMPLE_WITH_NBT);
		target.setString("n", name);
		target.setShort("id", (short) id);
		target.setShort("damage", (short) damage);
		return target;
	}
	
}
