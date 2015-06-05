// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            ItemBlock, Block, BlockLeaves, ColorizerFoliage

public class ItemLeaves extends ItemBlock {
	private String field_41034_b[] = new String[]{"oak", "redwood", "birch", "jungle"};

	public ItemLeaves(int i) {
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getPlacedBlockMetadata(int i) {
		return i | 4;
	}

	@Override
	public int getColorFromDamage(int i) {
		if((i & 1) == 1) {
			return ColorizerFoliage.getFoliageColorPine();
		}
		if((i & 2) == 2) {
			return ColorizerFoliage.getFoliageColorBirch();
		} else {
			return ColorizerFoliage.getFoliageColorBasic();
		}
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) {
		if(field_41034_b == null) {
			return super.getItemNameIS(itemstack);
		}
		int i = itemstack.getItemDamage();
		if(i >= 0 && i < field_41034_b.length) {
			return (new StringBuilder()).append(super.getItemNameIS(itemstack)).append(".").append(field_41034_b[i]).toString();

		} else {
			return super.getItemNameIS(itemstack);
		}
	}
}
