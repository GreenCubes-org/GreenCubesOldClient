// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            EntityAnimal, Item, EntityPlayer, InventoryPlayer, 
//            ItemStack, World, NBTTagCompound

public class EntityCow extends EntityAnimal {

	public EntityCow(World world) {
		super(world);
		texture = "/mob/cow.png";
		setSize(0.9F, 1.3F);
		this.health = this.maxHealth = 10;
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
		return "mob.cow.say";
	}

	@Override
	protected String getHurtSound() {
		return "mob.cow.hurt";
	}

	@Override
	protected String getDeathSound() {
		return "mob.cow.hurt";
	}

	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}

	@Override
	protected int getDropItemId() {
		return Item.leather.shiftedIndex;
	}

	@Override
	protected void dropFewItems(boolean flag, int i) {
		int j = rand.nextInt(3) + rand.nextInt(1 + i);
		for(int k = 0; k < j; k++) {
			dropItem(Item.leather.shiftedIndex, 1);
		}

		j = rand.nextInt(3) + 1 + rand.nextInt(1 + i);
		for(int l = 0; l < j; l++) {
			if(isBurning()) {
				dropItem(Item.beefCooked.shiftedIndex, 1);
			} else {
				dropItem(Item.beefRaw.shiftedIndex, 1);
			}
		}

	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if(itemstack != null && itemstack.itemID == Item.bucketEmpty.shiftedIndex) {
			entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Item.bucketMilk));
			return true;
		} else {
			return super.interact(entityplayer);
		}
	}

	@Override
	protected EntityAnimal func_40145_a(EntityAnimal entityanimal) {
		return new EntityCow(worldObj);
	}
}
