// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;
import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            Entity, MathHelper, Tessellator, World, 
//            NBTTagCompound

public class EntityFXGC {

	public static double interpPosX;
	public static double interpPosY;
	public static double interpPosZ;

	private int particleTextureIndex;
	protected float particleTextureJitterX;
	protected float particleTextureJitterY;
	protected int particleAge = 0;
	protected int particleMaxAge = 0;
	protected float particleScale;
	protected float particleGravity;
	protected float particleRed = 1.0F;
	protected float particleGreen = 1.0F;
	protected float particleBlue = 1.0F;
	protected float particleAlpha = 1.0F;

	/*
	 * Copied from Entity
	 */
	protected boolean canCollideWithEntities = false;
	protected boolean noClip = false;

	public float height = 0.2F;
	public float yOffset = 0.1F;
	public float width = 0.2F;
	//public float ySize = 0.0F;
	public final AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

	public double motionX;
	public double motionY;
	public double motionZ;
	public double posX;
	public double posY;
	public double posZ;
	public double prevPosY;
	public double prevPosZ;
	public double prevPosX;
	public boolean onGround = false;
	public boolean isCollidedHorizontally = false;
	public boolean isCollidedVertically = false;
	public boolean isCollided = false;

	public boolean isDead = false;

	public World worldObj;
	public Random rand = new Random();

	public EntityFXGC(World world, double x, double y, double z, double mx, double my, double mz) {
		worldObj = world;
		//super(world);
		canCollideWithEntities = false;
		//setSize(0.2F, 0.2F);
		//yOffset = height / 2.0F;
		setPosition(x, y, z);
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		motionX = mx + ((float) (Math.random() * 2D - 1.0D) * 0.4F);
		motionY = my + ((float) (Math.random() * 2D - 1.0D) * 0.4F);
		motionZ = mz + ((float) (Math.random() * 2D - 1.0D) * 0.4F);
		float f = (float) (Math.random() + Math.random() + 1.0D) * 0.15F;
		float f1 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
		motionX = (motionX / f1) * f * 0.4D;
		motionY = (motionY / f1) * f * 0.4D + 0.1D;
		motionZ = (motionZ / f1) * f * 0.4D;
		particleTextureJitterX = rand.nextFloat() * 3F;
		particleTextureJitterY = rand.nextFloat() * 3F;
		particleScale = (rand.nextFloat() * 0.5F + 0.5F) * 2.0F;
		particleMaxAge = (int) (4F / (rand.nextFloat() * 0.9F + 0.1F));
	}

	protected void setSize(float f, float f1) {
		width = f;
		height = f1;
	}

	public void setPosition(double d, double d1, double d2) {
		posX = d;
		posY = d1;
		posZ = d2;
		float f = width / 2.0F;
		float f1 = height;
		boundingBox.setBounds(d - f, (d1 - yOffset), d2 - f, d + f, (d1 - yOffset) + f1, d2 + f);
	}

	public void setEntityDead() {
		isDead = true;
	}

	public AxisAlignedBB getCollisionBox(Entity entity) {
		return boundingBox;
	}

	public int getEntityBrightnessForRender(float f) {
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(posZ);
		if(worldObj.chunkExists(i >> 4, j >> 4)) {
			double d = (boundingBox.maxY - boundingBox.minY) * 0.66000000000000003D;
			int k = MathHelper.floor_double((posY - yOffset) + d);
			return worldObj.getLightBrightnessForSkyBlocks(i, k, j, 0);
		} else
			return 0;
	}

	public float getEntityBrightness(float f) {
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(posZ);
		if(worldObj.chunkExists(i >> 4, j >> 4)) {
			double d = (boundingBox.maxY - boundingBox.minY) * 0.66000000000000003D;
			int k = MathHelper.floor_double(posY - yOffset + d);
			return worldObj.getLightBrightness(i, k, j);
		} else
			return 0.0F;
	}

	public EntityFXGC multiplyVelocity(float f) {
		motionX *= f;
		motionY = (motionY - 0.10000000149011612D) * f + 0.10000000149011612D;
		motionZ *= f;
		return this;
	}

	public EntityFXGC setSize(float f) {
		setSize(0.2F * f, 0.2F * f);
		particleScale *= f;
		return this;
	}

	public void setColor(float f, float f1, float f2) {
		particleRed = f;
		particleGreen = f1;
		particleBlue = f2;
	}

	public float getRedColor() {
		return particleRed;
	}

	public float getGreenColor() {
		return particleGreen;
	}

	public float getBlueColor() {
		return particleBlue;
	}

