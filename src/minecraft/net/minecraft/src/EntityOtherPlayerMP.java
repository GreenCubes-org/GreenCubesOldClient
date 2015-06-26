// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            EntityPlayer, MathHelper, InventoryPlayer, Item, 
//            ItemStack, World, DamageSource

public class EntityOtherPlayerMP extends EntityPlayer {

	private int otherPlayerMPPosRotationIncrements;
	private double otherPlayerMPX;
	private double otherPlayerMPY;
	private double otherPlayerMPZ;
	private double otherPlayerMPYaw;
	private double otherPlayerMPPitch;
	float field_20924_a;
	public String coloredName;

	public EntityOtherPlayerMP(World world, String s) {
		super(world);
		field_20924_a = 0.0F;
		username = s;
		yOffset = 0.0F;
		stepHeight = 0.0F;
		if(s != null && s.length() > 0) {
			skinUrl = (new StringBuilder()).append("http://greenusercontent.net/mc/skins/").append(s).append(".png").toString();
		}
		noClip = true;
		field_22062_y = 0.25F;
		renderDistanceWeight = 10D;
	}

	@Override
	public ItemStack getEquipment(int slot) {
		if(slot == 0)
			return inventory.getCurrentItem();
		return inventory.armorItemInSlot(slot - 1);
	}

	@Override
	public void updateCloak() {
		playerCloakUrl = (new StringBuilder()).append("http://greenusercontent.net/mc/capes/").append(username).append(".png").toString();
		cloakUrl = playerCloakUrl;
	}

	@Override
	protected void resetHeight() {
		yOffset = 0.0F;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, int i) {
		return true;
	}

	@Override
	public void setPositionAndRotation2(double d, double d1, double d2, float f, float f1, int i) {
		otherPlayerMPX = d;
		otherPlayerMPY = d1;
		otherPlayerMPZ = d2;
		otherPlayerMPYaw = f;
		otherPlayerMPPitch = f1;
		otherPlayerMPPosRotationIncrements = i;
	}

	@Override
	public void onUpdate() {
		field_22062_y = 0.0F;
		super.onUpdate();
		field_705_Q = field_704_R;
		double d = posX - prevPosX;
		double d1 = posZ - prevPosZ;
		float f = MathHelper.sqrt_double(d * d + d1 * d1) * 4F;
		if(f > 1.0F) {
			f = 1.0F;
		}
		field_704_R += (f - field_704_R) * 0.4F;
		field_703_S += field_704_R;
	}

	@Override
	public float getShadowSize() {
		return 0.0F;
	}

	@Override
	public void onLivingUpdate() {
		prevSwingProgress = swingProgress;
		int i1 = getArmSwingSpeed();
		if(isSwinging) {
			swingProgressInt++;
			if(swingProgressInt >= i1) {
				swingProgressInt = 0;
				isSwinging = false;
			}
		} else {
			swingProgressInt = 0;
		}
		swingProgress = (float) swingProgressInt / (float) i1;
		super.updateEntityActionState();
		if(otherPlayerMPPosRotationIncrements > 0) {
			double d = posX + (otherPlayerMPX - posX) / otherPlayerMPPosRotationIncrements;
			double d1 = posY + (otherPlayerMPY - posY) / otherPlayerMPPosRotationIncrements;
			double d2 = posZ + (otherPlayerMPZ - posZ) / otherPlayerMPPosRotationIncrements;
			double d3;
			for(d3 = otherPlayerMPYaw - rotationYaw; d3 < -180D; d3 += 360D) {
			}
			for(; d3 >= 180D; d3 -= 360D) {
			}
			rotationYaw += d3 / otherPlayerMPPosRotationIncrements;
			rotationPitch += (otherPlayerMPPitch - rotationPitch) / otherPlayerMPPosRotationIncrements;
			otherPlayerMPPosRotationIncrements--;
			setPosition(d, d1, d2);
			setRotation(rotationYaw, rotationPitch);
		}
		prevCameraYaw = cameraYaw;
		float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		float f1 = (float) Math.atan(-motionY * 0.20000000298023224D) * 15F;
		if(f > 0.1F) {
			f = 0.1F;
		}
		if(!onGround || getEntityHealth() <= 0) {
			f = 0.0F;
		}
		if(onGround || getEntityHealth() <= 0) {
			f1 = 0.0F;
		}
		cameraYaw += (f - cameraYaw) * 0.4F;
		cameraPitch += (f1 - cameraPitch) * 0.8F;
	}

	@Override
	public void outfitWithItem(int i, ItemStack item) {
		if(i == 0) {
			inventory.mainInventory[inventory.currentItem] = item;
		} else {
			inventory.armorInventory[i - 1] = item;
		}
	}

	@Override
	public void func_6420_o() {
	}

	@Override
	public float getEyeHeight() {
		return 1.82F;
	}
}
