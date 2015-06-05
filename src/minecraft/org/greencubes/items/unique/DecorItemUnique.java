package org.greencubes.items.unique;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public enum DecorItemUnique {

	UNIQUE_WEAK(1), UNIQUE(2), UNIQUE_STRONG(3);
	
	private static final TIntObjectMap<DecorItemUnique> byId = new TIntObjectHashMap<DecorItemUnique>();
	
	public final int id;
	
	private DecorItemUnique(int id) {
		this.id = id;
	}
	
	public static DecorItemUnique getById(int id) {
		return byId.get(id);
	}
	
	static {
		for(DecorItemUnique i : values())
			byId.put(i.id, i);
	}
}
