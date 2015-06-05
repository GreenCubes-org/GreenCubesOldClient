package net.minecraft.src;

public class EntityDotFXGC extends EntityFXGC {

	public EntityDotFXGC(World world, double d, double d1, double d2, double d3, double d4, double d5) {
		super(world, d, d1, d2, 0, 0, 0);
		this.particleMaxAge = 20 * 60 * 10;
		this.particleRed = (float) d3;
		this.particleGreen = (float) d4;
		this.particleBlue = (float) d5;
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
	}

}
