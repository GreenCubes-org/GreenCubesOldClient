// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            EntityMob, DataWatcher, World, Entity, 
//            MathHelper, Item, EnumCreatureAttribute, PotionEffect, 
//            Potion, NBTTagCompound

public class EntitySpider extends EntityMob {

	public EntitySpider(World world) {
		super(world);
		texture = "/mob/spider.png";
		setSize(1.4F, 0.9F);
		moveSpeed = 0.8F;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, new Byte((byte) 0));
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if(!worldObj.multiplayerWorld) {
			func_40148_a(isCollidedHorizontally);
		}
	}

	@Override
	public int getMaxHealth() {
		return 16;
	}

	@Override
	public double getMountedYOffset() {
		return height * 0.75D - 0.5D;
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	protected Entity findPlayerToAttack() {
		float f = getEntityBrightness(1.0F);
		if(f < 0.5F) {
			double d = 16D;
			return worldObj.getClosestVulnerablePlayerToEntity(this, d);
		} else {
			return null;
		}
	}

	@Override
	protected String getLivingSound() {
		return "mob.spider";
	}

	@Override
	protected String getHurtSound() {
		return "mob.spider";
	}

	@Override
	protected String getDeathSound() {
		return "mob.spiderdeath";
	}

	@Override
	protected void attackEntity(Entity entity, float f) {
		float f1 = getEntityBrightness(1.0F);
		if(f1 > 0.5F && rand.nextInt(100) == 0) {
			entityToAttack = null;
			return;
		}
		if(f > 2.0F && f < 6F && rand.nextInt(10) == 0) {
			if(onGround) {
				double d = entity.posX - posX;
				double d1 = entity.posZ - posZ;
				float f2 = MathHelper.sqrt_double(d * d + d1 * d1);
				motionX = (d / f2) * 0.5D * 0.80000001192092896D + motionX * 0.20000000298023224D;
				motionZ = (d1 / f2) * 0.5D * 0.80000001192092896D + motionZ * 0.20000000298023224D;
				motionY = 0.40000000596046448D;
			}
		} else {
			super.attackEntity(entity, f);
		}
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
	protected int getDropItemId() {
		return Item.silk.shiftedIndex;
	}

	@Override
	protected void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		if(flag && (rand.nextInt(3) == 0 || rand.nextInt(1 + i) > 0)) {
			dropItem(Item.spiderEye.shiftedIndex, 1);
		}
	}

	@Override
	public boolean isOnLadder() {
		return func_40149_l_();
	}

	@Override
	public void setInWeb() {
	}

	public float spiderScaleAmount() {
		return 1.0F;
	}

	@Override
	public EnumCreatureAttribute func_40124_t() {
		return EnumCreatureAttribute.ARTHROPOD;
	}

	@Override
	public boolean func_40126_a(PotionEffect potioneffect) {
		if(potioneffect.getPotionID() == Potion.potionPoison.id) {
			return false;
		} else {
			return super.func_40126_a(potioneffect);
		}
	}

	public boolean func_40149_l_() {
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public void func_40148_a(boolean flag) {
		byte byte0 = dataWatcher.getWatchableObjectByte(16);
		if(flag) {
			byte0 |= 1;
		} else {
			byte0 &= 0xfe;
		}
		dataWatcher.updateObject(16, Byte.valueOf(byte0));
	}
}
