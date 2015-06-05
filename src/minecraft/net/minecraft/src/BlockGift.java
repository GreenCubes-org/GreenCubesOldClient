package net.minecraft.src;

public class BlockGift extends Block {

	public int up;
	public int side;
	public int down;

	public BlockGift(int i, Block original, int up, int down, int side) {
		super(i, Material.wool);
		this.up = up;
		this.down = down;
		this.side = side;
		setHardness(original.blockHardness);
		setResistance(original.blockResistance);
		setStepSound(original.stepSound);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {// j - дата, i - сторона 0,1 - верх и низ.
		if(i == 1)
			return up;
		if(i == 0)
			return down;
		return side;
	}

	@Override
	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		return true;
	}
}
