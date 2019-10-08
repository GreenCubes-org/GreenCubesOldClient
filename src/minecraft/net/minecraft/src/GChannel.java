/*
 * Copyright 2015 Eiren 'Eirenliel' Rain and GreenCubes.org
 * authors
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
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
