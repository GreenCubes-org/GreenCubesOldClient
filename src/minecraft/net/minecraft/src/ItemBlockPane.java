package net.minecraft.src;

public class ItemBlockPane extends ItemBlock {

	public ItemBlockPane(int i) {
		super(i);
	}

	@Override
	public int getIconFromDamage(int i) {
		return Block.blocksList[shiftedIndex].blockIndexInTexture;
	}
}
