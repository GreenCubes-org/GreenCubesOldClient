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

import java.util.Arrays;

import org.greencubes.nbt.NBTTagCompound;
import org.greencubes.nbt.NBTTagList;

import net.minecraft.src.ItemStack;

public class ItemMatcherHasNBT implements IItemMatcher {
	
	protected final String name;
	protected String[] requiredKeys;
	
	public ItemMatcherHasNBT(String name, String ... requiredKeys) {
		this.name = name;
		this.requiredKeys = requiredKeys;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(requiredKeys);
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
		ItemMatcherHasNBT other = (ItemMatcherHasNBT) obj;
		if(name == null) {
			if(other.name != null)
				return false;
		} else if(!name.equals(other.name))
			return false;
		if(!Arrays.equals(requiredKeys, other.requiredKeys))
			return false;
		return true;
	}

	@Override
	public boolean matches(ItemStack is) {
		if(is.nbtData == null)
			return false;
		for(String key : requiredKeys) {
			if(!is.nbtData.hasKey(key))
				return false;
		}
		return true;
	}
	
	@Override
	public boolean selectable() {
		return true;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public NBTTagCompound toNBTTag(NBTTagCompound target) {
		if(target == null)
			target = new NBTTagCompound();
		target.setByte("type", IItemMatcher.MATCHER_HASNBT);
		target.setString("n", name);
		target.setNBTTag("k", NBTTagList.toList(requiredKeys));
		return target;
	}
	
}
