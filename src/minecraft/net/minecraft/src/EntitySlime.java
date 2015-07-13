// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            EntityLiving, IMob, DataWatcher, NBTTagCompound, 
//            World, MathHelper, AxisAlignedBB, DamageSource, 
//            EntityPlayer, Item, Chunk

public class EntitySlime extends EntityLiving implements IMob {

	public float field_40139_a;
	public float field_768_a;
	public float field_767_b;
	private int slimeJumpDelay;

	public EntitySlime(World world) {
		super(world);
		slimeJumpDelay = 0;
		texture = "/mob/slime.png";
		int i = 1 << rand.nextInt(3);
		yOffset = 0.0F;
		slimeJumpDelay = rand.nextInt(20) + 10;
		setSlimeSize(i);
		field_35171_bJ = i;
	}
	
	@Override
	public void afterRegister() {
		super.afterRegister();
		setSlimeSize(getSlimeSize());
	}
	
	@Override
	public boolean isDanger() {
		return true;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, new Byte((byte) 0));
		setSlimeSize(1);
	}

	public void setSlimeSize(int i) {
		dataWatcher.updateObject(16, new Byte((byte) i));
		setSize(0.6F * i, 0.6F * i);
		setPosition(posX, posY, posZ);
		maxHealth = i * i;
		setEntityHealth(maxHealth);
	}

	public int getSlimeSize() {
		return dataWatcher.getWatchableObjectByte(16);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setInteger("Size", getSlimeSize() - 1);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
		setSlimeSize(nbttagcompound.getInteger("Size") + 1);
	}

	protected String func_40135_ac() {
		return "slime";
	}

	protected String func_40138_aj() {
		return "mob.slime.big";
	}

	@Override
	public void onUpdate() {
		if(!worldObj.multiplayerWorld && worldObj.difficultySetting == 0 && getSlimeSize() > 0) {
			isDead = true;
		}
		field_768_a = field_768_a + (field_40139_a - field_768_a) * 0.5F;
		field_767_b = field_768_a;
		boolean flag = onGround;
		super.onUpdate();
		if(onGround && !flag) {
			int i = getSlimeSize();
			for(int j = 0; j < i * 8; j++) {
				float f = rand.nextFloat() * 3.141593F * 2.0F;
				float f1 = rand.nextFloat() * 0.5F + 0.5F;
				float f2 = MathHelper.sin(f) * i * 0.5F * f1;
				float f3 = MathHelper.cos(f) * i * 0.5F * f1;
				worldObj.spawnParticle(func_40135_ac(), posX + f2, boundingBox.minY, posZ + f3, 0.0D, 0.0D, 0.0D);
			}

			if(func_40134_ak()) {
				worldObj.playSoundAtEntity(this, func_40138_aj(), getSoundVolume(), ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
			}
			field_40139_a = -0.5F;
		}
		func_40136_ag();
	}

	@Override
	protected void updateEntityActionState() {
		despawnEntity();
		EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, 16D);
		if(entityplayer != null) {
			faceEntity(entityplayer, 10F, 20F);
		}
		if(onGround && slimeJumpDelay-- <= 0) {
			slimeJumpDelay = func_40131_af();
			if(entityplayer != null) {
				slimeJumpDelay /= 3;
			}
			isJumping = true;
			if(func_40133_ao()) {
				worldObj.playSoundAtEntity(this, func_40138_aj(), getSoundVolume(), ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
			}
			field_40139_a = 1.0F;
			moveStrafing = 1.0F - rand.nextFloat() * 2.0F;
			moveForward = 1 * getSlimeSize();
		} else {
			isJumping = false;
			if(onGround) {
				moveStrafing = moveForward = 0.0F;
			}
		}
	}

	protected void func_40136_ag() {
		field_40139_a = field_40139_a * 0.6F;
	}

	protected int func_40131_af() {
		return rand.nextInt(20) + 10;
	}

	protected EntitySlime func_40132_ae() {
		return new EntitySlime(worldObj);
	}

	@Override
	public void setEntityDead() {
		int i = getSlimeSize();
		if(!worldObj.multiplayerWorld && i > 1 && getEntityHealth() <= 0) {
			int j = 2 + rand.nextInt(3);
			for(int k = 0; k < j; k++) {
				float f = (((k % 2) - 0.5F) * i) / 4F;
				float f1 = (((k / 2) - 0.5F) * i) / 4F;
				EntitySlime entityslime = func_40132_ae();
				entityslime.setSlimeSize(i / 2);
				entityslime.setLocationAndAngles(posX + f, posY + 0.5D, posZ + f1, rand.nextFloat() * 360F, 0.0F);
				worldObj.entityJoinedWorld(entityslime);
			}

		}
		super.setEntityDead();
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer entityplayer) {
		if(func_40137_ah()) {
			int i = getSlimeSize();
			if(canEntityBeSeen(entityplayer) && getDistanceToEntity(entityplayer) < 0.59999999999999998D * i && entityplayer.attackEntityFrom(DamageSource.causeMobDamage(this), func_40130_ai())) {
				worldObj.playSoundAtEntity(this, "mob.slime.attack", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
			}
		}
	}

	protected boolean func_40137_ah() {
		return getSlimeSize() > 1;
	}

	protected int func_40130_ai() {
		return getSlimeSize();
	}

	@Override
	protected String getHurtSound() {
		return "mob.slime.big";
	}

	@Override
	protected String getDeathSound() {
		return "mob.slime.big";
	}

	@Override
	protected int getDropItemId() {
		if(getSlimeSize() == 1) {
			return Item.slimeBall.shiftedIndex;
		} else {
			return 0;
		}
	}

	@Override
	public boolean getCanSpawnHere() {
		Chunk chunk = worldObj.getChunkFromBlockCoords(MathHelper.floor_double(posX), MathHelper.floor_double(posZ));
		if((getSlimeSize() == 1 || worldObj.difficultySetting > 0) && rand.nextInt(10) == 0 && chunk.getRandomWithSeed(0x3ad8025fL).nextInt(10) == 0 && posY < 40D) {
			return super.getCanSpawnHere();
		} else {
			return false;
		}
	}

	@Override
	protected float getSoundVolume() {
		return 0.4F * getSlimeSize();
	}

	@Override
	protected int getVerticalFaceSpeed() {
		return 0;
	}

	protected boolean func_40133_ao() {
		return true;
	}

	protected boolean func_40134_ak() {
		return true;
	}
}
