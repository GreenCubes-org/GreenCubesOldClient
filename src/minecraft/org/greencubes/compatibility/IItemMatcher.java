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

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.util.Comparator;

import org.greencubes.nbt.NBTTagCompound;

import net.minecraft.src.ItemStack;

public interface IItemMatcher {
	
	public static final byte MATCHER_SIMPLE = 1;
	public static final byte MATCHER_DAMAGEABLE = 2;
	public static final byte MATCHER_DAMAGED = 3;
	public static final byte MATCHER_COMPOUND = 4;
	public static final byte MATCHER_MOD_COLOR = 5;
	public static final byte MATCHER_MOD_EXTRACT_TARGET = 6;
	public static final byte MATCHER_MOD_EXTRACT_SOURCE = 7;
	public static final byte MATCHER_COLORABLE = 8;
	public static final byte MATCHER_COLORED_LOCKED = 9;
	public static final byte MATCHER_MOD_COLOR_NAMED = 10;
	public static final byte MATCHER_MOD_DURABILITY = 11;
	public static final byte MATCHER_MOD_NAME = 12;
	public static final byte MATCHER_MOD_DESCRIPTION = 13;
	public static final byte MATCHER_WITH_COUNTER = 14;
	public static final byte MATCHER_SCROLL_COORD = 15;
	public static final byte MATCHER_DECOR = 16;
	public static final byte MATCHER_DAMAGEABLE_DECOR = 17;
	public static final byte MATCHER_DECOR_ANY = 18;
	public static final byte MATCHER_MULTIPLE = 19;
	public static final byte MATCHER_HASNBT = 20;
	public static final byte MATCHER_SIMPLE_WITH_NBT = 21;
	
	public static final ItemMatcherSorter sorter = new ItemMatcherSorter();
	
	
	public boolean matches(ItemStack is);
	
	public boolean selectable();
	
	public String getName();
	
	public NBTTagCompound toNBTTag(NBTTagCompound target);
	
	/**
	 * This sorter suitable only for last attemp of sorting item matchers -
	 * it sorts them only for decorative puproses, they should be sorted
	 * previously by needs.
	 * @author Rena
	 *
	 */
	public static class ItemMatcherSorter implements Comparator<IItemMatcher> {
		
		private final TObjectIntMap<Class<? extends IItemMatcher>> classCompare = new TObjectIntHashMap<Class<? extends IItemMatcher>>();
		
		private ItemMatcherSorter() {
			classCompare.put(ItemMatcherCompound.class, 1);
			classCompare.put(ItemMatcherDamageable.class, 3);
			classCompare.put(ItemMatcherSimple.class, 4);
		}

		@Override
		public int compare(IItemMatcher o1, IItemMatcher o2) {
			if(o1 == null)
				return o2 == null ? 0 : -1;
			if(o2 == null)
				return 1;
			if(o1 instanceof ItemMatcherSimple && o2 instanceof ItemMatcherSimple) {
				ItemMatcherSimple m1 = (ItemMatcherSimple) o1;
				ItemMatcherSimple m2 = (ItemMatcherSimple) o2;
				return m1.id > m2.id ? 1 : m1.id < m2.id ? -1 : m1.damage > m2.damage ? 1 : m1.damage < m2.damage ? -1 : 0;
			} else if(o1 instanceof ItemMatcherDamageable && o2 instanceof ItemMatcherDamageable) {
				ItemMatcherDamageable m1 = (ItemMatcherDamageable) o1;
				ItemMatcherDamageable m2 = (ItemMatcherDamageable) o2;
				return m1.id > m2.id ? 1 : m1.id < m2.id ? -1 : 0;
			}
			if(o1 instanceof ItemMatcherCompound)
				return sortWithCompoound((ItemMatcherCompound) o1, o2);
			if(o2 instanceof ItemMatcherCompound)
				return -1 * sortWithCompoound((ItemMatcherCompound) o2, o1);
			int i1 = classCompare.get(o1.getClass());
			int i2 = classCompare.get(o2.getClass());
			return i1 > i2 ? 1 : i1 < i2 ? -1 : 0;
		}
		
		private int sortWithCompoound(ItemMatcherCompound o1, IItemMatcher o2) {
			if(o2 instanceof ItemMatcherCompound) {
				int c1 = o1.matchers.length;
				int c2 = ((ItemMatcherCompound) o2).matchers.length;
				return c1 > c2 ? -1 : c1 < c2 ? 1 : 0;
			}
			return -1;
		}
	}

}
