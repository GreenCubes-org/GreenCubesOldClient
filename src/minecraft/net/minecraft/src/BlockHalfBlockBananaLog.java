package net.minecraft.src;

public class BlockHalfBlockBananaLog extends BlockHalfBlockLog {

	public BlockHalfBlockBananaLog(int i, Material material) {
		super(i, material, 3);
	}

	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0.5F, 0.125F, 0.125F, 1 - 0.125F, 1 - 0.125F, 1 - 0.125F);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
		switch(world.getBlockMetadata(i, j, k)) {
		case 0:
			return AxisAlignedBB.getBoundingBoxFromPool(i + 0.5, j, k + 0.125, i + 1.0 - 0.125, j + 1.0, k + 1.0 - 0.125);
		case 2:
			return AxisAlignedBB.getBoundingBoxFromPool(i + 0.125, j, k + 0.5, i + 1.0 - 0.125, j + 1.0, k + 1.0 - 0.125);
		case 1:
			return AxisAlignedBB.getBoundingBoxFromPool(i + 0.125, j, k + 0.125, i + 0.5, j + 1.0, k + 1.0 - 0.125);
		case 3:
			return AxisAlignedBB.getBoundingBoxFromPool(i + 0.125, j, k + 0.125, i + 1.0 - 0.125, j + 1.0, k + 0.5);
		}
		return super.getSelectedBoundingBoxFromPool(world, i, j, k);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		switch(world.getBlockMetadata(i, j, k)) {
		case 0:
			return AxisAlignedBB.getBoundingBoxFromPool(i + 0.5, j, k + 0.125, i + 1.0 - 0.125, j + 1.0, k + 1.0 - 0.125);
		case 2:
			return AxisAlignedBB.getBoundingBoxFromPool(i + 0.125, j, k + 0.5, i + 1.0 - 0.125, j + 1.0, k + 1.0 - 0.125);
		case 1:
			return AxisAlignedBB.getBoundingBoxFromPool(i + 0.125, j, k + 0.125, i + 0.5, j + 1.0, k + 1.0 - 0.125);
		case 3:
			return AxisAlignedBB.getBoundingBoxFromPool(i + 0.125, j, k + 0.125, i + 1.0 - 0.125, j + 1.0, k + 0.5);
		}
		return super.getCollisionBoundingBoxFromPool(world, i, j, k);
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
		int l = MathHelper.floor_double(((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		if(l == 0) {
			world.setBlockMetadataWithNotify(i, j, k, 2);
		}
		if(l == 1) {
			world.setBlockMetadataWithNotify(i, j, k, 1);
		}
		if(l == 2) {
			world.setBlockMetadataWithNotify(i, j, k, 3);
		}
		if(l == 3) {
			world.setBlockMetadataWithNotify(i, j, k, 0);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
		switch(blockAccess.getBlockMetadata(i, j, k)) {
		case 0:
			setBlockBounds(0.5F, 0.0F, 0.125F, 1.0F - 0.125F, 1.0F, 1.0F - 0.125F);
			break;
		case 2:
			setBlockBounds(0.0F + 0.125F, 0.0F, 0.5F, 1.0F - 0.125F, 1.0F, 1.0F - 0.125F);
			break;
		case 1:
			setBlockBounds(0.0F + 0.125F, 0.0F, 0.0F + 0.125F, 0.5F, 1.0F, 1.0F - 0.125F);
			break;
		case 3:
			setBlockBounds(0.0F + 0.125F, 0.0F, 0.0F + 0.125F, 1.0F - 0.125F, 1.0F, 0.5F);
			break;
		}
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {// j - дата, i - сторона 0,1 - верх и низ.
		if(i == 0 || i == 1)
			return BlockBananaLog.top;
		if(j == 0 && i == 4)
			return BlockBananaLog.slice;
		if(j == 1 && i == 5)
			return BlockBananaLog.slice;
		if(j == 2 && i == 2)
			return BlockBananaLog.slice;
		if(j == 3 && i == 3)
			return BlockBananaLog.slice;
		if(j == 0 && i == 5)
			return BlockBananaLog.side1;
		if(j == 1 && i == 4)
			return BlockBananaLog.side1;
		if(j == 2 && i == 3)
			return BlockBananaLog.side1;
		if(j == 3 && i == 2)
			return BlockBananaLog.side1;

		return BlockBananaLog.side;
	}
}
