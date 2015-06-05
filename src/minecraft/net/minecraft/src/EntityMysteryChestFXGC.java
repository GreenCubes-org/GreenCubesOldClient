package net.minecraft.src;

import static net.minecraft.src.GreenTextures.*;

public class EntityMysteryChestFXGC extends EntityFXGC {

	private static final int TEXTURE_CHANGE_SPEED = 15;
	private static final double SPEED_MULTIPLER = 0.8D;

	private int textures[][] = {{P_M_TEX_1_1, P_M_TEX_1_2, P_M_TEX_1_3, P_M_TEX_1_4}, {P_M_TEX_2_1, P_M_TEX_2_2, P_M_TEX_2_3, P_M_TEX_2_4}, {P_M_TEX_3_1, P_M_TEX_3_2, P_M_TEX_3_3, P_M_TEX_3_4}, {P_M_TEX_4_1, P_M_TEX_4_2, P_M_TEX_4_3, P_M_TEX_4_4}};
	private int currentTexture = 0;
	private int texturePart = 0;

	protected int sourceBlockX;
	protected int sourceBlockY;
	protected int sourceBlockZ;

	public EntityMysteryChestFXGC(World world, int blockX, int blockY, int blockZ) {
		super(world, blockX, blockY, blockZ, 0, 0, 0);
		double x = blockX + 0.5D + (rand.nextBoolean() ? -1 : 1) * (0.5D + rand.nextDouble() * 0.3D);
		double y = blockY + 0.3D + rand.nextDouble() * 0.9D;
		double z = blockZ + 0.5D + (rand.nextBoolean() ? -1 : 1) * (0.5D + rand.nextDouble() * 0.3D);
		setPosition(x, y, z);
		this.prevPosX = posX;
		this.prevPosY = posY;
		this.prevPosZ = posZ;
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		this.sourceBlockX = blockX;
		this.sourceBlockY = blockY;
		this.sourceBlockZ = blockZ;
		this.particleMaxAge = getMinLifetime() + (int) ((getMaxLifetime() - getMinLifetime()) * rand.nextFloat());
		this.particleGravity = 0.0F;

		texturePart = rand.nextInt(textures.length);
		currentTexture = rand.nextInt(textures[texturePart].length);
		setTextureIndex(textures[texturePart][currentTexture]);
		this.particleScale *= 0.8F;
	}

	protected int getMinLifetime() {
		return 20;
	}

	protected int getMaxLifetime() {
		return 60;
	}

	@Override
	public void onUpdate() {
		updateToTarget(sourceBlockX + 0.5D, sourceBlockY + 0.1D, sourceBlockZ + 0.5D);
		super.onUpdate();
		afterUpdate();
	}

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
