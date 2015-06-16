// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;

// Referenced classes of package net.minecraft.src:
//            Item, Block, BlockLeaves, BlockTallGrass, 
//            ItemStack, EntityLiving

public class ItemShears extends Item {
	
	protected ItemStack toFix;

	public ItemShears(int i) {
		super(i);
		setMaxStackSize(1);
		setMaxDamage(238);
	}
	
	public ItemShears setFixItem(int id) {
		if(id != -1)
			this.toFix = new ItemStack(id, 1, 0);
		return this;
	}
	
	@Override
	public void appendDescription(ItemStack itemstack, List<String> list) {
		super.appendDescription(itemstack, list);
		StringBuilder sb = new StringBuilder();
		sb.append("\2477Ножницы");
		if(toFix != null) {
			sb.append(", чинятся: ");
			sb.append(toFix.getItem().getTranslatedName(toFix));
		}
		list.add(sb.toString());
	}

	@Override
	public float getBlockDamageMultipler(ItemStack itemStack, Block block, EntityPlayer player, int data) {
		while(block instanceof IBlockMadeOf)
			block = ((IBlockMadeOf) block).getBlockMadeOf();
		if(block.blockID == Block.web.blockID || block instanceof BlockLeavesBase)
			return 15F;
		if(block.blockID == Block.wool.blockID)
			return 5F;
		return super.getBlockDamageMultipler(itemStack, block, player, data);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, int i, int j, int k, int l, EntityLiving entityliving) {
		if(i == Block.leaves.blockID || i == Block.web.blockID || i == Block.tallGrass.blockID || i == Block.vine.blockID) {
			itemstack.damageItem(1, entityliving);
			return true;
		} else {
			return super.onBlockDestroyed(itemstack, i, j, k, l, entityliving);
		}
	}

	@Override
	public boolean canHarvestBlock(Block block) {
		return block.blockID == Block.web.blockID;
	}
}
