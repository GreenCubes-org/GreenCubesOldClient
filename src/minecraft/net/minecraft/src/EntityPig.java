// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            EntityAnimal, DataWatcher, NBTTagCompound, World, 
//            EntityPlayer, Item, EntityPigZombie, AchievementList, 
//            EntityLightningBolt

public class EntityPig extends EntityAnimal {

	public EntityPig(World world) {
		super(world);
		texture = "/mob/pig.png";
		setSize(0.9F, 0.9F);
	}

	@Override
	public int getMaxHealth() {
		return 10;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setBoolean("Saddle", getSaddled());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
		setSaddled(nbttagcompound.getBoolean("Saddle"));
	}

	@Override
	protected String getLivingSound() {
		return "mob.pig.say";
	}

	@Override
	protected String getHurtSound() {
		return "mob.pig.say";
	}

	@Override
	protected String getDeathSound() {
		return "mob.pig.death";
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		if(!super.interact(entityplayer)) {
			if(getSaddled() && !worldObj.multiplayerWorld && (riddenByEntity == null || riddenByEntity == entityplayer)) {
				entityplayer.mountEntity(this);
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	@Override
	protected int getDropItemId() {
		if(isBurning()) {
			return Item.porkCooked.shiftedIndex;
		} else {
			return Item.porkRaw.shiftedIndex;
		}
	}

	public boolean getSaddled() {
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public void setSaddled(boolean flag) {
		if(flag) {
			dataWatcher.updateObject(16, Byte.valueOf((byte) 1));
		} else {
			dataWatcher.updateObject(16, Byte.valueOf((byte) 0));
		}
	}

	@Override
	public void onStruckByLightning(EntityLightningBolt entitylightningbolt) {
		if(worldObj.multiplayerWorld) {
			return;
		} else {
			EntityPigZombie entitypigzombie = new EntityPigZombie(worldObj);
			entitypigzombie.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
			worldObj.entityJoinedWorld(entitypigzombie);
			setEntityDead();
			return;
		}
	}

	@Override
	protected void fall(float f) {
		super.fall(f);
		if(f > 5F && (riddenByEntity instanceof EntityPlayer)) {
			((EntityPlayer) riddenByEntity).triggerAchievement(AchievementList.flyPig);
		}
	}

	@Override
	protected EntityAnimal func_40145_a(EntityAnimal entityanimal) {
		return new EntityPig(worldObj);
	}
}
