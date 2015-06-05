// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            SPCPoint, SPCWorldInterface, Entity

public interface SPCEntityInterface {

	public abstract void setPosition(SPCPoint spcpoint);

	public abstract SPCPoint getPosition();

	public abstract float getYaw();

	public abstract void setYaw(float f);

	public abstract float getPitch();

	public abstract void setPitch(float f);

	public abstract SPCWorldInterface getWorld();

	public abstract void setNoclip(boolean flag);

	public abstract boolean getNoclip();

	public abstract void setAir(int i);

	public abstract int getAir();

	public abstract void setFire(int i);

	public abstract int getFire();

	public abstract void kill();

	public abstract void dropItem(int i, int j);

	public abstract void setMotion(SPCPoint spcpoint);

	public abstract SPCPoint getMotion();

	public abstract void setRotation(float f, float f1);

	public abstract Entity getRawEntity();

	public abstract boolean getImmuneToFire();

	public abstract void setImmuneToFire(boolean flag);
}
