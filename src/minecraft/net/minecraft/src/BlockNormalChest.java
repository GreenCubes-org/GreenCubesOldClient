// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            BlockContainer, Material, World, EntityLiving, 
//            MathHelper, Block, IBlockAccess, TileEntityChest, 
//            IInventory, ItemStack, EntityItem, NBTTagCompound, 
//            InventoryLargeChest, EntityPlayer, TileEntity

public class BlockNormalChest extends BlockContainer {

	protected Random random = new Random();
	public String texture;

	protected BlockNormalChest(int i, String texture) {
		super(i, Material.wood);
		blockIndexInTexture = 26;
		this.texture = texture;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return 41;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
		int l = world.getBlockId(i, j, k - 1);
		int i1 = world.getBlockId(i, j, k + 1);
		int j1 = world.getBlockId(i - 1, j, k);
		int k1 = world.getBlockId(i + 1, j, k);
		byte byte0 = 0;
		int l1 = MathHelper.floor_double(((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		if(l1 == 0)
			byte0 = 2;
		if(l1 == 1)
			byte0 = 5;
		if(l1 == 2)
			byte0 = 3;
		if(l1 == 3)
			byte0 = 4;
		world.setBlockMetadataWithNotify(i, j, k, byte0);
	}

	@Override
	public int getBlockTextureFromSide(int i) {
		if(i == 1) {
			return blockIndexInTexture - 1;
		}
		if(i == 0) {
			return blockIndexInTexture - 1;
		}
		if(i == 3) {
			return blockIndexInTexture + 1;
		} else {
			return blockIndexInTexture;
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		super.onNeighborBlockChange(world, i, j, k, l);
		TileEntityNormalChest tileentitychest = (TileEntityNormalChest) world.getBlockTileEntity(i, j, k);
		if(tileentitychest != null) {
			tileentitychest.func_35144_b();
		}
	}

	@Override
	public void onBlockRemoval(World world, int i, int j, int k) {
		TileEntityNormalChest tileentitychest = (TileEntityNormalChest) world.getBlockTileEntity(i, j, k);
		if(tileentitychest != null) {
			for(int l = 0; l < tileentitychest.getSizeInventory(); l++) {
				ItemStack itemstack = tileentitychest.getStackInSlot(l);
				if(itemstack == null) {
					continue;
				}
				float f = random.nextFloat() * 0.8F + 0.1F;
				float f1 = random.nextFloat() * 0.8F + 0.1F;
				float f2 = random.nextFloat() * 0.8F + 0.1F;
				while(itemstack.stackSize > 0) {
					int i1 = random.nextInt(21) + 10;
					if(i1 > itemstack.stackSize) {
						i1 = itemstack.stackSize;
					}
					itemstack.stackSize -= i1;
					EntityItem entityitem = new EntityItem(world, i + f, j + f1, k + f2, new ItemStack(itemstack.itemID, i1, itemstack.getItemDamage()));
					float f3 = 0.05F;
					entityitem.motionX = (float) random.nextGaussian() * f3;
					entityitem.motionY = (float) random.nextGaussian() * f3 + 0.2F;
					entityitem.motionZ = (float) random.nextGaussian() * f3;
					if(itemstack.hasNBTData()) {
						entityitem.item.setNBTData((NBTTagCompound) itemstack.getNBTData().clone());
					}
					world.entityJoinedWorld(entityitem);
				}
			}

		}
		super.onBlockRemoval(world, i, j, k);
	}

	@Override
	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		if(world.multiplayerWorld)
			return true;
		Object obj = world.getBlockTileEntity(i, j, k);
		if(obj == null)
			return true;
		if(world.isBlockNormalCube(i, j + 1, k))
			return true;
		entityplayer.displayGUIChest(((IInventory) (obj)));
		return true;
	}

	@Override
	public TileEntity getBlockEntity() {
		return new TileEntityNormalChest();
	}
}
