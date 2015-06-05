package net.minecraft.src;

public class ItemGreenFlowers extends ItemBlock {

	public ItemGreenFlowers(int i) {
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getIconFromDamage(int i) {
		return Block.blockGreenFlowers.getBlockTextureFromSideAndMetadata(2, i);
	}

	@Override
	public int getPlacedBlockMetadata(int i) {
		return i;
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) {
		if(itemstack.getItemDamage() == 3)
			return "item.chamomile";
		if(itemstack.getItemDamage() == 2)
			return "item.vasilek";
		if(itemstack.getItemDamage() == 1)
			return "item.lavender";
		if(itemstack.getItemDamage() == 0)
			return "item.tulip";
		return "item.tulip";
	}
}
