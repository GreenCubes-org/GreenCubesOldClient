// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            SPCEntityInterface, Entity, SPCPoint, SPCWorld, 
//            SPCWorldInterface

public class SPCEntity implements SPCEntityInterface {

	private Entity e;

	public SPCEntity(Entity entity) {
		e = entity;
	}

	@Override
	public void dropItem(int i, int j) {
		e.dropItem(i, j);
	}

	@Override
	public int getAir() {
		return e.func_41001_Z();
	}

	@Override
	public int getFire() {
		return e.fireResistance;
	}

	@Override
	public SPCPoint getMotion() {
		return new SPCPoint(e.motionX, e.motionY, e.motionZ);
	}

	@Override
	public float getPitch() {
		return e.rotationPitch;
	}

	@Override
	public SPCPoint getPosition() {
		return new SPCPoint(e.posX, e.posY, e.posZ);
	}

	@Override
	public SPCWorldInterface getWorld() {
		return new SPCWorld(e.worldObj);
	}

	@Override
	public float getYaw() {
		return e.rotationYaw;
	}

	@Override
	public void kill() {
		e.kill();
	}

	@Override
	public void setNoclip(boolean flag) {
		e.noClip = flag;
	}

	@Override
	public boolean getNoclip() {
		return e.noClip;
	}

	@Override
	public void setAir(int i) {
		e.func_41003_g(i);
	}

	@Override
	public void setFire(int i) {
		e.fireResistance = i;
	}

	@Override
	public void setMotion(SPCPoint spcpoint) {
		e.setVelocity(spcpoint.dx, spcpoint.dy, spcpoint.dz);
	}

	@Override
	public void setPitch(float f) {
		e.rotationPitch = f;
	}

	@Override
	public void setPosition(SPCPoint spcpoint) {
		e.setPosition(spcpoint.dx, spcpoint.dy, spcpoint.dz);
	}

	@Override
	public void setYaw(float f) {
		e.rotationYaw = f;
	}

	@Override
	public void setRotation(float f, float f1) {
		e.setRotation(f, f1);
	}

	@Override
	public Entity getRawEntity() {
		return e;
	}

	@Override
	public boolean getImmuneToFire() {
		return e.isImmuneToFire;
	}

	@Override
	public void setImmuneToFire(boolean flag) {
		e.isImmuneToFire = flag;
	}
}
