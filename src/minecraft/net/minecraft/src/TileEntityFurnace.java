// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            TileEntity, IInventory, ItemStack, NBTTagCompound, 
//            NBTTagList, World, Item, BlockFurnace, 
//            FurnaceRecipes, Block, Material, ModLoader, 
//            EntityPlayer

public class TileEntityFurnace extends TileEntity implements IInventory {

	private ItemStack furnaceItemStacks[];
	public int furnaceBurnTime;
	public int currentItemBurnTime;
	public int furnaceCookTime;

	public TileEntityFurnace() {
		furnaceItemStacks = new ItemStack[3];
		furnaceBurnTime = 0;
		currentItemBurnTime = 0;
		furnaceCookTime = 0;
	}

	@Override
	public int getSizeInventory() {
		return furnaceItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return furnaceItemStacks[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(furnaceItemStacks[i] != null) {
			if(furnaceItemStacks[i].stackSize <= j) {
				ItemStack itemstack = furnaceItemStacks[i];
				furnaceItemStacks[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = furnaceItemStacks[i].splitStack(j);
			if(furnaceItemStacks[i].stackSize == 0) {
				furnaceItemStacks[i] = null;
			}
			return itemstack1;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		furnaceItemStacks[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() {
		return "Furnace";
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		furnaceItemStacks = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.size(); i++) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.get(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < furnaceItemStacks.length) {
				furnaceItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		furnaceBurnTime = nbttagcompound.getShort("BurnTime");
		furnaceCookTime = nbttagcompound.getShort("CookTime");
		currentItemBurnTime = getItemBurnTime(furnaceItemStacks[1]);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setShort("BurnTime", (short) furnaceBurnTime);
		nbttagcompound.setShort("CookTime", (short) furnaceCookTime);
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < furnaceItemStacks.length; i++) {
			if(furnaceItemStacks[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				furnaceItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.setTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);
	}

	@Override
	public int getInventoryStackLimit() {
		return 32768;
	}

	public int getCookProgressScaled(int i) {
		return (furnaceCookTime * i) / 200;
	}

	public int getBurnTimeRemainingScaled(int i) {
		if(currentItemBurnTime == 0) {
			currentItemBurnTime = 200;
		}
		return (furnaceBurnTime * i) / currentItemBurnTime;
	}

	public boolean isBurning() {
		return furnaceBurnTime > 0;
	}

	@Override
	public void updateEntity() {
		boolean flag = furnaceBurnTime > 0;
		boolean flag1 = false;
		if(furnaceBurnTime > 0) {
			furnaceBurnTime--;
		}
		if(!worldObj.multiplayerWorld) {
			if(furnaceBurnTime == 0 && canSmelt()) {
				currentItemBurnTime = furnaceBurnTime = getItemBurnTime(furnaceItemStacks[1]);
				if(furnaceBurnTime > 0) {
					flag1 = true;
					if(furnaceItemStacks[1] != null) {
						if(furnaceItemStacks[1].getItem().hasContainerItem()) {
							furnaceItemStacks[1] = new ItemStack(furnaceItemStacks[1].getItem().getContainerItem());
						} else {
							furnaceItemStacks[1].stackSize--;
						}
						if(furnaceItemStacks[1].stackSize == 0) {
							furnaceItemStacks[1] = null;
						}
					}
				}
			}
			if(isBurning() && canSmelt()) {
				furnaceCookTime++;
				if(furnaceCookTime == 200) {
					furnaceCookTime = 0;
					smeltItem();
					flag1 = true;
				}
			} else {
				furnaceCookTime = 0;
			}
			if(flag != (furnaceBurnTime > 0)) {
				flag1 = true;
				BlockFurnace.updateFurnaceBlockState(furnaceBurnTime > 0, worldObj, xCoord, yCoord, zCoord);
			}
		}
		if(flag1) {
			onInventoryChanged();
		}
	}

	private boolean canSmelt() {
		if(furnaceItemStacks[0] == null) {
			return false;
		}
		ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(furnaceItemStacks[0].getItem().shiftedIndex);
		if(itemstack == null) {
			return false;
		}
		if(furnaceItemStacks[2] == null) {
			return true;
		}
		if(!furnaceItemStacks[2].isItemEqual(itemstack)) {
			return false;
		}
		if(furnaceItemStacks[2].stackSize < getInventoryStackLimit() && furnaceItemStacks[2].stackSize < furnaceItemStacks[2].getMaxStackSize()) {
			return true;
		}
		return furnaceItemStacks[2].stackSize < itemstack.getMaxStackSize();
	}

	public void smeltItem() {
		if(!canSmelt()) {
			return;
		}
		ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(furnaceItemStacks[0].getItem().shiftedIndex);
		if(furnaceItemStacks[2] == null) {
			furnaceItemStacks[2] = itemstack.copy();
		} else if(furnaceItemStacks[2].itemID == itemstack.itemID) {
			furnaceItemStacks[2].stackSize++;
		}
		if(furnaceItemStacks[0].getItem().hasContainerItem()) {
			furnaceItemStacks[0] = new ItemStack(furnaceItemStacks[0].getItem().getContainerItem());
		} else {
			furnaceItemStacks[0].stackSize--;
		}
		if(furnaceItemStacks[0].stackSize <= 0) {
			furnaceItemStacks[0] = null;
		}
	}

	private int getItemBurnTime(ItemStack itemstack) {
		if(itemstack == null) {
			return 0;
		}
		int i = itemstack.getItem().shiftedIndex;
		if(i < 256 && Block.blocksList[i].blockMaterial == Material.wood) {
			return 300;
		}
		if(i == Item.stick.shiftedIndex) {
			return 100;
		}
		if(i == Item.coal.shiftedIndex) {
			return 1600;
		}
		if(i == Item.bucketLava.shiftedIndex) {
			return 20000;
		}
		if(i == Block.sapling.blockID) {
			return 100;
		}
		if(i == Item.blazeRod.shiftedIndex) {
			return 2400;
		} else {
			return ModLoader.AddAllFuel(i, itemstack.getItemDamage());
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		}
		return entityplayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64D;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}
}
