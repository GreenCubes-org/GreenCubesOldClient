package net.minecraft.src;

public class ItemWhiteSign extends ItemSign {

	public ItemWhiteSign(int i) {
		super(i);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
		if(l == 0)
			return false;
		if(!world.getBlockMaterial(i, j, k).isSolid())
			return false;
		if(l == 1)
			j++;
		else if(l == 2)
			k--;
		else if(l == 3)
			k++;
		else if(l == 4)
			i--;
		else if(l == 5)
			i++;
		if(!entityplayer.func_35190_e(i, j, k))
			return false;
		if(!Block.signPost.canPlaceBlockAt(world, i, j, k))
			return false;
		if(l == 1) {
			int i1 = MathHelper.floor_double((((entityplayer.rotationYaw + 180F) * 16F) / 360F) + 0.5D) & 0xf;
			world.setBlockAndMetadataWithNotify(i, j, k, Block.blockWhiteSign.blockID, i1);
		} else {
			world.setBlockAndMetadataWithNotify(i, j, k, Block.blockWhiteSignWall.blockID, l);
		}
		itemstack.stackSize--;
		TileEntitySign tileentitysign = (TileEntitySign) world.getBlockTileEntity(i, j, k);
		if(tileentitysign != null) {
			entityplayer.displayGUIEditSign(tileentitysign);
		}
		return true;
	}
}
