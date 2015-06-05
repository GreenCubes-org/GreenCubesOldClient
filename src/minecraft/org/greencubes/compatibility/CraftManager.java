package org.greencubes.compatibility;

import org.greencubes.nbt.NBTTagCompound;
import org.greencubes.nbt.NBTTagList;
import org.greencubes.nbt.NBTTagString;

public class CraftManager {

	public static IItemMatcher getOrLoadMatcher(NBTTagCompound tag) {
		IItemMatcher m = null;
		switch(tag.getInt("type")) {
		case 0: // It is valit that matcher is not written
			break;
		case IItemMatcher.MATCHER_SIMPLE:
			m = new ItemMatcherSimple(tag.getString("n"), tag.getInt("id"), tag.getInt("damage"));
			break;
		case IItemMatcher.MATCHER_DAMAGED:
			break;
		case IItemMatcher.MATCHER_DAMAGEABLE:
			m = new ItemMatcherDamageable(tag.getString("n"), tag.getInt("id"));
			break;
		case IItemMatcher.MATCHER_COMPOUND:
			NBTTagList list = tag.getTagList("ms");
			IItemMatcher[] matchers = new IItemMatcher[list.size()];
			for(int i = 0; i < list.size(); ++i) {
				matchers[i] = getOrLoadMatcher((NBTTagCompound) list.get(i));
			}
			if(tag.hasKey("img"))
				m = new ItemMatcherCompound(tag.getString("n"), matchers, tag.getString("img"));
			else
				m = new ItemMatcherCompound(tag.getString("n"), matchers);
			break;
		case IItemMatcher.MATCHER_MOD_COLOR:
			break;
		case IItemMatcher.MATCHER_MOD_EXTRACT_TARGET:
			break;
		case IItemMatcher.MATCHER_MOD_EXTRACT_SOURCE:
			break;
		case IItemMatcher.MATCHER_COLORABLE:
			break;
		case IItemMatcher.MATCHER_COLORED_LOCKED:
			break;
		case IItemMatcher.MATCHER_MOD_COLOR_NAMED:
			break;
		case IItemMatcher.MATCHER_MOD_DURABILITY:
			break;
		case IItemMatcher.MATCHER_MOD_NAME:
			break;
		case IItemMatcher.MATCHER_MOD_DESCRIPTION:
			break;
		case IItemMatcher.MATCHER_DECOR:
			break;
		case IItemMatcher.MATCHER_DAMAGEABLE_DECOR:
			break;
		case IItemMatcher.MATCHER_DECOR_ANY:
			break;
		case IItemMatcher.MATCHER_MULTIPLE:
			list = tag.getTagList("ms");
			matchers = new IItemMatcher[list.size()];
			for(int i = 0; i < list.size(); ++i) {
				matchers[i] = getOrLoadMatcher((NBTTagCompound) list.get(i));
			}
			if(tag.hasKey("img"))
				m = new ItemMatcherMultiple(tag.getString("n"), matchers, tag.getString("img"));
			else
				m = new ItemMatcherMultiple(tag.getString("n"), matchers);
			break;
		case IItemMatcher.MATCHER_HASNBT:
			list = tag.getTagList("k");
			String[] keys = new String[list.size()];
			for(int i = 0; i < list.size(); ++i) {
				keys[i] = ((NBTTagString) list.get(i)).a;
			}
			m = new ItemMatcherHasNBT(tag.getString("n"), keys);
			break;
		case IItemMatcher.MATCHER_SIMPLE_WITH_NBT:
			m = new ItemMatcherSimpleWithNBT(tag.getString("n"), tag.getInt("id"), tag.getInt("damage"));
			break;
		default:
			throw new RuntimeException("Unknown matcher type " + tag.getInt("type"));
		}
		return m;
	}

}
