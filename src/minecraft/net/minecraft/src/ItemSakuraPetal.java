package net.minecraft.src;

public class ItemSakuraPetal extends Item {

	public ItemSakuraPetal(int i) {
		super(i);
	}

	@Override
	public int getIconIndex(ItemStack itemstack) {
		switch(itemstack.hashCode() % 3) {
		case 0:
			return GreenTextures.sakurapetal1;
		case 1:
			return GreenTextures.sakurapetal2;
		case 2:
			return GreenTextures.sakurapetal3;
		}
		return super.getIconIndex(itemstack);
	}

}
