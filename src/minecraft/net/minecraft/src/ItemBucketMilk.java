// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            Item, ItemStack, World, EntityPlayer, 
//            EnumAction

public class ItemBucketMilk extends Item {

	public ItemBucketMilk(int i) {
		super(i);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onFoodEaten(ItemStack itemstack, World world, EntityLiving entityplayer) {
		itemstack.stackSize--;
		if(!world.multiplayerWorld) {
			entityplayer.func_40112_aN();
		}
		if(itemstack.stackSize <= 0) {
			return new ItemStack(Item.bucketEmpty);
		} else {
			return itemstack;
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.drink;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		return itemstack;
	}
}
