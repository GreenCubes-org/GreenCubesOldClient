// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            EntitySlime, World

public class EntityMagmaCube extends EntitySlime {

	public EntityMagmaCube(World world) {
		super(world);
		texture = "/mob/lava.png";
		isImmuneToFire = true;
		landMovementFactor = 0.2F;
	}

	@Override
	public boolean getCanSpawnHere() {
		return worldObj.difficultySetting > 0 && worldObj.checkIfAABBIsClear(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0 && !worldObj.getIsAnyLiquid(boundingBox);
	}

	@Override
	protected int func_40119_ar() {
		return getSlimeSize() * 3;
	}

	@Override
	public int getEntityBrightnessForRender(float f) {
		return 0xf000f0;
	}

	@Override
	public float getEntityBrightness(float f) {
		return 1.0F;
	}

	@Override
	protected String func_40135_ac() {
		return "flame";
	}

	@Override
	protected EntitySlime func_40132_ae() {
		return new EntityMagmaCube(worldObj);
	}

	@Override
	protected int getDropItemId() {
		return 0;
	}

	@Override
	public boolean isBurning() {
		return false;
	}

	@Override
	protected int func_40131_af() {
		return super.func_40131_af() * 4;
	}

	@Override
	protected void func_40136_ag() {
		field_40139_a = field_40139_a * 0.9F;
	}

	@Override
	protected void jump() {
		motionY = 0.42F + getSlimeSize() * 0.1F;
		isAirBorne = true;
	}

	@Override
	protected void fall(float f) {
	}

	@Override
	protected boolean func_40137_ah() {
		return true;
	}

	@Override
	protected int func_40130_ai() {
		return super.func_40130_ai() + 2;
	}

	@Override
	protected String getHurtSound() {
		return "mob.slime";
	}

	@Override
	protected String getDeathSound() {
		return "mob.slime";
	}

	@Override
	protected String func_40138_aj() {
		if(getSlimeSize() > 1) {
			return "mob.magmacube.big";
		} else {
			return "mob.magmacube.small";
		}
	}

	@Override
	public boolean handleLavaMovement() {
		return false;
	}

	@Override
	protected boolean func_40134_ak() {
		return true;
	}
}
