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

public class ItemMatcherCompound implements IItemMatcher {

	protected final String name;
	public String imageName;
	public IItemMatcher[] matchers;
	
	public ItemMatcherCompound(String name, IItemMatcher[] matchers) {
		this.matchers = matchers;
		this.name = name;
	}

	public ItemMatcherCompound(String name, IItemMatcher[] matchers, String imageName) {
		this(name, matchers);
		this.imageName = imageName;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append('{').append(getName()).append(',').append(imageName).append(",[");
		for(int i = 0; i < matchers.length; ++i) {
			if(i != 0)
				sb.append(',');
			sb.append(matchers[i].toString());
		}
		sb.append("]}");
		return sb.toString();
	}
	
	@Override
	public boolean matches(ItemStack is) {
		for(int i = 0; i < matchers.length; ++i)
			if(matchers[i].matches(is))
				return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 2;
		result = prime * result + Arrays.hashCode(matchers);
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
		ItemMatcherCompound other = (ItemMatcherCompound) obj;
		if(!Arrays.equals(matchers, other.matchers))
			return false;
		return true;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean selectable() {
		return matchers.length > 1 || matchers[0].selectable();
	}
	
	@Override
	public NBTTagCompound toNBTTag(NBTTagCompound target) {
		if(target == null)
			target = new NBTTagCompound();
		target.setByte("type", IItemMatcher.MATCHER_COMPOUND);
		target.setString("n", name);
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < matchers.length; ++i)
			list.add(matchers[i].toNBTTag(null));
		target.setNBTTag("ms", list);
		if(imageName != null)
			target.setString("img", imageName);
		return target;
	}
	
}
