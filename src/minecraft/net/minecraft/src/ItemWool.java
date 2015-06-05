// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            ItemBlock, Block, BlockCloth, ItemDye, 
//            ItemStack

public class ItemWool extends ItemBlock {

	public ItemWool(int i) {
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getIconFromDamage(int i) {
		return Block.wool.getBlockTextureFromSideAndMetadata(2, BlockWool.getBlockFromDye(i));
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
