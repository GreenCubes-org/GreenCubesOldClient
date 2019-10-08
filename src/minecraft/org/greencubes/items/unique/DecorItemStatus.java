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
