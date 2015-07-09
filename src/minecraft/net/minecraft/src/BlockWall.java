package net.minecraft.src;

public class BlockWall extends Block {

	public BlockWall(int par1, Block source) {
		super(par1, source.blockIndexInTexture, source.blockMaterial);
		this.setHardness(source.blockHardness);
		this.setResistance(source.blockResistance / 3.0F);
		this.setStepSound(source.stepSound);
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType() {
		return 32;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		boolean var5 = this.canConnectWallTo(par1IBlockAccess, par2, par3, par4 - 1, BlockFace.EAST);
		boolean var6 = this.canConnectWallTo(par1IBlockAccess, par2, par3, par4 + 1, BlockFace.WEST);
		boolean var7 = this.canConnectWallTo(par1IBlockAccess, par2 - 1, par3, par4, BlockFace.NORTH);
		boolean var8 = this.canConnectWallTo(par1IBlockAccess, par2 + 1, par3, par4, BlockFace.SOUTH);
		float var9 = 0.25F;
		float var10 = 0.75F;
		float var11 = 0.25F;
		float var12 = 0.75F;
		float var13 = 1.0F;

		if(var5) {
			var11 = 0.0F;
		}

		if(var6) {
			var12 = 1.0F;
		}

		if(var7) {
			var9 = 0.0F;
		}

		if(var8) {
			var10 = 1.0F;
		}

		if(var5 && var6 && !var7 && !var8) {
			var13 = 0.8125F;
			var9 = 0.3125F;
			var10 = 0.6875F;
		} else if(!var5 && !var6 && var7 && var8) {
			var13 = 0.8125F;
			var11 = 0.3125F;
			var12 = 0.6875F;
		}

		this.setBlockBounds(var9, 0.0F, var11, var10, var13, var12);
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		this.maxY = 1.5D;
		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	public static boolean isWall(int id) {
		return blocksList[id] != null && blocksList[id] instanceof BlockWall;
	}

	/**
	 * Return whether an adjacent block can connect to a wall.
	 */
	public boolean canConnectWallTo(IBlockAccess par1IBlockAccess, int x, int y, int z, BlockFace face) {
		int var5 = par1IBlockAccess.getBlockId(x, y, z);
		if(blocksList[var5] == null)
			return false;
		if(blocksList[var5].isOpaqueCube())
			return true;
		if(blocksList[var5] instanceof BlockColumn) {
			if(BlockColumn.getSideDiff(BlockColumn.getSide(face.getOpposite()), par1IBlockAccess.getBlockMetadata(x, y, z)) == 0.0f)
				return true;
		}
		return blocksList[var5] instanceof BlockWall || blocksList[var5] instanceof BlockFenceGate;
	}

	/**
	 * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
	 * coordinates.  Args: blockAccess, x, y, z, side
	 */
	@Override
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		return par5 == 0 ? super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5) : true;
	}
}
