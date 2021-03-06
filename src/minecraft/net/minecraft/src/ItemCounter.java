package net.minecraft.src;

public class ItemCounter extends Item {

	public ItemCounter(int i) {
		super(i);
		setMaxStackSize(1);
	}

	@Override
	public String getTranslatedName(ItemStack itemstack) {
		String name = new StringBuilder().append(StringTranslate.getInstance().translateNamedKey(getUntranslatedName(itemstack))).toString().trim();
		switch(itemstack.itemDamage) {
		case 1:
			name += " - ��������� ������";
			break;
		case 2:
			name += " - ������� ������";
			break;
		case 3:
			name += " - ������� ������ ��������";
			break;
		case 4:
			name += " - ��������� ������";
			break;
		case 5:
			name += " - ����������� ��������";
			break;
		case 6:
			name += " - ������ ��������";
			break;
		case 7:
			name += " - ������� ���� ������";
			break;
		case 8:
			name += " - �������� ���� ������";
			break;
		case 9:
			name += " - ������� ���� ������";
			break;
		case 10:
			name += " - �������� ������";
			break;
		case 11:
			name += " - ������ ��������";
			break;
		case 12:
			name += " - ���� ������";
			break;
		case 13:
			name += " - ����� ��������";
			break;
		case 14:
			name += " - ���������� ��������";
			break;
		case 15:
			name += " - ������ ����������";
			break;
		case 16:
			name += " - ������ ������ �����������";
			break;
		case 17:
			name += " - ���������� ������";
			break;
		case 18:
			name += " - ���� �������";
			break;
		case 19:
			name += " - ������� �������";
			break;
		case 20:
			name += " - �������� �����";
			break;
		case 21:
			name += " - �������� �����";
			break;
		}
		return name;
	}

}
