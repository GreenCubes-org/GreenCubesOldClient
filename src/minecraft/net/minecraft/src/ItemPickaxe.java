// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            ItemTool, Block, EnumToolMaterial, Material, 
//            ItemStack

public class ItemPickaxe extends ItemTool {

	
	private static Block[] blocksEffectiveAgainst = new Block[]{Block.cobblestone, Block.stairDouble, Block.stairSingle,
		Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockSteel, Block.oreCoal, Block.blockGold,
		Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.netherrack, Block.oreLapis, Block.blockLapis,
		Block.oreRedstone, Block.oreRedstoneGlowing, Block.rail, Block.railDetector, Block.railPowered};

	private static BlockDataPair[] effectiveBlocks = new BlockDataPair[]{new BlockDataPair(Block.cobblestone.blockID),
		new BlockDataPair(Block.stairDouble.blockID), new BlockDataPair(Block.stairSingle.blockID),
		new BlockDataPair(Block.stone.blockID), new BlockDataPair(Block.sandStone.blockID), new BlockDataPair(Block.cobblestoneMossy.blockID),
		new BlockDataPair(Block.oreIron.blockID), new BlockDataPair(Block.blockSteel.blockID), new BlockDataPair(Block.oreCoal.blockID),
		new BlockDataPair(Block.blockGold.blockID), new BlockDataPair(Block.oreGold.blockID), new BlockDataPair(Block.oreDiamond.blockID),
		new BlockDataPair(Block.blockDiamond.blockID), new BlockDataPair(Block.ice.blockID), new BlockDataPair(Block.netherrack.blockID),
		new BlockDataPair(Block.oreLapis.blockID), new BlockDataPair(Block.blockLapis.blockID), new BlockDataPair(Block.oreRedstone.blockID),
		new BlockDataPair(Block.oreRedstoneGlowing.blockID), new BlockDataPair(Block.rail.blockID), new BlockDataPair(Block.railDetector.blockID),
		new BlockDataPair(Block.railPowered.blockID), new BlockDataPair(Block.blockStepUp.blockID),};

	protected ItemPickaxe(int i, EnumToolMaterial enumtoolmaterial) {
		super(i, 2, enumtoolmaterial, blocksEffectiveAgainst, effectiveBlocks);
	}

	@Override
	public float getBlockDamageMultipler(ItemStack itemStack, Block block, EntityPlayer player, int data) {
		if(block.blockMaterial == Material.iron || block.blockMaterial == Material.rock)
			return efficiencyOnProperMaterial;
		return super.getBlockDamageMultipler(itemStack, block, player, data);
	}

	@Override
	public boolean canHarvestBlock(Block block, int data) {
		if((block == Block.stairDouble || block == Block.stairSingle || block == Block.blockStepUp) && data == 2)
			return false;
		return super.canHarvestBlock(block, data);
	}

	@Override
	public boolean canHarvestBlock(Block block) {
		if(block == Block.obsidian)
			return toolMaterial.getHarvestLevel() == 3;
		if(block == Block.blockDiamond || block == Block.oreDiamond)
			return toolMaterial.getHarvestLevel() >= 2;
		if(block == Block.blockGold || block == Block.oreGold)
			return toolMaterial.getHarvestLevel() >= 2;
		if(block == Block.blockSteel || block == Block.oreIron)
			return toolMaterial.getHarvestLevel() >= 1;
		if(block == Block.blockLapis || block == Block.oreLapis)
			return toolMaterial.getHarvestLevel() >= 1;
		if(block == Block.oreRedstone || block == Block.oreRedstoneGlowing)
			return toolMaterial.getHarvestLevel() >= 2;
		if(block.blockMaterial == Material.rock)
			return true;
		if(block.blockMaterial == Material.iron)
			return true;
		return block.blockMaterial.getIsHarvestable();
	}
}
