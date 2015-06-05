// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import net.minecraft.client.Minecraft;

// Referenced classes of package net.minecraft.src:
//            EntityPlayerSP, World, Session, DamageSource

public class SPCEntityCamera extends EntityPlayerSP {

	public SPCEntityCamera(Minecraft minecraft, World world, Session session, int i) {
		super(minecraft, world, session, i);
		yOffset = 1.62F;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public void onEntityUpdate() {
	}

	@Override
	public void onUpdate() {
	}

	@Override
	public void onDeath(DamageSource damagesource) {
	}

	@Override
	public boolean isEntityAlive() {
		return true;
	}

	public void setCamera(double d, double d1, double d2, float f, float f1) {
		lastTickPosX = posX;
		lastTickPosY = posY;
		lastTickPosZ = posZ;
		posX += d;
		posY += d1;
		posZ += d2;
		prevRotationYaw = rotationYaw;
		prevRotationPitch = rotationPitch;
		rotationYaw = f;
		rotationPitch = f1;
	}
}
