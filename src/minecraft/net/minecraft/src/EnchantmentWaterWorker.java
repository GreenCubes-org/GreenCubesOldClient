// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            Enchantment, EnumEnchantmentType

public class EnchantmentWaterWorker extends Enchantment {

	public EnchantmentWaterWorker(int i, int j) {
		super(i, j, EnumEnchantmentType.armor_head);
		setName("waterWorker");
	}

	@Override
	public int getMinEnchantability(int i) {
		return 1;
	}

	@Override
	public int getMaxEnchantability(int i) {
		return getMinEnchantability(i) + 40;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}
}
