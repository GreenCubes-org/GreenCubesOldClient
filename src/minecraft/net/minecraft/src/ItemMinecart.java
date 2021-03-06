// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            Item, World, BlockRail, EntityMinecart, 
//            ItemStack, EntityPlayer

public class ItemMinecart extends Item {

	public int minecartType;

	public ItemMinecart(int i, int j) {
		super(i);
		maxStackSize = 64; // GreenCubes
		minecartType = j;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
		int i1 = world.getBlockId(i, j, k);
		if(BlockRail.isRailBlock(i1)) {
			if(!world.multiplayerWorld) {
				world.entityJoinedWorld(new EntityMinecart(world, i + 0.5F, j + 0.5F, k + 0.5F, minecartType));
			}
			itemstack.stackSize--;
			return true;
		} else {
			return false;
		}
	}
}
