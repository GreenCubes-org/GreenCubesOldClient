// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import org.greencubes.items.unique.DecorItemQuality;

import net.minecraft.client.Minecraft;

// Referenced classes of package net.minecraft.src:
//            PlayerController, PlayerControllerCreative, EntityPlayer, World, 
//            EntityPlayerSP, ItemStack, Packet14BlockDig, NetClientHandler, 
//            Block, StepSound, SoundManager, GuiIngame, 
//            RenderGlobal, InventoryPlayer, Packet16BlockItemSwitch, Packet15Place, 
//            EntityClientPlayerMP, Packet7UseEntity, Entity, Container, 
//            Packet102WindowClick, Packet108EnchantItem, Packet107CreativeSetSlot

public class PlayerControllerMP extends PlayerController {

	private int currentBlockX;
	private int currentBlockY;
	private int currentblockZ;
	private float curBlockDamageMP;
	private float prevBlockDamageMP;
	private float stepSoundTickCounter;
	private int blockHitDelay;
	private boolean isHittingBlock;
	private boolean creativeMode;
	private NetClientHandler netClientHandler;
	private int currentPlayerItem;

	public PlayerControllerMP(Minecraft minecraft, NetClientHandler netclienthandler) {
		super(minecraft);
		currentBlockX = -1;
		currentBlockY = -1;
		currentblockZ = -1;
		curBlockDamageMP = 0.0F;
		prevBlockDamageMP = 0.0F;
		stepSoundTickCounter = 0.0F;
		blockHitDelay = 0;
		isHittingBlock = false;
		currentPlayerItem = 0;
		netClientHandler = netclienthandler;
	}

	public void func_35648_a(boolean flag) {
		creativeMode = flag;
		if(creativeMode) {
			PlayerControllerCreative.func_35646_d(mc.thePlayer);
		} else {
			PlayerControllerCreative.func_35645_e(mc.thePlayer);
		}
	}

	@Override
	public void flipPlayer(EntityPlayer entityplayer) {
		entityplayer.rotationYaw = -180F;
	}

	@Override
	public boolean shouldDrawHUD() {
		return !creativeMode;
	}

	@Override
	public boolean sendBlockRemoved(int i, int j, int k, int l) {
		if(creativeMode) {
			return super.sendBlockRemoved(i, j, k, l);
		}
		int i1 = mc.theWorld.getBlockId(i, j, k);
		boolean flag = super.sendBlockRemoved(i, j, k, l);
		ItemStack itemstack = mc.thePlayer.getCurrentEquippedItem();
		if(itemstack != null) {
			itemstack.onDestroyBlock(i1, i, j, k, mc.thePlayer);
			if(itemstack.stackSize == 0) {
				itemstack.onItemDestroyedByUse(mc.thePlayer);
				mc.thePlayer.destroyCurrentEquippedItem();
			}
		}
		return flag;
	}

	@Override
	public void clickBlock(int x, int y, int z, int face) {
		if(creativeMode || instabreak()) {
			netClientHandler.addToSendQueue(new Packet14BlockDig(0, x, y, z, face, System.currentTimeMillis()));
			PlayerControllerCreative.func_35644_a(mc, this, x, y, z, face);
			blockHitDelay = 5;
		} else if(!isHittingBlock || x != currentBlockX || y != currentBlockY || z != currentblockZ) {
			netClientHandler.addToSendQueue(new Packet14BlockDig(0, x, y, z, face, System.currentTimeMillis()));
			int i1 = mc.theWorld.getBlockId(x, y, z);
			if(i1 > 0 && curBlockDamageMP == 0.0F) {
				Block.blocksList[i1].onBlockClicked(mc.theWorld, x, y, z, mc.thePlayer, face);
			}
			if(i1 > 0 && Block.blocksList[i1].blockStrength(mc.thePlayer, mc.theWorld.getBlockMetadata(x, y, z)) >= 1.0F) {
				sendBlockRemoved(x, y, z, face);
			} else {
				isHittingBlock = true;
				currentBlockX = x;
				currentBlockY = y;
				currentblockZ = z;
				curBlockDamageMP = 0.0F;
				prevBlockDamageMP = 0.0F;
				stepSoundTickCounter = 0.0F;
			}
		}
	}

