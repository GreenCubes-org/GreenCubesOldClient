package net.minecraft.src;

import java.util.List;

public class ItemStepWool extends ItemBlock {

	public ItemStepWool(int i) {
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
		return Block.wool.getBlockTextureFromSideAndMetadata(2, BlockWool.getBlockFromDye(i));
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) {
		return (new StringBuilder()).append("tile.clothStep").append(".").append(ItemDye.dyeColorNames[BlockWool.getBlockFromDye(itemstack.getItemDamage())]).toString();
	}

	@Override
	public void appendDescription(ItemStack itemstack, List list) {
		if(((BlockStepWool) Block.blocksList[itemstack.itemID]).isUp)
			list.add("\2477(Верхний)");
	}
}
