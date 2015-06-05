package net.minecraft.src;

import java.util.Random;

public class BlockChocoCake extends BlockCake {

	public static int top = ModLoader.addOverride("/terrain.png", "/gc_images/chococake_top.png");;
	public static int side = ModLoader.addOverride("/terrain.png", "/gc_images/chococake_side.png");
	public static int sidecut = ModLoader.addOverride("/terrain.png", "/gc_images/chococake_sidecut.png");
	public static int bottom = ModLoader.addOverride("/terrain.png", "/gc_images/chococake_bottom.png");

	public BlockChocoCake(int i) {
		super(i, 1);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		if(i == 1) {
			return top;
		}
		if(i == 0) {
			return bottom;
		}
		if(j > 0 && i == 4) {
			return sidecut;
		} else {
			return side;
		}
	}

	@Override
	public int getBlockTextureFromSide(int i) {
		if(i == 1) {
			return top;
		}
		if(i == 0) {
			return bottom;
		} else {
			return side;
		}
	}

	public int idDropped(int i, Random random) {
		return Item.chocoCakeItem.shiftedIndex;
	}

}
