package net.minecraft.src;

public class BlockSandStone extends Block {

	public static int creeper = 229;
	public static int smooth = 230;
	public static int top = 176;
	public static int bottom = 208;
	public static int main = 192;

	public BlockSandStone(int i) {
		super(i, 192, Material.rock);
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		if(i == 1 || i == 0 && j != 0)
			return top;

		if(i == 0)
			return bottom;

		if(j == 1)
			return creeper;
		if(j == 2)
			return smooth;
		if(j == 3)
			return top;

		return main;
	}

	/**
	 * Returns the block texture based on the side being looked at.  Args: side
	 */
	@Override
	public int getBlockTextureFromSide(int i) {
		if(i == 1)
			return top;
		if(i == 0)
			return bottom;
		else
			return main;
	}

	/**
	 * Determines the damage on the item the block drops. Used in cloth and wood.
	 */
	@Override
	protected int damageDropped(int i) {
		return i;
	}
}
