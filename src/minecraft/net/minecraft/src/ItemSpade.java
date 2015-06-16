// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;

// Referenced classes of package net.minecraft.src:
//            ItemTool, Block, EnumToolMaterial

public class ItemSpade extends ItemTool {

	private static Block[] blocksEffectiveAgainst = new Block[] {Block.grass, Block.dirt, Block.sand, Block.gravel,
		Block.snow, Block.blockSnow, Block.blockClay, Block.tilledField, Block.slowSand, Block.mycelium};

	private static BlockDataPair[] effectiveBlocks = new BlockDataPair[]{new BlockDataPair(Block.grass.blockID),
		new BlockDataPair(Block.dirt.blockID), new BlockDataPair(Block.sand.blockID), new BlockDataPair(Block.gravel.blockID),
		new BlockDataPair(Block.snow.blockID), new BlockDataPair(Block.blockSnow.blockID), new BlockDataPair(Block.blockClay.blockID),
		new BlockDataPair(Block.tilledField.blockID), new BlockDataPair(Block.slowSand.blockID), new BlockDataPair(Block.mycelium.blockID)};

	public ItemSpade(int i, EnumToolMaterial enumtoolmaterial) {
		super(i, 1, enumtoolmaterial, blocksEffectiveAgainst, effectiveBlocks);
	}

	@Override
	public boolean canHarvestBlock(Block block) {
		return block == Block.snow || block == Block.blockSnow;
	}
	
	@Override
	public void appendDescription(ItemStack itemstack, List<String> list) {
		super.appendDescription(itemstack, list);
		StringBuilder sb = new StringBuilder();
		sb.append("\2477������");
		if(toFix != null) {
			sb.append(", �������: ");
			sb.append(toFix.getItem().getTranslatedName(toFix));
		}
		list.add(sb.toString());
	}
}
