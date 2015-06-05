package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class BlockTile extends Block {

	public float mod_up;
	public float mod_down;
	public int sidetex;
	public boolean isStairs;
	public static int unallowedBlocks[] = new int[]{Block.blockGift.blockID, Block.blockGift1402.blockID, Block.blockGiftNY.blockID, Block.leaves.blockID, Block.blockAppleTreeLeaves.blockID, Block.blockAppleTreeLeavesPlayer.blockID, Block.blockSakuraFlowers.blockID, Block.blockSakuraFlowersDense.blockID, Block.blockSakuraLeaves.blockID, Block.blockLeavesBao.blockID, Block.pumpkin.blockID, Block.pumpkinLantern.blockID, Block.glowStone.blockID, Block.mycelium.blockID, Block.melon.blockID, Block.grass.blockID, Block.blockLogBao.blockID, Block.blockLogPalm.blockID,};

	public BlockTile(int i, int j, int sidetex, Material material, float up, float down) {
		super(i, j, material);
		mod_up = up;
		mod_down = down;
		this.sidetex = sidetex;
		if(up == 0.0 && down == 0.5) {
			isStairs = true;
		} else if(up == 0.5 && down == 0.5) {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		} else if(up == 0.0 && down == 0.0) {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		} else if(up == 1.0F - 0.0625F - 0.0625F && down == 0.5F) {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		}

	}

	@Override
	public int getRenderType() {
		return 36;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void getCollidingBoundingBoxes(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, ArrayList arraylist) {
		if(isStairs) {
			int l = world.getBlockMetadata(i, j, k);
			if(l == 3) {
				setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F);
				super.getCollidingBoundingBoxes(world, i, j, k, axisalignedbb, arraylist);
				setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
				super.getCollidingBoundingBoxes(world, i, j, k, axisalignedbb, arraylist);
			} else if(l == 1) {
				setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
				super.getCollidingBoundingBoxes(world, i, j, k, axisalignedbb, arraylist);
				setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
				super.getCollidingBoundingBoxes(world, i, j, k, axisalignedbb, arraylist);
			} else if(l == 0) {
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
				super.getCollidingBoundingBoxes(world, i, j, k, axisalignedbb, arraylist);
				setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
				super.getCollidingBoundingBoxes(world, i, j, k, axisalignedbb, arraylist);
			} else if(l == 2) {
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
				super.getCollidingBoundingBoxes(world, i, j, k, axisalignedbb, arraylist);
				setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
				super.getCollidingBoundingBoxes(world, i, j, k, axisalignedbb, arraylist);
			}
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		} else
			super.getCollidingBoundingBoxes(world, i, j, k, axisalignedbb, arraylist);
	}

	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
		int l = MathHelper.floor_double(((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		if(l == 0) {
			world.setBlockMetadataWithNotify(i, j, k, 0);
		}
		if(l == 1) {
			world.setBlockMetadataWithNotify(i, j, k, 1);
		}
		if(l == 2) {
			world.setBlockMetadataWithNotify(i, j, k, 2);
		}
		if(l == 3) {
			world.setBlockMetadataWithNotify(i, j, k, 3);
		}
	}

	@Override
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer, int face) {
		ItemStack item = entityplayer.getCurrentEquippedItem();
		if(item != null && (item.itemID == Item.redTileItem.shiftedIndex || item.itemID == Item.orangeTileItem.shiftedIndex || item.itemID == Item.whiteTileItem.shiftedIndex || item.itemID == Item.blueTileItem.shiftedIndex || item.itemID == Item.greenTileItem.shiftedIndex || item.itemID == Item.grayTile.shiftedIndex || item.itemID == Item.lightGrayTile.shiftedIndex)) {
			int id = world.getBlockId(i, j, k);
			int data = world.getBlockMetadata(i, j, k);
			if(id == Block.blockTileRed_45degree.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileRed_halfUp.blockID, data);
			} else if(id == Block.blockTileRed_halfUp.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileRed_halfDown.blockID, data);
			} else if(id == Block.blockTileRed_halfDown.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileRed_floor.blockID, data);
			} else if(id == Block.blockTileRed_floor.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileRed_45degree.blockID, data);
			} else if(id == Block.blockTileOrange_45degree.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileOrange_halfUp.blockID, data);
			} else if(id == Block.blockTileOrange_halfUp.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileOrange_halfDown.blockID, data);
			} else if(id == Block.blockTileOrange_halfDown.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileOrange_floor.blockID, data);
			} else if(id == Block.blockTileOrange_floor.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileOrange_45degree.blockID, data);
			} else if(id == Block.blockTileWhite_45degree.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileWhite_halfUp.blockID, data);
			} else if(id == Block.blockTileWhite_halfUp.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileWhite_halfDown.blockID, data);
			} else if(id == Block.blockTileWhite_halfDown.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileWhite_floor.blockID, data);
			} else if(id == Block.blockTileWhite_floor.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileWhite_45degree.blockID, data);
			} else if(id == Block.blockTileBlue_45degree.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileBlue_halfUp.blockID, data);
			} else if(id == Block.blockTileBlue_halfUp.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileBlue_halfDown.blockID, data);
			} else if(id == Block.blockTileBlue_halfDown.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileBlue_floor.blockID, data);
			} else if(id == Block.blockTileBlue_floor.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileBlue_45degree.blockID, data);
			} else if(id == Block.blockTileGreen_45degree.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileGreen_halfUp.blockID, data);
			} else if(id == Block.blockTileGreen_halfUp.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileGreen_halfDown.blockID, data);
			} else if(id == Block.blockTileGreen_halfDown.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileGreen_floor.blockID, data);
			} else if(id == Block.blockTileGreen_floor.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileGreen_45degree.blockID, data);
			} else if(id == Block.blockTileGray_45degree.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileGray_halfUp.blockID, data);
			} else if(id == Block.blockTileGray_halfUp.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileGray_halfDown.blockID, data);
			} else if(id == Block.blockTileGray_halfDown.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileGray_floor.blockID, data);
			} else if(id == Block.blockTileGray_floor.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileGray_45degree.blockID, data);
			} else if(id == Block.blockTileLightGray_45degree.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileLightGray_halfUp.blockID, data);
			} else if(id == Block.blockTileLightGray_halfUp.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileLightGray_halfDown.blockID, data);
			} else if(id == Block.blockTileLightGray_halfDown.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileLightGray_floor.blockID, data);
			} else if(id == Block.blockTileLightGray_floor.blockID) {
				world.setBlockAndMetadataWithNotify(i, j, k, Block.blockTileLightGray_45degree.blockID, data);
			}
		}
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return 0;
	}
}
