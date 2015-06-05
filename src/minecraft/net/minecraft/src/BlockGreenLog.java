package net.minecraft.src;

import java.util.Random;

public class BlockGreenLog extends Block implements IBlockMadeOf {

	public int top;
	public int side;

	public BlockGreenLog(int i, int top, int side) {
		super(i, Material.wood);
		this.top = top;
		this.side = side;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
		if(world.multiplayerWorld)
			return;
		int n = world.getBlockMetadata(i, j, k) & 0x3;
		int l = BlockPistonBase.determineOrientation(world, i, j, k, (EntityPlayer) entityliving);
		int d = 0;

		switch(l) {
		case 2:
		case 3:
			d = 8;
			break;
		case 4:
		case 5:
			d = 4;
			break;
		case 0:
		case 1:
			d = 0;
		}
		world.setBlockMetadataWithNotify(i, j, k, n | d);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

	@Override
	public int getRenderType() {
		return 31;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l) {
		super.harvestBlock(world, entityplayer, i, j, k, l);
	}

	@Override
	protected int damageDropped(int i) {
		return i & 0x3;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int data) {
		int rot = data & 0xC;
		if((rot == 0) && ((side == 1) || (side == 0))) {
			return this.top;
		}
		if((rot == 4) && ((side == 5) || (side == 4))) {
			return this.top;
		}
		if((rot == 8) && ((side == 2) || (side == 3))) {
			return this.top;
		}
		return this.side;
	}

	@Override
	public Block getBlockMadeOf() {
		return Block.wood;
	}

}
