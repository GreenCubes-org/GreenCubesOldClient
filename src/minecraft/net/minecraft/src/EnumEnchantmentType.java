// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            ItemArmor, ItemSword, ItemTool, Item

public enum EnumEnchantmentType {
	all("all", 0), armor("armor", 1), armor_feet("armor_feet", 2), armor_legs("armor_legs", 3), armor_torso("armor_torso", 4), armor_head("armor_head", 5), weapon("weapon", 6), digger("digger", 7);
	/*
	    public static final EnumEnchantmentType all;
	    public static final EnumEnchantmentType armor;
	    public static final EnumEnchantmentType armor_feet;
	    public static final EnumEnchantmentType armor_legs;
	    public static final EnumEnchantmentType armor_torso;
	    public static final EnumEnchantmentType armor_head;
	    public static final EnumEnchantmentType weapon;
	    public static final EnumEnchantmentType digger;
	*/
	private static final EnumEnchantmentType allEnchantmentTypes[]; /* synthetic field */

	/*
	    public static final EnumEnchantmentType[] values()
	    {
	        return (EnumEnchantmentType[])allEnchantmentTypes.clone();
	    }

	    public static EnumEnchantmentType valueOf(String s)
	    {
	        return (EnumEnchantmentType)Enum.valueOf(net.minecraft.src.EnumEnchantmentType.class, s);
	    }
	*/
	private EnumEnchantmentType(String s, int i) {
		//        super(s, i);
	}

	public boolean canEnchantItem(Item item) {
		return false;
	}

	static {
		/*
		        all = new EnumEnchantmentType("all", 0);
		        armor = new EnumEnchantmentType("armor", 1);
		        armor_feet = new EnumEnchantmentType("armor_feet", 2);
		        armor_legs = new EnumEnchantmentType("armor_legs", 3);
		        armor_torso = new EnumEnchantmentType("armor_torso", 4);
		        armor_head = new EnumEnchantmentType("armor_head", 5);
		        weapon = new EnumEnchantmentType("weapon", 6);
		        digger = new EnumEnchantmentType("digger", 7);
		*/
		allEnchantmentTypes = (new EnumEnchantmentType[]{all, armor, armor_feet, armor_legs, armor_torso, armor_head, weapon, digger});
	}
}
