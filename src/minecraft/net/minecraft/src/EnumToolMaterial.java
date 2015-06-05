// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

public enum EnumToolMaterial {
	WOOD(0, 60, 2.0F, 0, 15),
	STONE(1, 140, 4F, 1, 5),
	IRON(2, 250, 6F, 2, 14),
	DIAMOND(3, 1650, 8F, 3, 10),
	GOLD(0, 100, 12F, 0, 22);

	private final int harvestLevel;
	private final int maxUses;
	private final float efficiencyOnProperMaterial;
	private final int damageVsEntity;
	private final int enchantability;

	private EnumToolMaterial(int j, int k, float f, int l, int i1) {
		harvestLevel = j;
		maxUses = k;
		efficiencyOnProperMaterial = f;
		damageVsEntity = l;
		enchantability = i1;
	}

	public int getMaxUses() {
		return maxUses;
	}

	public float getEfficiencyOnProperMaterial() {
		return efficiencyOnProperMaterial;
	}

	public int getDamageVsEntity() {
		return damageVsEntity;
	}

	public int getHarvestLevel() {
		return harvestLevel;
	}

	public int getEnchantability() {
		return enchantability;
	}
}
