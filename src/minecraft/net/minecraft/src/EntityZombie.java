// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            EntityMob, World, MathHelper, Item, 
//            EnumCreatureAttribute

public class EntityZombie extends EntityMob {

	public EntityZombie(World world) {
		super(world);
		texture = "/mob/zombie.png";
		moveSpeed = 0.5F;
		attackStrength = 4;
		this.health = this.maxHealth = 20;
	}

	@Override
	protected int func_40119_ar() {
		return 2;
	}

	@Override
	public void onLivingUpdate() {
		if(worldObj.isDaytime() && !worldObj.multiplayerWorld) {
			float f = getEntityBrightness(1.0F);
			if(f > 0.5F && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) && rand.nextFloat() * 30F < (f - 0.4F) * 2.0F) {
				func_40046_d(8);
			}
		}
		super.onLivingUpdate();
	}

	@Override
	protected String getLivingSound() {
		return "mob.zombie";
	}

	@Override
	protected String getHurtSound() {
		return "mob.zombiehurt";
	}

	@Override
	protected String getDeathSound() {
		return "mob.zombiedeath";
	}

	@Override
	protected int getDropItemId() {
		return Item.rottenFlesh.shiftedIndex;
	}

	@Override
	public EnumCreatureAttribute func_40124_t() {
		return EnumCreatureAttribute.UNDEAD;
	}
}