	@Override
	public void resetBlockRemoving() {
		curBlockDamageMP = 0.0F;
		isHittingBlock = false;
	}

	@Override
	public void sendBlockRemoving(int x, int y, int z, int face) {
		syncCurrentPlayItem();
		if(blockHitDelay > 0) {
			blockHitDelay--;
			return;
		}
		if(creativeMode || instabreak()) {
			blockHitDelay = 5;
			netClientHandler.addToSendQueue(new Packet14BlockDig(0, x, y, z, face, System.currentTimeMillis()));
			PlayerControllerCreative.func_35644_a(mc, this, x, y, z, face);
			return;
		}
		if(x == currentBlockX && y == currentBlockY && z == currentblockZ) {
			int i1 = mc.theWorld.getBlockId(x, y, z);
			Block block = Block.blocksList[i1];
			if(block == null) {
				isHittingBlock = false;
				return;
			}
			curBlockDamageMP += block.blockStrength(mc.thePlayer, mc.theWorld.getBlockMetadata(x, y, z));
			//System.out.println("Current damage: " + curBlockDamageMP);
			if(stepSoundTickCounter % 4F == 0.0F && block != null) {
				mc.sndManager.playSound(block.stepSound.stepSoundDir2(), x + 0.5F, y + 0.5F, z + 0.5F, (block.stepSound.getVolume() + 1.0F) / 8F, block.stepSound.getPitch() * 0.5F);
			}
			stepSoundTickCounter++;
			if(curBlockDamageMP >= 1.0F) {
				isHittingBlock = false;
				netClientHandler.addToSendQueue(new Packet14BlockDig(2, x, y, z, face, System.currentTimeMillis()));
				sendBlockRemoved(x, y, z, face);
				curBlockDamageMP = 0.0F;
				prevBlockDamageMP = 0.0F;
				stepSoundTickCounter = 0.0F;
				blockHitDelay = 5;
			}
		} else {
			clickBlock(x, y, z, face);
		}
	}

	@Override
	public void setPartialTime(float f) {
		if(curBlockDamageMP <= 0.0F) {
			mc.ingameGUI.damageGuiPartialTime = 0.0F;
			mc.renderGlobal.damagePartialTime = 0.0F;
		} else {
			float f1 = prevBlockDamageMP + (curBlockDamageMP - prevBlockDamageMP) * f;
			mc.ingameGUI.damageGuiPartialTime = f1;
			mc.renderGlobal.damagePartialTime = f1;
		}
	}

	@Override
	public float getBlockReachDistance() {
		return !creativeMode ? 4.5F : 5F;
	}

	@Override
	public void onWorldChange(World world) {
		super.onWorldChange(world);
	}

	@Override
	public void updateController() {
		syncCurrentPlayItem();
		prevBlockDamageMP = curBlockDamageMP;
		mc.sndManager.playRandomMusicIfReady();
	}

	private void syncCurrentPlayItem() {
		int i = mc.thePlayer.inventory.currentItem;
		if(i != currentPlayerItem) {
			currentPlayerItem = i;
			netClientHandler.addToSendQueue(new Packet16BlockItemSwitch(currentPlayerItem));
		}
	}

	@Override
	public boolean sendPlaceBlock(EntityPlayer entityplayer, World world, ItemStack itemstack, int i, int j, int k, int l) {
		syncCurrentPlayItem();
		int i1 = world.getBlockId(i, j, k);
		if(i1 > 0 && Block.blocksList[i1].blockActivated(world, i, j, k, entityplayer)) {
			netClientHandler.addToSendQueue(new Packet15Place(i, j, k, l, 0));
			return true;
		}
		ItemState state = itemstack != null && itemstack.getDecorQuality() != DecorItemQuality.COLLECTIBLE ? itemstack.getItem().getState(itemstack, entityplayer, world, i, j, k, l) : null;
		if(state == null)
			netClientHandler.addToSendQueue(new Packet15Place(i, j, k, l, 0));
		else
			netClientHandler.addToSendQueue(new Packet15Place(state));
		if(itemstack == null)
			return false;
		if(creativeMode) {
			int j1 = itemstack.getItemDamage();
			int k1 = itemstack.stackSize;
			boolean flag = state == null ? itemstack.useItem(entityplayer, world, i, j, k, l) : itemstack.useItem(entityplayer, world, state.blockX, state.blockY, state.blockZ, state.blockFace);
			itemstack.setItemDamage(j1);
			itemstack.stackSize = k1;
			return flag;
		}
		return itemstack.getDecorQuality() != DecorItemQuality.COLLECTIBLE ? state == null ? itemstack.useItem(entityplayer, world, i, j, k, l) : itemstack.useItem(entityplayer, world, state.blockX, state.blockY, state.blockZ, state.blockFace) : false;
	}

