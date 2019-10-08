/*
 * Copyright 2015 Eiren 'Eirenliel' Rain and GreenCubes.org
 * authors
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
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
		target.setByte("type", IItemMatcher.MATCHER_SIMPLE_WITH_NBT);
		target.setString("n", name);
		target.setShort("id", (short) id);
		target.setShort("damage", (short) damage);
		return target;
	}
	
}
