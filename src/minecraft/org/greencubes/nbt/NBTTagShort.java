package org.greencubes.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.greencubes.util.Util;

public class NBTTagShort extends NBTBase implements NBTNumber {
	
	public short a;
	
	public NBTTagShort() {
	}
	
	public NBTTagShort(short paramShort) {
		this.a = paramShort;
	}
	
	@Override
	void write(DataOutput paramDataOutput) throws IOException {
		paramDataOutput.writeShort(this.a);
	}
	
	@Override
	void read(DataInput paramDataInput) throws IOException {
		this.a = paramDataInput.readShort();
	}
	
	@Override
	public byte getId() {
		return 2;
	}
	
	@Override
	public byte getIdMinecraft() {
		return 2;
	}
	
	@Override
	public NBTTagShort clone() {
		return new NBTTagShort(this.a);
	}
	
	@Override
	public String dump() {
		return Short.toString(this.a);
	}
	
	@Override
	public String toString() {
		return Short.toString(this.a);
	}
	
	@Override
	public boolean equals(NBTBase tag) {
		if(tag instanceof NBTTagShort)
			return ((NBTTagShort) tag).a == this.a;
		return false;
	}
	
	@Override
	public StringBuilder structureDump(StringBuilder store, int depth) {
		if(store == null)
			store = new StringBuilder();
		Util.dump(store, depth, "Short(" + getId() + "," + getName() + "): " + dump() + "\n");
		return store;
	}
	
	@Override
	public int hashCode() {
		return a;
	}

	@Override
	public int getIntValue() {
		return a;
	}

	@Override
	public long getLongValue() {
		return a;
	}

	@Override
	public short getShortValue() {
		return a;
	}

	@Override
	public boolean getBooleanValue() {
		return a != 0;
	}

	@Override
	public byte getByteValue() {
		return (byte) a;
	}

	@Override
	public float getFloatValue() {
		return a;
	}

	@Override
	public double getDoubleValue() {
		return a;
	}
}
