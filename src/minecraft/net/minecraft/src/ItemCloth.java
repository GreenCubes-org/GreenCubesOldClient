package net.minecraft.src;

import java.util.List;

import org.greencubes.items.unique.DecorItemQuality;

public class ItemCloth extends ItemWearable {
	
	private ItemStack toFix;

	public ItemCloth(int id, int texture, int slot) {
		super(id, texture, slot);
		setMaxDamage(-1);
	}
	
	public ItemCloth(int id, int texture, int slot, int toFix) {
		super(id, texture, slot);
		setMaxDamage(-1);
		if(toFix != -1)
			this.toFix = new ItemStack(toFix, 1, 0);
	}

	public ItemCloth setFixItem(int id) {
		this.toFix = new ItemStack(id, 1, 0);
		return this;
	}
	
	@Override
	public DecorItemQuality getDecorQuality() {
		return DecorItemQuality.NORMAL_WEAR;
	}
	
	@Override
	public void appendDescription(ItemStack itemstack, List<String> list) {
		super.appendDescription(itemstack, list);
		StringBuilder sb = new StringBuilder();
		sb.append("\2477Декоративная одежда (");
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
