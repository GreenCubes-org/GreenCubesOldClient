package net.minecraft.src;

public class EntityFireworkTrace extends EntityThrowable {

	private int field_92056_a = 0;

	public EntityFireworkTrace(World world) {
		super(world);
	}

	public EntityFireworkTrace(World world, EntityLiving entityliving) {
		super(world, entityliving);
	}

	public EntityFireworkTrace(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if(this.field_92056_a == 0)
			this.worldObj.playSoundAtEntity(this, "fireworks.launch", 2.0F, 1.0F);
		++this.field_92056_a;
		if(this.worldObj.multiplayerWorld && this.field_92056_a % 2 < 2)
			this.worldObj.spawnParticle("fireworksSpark", this.posX, this.posY - 0.3D, this.posZ, this.rand.nextGaussian() * 0.05D, -this.motionY * 0.5D, this.rand.nextGaussian() * 0.05D);
	}

	@Override
	protected void func_40078_a(MovingObjectPosition movingobjectposition) {
		if(!worldObj.multiplayerWorld)
			kill();
	}

}
