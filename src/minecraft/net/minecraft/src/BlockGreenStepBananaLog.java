package net.minecraft.src;

public class BlockGreenStepBananaLog extends BlockGreenStep {

	public final boolean isUp;

	public BlockGreenStepBananaLog(int i, int j, Block original, boolean up) {
		super(i, j, original, up);
		isUp = up;
		if(up)
			setBlockBounds(0.125F, 0.5F, 0.125F, 1.0F - 0.125F, 1.0F, 1.0F - 0.125F);
		else
			setBlockBounds(0.125F, 0.0F, 0.125F, 1.0F - 0.125F, 0.5F, 1.0F - 0.125F);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		if(j == 0) {
			if(i == 4 || i == 5)
				return BlockBananaLog.side;
			if(i == 2 || i == 3)
				return BlockBananaLog.side1;
			return BlockBananaLog.top;
		} else {
			if(i == 5 || i == 2)
				return BlockBananaLog.side;
			if(i == 3 || i == 4)
				return BlockBananaLog.side1;
			return BlockBananaLog.top;
		}
	}

}
