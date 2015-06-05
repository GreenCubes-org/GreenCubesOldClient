package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class EntityArmorFXGC extends EntityFXGC {

	public final EntityLiving source;
	protected double targetHeight;

	public EntityArmorFXGC(World world, double x, double y, double z, EntityLiving source, double targetHeight) {
		super(world, x, y, z, 0, 0, 0);
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		this.source = source;
		this.particleMaxAge = getMinLifetime() + (int) ((getMaxLifetime() - getMinLifetime()) * rand.nextFloat());
		this.targetHeight = targetHeight;
		this.particleGravity = 0.0F;
	}

	protected int getMinLifetime() {
		return 20;
	}

	protected int getMaxLifetime() {
		return 60;
	}

	protected double getMaxUpdateDistance() {
		return 7.0D;
	}

	@Override
	public void onUpdate() {
		updateToSource(source);
		super.onUpdate();
		afterUpdate();
	}

	protected void afterUpdate() {

	}

	protected void updateToSource(Entity source) {
		if(!source.isDead && source.getDistance(posX, posY, posZ) < getMaxUpdateDistance()) {
			double targetX = source.posX;
			double targetZ = source.posZ;
			double targetY = source.boundingBox.minY + (source.boundingBox.maxY - source.boundingBox.minY) * targetHeight;
			updateToTarget(targetX, targetY, targetZ);
		}
	}

	protected void updateToTarget(double targetX, double targetY, double targetZ) {
		motionX -= targetX - posX;
		motionY -= targetY - posY;
		motionZ -= targetZ - posZ;
		double f = 0.08;
		double f1 = Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
		motionX = (motionX / f1) * f * 0.40000000596046448D;
		motionY = (motionY / f1) * f * 0.40000000596046448D;
		motionZ = (motionZ / f1) * f * 0.40000000596046448D;
	}

	@Override
	public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
		if(source == Minecraft.theMinecraft.renderViewEntity && Minecraft.theMinecraft.gameSettings.thirdPersonView == 0)
			return;
		super.renderParticle(tessellator, f, f1, f2, f3, f4, f5);
	}

}
