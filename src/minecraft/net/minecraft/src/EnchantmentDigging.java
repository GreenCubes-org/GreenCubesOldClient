// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            Enchantment, EnumEnchantmentType

public class EnchantmentDigging extends Enchantment {

	protected EnchantmentDigging(int i, int j) {
		super(i, j, EnumEnchantmentType.digger);
		setName("digging");
	}

	@Override
	public int getMinEnchantability(int i) {
		return 1 + 15 * (i - 1);
	}

	@Override
	public int getMaxEnchantability(int i) {
		return super.getMinEnchantability(i) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}
}
