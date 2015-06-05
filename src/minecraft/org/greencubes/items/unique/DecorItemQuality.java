package org.greencubes.items.unique;

import net.minecraft.src.GChat;

import org.greencubes.util.RGBUtils;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public enum DecorItemQuality {

	NORMAL(1, "", "Обычный декоративный предмет", 0xB9D8A2, false),
	IMPROOVED(2, "", "Улучшенный декоративный предмет", 0x6CA543, false),
	LEGENDARY(3, "", "Легендарный декоративный предмет", 0x20B0A0, true),
	NORMAL_WEAR(4, "", "Предмет внешнего вида", 0x8C9FFF, false),
	RARE_WEAR(5, "", "Редкий предмет внешнего вида", 0x3050FA, false),
	HIGH_QUALITY_WEAR(6, "", "Высококачественный предмет внешнего вида", 0x593CF2, true),
	UNUSUAL_WEAR(7, "", "Необычный предмет внешнего вида", 0xBE23FF, true),
	NAMED(8, "", "Именной декоративный предмет", 0xFF2400, true),
	UNREPEATABLE(9, "", "Неповторимый декоративный предмет", 0xFF1493, true),
	ADMIN(10, "", "Предмет администрации", 0x06A53B, true),
	UNIQUE(11, "", "Уникальный декоративный предмет", 0xFF7518, true),
	COLLECTIBLE(12, "", "Коллекционный предмет", 0xBDA5CB, false),
	COLLECTIBLE_RARE(13, "", "Редкий коллекционный предмет", 0x8B5EA4, false),
	;
	
	private static final TIntObjectMap<DecorItemQuality> byId = new TIntObjectHashMap<DecorItemQuality>();
	
	public final int id;
	//public final String name;
	public final String localizedName;
	public final String colorCode;
	public final int color;
	public final boolean overridesUnique;
	
	private DecorItemQuality(int id, String name, String localizedName, int color, boolean overridesUnique) {
		this.id = id;
		//this.name = name;
		this.localizedName = localizedName;
		this.color = color;
		this.overridesUnique = overridesUnique;
		this.colorCode = GChat.colorChar + "rff" + RGBUtils.toHexString(color);
	}
	
	public static DecorItemQuality getById(int id) {
		return byId.get(id);
	}
	
	static {
		for(DecorItemQuality i : values())
			byId.put(i.id, i);
	}
}
