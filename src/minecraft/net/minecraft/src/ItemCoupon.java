package net.minecraft.src;

public class ItemCoupon extends Item {

	protected ItemCoupon(int i) {
		super(i);
	}

	@Override
	public ItemStack onFoodEaten(ItemStack itemstack, World world, EntityLiving entityplayer) {
		itemstack.stackSize--;
		world.playSoundAtEntity(entityplayer, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		return itemstack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.eat;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		return itemstack;
	}

}
