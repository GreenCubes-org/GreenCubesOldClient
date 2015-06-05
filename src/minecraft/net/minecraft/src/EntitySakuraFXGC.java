package net.minecraft.src;

public class EntitySakuraFXGC extends EntityFXGC {

	public EntitySakuraFXGC(World world, double d, double d1, double d2, double d3, double d4, double d5) {
		super(world, d, d1, d2, d3, d4, d5);
		particleGravity = 0.0116F;
		switch(rand.nextInt(3)) {
		case 0:
			setTextureIndex(GreenTextures.sakurapetal1);
			break;
		case 1:
			setTextureIndex(GreenTextures.sakurapetal2);
			break;
		case 2:
			setTextureIndex(GreenTextures.sakurapetal3);
			break;
		}
		if(d4 == 0.0D && (d3 != 0.0D || d5 != 0.0D)) {
			motionX = d3;
			motionY = d4;
			motionZ = d5;
		}
		motionX *= -0.2D;
		motionY = Math.abs(motionY);
		motionY *= -0.1D;
		motionZ *= -0.2D;
		particleMaxAge = (int) (350D / (Math.random() * 0.8D + 0.2D));
		//int i = rand.nextInt(4);
		//chooseColor(i);

	}

	@Override
	public int getFXLayer() {
		return 2;
	}

	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		if(particleAge++ >= particleMaxAge) {
			setEntityDead();
		}
		motionY -= 0.040000000000000001D * particleGravity;
		moveEntity(motionX, motionY, motionZ);
		if(onGround) {
			motionX = 0;
			motionY = 0;
			motionZ = 0;
		}
		motionX *= 0.99399997854232788D;
		motionY *= 0.95999997854232788D;
		motionZ *= 0.99399997854232788D;
	}
}
