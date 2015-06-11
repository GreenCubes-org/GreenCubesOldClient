package net.minecraft.src;

import static net.minecraft.src.GreenTextures.*;

public class EntitySandFXGC extends EntityArmorFXGC {

	private static final int TEXTURE_CHANGE_SPEED = 15;
	private static final double SPEED_MULTIPLER = 1D;

	private int textures[][] = {{P_S_C_1_1, P_S_C_1_2, P_S_C_1_3, P_S_C_1_4}, {P_S_C_2_1, P_S_C_2_2, P_S_C_2_3, P_S_C_2_4}, {P_S_C_3_1, P_S_C_3_2, P_S_C_3_3, P_S_C_3_4}, {P_S_C_4_1, P_S_C_4_2, P_S_C_4_3, P_S_C_4_4}};
	private int currentTexture = 0;
	private int texturePart = 0;

	public EntitySandFXGC(World world, double x, double y, double z, EntityLiving source) {
		super(world, x, y, z, source, 0.5d);
		texturePart = rand.nextInt(textures.length);
		currentTexture = rand.nextInt(textures[texturePart].length);
		setTextureIndex(textures[texturePart][currentTexture]);
		this.particleScale *= 0.8F;
	}

	@Override
	protected void updateToTarget(double targetX, double targetY, double targetZ) {
		Vec3D targetVector = Vec3D.createVector(targetX - posX, 0, targetZ - posZ);
		targetVector.rotateAroundY((float) Math.toRadians(-90d));
		motionX = targetVector.xCoord;
		motionY = targetVector.yCoord;
		motionZ = targetVector.zCoord;
		double f = 0.1 * SPEED_MULTIPLER;
		double f1 = Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
		motionX = (motionX / f1) * f * 0.40000000596046448D;
		motionY = (motionY / f1) * f * 0.40000000596046448D;
		motionZ = (motionZ / f1) * f * 0.40000000596046448D;
	}

	@Override
	protected void afterUpdate() {
		if(particleAge % TEXTURE_CHANGE_SPEED == 0) {
			currentTexture--;
			if(currentTexture < 0) {
				setEntityDead();
			} else
				setTextureIndex(textures[texturePart][currentTexture]);
		}
	}

}
