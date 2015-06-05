// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            ItemBlock, Block, ItemStack, BlockStep, 
//            EntityPlayer, World, StepSound

public class ItemSlab extends ItemBlock {

	public ItemSlab(int i) {
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getIconFromDamage(int i) {
		return Block.stairSingle.getBlockTextureFromSideAndMetadata(2, i);
	}

	@Override
	public int getPlacedBlockMetadata(int i) {
		return i;
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) {
		int i = itemstack.getItemDamage();
		if(i < 0 || i >= BlockStep.field_22037_a.length)
			return null;
		return (new StringBuilder()).append(super.getItemName()).append(".").append(BlockStep.field_22037_a[i]).toString();
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
		if(l == 1)
			;
		if(itemstack.stackSize == 0) {
			return false;
		}
		if(!entityplayer.func_35190_e(i, j, k)) {
			return false;
		}
		int i1 = world.getBlockId(i, j, k);
		int j1 = world.getBlockMetadata(i, j, k);
		if(l == 1 && i1 == Block.stairSingle.blockID && j1 == itemstack.getItemDamage()) {
			if(world.setBlockAndMetadataWithNotify(i, j, k, Block.stairDouble.blockID, j1)) {
				world.playSoundEffect(i + 0.5F, j + 0.5F, k + 0.5F, Block.stairDouble.stepSound.stepSoundDir2(), (Block.stairDouble.stepSound.getVolume() + 1.0F) / 2.0F, Block.stairDouble.stepSound.getPitch() * 0.8F);
				itemstack.stackSize--;
			}
			return true;
		} else {
			return super.onItemUse(itemstack, entityplayer, world, i, j, k, l);
		}
	}
}
