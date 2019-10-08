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
package org.greencubes.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.greencubes.util.HSVColor;
import org.greencubes.util.Util;

public class NBTTagHSVColor extends NBTBase {
	
	public HSVColor color;
	
	public NBTTagHSVColor() {
	}
	
	public NBTTagHSVColor(HSVColor color) {
		this.color = color;
	}
	
	@Override
	void write(DataOutput paramDataOutput) throws IOException {
		HSVColor.writePacked(color, paramDataOutput);
	}
	
	@Override
	void read(DataInput paramDataInput) throws IOException {
		color = HSVColor.readPacked(paramDataInput);
	}
	
	@Override
	public byte getId() {
		return 15;
	}
	
	@Override
	public byte getIdMinecraft() {
		throw new UnsupportedOperationException("Minecraft does not support tag " + getName(getId()));
	}
	
	@Override
	public String dump() {
		return String.valueOf(color);
	}
	
	@Override
	public NBTBase clone() {
		return new NBTTagHSVColor(color);
	}
	
	@Override
	public boolean equals(NBTBase tag) {
		if(tag instanceof NBTTagHSVColor)
			return color.equalsPacked(((NBTTagHSVColor) tag).color);
		return false;
	}
	
	@Override
	public StringBuilder structureDump(StringBuilder store, int depth) {
		if(store == null)
			store = new StringBuilder();
		Util.dump(store, depth, "HSV(" + getId() + "," + getName() + "): " + dump() + "\n");
		return store;
	}
	
	@Override
	public int hashCode() {
		return color.hashCode();
	}
	
}
