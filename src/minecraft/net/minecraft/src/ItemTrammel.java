package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

public class ItemTrammel extends Item {

	protected ItemTrammel(int id) {
		super(id);
		setMaxStackSize(1);
		setMaxDamage(512);
	}

	@Override
	public int getIconFromDamage(int damage) {
		if(damage == 0)
			return GreenTextures.trammel;
		return GreenTextures.trammelActive;
	}

	@Override
	public boolean useMultirender(ItemStack source, ItemRenderType renderType) {
		return renderType == ItemRenderType.INVENTORY && source.itemDamage != 0 && source.hasNBTData() && source.nbtData.hasKey("Content");
	}

	@Override
	public List<ItemStack> getMultirender(ItemStack source, ItemRenderType renderType) {
		List<ItemStack> list = new ArrayList<ItemStack>(2);
		list.add(new ItemStack(source.nbtData.getCompoundTag("Content").getInteger("Id"), 1, 0));
		ItemStack r = source.clone();
		r.nbtData.remove("Content");
		list.add(r);
		return list;
	}

	@Override
	public boolean shouldDrawOnSelection(ItemStack stack) {
		return useMultirender(stack, ItemRenderType.INVENTORY);
	}

	@Override
	public void drawOnSelection(World world, MovingObjectPosition selectedObject, ItemStack item, EntityPlayer player, float f) {
		NBTTagCompound content = item.nbtData.getCompoundTag("Content");
		double d = player.lastTickPosX + (player.posX - player.lastTickPosX) * f;
		double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * f;
		double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * f;
		int blockX = selectedObject.blockX;
		int blockY = selectedObject.blockY;
		int blockZ = selectedObject.blockZ;
		Block hitBlock = Block.blocksList[world.getBlockId(blockX, blockY, blockZ)];
		Vec3D hitVector = selectedObject.hitVec;
		if(hitBlock.blockID == item.nbtData.getCompoundTag("Content").getInteger("Id")) {
			Vec3D look = Minecraft.theMinecraft.thePlayer.getLookVec();
			if(look.xCoord > 0)
				hitVector.xCoord -= 0.001F;
			else if(look.xCoord < 0)
				hitVector.xCoord += 0.001F;
			if(look.yCoord > 0)
				hitVector.yCoord -= 0.001F;
			else if(look.yCoord < 0)
				hitVector.yCoord += 0.001F;
			if(look.zCoord > 0)
				hitVector.zCoord -= 0.001F;
			else if(look.zCoord < 0)
				hitVector.zCoord += 0.001F;
			blockX = MathHelper.floor_double(hitVector.xCoord);
			blockY = MathHelper.floor_double(hitVector.yCoord);
			blockZ = MathHelper.floor_double(hitVector.zCoord);
		} else {
			BlockFace blockFace = BlockFace.getFaceById(selectedObject.sideHit);
			blockX += blockFace.getModX();
			blockY += blockFace.getModY();
			blockZ += blockFace.getModZ();
		}
		hitBlock = Block.blocksList[world.getBlockId(blockX, blockY, blockZ)];
		if(hitBlock == Block.waterMoving || hitBlock == Block.waterStill || hitBlock == Block.lavaMoving || hitBlock == Block.lavaStill || hitBlock == Block.fire || hitBlock == Block.snow || hitBlock == Block.vine || hitBlock == Block.tallGrass)
			hitBlock = null;
		if(hitBlock == null || hitBlock.blockID == item.nbtData.getCompoundTag("Content").getInteger("Id")) {
			Vec3D hitPoint = selectedObject.hitVec.addVector(-blockX, -blockY, -blockZ);
			GL11.glPushMatrix();
			GL11.glTranslated(-d, -d1, -d2);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			//GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
			Tessellator.instance.startDrawingQuads();
			int data = content.getShort("Data");
			int blockId = content.getInteger("Id");
			if(hitBlock != null && hitBlock.blockID == blockId)
				data &= ~(world.getBlockMetadata(blockX, blockY, blockZ));
			drawBlock(world, data, (BlockParts) Block.blocksList[blockId], blockX, blockY, blockZ);
			Tessellator.instance.draw();
			GL11.glPopMatrix();
			GL11.glDisable(GL11.GL_BLEND);
		}
	}

	@Override
	public boolean onItemUse2(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
		int block = world.getBlockId(i, j, k);
		Block type = Block.blocksList[block];
		if(type != null && type instanceof BlockParts) {
			itemstack.nbtData = new NBTTagCompound();
			NBTTagCompound content = new NBTTagCompound();
			itemstack.nbtData.setCompoundTag("Content", content);
			content.setInteger("Id", block);
			content.setShort("Data", (short) world.getBlockMetadata(i, j, k));
			if(itemstack.itemDamage == 0)
				itemstack.itemDamage = 1;
			return true;
		}
		return false;
	}

