// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

import org.greencubes.items.unique.DecorItemQuality;

// Referenced classes of package net.minecraft.src:
//            Item, EnumToolMaterial, ItemStack, Block, 
//            EntityLiving, Entity

public class ItemTool extends Item {

	protected DecorItemQuality quality;
	private BlockDataPair[] effectiveBlocks;
	private ArrayList<Block> blocksEffectiveAgainst = new ArrayList<Block>();
	protected float efficiencyOnProperMaterial;
	private int damageVsEntity;
	protected EnumToolMaterial toolMaterial;

	protected ItemTool(int i, int j, EnumToolMaterial enumtoolmaterial, Block ablock[], BlockDataPair[] effectiveBlocks) {
		super(i);
		this.effectiveBlocks = effectiveBlocks;
		efficiencyOnProperMaterial = 4F;
		toolMaterial = enumtoolmaterial;
		blocksEffectiveAgainst.ensureCapacity(ablock.length);
		for(Block block : ablock)
			blocksEffectiveAgainst.add(block);
		maxStackSize = 1;
		setMaxDamage(enumtoolmaterial.getMaxUses());
		efficiencyOnProperMaterial = enumtoolmaterial.getEfficiencyOnProperMaterial();
		damageVsEntity = j + enumtoolmaterial.getDamageVsEntity();
	}

	@Override
	public boolean canHarvestBlock(Block block, int data) {
		for(BlockDataPair pair : effectiveBlocks) {
			if(pair.matches(block.blockID, data))
				return true;
		}
		return super.canHarvestBlock(block, data);
	}

	@Override
	public float getBlockDamageMultipler(ItemStack itemStack, Block block, EntityPlayer player, int data) {
		while(block instanceof IBlockMadeOf)
			block = ((IBlockMadeOf) block).getBlockMadeOf();
		for(BlockDataPair pair : effectiveBlocks) {
			if(pair.matches(block.blockID, data))
				return efficiencyOnProperMaterial;
		}
		return 1.0F;
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
		itemstack.damageItem(2, entityliving1);
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, int i, int j, int k, int l, EntityLiving entityliving) {
		itemstack.damageItem(1, entityliving);
		return true;
	}

	@Override
	public int getDamageVsEntity(Entity entity) {
		return damageVsEntity;
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public int getItemEnchantability() {
		return toolMaterial.getEnchantability();
	}
	
	public ItemTool setDecor(DecorItemQuality q) {
		this.quality = q;
		return this;
	}
	
	@Override
	public boolean noDrop() {
		return this.quality != null;
	}
	
	@Override
	public boolean isUnbreakable() {
		return this.quality != null;
	}
	
	@Override
	public DecorItemQuality getDecorQuality() {
		return this.quality;
	}
	
	@Override
	public void appendAttributes(ItemStack itemstack, List<String> list) {
		StringBuilder sb = new StringBuilder();
		int damage = damageVsEntity;
		sb.append("\2477Урон: ");
		if(itemstack.hasNBTData()) {
			if(itemstack.getNBTData().hasKey("DamageMultipler")) {
				float multipler = itemstack.getNBTData().getFloat("DamageMultipler") + 1;
				damage *= multipler;
			}
			if(itemstack.getNBTData().hasKey("AddDamage")) {
				damage += itemstack.getNBTData().getShort("AddDamage");
			}
		}
		sb.append(damage);
		if(itemstack.hasNBTData() && (itemstack.getNBTData().hasKey("DamageMultipler") || itemstack.getNBTData().hasKey("AddDamage"))) {
			sb.append(" (");
			if(itemstack.getNBTData().hasKey("DamageMultipler")) {
				float multipler = itemstack.getNBTData().getFloat("DamageMultipler");
				sb.append("+").append((int) (multipler * 100)).append("%");
				if(itemstack.getNBTData().hasKey("AddDamage"))
					sb.append(", ");
			}
			if(itemstack.getNBTData().hasKey("AddDamage")) {
				sb.append("+").append(itemstack.getNBTData().getShort("AddDamage"));
			}
			sb.append(")");
		}
		list.add(sb.toString());
		super.appendAttributes(itemstack, list);
	}
}
