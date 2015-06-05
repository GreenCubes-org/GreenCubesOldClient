package net.minecraft.src;

public class BlockSakuraLog extends Block {

	protected BlockSakuraLog(int i) {
		super(i, Material.wood);
	}

	@Override
	public int getRenderType() {
		return 31;
	}

	public int getBlockTextureFromSideAndMetadata1(int i, int j) { // j - дата, i - сторона 0,1 - верх и низ.
		if(i == 1 || i == 0) {
			return GreenTextures.sakuralog;
		}
		return GreenTextures.sakuralogside;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int data) {
		int rot = data & 0xC;
		int type = data & 0x3;

		if((rot == 0) && ((side == 1) || (side == 0))) {
			return GreenTextures.sakuralog;
		}

		if((rot == 4) && ((side == 5) || (side == 4)))
			return GreenTextures.sakuralog;
		if((rot == 8) && ((side == 2) || (side == 3))) {
			return GreenTextures.sakuralog;
		}

		return GreenTextures.sakuralogside;
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
}
