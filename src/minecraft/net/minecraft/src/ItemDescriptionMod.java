package net.minecraft.src;

import java.util.List;

public class ItemDescriptionMod extends Item {

	public ItemDescriptionMod(int i) {
		super(i);
	}
	
	@Override
	public void appendDescription(ItemStack itemstack, List<String> list) {
		list.add("\247rffd2d2d2Для добавления описания используйте команду \247b/mod descr");
	}
	
	@Override
	public void appendAttributes(ItemStack itemstack, List<String> list) {
		if(itemstack.nbtData != null && itemstack.nbtData.hasKey("ModValue")) {
			list.add("\247rffd2d2d2После использования на предмете сменит его описание на:");
			NBTTagList tag = (NBTTagList) itemstack.nbtData.getTag("ModValue");
			for(int i = 0; i < tag.size(); ++i)
				list.add("\247rffcccccc\247i" + ((NBTTagString) tag.get(i)).stringValue);
		}
	}

}
