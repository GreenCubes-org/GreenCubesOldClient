// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;

import org.greencubes.items.unique.DecorItemQuality;

// Referenced classes of package net.minecraft.src:
//            Item, EnumToolMaterial, Block, ItemStack, 
//            EnumAction, EntityPlayer, EntityLiving, Entity, 
//            World

public class ItemSword extends Item {

	protected ItemStack toFix;
	protected DecorItemQuality quality;
	private int weaponDamage;
	private final EnumToolMaterial material;

	public ItemSword(int i, EnumToolMaterial enumtoolmaterial) {
		super(i);
		material = enumtoolmaterial;
		maxStackSize = 1;
		setMaxDamage(enumtoolmaterial.getMaxUses());
		weaponDamage = 4 + enumtoolmaterial.getDamageVsEntity() * 2;
	}
	
	public ItemSword setFixItem(int id) {
		if(id != -1)
			this.toFix = new ItemStack(id, 1, 0);
		return this;
	}
	
	@Override
	public void appendDescription(ItemStack itemstack, List<String> list) {
		super.appendDescription(itemstack, list);
		StringBuilder sb = new StringBuilder();
		sb.append("\2477Меч");
		if(toFix != null) {
			sb.append(", чинится: ");
			sb.append(toFix.getItem().getTranslatedName(toFix));
		}
		list.add(sb.toString());
	}

	@Override
	public float getBlockDamageMultipler(ItemStack itemStack, Block block, EntityPlayer player, int data) {
		while(block instanceof IBlockMadeOf)
			block = ((IBlockMadeOf) block).getBlockMadeOf();
		if(block.blockID == Block.web.blockID)
			return 15F;
		return 1.5F;
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
		itemstack.damageItem(1, entityliving1);
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, int i, int j, int k, int l, EntityLiving entityliving) {
		itemstack.damageItem(2, entityliving);
		return true;
	}

	@Override
	public int getDamageVsEntity(Entity entity) {
		return weaponDamage;
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.block;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 0x11940;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		return itemstack;
	}

	@Override
	public boolean canHarvestBlock(Block block) {
		return block.blockID == Block.web.blockID;
	}

	@Override
	public int getItemEnchantability() {
		return material.getEnchantability();
	}

	@Override
	public void appendAttributes(ItemStack itemstack, List<String> list) {
		StringBuilder sb = new StringBuilder();
		int damage = weaponDamage;
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
	
	public ItemSword setDecor(DecorItemQuality q) {
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
}
