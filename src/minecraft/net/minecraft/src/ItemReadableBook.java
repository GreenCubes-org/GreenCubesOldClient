package net.minecraft.src;

/**
 * GreenCubes
 * Books mod
 * @author Feyola
 *
 */
public class ItemReadableBook extends Item {

	protected ItemReadableBook(int i) {
		super(i);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
		return true;
	}

	@Override
	public boolean noDrop() {
		return true;
	}
}