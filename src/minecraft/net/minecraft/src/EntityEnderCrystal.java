// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            Entity, DataWatcher, MathHelper, World, 
//            Block, BlockFire, NBTTagCompound, DamageSource

public class EntityEnderCrystal extends Entity {

	public int field_41032_a;
	public int field_41031_b;

	public EntityEnderCrystal(World world) {
		super(world);
		field_41032_a = 0;
		preventEntitySpawning = true;
		setSize(2.0F, 2.0F);
		yOffset = height / 2.0F;
		field_41031_b = 5;
		field_41032_a = rand.nextInt(0x186a0);
	}

	public EntityEnderCrystal(World world, double d, double d1, double d2) {
		this(world);
		setPosition(d, d1, d2);
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	protected void entityInit() {
		dataWatcher.addObject(8, Integer.valueOf(field_41031_b));
	}

	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		field_41032_a++;
		dataWatcher.updateObject(8, Integer.valueOf(field_41031_b));
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(posY);
		int k = MathHelper.floor_double(posZ);
		if(worldObj.getBlockId(i, j, k) != Block.fire.blockID) {
			worldObj.setBlockWithNotify(i, j, k, Block.fire.blockID);
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
	}

	@Override
	public float getShadowSize() {
		return 0.0F;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, int i) {
		if(!isDead && !worldObj.multiplayerWorld) {
			field_41031_b = 0;
			if(field_41031_b <= 0) {
				if(!worldObj.multiplayerWorld) {
					setEntityDead();
					worldObj.createExplosion(null, posX, posY, posZ, 6F);
				} else {
					setEntityDead();
				}
			}
		}
		return true;
	}
}
