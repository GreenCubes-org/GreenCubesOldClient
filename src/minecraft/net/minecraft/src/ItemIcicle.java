// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            Item, Block, World, EntityPlayer, 
//            ItemStack, StepSound

public class ItemIcicle extends Item {

	private final int maxMetadata = 5;
	private BlockIcicle icicle;

	public ItemIcicle(int i, Block block) {
		super(i);
		this.icicle = (BlockIcicle) block;
		setMaxDamage(32);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		return itemstack;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
		if(world.multiplayerWorld)
			return true;
		if(l == 1)
			return true;
		int i1 = world.getBlockId(i, j, k);
		if(i1 == Block.snow.blockID) {
			l = 0;
		} else if(i1 != Block.vine.blockID && i1 != Block.ICICLE.blockID) {
			if(l == 0)
				j--;
			if(l == 1)
				j++;
			if(l == 2)
				k--;
			if(l == 3)
				k++;
			if(l == 4)
				i--;
			if(l == 5)
				i++;
			i1 = world.getBlockId(i, j, k);
		}
		if(!entityplayer.func_35190_e(i, j, k))
			return false;
		if(itemstack.stackSize == 0)
			return false;
		if(i1 == Block.ICICLE.blockID || world.canBlockBePlacedAt(icicle.blockID, i, j, k, false, l)) {
			BlockFace face = BlockFace.getDirectionOrt(entityplayer);
			if(icicle.canBePlacedAt(world, i, j, k, l, face)) {
				if(icicle.place(world, i, j, k, l, face)) {
					if(world.getBlockId(i, j, k) == icicle.blockID) {
						icicle.onBlockPlaced(world, i, j, k, l);
						icicle.onBlockPlacedBy(world, i, j, k, entityplayer);
					}
					world.playSoundEffect(i + 0.5F, j + 0.5F, k + 0.5F, icicle.stepSound.stepSoundDir2(), (icicle.stepSound.getVolume() + 1.0F) / 2.0F, icicle.stepSound.getPitch() * 0.8F);
					itemstack.damageItem(1, entityplayer);
					if(itemstack.stackSize == 0)
						itemstack = new ItemStack(Item.bucketEmpty, 1, 0);
				}
			}
		}
		return true;
	}
}
