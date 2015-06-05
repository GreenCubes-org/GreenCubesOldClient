package net.minecraft.src;

import java.util.Random;
import static net.minecraft.src.GreenTextures.*;

public class BlockIcicle extends Block implements IBlockMadeOf {

	private final int[] textures = new int[]{icicle1, icicle2, icicle3, icicle4, icicle5, icicle6};
	private final Random random = new Random();

	public BlockIcicle(int i) {
		super(i, Material.ice);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		return textures[(i * 31 + ((j & 0xF0) >> 4)) % textures.length];
	}

	@Override
	public int getBlockTextureFromSide(int i) {
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
	public void onNeighborBlockChange(World world, int x, int y, int z, int l) {
		int currentData = world.getBlockMetadata(x, y, z);
		int newData = 0;
		for(BlockFace face : BlockFace.sideFaces) {
			int data = faceToData(face);
			if((data & currentData) != 0) {
				if(canBePlacedOn(world.getBlockId(x + face.getOpposite().getModX(), y, z + face.getOpposite().getModZ())) || canBePlacedUnder(world.getBlockId(x, y + 1, z), world.getBlockMetadata(x, y + 1, z), face))
					newData |= data;
			}
		}
		if(newData == 0)
			world.setBlockWithNotify(x, y, z, 0);
		else if(newData != currentData)
			world.setBlockMetadataWithNotify(x, y, z, newData);
	}

	public boolean place(World world, int x, int y, int z, int blockFace, BlockFace playerFace) {
		boolean onTop = blockFace == 0;
		BlockFace dir = onTop ? playerFace.getOpposite() : BlockFace.getFaceById(blockFace);
		int data = faceToData(dir);
		if(world.getBlockId(x, y, z) == blockID)
			data |= world.getBlockMetadata(x, y, z);
		if((data & 0xF0) == 0)
			data |= random.nextInt(textures.length) << 4;
		return world.setBlockAndMetadataWithNotify(x, y, z, blockID, data);
	}

	public boolean canBePlacedAt(World world, int x, int y, int z, int blockFace, BlockFace playerFace) {
		boolean onTop = blockFace == 0;
		BlockFace dir = onTop ? playerFace.getOpposite() : BlockFace.getFaceById(blockFace);
		if(canBePlacedOn(world.getBlockId(x + dir.getOpposite().getModX(), y, z + dir.getOpposite().getModZ())))
			return true;
		return canBePlacedUnder(world.getBlockId(x, y + 1, z), world.getBlockMetadata(x, y + 1, z), dir);
	}

	private boolean canBePlacedOn(int i) {
		if(i == 0)
			return false;
		Block block = Block.blocksList[i];
		return block.renderAsNormalBlock() && block.blockMaterial.getIsSolid();
	}

	private boolean canBePlacedUnder(int blockId, int blockData, BlockFace direction) {
		if(blockId == 0)
			return false;
		Block blockNear = Block.blocksList[blockId];
		if(blockNear.renderAsNormalBlock() && blockNear.blockMaterial.getIsSolid())
			return true;
		if(blockNear instanceof BlockTile)
			return BlockFace.simpleDirectionToFace(blockData & 0x3) == direction;
		if(blockNear instanceof BlockGreenStepBananaLog)
			return false;
		if(blockNear instanceof BlockGreenStep) {
			if(((BlockGreenStep) blockNear).isUp)
				return false;
			if(((BlockGreenStep) blockNear).original.isOpaqueCube())
				return true;
			return true;
		}
		if(blockNear instanceof BlockStep && !(blockNear instanceof BlockStepUp))
			return blockNear.blockMaterial.getIsOpaque();
		if(blockNear instanceof BlockStairs)
			return (blockData >> 2) == 1;
		return false;
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
		if((l & 2) != 0) {
			f3 = Math.max(f3, 0.0625F);
			f = 0.0F;
			f1 = 0.0F;
			f4 = 1.0F;
			f2 = 0.0F;
			f5 = 1.0F;
		}
		if((l & 8) != 0) {
			f = Math.min(f, 0.9375F);
			f3 = 1.0F;
			f1 = 0.0F;
			f4 = 1.0F;
			f2 = 0.0F;
			f5 = 1.0F;
		}
		if((l & 4) != 0) {
			f5 = Math.max(f5, 0.0625F);
			f2 = 0.0F;
			f = 0.0F;
			f3 = 1.0F;
			f1 = 0.0F;
			f4 = 1.0F;
		}
		if((l & 1) != 0) {
			f2 = Math.min(f2, 0.9375F);
			f5 = 1.0F;
			f = 0.0F;
			f3 = 1.0F;
			f1 = 0.0F;
			f4 = 1.0F;
		}
		setBlockBounds(f, f1, f2, f3, f4, f5);
	}

	public static int faceToData(BlockFace face) {
		switch(face.getId()) {
		case 2:
			return 1;
		case 3:
			return 4;
		case 4:
			return 8;
		case 5:
			return 2;
		}
		return 0;
	}

	@Override
	public Block getBlockMadeOf() {
		return Block.ice;
	}
}
