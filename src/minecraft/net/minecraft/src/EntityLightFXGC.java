package net.minecraft.src;

import static net.minecraft.src.GreenTextures.*;

public class EntityLightFXGC extends EntityArmorFXGC {

	private static final int TEXTURE_CHANGE_SPEED = 15;
	private static final double SPEED_MULTIPLER = 0.8D;

	private int textures[][] = {{P_L_TEX_1_1, P_L_TEX_1_2, P_L_TEX_1_3, P_L_TEX_1_4}, {P_L_TEX_2_1, P_L_TEX_2_2, P_L_TEX_2_3, P_L_TEX_2_4}, {P_L_TEX_3_1, P_L_TEX_3_2, P_L_TEX_3_3, P_L_TEX_3_4}, {P_L_TEX_4_1, P_L_TEX_4_2, P_L_TEX_4_3, P_L_TEX_4_4}};
	private int currentTexture = 0;
	private int texturePart = 0;

	public EntityLightFXGC(World world, double x, double y, double z, EntityLiving source, double targetHeight) {
		super(world, x, y, z, source, targetHeight);
		texturePart = rand.nextInt(textures.length);
		currentTexture = rand.nextInt(textures[texturePart].length);
		setTextureIndex(textures[texturePart][currentTexture]);
		this.particleScale *= 0.8F;
	}

	@Override
	protected void updateToTarget(double targetX, double targetY, double targetZ) {
		motionX -= targetX - posX;
		motionY -= targetY - posY;
		motionZ -= targetZ - posZ;
		double f = 0.1 * SPEED_MULTIPLER;
		double f1 = Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
		motionX = (motionX / f1) * f * 0.40000000596046448D;
		motionY = (motionY / f1) * f * 0.40000000596046448D;
		motionZ = (motionZ / f1) * f * 0.40000000596046448D;
	}

	@Override
	protected void afterUpdate() {
		if(particleAge % TEXTURE_CHANGE_SPEED == 0) {
			currentTexture++;
			if(currentTexture >= textures[texturePart].length)
				setEntityDead();
			else
				setTextureIndex(textures[texturePart][currentTexture]);
		}
	}

}