	/**
	* Sets the particle alpha (float)
	*/
	public void setAlphaF(float par1) {
		this.particleAlpha = par1;
	}

	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		if(particleAge++ >= particleMaxAge) {
			setEntityDead();
			return;
		}
		motionY -= 0.040000000000000001D * particleGravity;
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.98000001907348633D;
		motionY *= 0.98000001907348633D;
		motionZ *= 0.98000001907348633D;
		if(onGround) {
			motionX *= 0.69999998807907104D;
			motionZ *= 0.69999998807907104D;
		}
	}

	public void renderParticle(Tessellator tessellator, float framePart, float f1, float f2, float f3, float f4, float f5) {
		float f6 = (particleTextureIndex % 16) / 16F;
		float f7 = f6 + 0.0624375F;
		float f8 = (particleTextureIndex / 16) / (16F * (getFXLayer() == 2 ? RenderEngine.ITEMS_MULT : getFXLayer() == 1 ? RenderEngine.TERRAIN_MULT : 1));
		float f9 = f8 + 0.0624375F / (getFXLayer() == 2 ? RenderEngine.ITEMS_MULT : getFXLayer() == 1 ? RenderEngine.TERRAIN_MULT : 1);
		float f10 = 0.1F * particleScale;
		float f11 = (float) ((prevPosX + (posX - prevPosX) * framePart) - interpPosX);
		float f12 = (float) ((prevPosY + (posY - prevPosY) * framePart) - interpPosY);
		float f13 = (float) ((prevPosZ + (posZ - prevPosZ) * framePart) - interpPosZ);
		tessellator.setColorRGBA_F(particleRed, particleGreen, particleBlue, particleAlpha);
		tessellator.addVertexWithUV(f11 - f1 * f10 - f4 * f10, f12 - f2 * f10, f13 - f3 * f10 - f5 * f10, f7, f9);
		tessellator.addVertexWithUV((f11 - f1 * f10) + f4 * f10, f12 + f2 * f10, (f13 - f3 * f10) + f5 * f10, f7, f8);
		tessellator.addVertexWithUV(f11 + f1 * f10 + f4 * f10, f12 + f2 * f10, f13 + f3 * f10 + f5 * f10, f6, f8);
		tessellator.addVertexWithUV((f11 + f1 * f10) - f4 * f10, f12 - f2 * f10, (f13 + f3 * f10) - f5 * f10, f6, f9);
	}

	public int getFXLayer() {
		return 0;
	}

	public void setTextureIndex(int i) {
		particleTextureIndex = i;
	}

	public int getTextureIndex() {
		return particleTextureIndex;
	}

	public void moveEntity(double d, double d1, double d2) {
		if(noClip) {
			boundingBox.offset(d, d1, d2);
			posX = (boundingBox.minX + boundingBox.maxX) / 2D;
			posY = (boundingBox.minY + yOffset);
			posZ = (boundingBox.minZ + boundingBox.maxZ) / 2D;
			onGround = false;
			isCollided = isCollidedHorizontally = isCollidedVertically = false;
			return;
		}
		AxisAlignedBB axisalignedbb = boundingBox.copy();
		List<AxisAlignedBB> list = canCollideWithEntities ? worldObj.getCollidingBoundingBoxes(null, boundingBox.addCoord(d, d1, d2)) : worldObj.getCollidingBlocks(boundingBox.addCoord(d, d1, d2));
		if(list.size() == 0) {
			boundingBox.offset(d, d1, d2);
			posX = (boundingBox.minX + boundingBox.maxX) / 2D;
			posY = (boundingBox.minY + yOffset);
			posZ = (boundingBox.minZ + boundingBox.maxZ) / 2D;
			onGround = false;
			isCollided = isCollidedHorizontally = isCollidedVertically = false;
		} else {
			double d5 = d;
			double d6 = d1;
			double d7 = d2;
			for(int i = 0; i < list.size(); i++)
				d1 = list.get(i).calculateYOffset(boundingBox, d1);
			boundingBox.offset(0.0D, d1, 0.0D);
			for(int j = 0; j < list.size(); j++)
				d = list.get(j).calculateXOffset(boundingBox, d);
			boundingBox.offset(d, 0.0D, 0.0D);
			for(int k = 0; k < list.size(); k++)
				d2 = list.get(k).calculateZOffset(boundingBox, d2);
			boundingBox.offset(0.0D, 0.0D, d2);
			posX = (boundingBox.minX + boundingBox.maxX) / 2D;
			posY = boundingBox.minY + yOffset;
			posZ = (boundingBox.minZ + boundingBox.maxZ) / 2D;
			isCollidedHorizontally = d5 != d || d7 != d2;
			isCollidedVertically = d6 != d1;
			onGround = d6 != d1 && d6 < 0.0D;
			isCollided = isCollidedHorizontally || isCollidedVertically;
			if(d5 != d)
				motionX = 0.0D;
			if(d6 != d1)
				motionY = 0.0D;
			if(d7 != d2)
				motionZ = 0.0D;
		}
	}
}
