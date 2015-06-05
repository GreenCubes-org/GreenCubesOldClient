package net.minecraft.src;

public class BlockPalmLeaves extends BlockGreenStep {

	private static Block nonOpaqueLeaves = new NonOpaqueLeaves(4095, Block.leaves.blockIndexInTexture, Material.leaves, true) {
		@Override
		public Block getBlockMadeOf() {
			return Block.leaves;
		}
	};

	public int top;
	public int side;

	public BlockPalmLeaves(int i, int top, int side, boolean isUp) {
		super(i, top, nonOpaqueLeaves, isUp);
		this.top = top;
		this.side = side;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int j, int i) {
		if(j == 1 || j == 0)
			return top;
		return side;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	private abstract static class NonOpaqueLeaves extends BlockLeavesBase implements IBlockMadeOf {

		protected NonOpaqueLeaves(int i, int j, Material material, boolean flag) {
			super(i, j, material, flag);
		}

	}
}
