package net.minecraft.src;

public class BlockFacelessPumpkin extends BlockPumpkin {

	public BlockFacelessPumpkin(int i) {
		super(i, 102, false);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		if(i == 1) {
			return 102;
		}
		if(i == 0) {
			return 102;
		} else {
			return 102 + 16;
		}
	}
}
