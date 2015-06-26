package net.minecraft.src;

import java.util.List;

public class ItemAccessory extends ItemWearable {
	
	private ItemStack toFix;
	
	public ItemAccessory(int i, int slot) {
		super(i, 0, slot);
	}
	
	public ItemAccessory setToFix(int i) {
		this.toFix = new ItemStack(i, 1, 0);
		return this;
	}
	
	@Override
	public void appendDescription(ItemStack itemstack, List<String> list) {
		super.appendDescription(itemstack, list);
		StringBuilder sb = new StringBuilder();
		sb.append("\2477Аксессуар (");
		switch(slot) {
		case 0:
			sb.append("голова");
			break;
		case 1:
			sb.append("тело");
			break;
		case 2:
			sb.append("штаны");
			break;
		case 3:
			sb.append("обувь");
			break;
		case 4:
			sb.append("перчатки");
			break;
		}
		sb.append(')');
		if(toFix != null) {
			sb.append(", чинится: ");
			sb.append(toFix.getItem().getTranslatedName(toFix));
		}
		list.add(sb.toString());
	}
}
