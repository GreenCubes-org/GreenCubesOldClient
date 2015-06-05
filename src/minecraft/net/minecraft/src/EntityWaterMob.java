// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            EntityCreature, World, NBTTagCompound, EntityPlayer

public abstract class EntityWaterMob extends EntityCreature {

	public EntityWaterMob(World world) {
		super(world);
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
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
	public boolean getCanSpawnHere() {
		return worldObj.checkIfAABBIsClear(boundingBox);
	}

	@Override
	public int getTalkInterval() {
		return 120;
	}

	@Override
	protected boolean canDespawn() {
		return true;
	}

	@Override
	protected int func_36001_a(EntityPlayer entityplayer) {
		return 1 + worldObj.rand.nextInt(3);
	}
}
