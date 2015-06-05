// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            ItemColored, MovingObjectPosition, EnumMovingObjectType, World, 
//            EntityPlayer, Material, Block, PlayerCapabilities, 
//            ItemStack

public class ItemLilyPad extends ItemColored {

	public ItemLilyPad(int i) {
		super(i, false);
	}

	@Override
	public boolean traceLiquids(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if(world.multiplayerWorld)
			return itemstack;
		MovingObjectPosition movingobjectposition = func_40402_a(world, entityplayer, true);
		if(movingobjectposition == null) {
			return itemstack;
		}
		if(movingobjectposition.typeOfHit == EnumMovingObjectType.TILE) {
			int i = movingobjectposition.blockX;
			int j = movingobjectposition.blockY;
			int k = movingobjectposition.blockZ;
			if(!world.canMineBlock(entityplayer, i, j, k)) {
				return itemstack;
			}
			if(!entityplayer.func_35190_e(i, j, k)) {
				return itemstack;
			}
			if(world.getBlockMaterial(i, j, k) == Material.water && world.getBlockMetadata(i, j, k) == 0 && world.isAirBlock(i, j + 1, k)) {
				world.setBlockWithNotify(i, j + 1, k, Block.waterlily.blockID);
				if(!entityplayer.capabilities.depleteBuckets) {
					itemstack.stackSize--;
				}
			}
		}
		return itemstack;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
		if(!world.multiplayerWorld)
			return false;
		int i1 = world.getBlockId(i, j, k);
		if(i1 == Block.snow.blockID) {
			l = 0;
		} else if(i1 != 0 && i1 != Block.vine.blockID && i1 != Block.tallGrass.blockID && i1 != Block.deadBush.blockID) {
			if(l == 0) {
				j--;
			}
			if(l == 1) {
				j++;
			}
			if(l == 2) {
				k--;
			}
			if(l == 3) {
				k++;
			}
			if(l == 4) {
				i--;
			}
			if(l == 5) {
				i++;
			}
		}
		if(itemstack.stackSize == 0) {
			return false;
		}
		if(!entityplayer.func_35190_e(i, j, k)) {
			return false;
		}
		if(j == world.field_35472_c - 1 && Block.waterlily.blockMaterial.isSolid()) {
			return false;
		}
		if(world.canBlockBePlacedAt(Block.waterlily.blockID, i, j, k, false, l)) {
			if(world.setBlockAndMetadataWithNotify(i, j, k, Block.waterlily.blockID, getPlacedBlockMetadata(itemstack.getItemDamage()))) {
				if(world.getBlockId(i, j, k) == Block.waterlily.blockID) {
					Block.waterlily.onBlockPlaced(world, i, j, k, l);
					Block.waterlily.onBlockPlacedBy(world, i, j, k, entityplayer);
				}
				world.playSoundEffect(i + 0.5F, j + 0.5F, k + 0.5F, Block.waterlily.stepSound.stepSoundDir2(), (Block.waterlily.stepSound.getVolume() + 1.0F) / 2.0F, Block.waterlily.stepSound.getPitch() * 0.8F);
				itemstack.stackSize--;
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ItemState getState(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int face) {
		MovingObjectPosition movingobjectposition = func_40402_a(world, player, true);
		if(movingobjectposition == null) {
			return null;
		}
		if(movingobjectposition.typeOfHit == EnumMovingObjectType.TILE) {
			int i = movingobjectposition.blockX;
			int j = movingobjectposition.blockY;
			int k = movingobjectposition.blockZ;
			if(!world.canMineBlock(player, i, j, k))
				return null;
			if(!player.func_35190_e(i, j, k))
				return null;
			if(world.getBlockMaterial(i, j, k) == Material.water && world.getBlockMetadata(i, j, k) == 0 && world.isAirBlock(i, j + 1, k)) {
				return new ItemState(i, j, k, 1, 0);
			}
		}
		return null;
	}

	@Override
	public int getColorFromDamage(int i) {
		return Block.waterlily.getRenderColor(i);
	}
}
