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
