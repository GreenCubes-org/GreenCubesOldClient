package net.minecraft.src;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

public class ItemBlockParts extends ItemBlock {

	private BlockParts blockParts;

	public ItemBlockParts(int i, BlockParts blockParts) {
		super(i);
		this.blockParts = blockParts;
	}

	@Override
	public boolean shouldDrawOnSelection(ItemStack stack) {
		return true;
	}

	@Override
	public void drawOnSelection(World world, MovingObjectPosition selectedObject, ItemStack item, EntityPlayer player, float f) {
		double d = player.lastTickPosX + (player.posX - player.lastTickPosX) * f;
		double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * f;
		double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * f;
		int blockX = selectedObject.blockX;
		int blockY = selectedObject.blockY;
		int blockZ = selectedObject.blockZ;
		Block hitBlock = Block.blocksList[world.getBlockId(blockX, blockY, blockZ)];
		Vec3D hitVector = selectedObject.hitVec;
		if(hitBlock == blockParts) {
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
			BlockFace face = BlockFace.getFaceById(selectedObject.sideHit);
			blockX += face.getModX();
			blockY += face.getModY();
			blockZ += face.getModZ();
		}
		hitBlock = Block.blocksList[world.getBlockId(blockX, blockY, blockZ)];
		if(hitBlock == Block.waterMoving || hitBlock == Block.waterStill || hitBlock == Block.lavaMoving || hitBlock == Block.lavaStill || hitBlock == Block.fire || hitBlock == Block.snow || hitBlock == Block.vine || hitBlock == Block.tallGrass)
			hitBlock = null;
		if(hitBlock == null || hitBlock == blockParts) {
			Vec3D hitPoint = selectedObject.hitVec.addVector(-blockX, -blockY, -blockZ);
			GL11.glPushMatrix();
			GL11.glTranslated(-d, -d1, -d2);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			//GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.99F);
			Tessellator.instance.startDrawingQuads();
			drawBlock(world, hitPoint, blockX, blockY, blockZ);
			Tessellator.instance.draw();
			GL11.glPopMatrix();
			GL11.glDisable(GL11.GL_BLEND);
		}
	}

	protected void drawBlock(World world, Vec3D hitVec, int x, int y, int z) {
		RenderBlocks render = new RenderBlocks(world);
		double hitX = hitVec.xCoord;
		double hitY = hitVec.yCoord;
		double hitZ = hitVec.zCoord;
		blockParts.setBoundsByHoldenData(getDataByHit(hitVec));
		blockParts.drawing = true;
		render.renderStandardBlockWithColorMultiplier(blockParts, x, y, z, 1, 1, 1);
		blockParts.setBlockBounds(0, 0, 0, 1, 1, 1);
		blockParts.drawing = false;
	}

	@Override
	public ItemState getState(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int face) {
		MovingObjectPosition hitObject = Minecraft.theMinecraft.objectMouseOver;
		if(hitObject.typeOfHit == EnumMovingObjectType.TILE) {
			int blockX = x;
			int blockY = y;
			int blockZ = z;
			Block hitBlock = Block.blocksList[world.getBlockId(blockX, blockY, blockZ)];
			Vec3D hitVector = hitObject.hitVec;
			if(hitBlock == blockParts) {
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
			if(hitBlock == null || hitBlock == blockParts) {
				Vec3D hitPoint = hitObject.hitVec.addVector(-blockX, -blockY, -blockZ);
				return new ItemState(blockX, blockY, blockZ, face, getDataByHit(hitPoint));
			}
		}
		return null;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
		if(itemstack.stackSize == 0)
			return false;
		MovingObjectPosition hitObject = Minecraft.theMinecraft.objectMouseOver;
		if(hitObject.typeOfHit == EnumMovingObjectType.TILE) {
			int blockX = hitObject.blockX;
			int blockY = hitObject.blockY;
			int blockZ = hitObject.blockZ;
			Block hitBlock = Block.blocksList[world.getBlockId(blockX, blockY, blockZ)];
			Vec3D hitVector = hitObject.hitVec;
			if(hitBlock == blockParts) {
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
				BlockFace face = BlockFace.getFaceById(hitObject.sideHit);
				blockX += face.getModX();
				blockY += face.getModY();
				blockZ += face.getModZ();
			}
			hitBlock = Block.blocksList[world.getBlockId(blockX, blockY, blockZ)];
			if(hitBlock == Block.waterMoving || hitBlock == Block.waterStill || hitBlock == Block.lavaMoving || hitBlock == Block.lavaStill || hitBlock == Block.fire || hitBlock == Block.snow || hitBlock == Block.vine || hitBlock == Block.tallGrass)
				hitBlock = null;
			if(hitBlock == null || hitBlock == blockParts) {
				Vec3D hitPoint = hitObject.hitVec.addVector(-blockX, -blockY, -blockZ);
				int data = 0;
				if(hitBlock == blockParts)
					data = world.getBlockMetadata(blockX, blockY, blockZ);
				if(data == (data | getDataByHit(hitPoint)))
					return true;
				world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, blockParts.blockID, data | getDataByHit(hitPoint));
				world.playSoundEffect(blockX + 0.5F, blockY + 0.5F, blockZ + 0.5F, blockParts.stepSound.stepSoundDir2(), (blockParts.stepSound.getVolume() + 1.0F) / 2.0F, blockParts.stepSound.getPitch() * 0.8F);
				itemstack.stackSize--;
				return true;
			}
		}
		return false;
	}

	protected static int getDataByHit(Vec3D hitVec) {
		double hitX = hitVec.xCoord;
		double hitY = hitVec.yCoord;
		double hitZ = hitVec.zCoord;
		if(hitX < 0.5F && hitY < 0.5 && hitZ < 0.5)
			return 1;
		if(hitX >= 0.5F && hitY < 0.5 && hitZ < 0.5)
			return 2;
		if(hitX < 0.5F && hitY < 0.5 && hitZ >= 0.5)
			return 4;
		if(hitX >= 0.5F && hitY < 0.5 && hitZ >= 0.5)
			return 8;
		if(hitX < 0.5F && hitY >= 0.5 && hitZ < 0.5)
			return 16;
		if(hitX >= 0.5F && hitY >= 0.5 && hitZ < 0.5)
			return 32;
		if(hitX < 0.5F && hitY >= 0.5 && hitZ >= 0.5)
			return 64;
		if(hitX >= 0.5F && hitY >= 0.5 && hitZ >= 0.5)
			return 128;
		return 0;
	}

}