	protected void drawBlock(World world, int data, BlockParts block, int x, int y, int z) {
		RenderBlocks render = new RenderBlocks(world);
		render.renderBlockTrammel(data, block, x, y, z);
	}

	@Override
	public ItemState getState(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int face) {
		if(useMultirender(itemStack, ItemRenderType.INVENTORY)) {
			MovingObjectPosition hitObject = Minecraft.theMinecraft.objectMouseOver;
			if(hitObject.typeOfHit == EnumMovingObjectType.TILE) {
				int blockX = x;
				int blockY = y;
				int blockZ = z;
				Block hitBlock = Block.blocksList[world.getBlockId(blockX, blockY, blockZ)];
				Vec3D hitVector = hitObject.hitVec;
				if(hitBlock.blockID == itemStack.nbtData.getCompoundTag("Content").getInteger("Id")) {
					Vec3D look = Minecraft.theMinecraft.thePlayer.getLookVec();
					if(look.xCoord > 0)
						hitVector.xCoord -= 0.001F;
					else if(look.xCoord < 0)
						hitVector.xCoord += 0.001F;
					if(look.yCoord > 0)
						hitVector.yCoord -= 0.001F;
					else if(look.yCoord < 0)
						hitVector.yCoord += 0.001F;
					if(look.zCoord > 0)
						hitVector.zCoord -= 0.001F;
					else if(look.zCoord < 0)
						hitVector.zCoord += 0.001F;
					blockX = MathHelper.floor_double(hitVector.xCoord);
					blockY = MathHelper.floor_double(hitVector.yCoord);
					blockZ = MathHelper.floor_double(hitVector.zCoord);
				} else {
					BlockFace blockFace = BlockFace.getFaceById(face);
					blockX += blockFace.getModX();
					blockY += blockFace.getModY();
					blockZ += blockFace.getModZ();
				}
				hitBlock = Block.blocksList[world.getBlockId(blockX, blockY, blockZ)];
				if(hitBlock == Block.waterMoving || hitBlock == Block.waterStill || hitBlock == Block.lavaMoving || hitBlock == Block.lavaStill || hitBlock == Block.fire || hitBlock == Block.snow || hitBlock == Block.vine || hitBlock == Block.tallGrass)
					hitBlock = null;
				if(hitBlock == null || hitBlock.blockID == itemStack.nbtData.getCompoundTag("Content").getInteger("Id")) {
					Vec3D hitPoint = hitObject.hitVec.addVector(-blockX, -blockY, -blockZ);
					return new ItemState(blockX, blockY, blockZ, face, itemStack.nbtData.getCompoundTag("Content").getShort("Data"));
				}
			}
		}
		return null;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
		if(!useMultirender(itemstack, ItemRenderType.INVENTORY) || itemstack.isBroken())
			return false;
		MovingObjectPosition hitObject = Minecraft.theMinecraft.objectMouseOver;
		if(hitObject.typeOfHit == EnumMovingObjectType.TILE) {
			System.out.println(hitObject);
			NBTTagCompound content = itemstack.nbtData.getCompoundTag("Content");
			int blockId = content.getInteger("Id");
			int reqCount = Integer.bitCount(content.getShort("Data"));
			ItemStack req = new ItemStack(blockId, reqCount, 0);
			if(entityplayer.inventory.contains(req) != null)
				return true;
			int blockX = i;
			int blockY = j;
			int blockZ = k;
			Block hitBlock = Block.blocksList[world.getBlockId(blockX, blockY, blockZ)];
			if(hitBlock == null || hitBlock.blockID == blockId) {
				Vec3D hitPoint = hitObject.hitVec.addVector(-blockX, -blockY, -blockZ);
				int data = 0;
				if(hitBlock != null && hitBlock.blockID == blockId)
					data = world.getBlockMetadata(blockX, blockY, blockZ);
				Block blockParts = Block.blocksList[blockId];
				req.stackSize = Integer.bitCount(content.getShort("Data") & ~data);
				world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, blockId, data | content.getShort("Data"));
				world.playSoundEffect(blockX + 0.5F, blockY + 0.5F, blockZ + 0.5F, blockParts.stepSound.stepSoundDir2(), (blockParts.stepSound.getVolume() + 1.0F) / 2.0F, blockParts.stepSound.getPitch() * 0.8F);
				itemstack.damageItem(1, entityplayer);
				entityplayer.inventory.remove(req);
				return true;
			}
		}
		return false;
	}

}
