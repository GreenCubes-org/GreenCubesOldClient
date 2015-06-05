package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GChannel {

	public final int id;
	public String name;
	public Type type;
	public List<GTab> attachedTabs = new ArrayList<GTab>();
	public List<String> aliases = new ArrayList<String>();
	public final int userId;
	public String color = "a";

	public GChannel(int userId, int id, String name, Type type) {
		this.userId = userId;
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public static enum Type {
		SYSTEM(1), GENERAL(2), PRIVATE(3), NORMAL(4);

		private static final HashMap<Integer, Type> idToType;

		public final int id;

		private Type(int id) {
			this.id = id;
		}

		public static Type getById(int id) {
			return idToType.get(id);
		}

		static {
			idToType = new HashMap<Integer, Type>(values().length * 2);
			for(Type t : values())
				idToType.put(t.id, t);
		}
	}
}
