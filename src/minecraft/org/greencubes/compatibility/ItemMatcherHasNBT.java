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
