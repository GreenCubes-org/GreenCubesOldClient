package net.minecraft.src;

import java.util.Random;

public class BlockGarland extends Block {
	
	protected int[] textures;

	public BlockGarland(int i, int[] texs) {
		super(i, texs[0], Material.circuits);
		this.textures = texs;
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		int n = (j & ~0xF) >> 4;
		if(textures.length > n)
			return textures[n];
		return textures[0];
	}

	@Override
	public int getRenderType() {
		return 20;
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
	public int idDropped(int i, Random random, int j) {
		return 0;
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int x, int y, int z, int i) {
		if(i == 0 || i == 1)
			return false;
		return true;
	}

	@Override
	public void onBlockPlaced(World world, int i, int j, int k, int l) {
		byte byte0 = 0;
		switch(l) {
		case 2: // '\002'
			byte0 = 1;
			break;

		case 3: // '\003'
			byte0 = 4;
			break;

		case 4: // '\004'
			byte0 = 8;
			break;

		case 5: // '\005'
			byte0 = 2;
			break;
		}
		if(byte0 != 0)
			world.setBlockMetadataWithNotify(i, j, k, byte0);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k) {
		int l = iblockaccess.getBlockMetadata(i, j, k);
		float f = 1.0F;
		float f1 = 1.0F;
		float f2 = 1.0F;
		float f3 = 0.0F;
		float f4 = 0.0F;
		float f5 = 0.0F;
		boolean flag = l > 0;
		if((l & 2) != 0) {
			f3 = Math.max(f3, 0.0625F);
			f = 0.0F;
			f1 = 0.0F;
			f4 = 1.0F;
			f2 = 0.0F;
			f5 = 1.0F;
			flag = true;
		}
		if((l & 8) != 0) {
			f = Math.min(f, 0.9375F);
			f3 = 1.0F;
			f1 = 0.0F;
			f4 = 1.0F;
			f2 = 0.0F;
			f5 = 1.0F;
			flag = true;
		}
		if((l & 4) != 0) {
			f5 = Math.max(f5, 0.0625F);
			f2 = 0.0F;
			f = 0.0F;
			f3 = 1.0F;
			f1 = 0.0F;
			f4 = 1.0F;
			flag = true;
		}
		if((l & 1) != 0) {
			f2 = Math.min(f2, 0.9375F);
			f5 = 1.0F;
			f = 0.0F;
			f3 = 1.0F;
			f1 = 0.0F;
			f4 = 1.0F;
			flag = true;
		}
		if(!flag && canBePlacedOn(iblockaccess.getBlockId(i, j + 1, k))) {
			f1 = Math.min(f1, 0.9375F);
			f4 = 1.0F;
			f = 0.0F;
			f3 = 1.0F;
			f2 = 0.0F;
			f5 = 1.0F;
		}
		setBlockBounds(f, f1, f2, f3, f4, f5);
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, int i, int j, int k, int l) {
		switch(l) {
		default:
			return false;
		case 1: // '\001'
			return canBePlacedOn(world.getBlockId(i, j + 1, k));
		case 2: // '\002'
			return canBePlacedOn(world.getBlockId(i, j, k + 1));
		case 3: // '\003'
			return canBePlacedOn(world.getBlockId(i, j, k - 1));
		case 5: // '\005'
			return canBePlacedOn(world.getBlockId(i - 1, j, k));
		case 4: // '\004'
			return canBePlacedOn(world.getBlockId(i + 1, j, k));
		}
	}

	private boolean canBePlacedOn(int i) {
		Block block = Block.blocksList[i];
		if(block == null)
			return false;
		return block.renderAsNormalBlock() && block.blockMaterial.getIsSolid();
	}

	private boolean canVineStay(World world, int i, int j, int k) {
		int l = world.getBlockMetadata(i, j, k);
		int i1 = l;
		if(i1 > 0) {
			for(int j1 = 0; j1 <= 3; j1++) {
				int k1 = 1 << j1;
				if((l & k1) != 0 && !canBePlacedOn(world.getBlockId(i + Direction.offsetX[j1], j, k + Direction.offsetZ[j1]))) {
					i1 &= ~k1;
				}
			}

		}
		if(i1 == 0 && !canBePlacedOn(world.getBlockId(i, j + 1, k))) {
			return false;
		}
		if(i1 != l) {
			world.setBlockMetadataWithNotify(i, j, k, i1);
		}
		return true;
	}
}
