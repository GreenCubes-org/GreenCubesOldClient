// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import net.minecraft.client.Minecraft;

// Referenced classes of package net.minecraft.src:
//            PlayerController, EntityPlayer, World, EntityPlayerSP, 
//            Block, ItemStack, StepSound, SoundManager, 
//            GuiIngame, RenderGlobal

public class PlayerControllerSP extends PlayerController {

	private int curBlockX;
	private int curBlockY;
	private int curBlockZ;
	private float curBlockDamage;
	private float prevBlockDamage;
	private float field_1069_h;
	private int blockHitWait;

	public PlayerControllerSP(Minecraft minecraft) {
		super(minecraft);
		curBlockX = -1;
		curBlockY = -1;
		curBlockZ = -1;
		curBlockDamage = 0.0F;
		prevBlockDamage = 0.0F;
		field_1069_h = 0.0F;
		blockHitWait = 0;
	}

	@Override
	public void flipPlayer(EntityPlayer entityplayer) {
		entityplayer.rotationYaw = -180F;
	}

	@Override
	public boolean shouldDrawHUD() {
		return true;
	}

	@Override
	public boolean sendBlockRemoved(int i, int j, int k, int l) {
		int i1 = mc.theWorld.getBlockId(i, j, k);
		int j1 = mc.theWorld.getBlockMetadata(i, j, k);
		boolean flag = super.sendBlockRemoved(i, j, k, l);
		ItemStack itemstack = mc.thePlayer.getCurrentEquippedItem();
		boolean flag1 = mc.thePlayer.canHarvestBlock(Block.blocksList[i1]);
		if(itemstack != null) {
			itemstack.onDestroyBlock(i1, i, j, k, mc.thePlayer);
			if(itemstack.stackSize == 0) {
				itemstack.onItemDestroyedByUse(mc.thePlayer);
				mc.thePlayer.destroyCurrentEquippedItem();
			}
		}
		if(flag && flag1) {
			Block.blocksList[i1].harvestBlock(mc.theWorld, mc.thePlayer, i, j, k, j1);
		}
		return flag;
	}

	@Override
	public void clickBlock(int x, int y, int z, int face) {
		if(!mc.thePlayer.func_35190_e(x, y, z)) {
			return;
		}
		mc.theWorld.onBlockHit(mc.thePlayer, x, y, z, face);
		int i1 = mc.theWorld.getBlockId(x, y, z);
		if(i1 > 0 && curBlockDamage == 0.0F) {
			Block.blocksList[i1].onBlockClicked(mc.theWorld, x, y, z, mc.thePlayer, face);
		}
		if(i1 > 0 && Block.blocksList[i1].blockStrength(mc.thePlayer, mc.theWorld.getBlockMetadata(x, y, z)) >= 1.0F) {
			sendBlockRemoved(x, y, z, face);
		}
	}

	@Override
	public void resetBlockRemoving() {
		curBlockDamage = 0.0F;
		blockHitWait = 0;
	}

	@Override
	public void sendBlockRemoving(int x, int y, int z, int face) {
		if(blockHitWait > 0) {
			blockHitWait--;
			return;
		}
		if(x == curBlockX && y == curBlockY && z == curBlockZ) {
			int i1 = mc.theWorld.getBlockId(x, y, z);
			if(!mc.thePlayer.func_35190_e(x, y, z))
				return;

			Block block = Block.blocksList[i1];
			if(block == null)
				return;
			curBlockDamage += block.blockStrength(mc.thePlayer, mc.theWorld.getBlockMetadata(x, y, z));
			if(field_1069_h % 4F == 0.0F && block != null) {
				mc.sndManager.playSound(block.stepSound.stepSoundDir2(), x + 0.5F, y + 0.5F, z + 0.5F, (block.stepSound.getVolume() + 1.0F) / 8F, block.stepSound.getPitch() * 0.5F);
			}
			field_1069_h++;
			if(curBlockDamage >= 1.0F) {
				sendBlockRemoved(x, y, z, face);
				curBlockDamage = 0.0F;
				prevBlockDamage = 0.0F;
				field_1069_h = 0.0F;
				blockHitWait = 5;
			}
		} else {
			curBlockDamage = 0.0F;
			prevBlockDamage = 0.0F;
			field_1069_h = 0.0F;
			curBlockX = x;
			curBlockY = y;
			curBlockZ = z;
		}
	}

	@Override
	public void setPartialTime(float f) {
		if(curBlockDamage <= 0.0F) {
			mc.ingameGUI.damageGuiPartialTime = 0.0F;
			mc.renderGlobal.damagePartialTime = 0.0F;
		} else {
			float f1 = prevBlockDamage + (curBlockDamage - prevBlockDamage) * f;
			mc.ingameGUI.damageGuiPartialTime = f1;
			mc.renderGlobal.damagePartialTime = f1;
		}
	}

	@Override
	public float getBlockReachDistance() {
		return 4F;
	}

	@Override
	public void onWorldChange(World world) {
		super.onWorldChange(world);
	}

	@Override
	public EntityPlayer createPlayer(World world) {
		EntityPlayer entityplayer = super.createPlayer(world);
		return entityplayer;
	}

	@Override
	public void updateController() {
		prevBlockDamage = curBlockDamage;
		mc.sndManager.playRandomMusicIfReady();
	}

	@Override
	public boolean sendPlaceBlock(EntityPlayer entityplayer, World world, ItemStack itemstack, int i, int j, int k, int l) {
		int i1 = world.getBlockId(i, j, k);
		if(i1 > 0 && Block.blocksList[i1].blockActivated(world, i, j, k, entityplayer))
			return true;
		if(itemstack == null)
			return false;
		return itemstack.useItem(entityplayer, world, i, j, k, l);
	}

	@Override
	public boolean sendUseItem2(EntityPlayer entityplayer, World world, ItemStack itemstack, int i, int j, int k, int l) {
		int i1 = world.getBlockId(i, j, k);
		if(i1 > 0 && Block.blocksList[i1].blockActivated2(world, i, j, k, entityplayer))
			return true;
		if(itemstack == null)
			return false;
		return itemstack.useItem2(entityplayer, world, i, j, k, l);
	}

	@Override
	public boolean func_35642_f() {
		return true;
	}
}
