// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;
import org.greencubes.items.unique.DecorItemQuality;

public class ItemBow extends Item {
	
	protected DecorItemQuality quality;
	protected int baseDamage = 5;
	protected float accuracy;
	protected int[] icons = {101, 117, 133};
	protected ItemStack toFix;
	protected float speed;
	
	public ItemBow(int i, float accuracy, float speed) {
		super(i);
		this.maxStackSize = 1;
		this.accuracy = accuracy;
		this.speed = speed;
		setMaxDamage(384);
	}
	
	public ItemBow setActionIcons(int[] icons) {
		this.icons = icons;
		return this;
	}
	
	public ItemBow setFixItem(int id) {
		if(id != -1)
			this.toFix = new ItemStack(id, 1, 0);
		return this;
	}
	
	@Override
	public void appendDescription(ItemStack itemstack, List<String> list) {
		super.appendDescription(itemstack, list);
		StringBuilder sb = new StringBuilder();
		sb.append("\2477���");
		if(toFix != null) {
			sb.append(", �������: ");
			sb.append(toFix.getItem().getTranslatedName(toFix));
		}
		list.add(sb.toString());
	}
	
	@Override
	public void appendAttributes(ItemStack itemstack, List<String> list) {
		StringBuilder sb = new StringBuilder();
		int damage = baseDamage;
		if(itemstack.hasNBTData()) {
			if(itemstack.getNBTData().hasKey("DamageMultipler")) {
				float multipler = itemstack.getNBTData().getFloat("DamageMultipler") + 1;
				damage *= multipler;
			}
		}
		sb.append(String.format("\2477����: \247f%d", damage));
		if(itemstack.hasNBTData() && itemstack.getNBTData().hasKey("DamageMultipler")) {
			sb.append(" \247f(");
			if(itemstack.getNBTData().hasKey("DamageMultipler")) {
				float multipler = itemstack.getNBTData().getFloat("DamageMultipler");
				sb.append("+").append((int) (multipler * 100)).append("%");
			}
			sb.append(")");
		}
		list.add(sb.toString());
		list.add(String.format("\2477��������: \247f%.1f", accuracy));
		list.add(String.format("\2477��������: \247f%.1f", 1f / speed));
		super.appendAttributes(itemstack, list);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i) {
		if(world.multiplayerWorld)
			return;
		if(entityplayer.capabilities.depleteBuckets || entityplayer.inventory.hasItem(Item.arrow.shiftedIndex)) {
			int j = getMaxItemUseDuration(itemstack) - i;
			float f = j / 20F;
			f = (f * f + f * 2.0F) / 3F;
			if(f < 0.10000000000000001D) {
				return;
			}
			if(f > 1.0F) {
				f = 1.0F;
			}
			EntityArrow entityarrow = new EntityArrow(world, entityplayer, f * 2.0F);
			if(f == 1.0F) {
				entityarrow.arrowCritical = true;
			}
			itemstack.damageItem(1, entityplayer);
			world.playSoundAtEntity(entityplayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
			entityplayer.inventory.consumeInventoryItem(Item.arrow.shiftedIndex);
			if(!world.multiplayerWorld) {
				world.entityJoinedWorld(entityarrow);
			}
		}
	}

	@Override
	public boolean noPrerender() {
		return true;
	}

	@Override
	public ItemStack onFoodEaten(ItemStack itemstack, World world, EntityLiving entityplayer) {
		return itemstack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 0x11940;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.bow;
	}

	public float getMaxUse() {
		return 20.0F;
	}

	public int getBowIcon(ItemStack itemstack, int inUse) {
		if(inUse >= 20)
			return icons[2];
		if(inUse > 10)
			return icons[1];
		return icons[0];
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if(entityplayer.capabilities.depleteBuckets || entityplayer.inventory.hasItem(Item.arrow.shiftedIndex) || entityplayer.inventory.hasItem(Item.explosiveArrow.shiftedIndex) || entityplayer.inventory.hasItem(Item.diamondArrow.shiftedIndex) || (itemstack.hasNBTData() && itemstack.getNBTData().getBoolean("NoAmmo"))) {
			entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		}
		return itemstack;
	}
	
	public ItemBow setDecor(DecorItemQuality q) {
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
