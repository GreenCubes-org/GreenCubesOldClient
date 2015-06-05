package org.greencubes.items.unique;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public enum DecorItemStatus {

	GENUINE(1, "Сувенирный", "\247rff469963сувенирный", 0x469963, true),
	IMMORTAL(2, "Снят с производства", "\247rffff9b84снят с производства", 0xFF9B84, true),
	;
	
	private static final TIntObjectMap<DecorItemStatus> byId = new TIntObjectHashMap<DecorItemStatus>();
	
	public final int id;
	public final String name;
	public final String addName;
	public final int color;
	public final boolean show;
	
	private DecorItemStatus(int id, String name, String lowercaseName, int color, boolean show) {
		this.id = id;
		this.name = name;
		this.addName = lowercaseName;
		this.color = color;
		this.show = show;
	}
	
	public static DecorItemStatus getById(int id) {
		return byId.get(id);
	}
	
	static {
		for(DecorItemStatus i : values())
			byId.put(i.id, i);
	}
}
