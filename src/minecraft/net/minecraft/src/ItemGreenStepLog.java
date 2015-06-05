package net.minecraft.src;

import java.util.List;

public class ItemGreenStepLog extends ItemBlock {

	public ItemGreenStepLog(int i) {
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getPlacedBlockMetadata(int i) {
		return i;
	}

	@Override
	public int getIconFromDamage(int i) {
		return Block.blockStepLog.getBlockTextureFromSideAndMetadata(2, i);
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) {
		if(itemstack.getItemDamage() == 0)
			return "tile.oakLogStep";
		if(itemstack.getItemDamage() == 1)
			return "tile.redwoodLogStep";
		if(itemstack.getItemDamage() == 2)
			return "tile.birchLogStep";
		if(itemstack.getItemDamage() == 3)
			return "tile.sakuraLogStep";
		if(itemstack.getItemDamage() == 4)
			return "tile.jungleLogStep";
		if(itemstack.getItemDamage() == 5)
			return "tile.baoLogStep";
		if(itemstack.getItemDamage() == 6)
			return "tile.palmLogStep";
		return "tile.oakLogStep";
	}

	@Override
	public void appendDescription(ItemStack itemstack, List list) {
		if(((BlockGreenStep) Block.blocksList[itemstack.itemID]).isUp)
			list.add("\2477(Верхний)");
	}
}
