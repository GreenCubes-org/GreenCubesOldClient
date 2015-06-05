// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            Item, MathHelper, ItemStack, EntityPlayer, 
//            World, Block, BlockSapling, BlockFlower, 
//            BlockMushroom, BlockStem, BlockCrops, BlockGrass, 
//            BlockTallGrass, EntitySheep, BlockCloth, EntityLiving

public class ItemDye extends Item {

	public static final String dyeColorNames[] = {"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};
	public static final int dyeColors[] = {0x1e1b1b, 0xb3312c, 0x3b511a, 0x51301a, 0x253192, 0x7b2fbe, 0x287697, 0x287697, 0x434343, 0xd88198, 0x41cd34, 0xdecf2a, 0x6689d3, 0xc354cd, 0xeb8844, 0xf0f0f0};

	public ItemDye(int i) {
		super(i);
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public int getIconFromDamage(int i) {
		int j = MathHelper.func_41084_a(i, 0, 15);
		return iconIndex + (j % 8) * 16 + j / 8;
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) {
		int i = MathHelper.func_41084_a(itemstack.getItemDamage(), 0, 15);
		return (new StringBuilder()).append(super.getItemName()).append(".").append(dyeColorNames[i]).toString();
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int l) {
		if(!entityplayer.func_35190_e(x, y, z)) {
			return false;
		}
		if(itemstack.getItemDamage() == 15) {
			int i1 = world.getBlockId(x, y, z);
			if(i1 == Block.sapling.blockID) {
				if(!world.multiplayerWorld) {
					((BlockSapling) Block.sapling).growTree(world, x, y, z, world.rand);
					itemstack.stackSize--;
				}
				return true;
			}
			if(i1 == Block.mushroomBrown.blockID || i1 == Block.mushroomRed.blockID) {
				if(!world.multiplayerWorld && ((BlockMushroom) Block.blocksList[i1]).func_35293_c(world, x, y, z, world.rand)) {
					itemstack.stackSize--;
				}
				return true;
			}
			if(i1 == Block.melonStem.blockID || i1 == Block.pumpkinStem.blockID) {
				if(!world.multiplayerWorld) {
					((BlockStem) Block.blocksList[i1]).func_35294_i(world, x, y, z);
					itemstack.stackSize--;
				}
				return false;
			}
			if(i1 == Block.crops.blockID) {
				if(!world.multiplayerWorld) {
					((BlockCrops) Block.crops).fertilize(world, x, y, z);
					itemstack.stackSize--;
				}
				return true;
			}
			if(i1 == Block.grass.blockID) {
				if(!world.multiplayerWorld) {
					itemstack.stackSize--;
					label0: for(int j1 = 0; j1 < 128; j1++) {
						int k1 = x;
						int l1 = y + 1;
						int i2 = z;
						for(int j2 = 0; j2 < j1 / 16; j2++) {
							k1 += itemRand.nextInt(3) - 1;
							l1 += ((itemRand.nextInt(3) - 1) * itemRand.nextInt(3)) / 2;
							i2 += itemRand.nextInt(3) - 1;
							if(world.getBlockId(k1, l1 - 1, i2) != Block.grass.blockID || world.isBlockNormalCube(k1, l1, i2)) {
								continue label0;
							}
						}

						if(world.getBlockId(k1, l1, i2) != 0) {
							continue;
						}
						if(itemRand.nextInt(10) != 0) {
							world.setBlockAndMetadataWithNotify(k1, l1, i2, Block.tallGrass.blockID, 1);
							continue;
						}
						if(itemRand.nextInt(3) != 0) {
							world.setBlockWithNotify(k1, l1, i2, Block.plantYellow.blockID);
						} else {
							world.setBlockWithNotify(k1, l1, i2, Block.plantRed.blockID);
						}
					}

				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void useItemOnEntity(ItemStack itemstack, EntityLiving entityliving) {
		if(entityliving instanceof EntitySheep) {
			EntitySheep entitysheep = (EntitySheep) entityliving;
			int i = BlockWool.getBlockFromDye(itemstack.getItemDamage());
			if(!entitysheep.getSheared() && entitysheep.getFleeceColor() != i) {
				entitysheep.setFleeceColor(i);
				itemstack.stackSize--;
			}
		}
	}

}
