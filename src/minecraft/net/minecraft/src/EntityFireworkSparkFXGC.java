package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class EntityFireworkSparkFXGC extends EntityFXGC {

	private int startingTexture = 160;
	private boolean trail;
	private boolean flicker;
	private float fadeColorR;
	private float fadeColorG;
	private float fadeColorB;
	private boolean isFading;

	public EntityFireworkSparkFXGC(World par1World, double par2, double par4, double par6, double par8, double par10, double par12) {
		super(par1World, par2, par4, par6, 0, 0, 0);
		this.motionX = par8;
		this.motionY = par10;
		this.motionZ = par12;
		this.particleScale *= 0.75F;
		this.particleMaxAge = 48 + this.rand.nextInt(12);
		this.noClip = false;
	}

	public void setTrail(boolean par1) {
		this.trail = par1;
	}

	public void setFlicker(boolean par1) {
		this.flicker = par1;
	}

	public void setColor(int par1) {
		float var2 = ((par1 & 16711680) >> 16) / 255.0F;
		float var3 = ((par1 & 65280) >> 8) / 255.0F;
		float var4 = ((par1 & 255) >> 0) / 255.0F;
		float var5 = 1.0F;
		this.setColor(var2 * var5, var3 * var5, var4 * var5);
	}

	public void setFadeColor(int par1) {
		this.fadeColorR = ((par1 & 16711680) >> 16) / 255.0F;
		this.fadeColorG = ((par1 & 65280) >> 8) / 255.0F;
		this.fadeColorB = ((par1 & 255) >> 0) / 255.0F;
		this.isFading = true;
	}

	/**
	 * returns the bounding box for this entity
	 */
	//@Override
	//public AxisAlignedBB getBoundingBox() {
	//	return null;
	//}

	/**
	 * Returns true if this entity should push and be pushed by other entities when colliding.
	 */
	//@Override
	//public boolean canBePushed() {
	//	return false;
	//}

	@Override
	public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
		if(!this.flicker || this.particleAge < this.particleMaxAge / 3 || (this.particleAge + this.particleMaxAge) / 3 % 2 == 0) {
			super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if(this.particleAge++ >= this.particleMaxAge) {
			this.setEntityDead();
		}

		if(this.particleAge > this.particleMaxAge / 2) {
			this.setAlphaF(1.0F - ((float) this.particleAge - (float) (this.particleMaxAge / 2)) / this.particleMaxAge);

			if(this.isFading) {
				this.particleRed += (this.fadeColorR - this.particleRed) * 0.2F;
				this.particleGreen += (this.fadeColorG - this.particleGreen) * 0.2F;
				this.particleBlue += (this.fadeColorB - this.particleBlue) * 0.2F;
			}
		}

		this.setTextureIndex(this.startingTexture + (7 - this.particleAge * 8 / this.particleMaxAge));
		this.motionY -= 0.004D;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9100000262260437D;
		this.motionY *= 0.9100000262260437D;
		this.motionZ *= 0.9100000262260437D;

		if(this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
		}

		if(this.trail && this.particleAge < this.particleMaxAge / 2 && (this.particleAge + this.particleMaxAge) % 2 == 0) {
			EntityFireworkSparkFXGC var1 = new EntityFireworkSparkFXGC(this.worldObj, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			var1.setColor(this.particleRed, this.particleGreen, this.particleBlue);
			int n = 2 << Minecraft.theMinecraft.gameSettings.particleSetting;
			var1.particleAge = var1.particleMaxAge / n;

			if(this.isFading) {
				var1.isFading = true;
				var1.fadeColorR = this.fadeColorR;
				var1.fadeColorG = this.fadeColorG;
				var1.fadeColorB = this.fadeColorB;
			}

			var1.flicker = this.flicker;
			Minecraft.theMinecraft.effectRenderer.addEffect(var1);
		}
	}

	public int getBrightnessForRender(float par1) {
		return 15728880;
	}

	/**
	 * Gets how bright this entity is.
	 */
	public float getBrightness(float par1) {
		return 1.0F;
	}
}
