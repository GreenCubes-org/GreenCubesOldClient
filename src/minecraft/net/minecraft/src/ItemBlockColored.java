package net.minecraft.src;

public class ItemBlockColored extends ItemBlock {

	public ItemBlockColored(int i) {
		super(i);
	}
	
	@Override
	public int getPlacedBlockMetadata(int i) {
		return i;
	}
	
	@Override
	public String getItemNameIS(ItemStack itemstack) {
		return (new StringBuilder()).append(super.getItemName()).append(".").append(ItemDye.dyeColorNames[BlockWool.getBlockFromDye(itemstack.getItemDamage())]).toString();
	}
}
