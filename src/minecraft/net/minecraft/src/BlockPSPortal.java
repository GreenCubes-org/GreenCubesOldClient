package net.minecraft.src;

public class BlockPSPortal extends Block {

	protected BlockPSPortal(int i, int j, Material material) {
		super(i, j, material);
		
	}

	@Override
	public int getRenderType() {
		return 99;
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
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return null;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
		int l = MathHelper.floor_double(((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		if(l == 0)
			world.setBlockMetadataWithNotify(i, j, k, 2);
		else if(l == 1)
			world.setBlockMetadataWithNotify(i, j, k, 1);
		else if(l == 2)
			world.setBlockMetadataWithNotify(i, j, k, 3);
		else if(l == 3)
			world.setBlockMetadataWithNotify(i, j, k, 0);
		if(MathHelper.abs((float) entityliving.posX - i) < 2.0F && MathHelper.abs((float) entityliving.posZ - k) < 2.0F) {
			double d = (entityliving.posY + 1.8200000000000001D) - entityliving.yOffset;
			if(d - j > 2D) {
				world.setBlockMetadataWithNotify(i, j, k, 4);
			}
			if(j - d > 0.0D) {
				world.setBlockMetadataWithNotify(i, j, k, 5);
			}
		}
	}
	
	@Override
	public int getRenderBlockPass() {
		return 1;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		if(entity.ridingEntity == null && entity.riddenByEntity == null) {
			entity.setInPortal();
		}
	}
}
