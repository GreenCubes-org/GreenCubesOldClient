// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

// Referenced classes of package net.minecraft.src:
//            Block, ItemStack, Item

public class FurnaceRecipes {

	private static final FurnaceRecipes smeltingBase = new FurnaceRecipes();
	private TIntObjectMap<ItemStack> smeltingList = new TIntObjectHashMap<ItemStack>();

	public static final FurnaceRecipes smelting() {
		return smeltingBase;
	}

	private FurnaceRecipes() {
		addSmelting(Block.oreIron.blockID, new ItemStack(Item.ingotIron));
		addSmelting(Block.oreGold.blockID, new ItemStack(Item.ingotGold));
		addSmelting(Block.oreDiamond.blockID, new ItemStack(Item.diamond));
		addSmelting(Block.sand.blockID, new ItemStack(Block.glass));
		addSmelting(Item.porkRaw.shiftedIndex, new ItemStack(Item.porkCooked));
		addSmelting(Item.beefRaw.shiftedIndex, new ItemStack(Item.beefCooked));
		addSmelting(Item.chickenRaw.shiftedIndex, new ItemStack(Item.chickenCooked));
		addSmelting(Item.fishRaw.shiftedIndex, new ItemStack(Item.fishCooked));
		addSmelting(Block.cobblestone.blockID, new ItemStack(Block.stone));
		addSmelting(Item.clay.shiftedIndex, new ItemStack(Item.brick));
		addSmelting(Block.cactus.blockID, new ItemStack(Item.dyePowder, 1, 2));
		addSmelting(Block.wood.blockID, new ItemStack(Item.coal, 1, 1));
		addSmelting(Block.oreCoal.blockID, new ItemStack(Item.coal));
		addSmelting(Block.oreRedstone.blockID, new ItemStack(Item.redstone));
		addSmelting(Block.oreLapis.blockID, new ItemStack(Item.dyePowder, 1, 4));
	}

	public void addSmelting(int i, ItemStack itemstack) {
		smeltingList.put(i, itemstack);
	}

	public ItemStack getSmeltingResult(int i) {
		return smeltingList.get(i);
	}

	public TIntObjectMap<ItemStack> getSmeltingList() {
		return smeltingList;
	}

}
