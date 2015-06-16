// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;

// Referenced classes of package net.minecraft.src:
//            ItemTool, Block, Material, EnumToolMaterial, 
//            ItemStack

public class ItemAxe extends ItemTool {

	private static Block blocksEffectiveAgainst[] = new Block[]{Block.planks, Block.bookShelf, Block.wood, Block.chest, Block.stairDouble, Block.stairSingle, Block.pumpkin, Block.pumpkinLantern};

	private static BlockDataPair[] effectiveBlocks = new BlockDataPair[]{new BlockDataPair(Block.planks.blockID), new BlockDataPair(Block.bookShelf.blockID), new BlockDataPair(Block.wood.blockID), new BlockDataPair(Block.chest.blockID), new BlockDataPair(Block.stairDouble.blockID, 2), new BlockDataPair(Block.stairSingle.blockID, 2), new BlockDataPair(Block.blockStepUp.blockID, 2), new BlockDataPair(Block.pumpkinLantern.blockID), new BlockDataPair(Block.blockHalfBlockSlabsWood.blockID),};

	protected ItemAxe(int i, EnumToolMaterial enumtoolmaterial) {
		super(i, 3, enumtoolmaterial, blocksEffectiveAgainst, effectiveBlocks);
	}

	@Override
	public boolean canHarvestBlock(Block block, int data) {
		if((block == Block.stairDouble || block == Block.stairSingle) && data == 1)
			return true;
		return super.canHarvestBlock(block, data);
	}

	@Override
	public float getBlockDamageMultipler(ItemStack itemStack, Block block, EntityPlayer player, int data) {
		if(block.blockMaterial == Material.wood)
			return efficiencyOnProperMaterial;
		return super.getBlockDamageMultipler(itemStack, block, player, data);
	}
	
	@Override
	public void appendDescription(ItemStack itemstack, List<String> list) {
		super.appendDescription(itemstack, list);
		StringBuilder sb = new StringBuilder();
		sb.append("\2477Топор");
		if(toFix != null) {
			sb.append(", чинится: ");
			sb.append(toFix.getItem().getTranslatedName(toFix));
		}
		list.add(sb.toString());
	}
}
