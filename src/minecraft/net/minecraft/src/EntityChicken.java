// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            EntityAnimal, World, Item, NBTTagCompound

public class EntityChicken extends EntityAnimal {

	public boolean field_753_a;
	public float field_752_b;
	public float destPos;
	public float field_757_d;
	public float field_756_e;
	public float field_755_h;
	public int timeUntilNextEgg;

	public EntityChicken(World world) {
		super(world);
		field_753_a = false;
		field_752_b = 0.0F;
		destPos = 0.0F;
		field_755_h = 1.0F;
		texture = "/mob/chicken.png";
		setSize(0.3F, 0.7F);
		timeUntilNextEgg = rand.nextInt(6000) + 6000;
	}

	@Override
	public int getMaxHealth() {
		return 4;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		field_756_e = field_752_b;
		field_757_d = destPos;
		destPos += (onGround ? -1 : 4) * 0.29999999999999999D;
		if(destPos < 0.0F) {
			destPos = 0.0F;
		}
		if(destPos > 1.0F) {
			destPos = 1.0F;
		}
		if(!onGround && field_755_h < 1.0F) {
			field_755_h = 1.0F;
		}
		field_755_h *= 0.90000000000000002D;
		if(!onGround && motionY < 0.0D) {
			motionY *= 0.59999999999999998D;
		}
		field_752_b += field_755_h * 2.0F;
		if(!func_40127_l() && !worldObj.multiplayerWorld && --timeUntilNextEgg <= 0) {
			worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
			dropItem(Item.egg.shiftedIndex, 1);
			timeUntilNextEgg = rand.nextInt(6000) + 6000;
		}
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
		return "mob.chicken.say";
	}

	@Override
	protected String getHurtSound() {
		return "mob.chicken.hurt";
	}

	@Override
	protected String getDeathSound() {
		return "mob.chicken.hurt";
	}

	@Override
	protected int getDropItemId() {
		return Item.feather.shiftedIndex;
	}

	@Override
	protected void dropFewItems(boolean flag, int i) {
		int j = rand.nextInt(3) + rand.nextInt(1 + i);
		for(int k = 0; k < j; k++) {
			dropItem(Item.feather.shiftedIndex, 1);
		}

		if(isBurning()) {
			dropItem(Item.chickenCooked.shiftedIndex, 1);
		} else {
			dropItem(Item.chickenRaw.shiftedIndex, 1);
		}
	}

	@Override
	protected EntityAnimal func_40145_a(EntityAnimal entityanimal) {
		return new EntityChicken(worldObj);
	}
}
