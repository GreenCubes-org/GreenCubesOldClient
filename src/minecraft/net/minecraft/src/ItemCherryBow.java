package net.minecraft.src;

public class ItemCherryBow extends ItemBow {

	private int bow1 = ModLoader.addOverride("/gui/items.png", "/gc_images/cherrybow1.png");
	private int bow2 = ModLoader.addOverride("/gui/items.png", "/gc_images/cherrybow2.png");
	private int bow3 = ModLoader.addOverride("/gui/items.png", "/gc_images/cherrybow3.png");

	public ItemCherryBow(int i, float accuracy) {
		super(i, accuracy);
		setMaxDamage(this.getMaxDamage() * 2);
		this.baseDamage = 8;
		icons = new int[] {bow1, bow2, bow3};
	}

	@Override
	public int getBowIcon(ItemStack itemstack, int inUse) {
		if(inUse >= 50)
			return icons[2];
		if(inUse > 25)
			return icons[1];
		return icons[0];
	}

	@Override
	public float getMaxUse() {
		return 50.0F;
	}
}