	@Override
	public boolean sendUseItem2(EntityPlayer entityplayer, World world, ItemStack itemstack, int i, int j, int k, int l) {
		syncCurrentPlayItem();
		netClientHandler.addToSendQueue(new Packet211UseBlock2(i, j, k, l, 0));
		int i1 = world.getBlockId(i, j, k);
		if(i1 > 0 && Block.blocksList[i1].blockActivated2(world, i, j, k, entityplayer))
			return true;
		if(itemstack == null)
			return false;
		return itemstack.useItem2(entityplayer, world, i, j, k, l);
	}

	@Override
	public boolean sendUseItem(EntityPlayer entityplayer, World world, ItemStack itemstack) {
		syncCurrentPlayItem();
		netClientHandler.addToSendQueue(new Packet15Place(-1, -1, -1, 255, 0));
		return super.sendUseItem(entityplayer, world, itemstack);
	}

	@Override
	public EntityPlayer createPlayer(World world) {
		return new EntityClientPlayerMP(mc, world, mc.session, netClientHandler);
	}

	@Override
	public void attackEntity(EntityPlayer entityplayer, Entity entity) {
		syncCurrentPlayItem();
		netClientHandler.addToSendQueue(new Packet7UseEntity(entityplayer.entityId, entity.entityId, 1));
		entityplayer.attackTargetEntityWithCurrentItem(entity);
	}

	@Override
	public void interactWithEntity(EntityPlayer entityplayer, Entity entity) {
		syncCurrentPlayItem();
		netClientHandler.addToSendQueue(new Packet7UseEntity(entityplayer.entityId, entity.entityId, 0));
		entityplayer.useCurrentItemOnEntity(entity);
	}

	@Override
	public ItemStack windowClick(int i, int j, int k, boolean flag, EntityPlayer entityplayer) {
		if(!entityplayer.craftingInventory.canEdit)
			return null;
		short word0 = entityplayer.craftingInventory.func_20111_a(entityplayer.inventory);
		ItemStack itemstack = super.windowClick(i, j, k, flag, entityplayer);
		netClientHandler.addToSendQueue(new Packet102WindowClick(i, j, k, flag, itemstack, word0));
		return itemstack;
	}

	@Override
	public void func_40593_a(int i, int j) {
		netClientHandler.addToSendQueue(new Packet108EnchantItem(i, j));
	}

	@Override
	public void func_35637_a(ItemStack itemstack, int i) {
		if(creativeMode) {
			netClientHandler.addToSendQueue(new Packet107CreativeSetSlot(i, itemstack));
		}
	}

	@Override
	public void func_35639_a(ItemStack itemstack) {
		if(creativeMode && itemstack != null) {
			netClientHandler.addToSendQueue(new Packet107CreativeSetSlot(-1, itemstack));
		}
	}

	@Override
	public void func_20086_a(int i, EntityPlayer entityplayer) {
		if(i == -9999) {
			return;
		} else {
			return;
		}
	}

	@Override
	public void onStoppedUsingItem(EntityPlayer entityplayer) {
		syncCurrentPlayItem();
		netClientHandler.addToSendQueue(new Packet14BlockDig(5, 0, 0, 0, 255, System.currentTimeMillis()));
		super.onStoppedUsingItem(entityplayer);
	}

	@Override
	public boolean func_35642_f() {
		return true;
	}

	@Override
	public boolean func_35641_g() {
		return !creativeMode;
	}

	@Override
	public boolean isInCreativeMode() {
		return creativeMode;
	}

	@Override
	public boolean extendedReach() {
		return creativeMode;
	}

	protected boolean instabreak() {
		return mc.thePlayer.inventory.mainInventory[mc.thePlayer.inventory.currentItem] != null && mc.thePlayer.inventory.mainInventory[mc.thePlayer.inventory.currentItem].isInstabreak();
	}
}
