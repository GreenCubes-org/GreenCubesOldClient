// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import net.minecraft.client.Minecraft;

// Referenced classes of package net.minecraft.src:
//            PlayerController, EntityPlayer, PlayerCapabilities, InventoryPlayer, 
//            ItemStack, Session, Block, World

public class PlayerControllerCreative extends PlayerController {

	private int field_35647_c;

	public PlayerControllerCreative(Minecraft minecraft) {
		super(minecraft);
		isInTestMode = true;
	}

	public static void func_35646_d(EntityPlayer entityplayer) {
		entityplayer.capabilities.allowFlying = true;
		entityplayer.capabilities.depleteBuckets = true;
		entityplayer.capabilities.disableDamage = true;
	}

	public static void func_35645_e(EntityPlayer entityplayer) {
		entityplayer.capabilities.allowFlying = false;
		entityplayer.capabilities.isFlying = false;
		entityplayer.capabilities.depleteBuckets = false;
		entityplayer.capabilities.disableDamage = false;
	}

	@Override
	public void func_6473_b(EntityPlayer entityplayer) {
		func_35646_d(entityplayer);
		for(int i = 0; i < 9; i++) {
			if(entityplayer.inventory.mainInventory[i] == null) {
				entityplayer.inventory.mainInventory[i] = new ItemStack((Block) Session.registeredBlocksList.get(i));
			}
		}

	}

	public static void func_35644_a(Minecraft minecraft, PlayerController playercontroller, int i, int j, int k, int l) {
		minecraft.theWorld.onBlockHit(minecraft.thePlayer, i, j, k, l);
		playercontroller.sendBlockRemoved(i, j, k, l);
	}

	@Override
	public boolean sendPlaceBlock(EntityPlayer entityplayer, World world, ItemStack itemstack, int i, int j, int k, int l) {
		int i1 = world.getBlockId(i, j, k);
		if(i1 > 0 && Block.blocksList[i1].blockActivated(world, i, j, k, entityplayer)) {
			return true;
		}
		if(itemstack == null) {
			return false;
		} else {
			int j1 = itemstack.getItemDamage();
			int k1 = itemstack.stackSize;
			boolean flag = itemstack.useItem(entityplayer, world, i, j, k, l);
			itemstack.setItemDamage(j1);
			itemstack.stackSize = k1;
			return flag;
		}
	}

	@Override
	public boolean sendUseItem2(EntityPlayer entityplayer, World world, ItemStack itemstack, int i, int j, int k, int l) {
		int i1 = world.getBlockId(i, j, k);
		if(i1 > 0 && Block.blocksList[i1].blockActivated2(world, i, j, k, entityplayer)) {
			return true;
		}
		if(itemstack == null) {
			return false;
		} else {
			int j1 = itemstack.getItemDamage();
			int k1 = itemstack.stackSize;
			boolean flag = itemstack.useItem2(entityplayer, world, i, j, k, l);
			itemstack.setItemDamage(j1);
			itemstack.stackSize = k1;
			return flag;
		}
	}

	@Override
	public void clickBlock(int i, int j, int k, int l) {
		func_35644_a(mc, this, i, j, k, l);
		field_35647_c = 5;
	}

	@Override
	public void sendBlockRemoving(int i, int j, int k, int l) {
		field_35647_c--;
		if(field_35647_c <= 0) {
			field_35647_c = 5;
			func_35644_a(mc, this, i, j, k, l);
		}
	}

	@Override
	public void resetBlockRemoving() {
	}

	@Override
	public boolean shouldDrawHUD() {
		return false;
	}

	@Override
	public void onWorldChange(World world) {
		super.onWorldChange(world);
	}

	@Override
	public float getBlockReachDistance() {
		return 5F;
	}

	@Override
	public boolean func_35641_g() {
		return false;
	}

	@Override
	public boolean isInCreativeMode() {
		return true;
	}

	@Override
	public boolean extendedReach() {
		return true;
	}
}
