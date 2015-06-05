// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            EntityCreature, World, NBTTagCompound

public abstract class EntitySnowmanBase extends EntityCreature {

	public EntitySnowmanBase(World world) {
		super(world);
	}

	@Override
	protected void fall(float f) {
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
	}

	@Override
	protected String getLivingSound() {
		return "none";
	}

	@Override
	protected String getHurtSound() {
		return "none";
	}

	@Override
	protected String getDeathSound() {
		return "none";
	}

	@Override
	public int getTalkInterval() {
		return 120;
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}
}
