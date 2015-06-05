package net.minecraft.src;

public class ItemCandy extends ItemFood {

	public ItemCandy(int i, int j, boolean flag) {
		super(i, j, flag);
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

}
