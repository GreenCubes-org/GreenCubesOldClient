package org.greencubes.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.greencubes.util.Util;

public class NBTTagByte extends NBTBase implements NBTNumber {
	
	public byte a;
	
	public NBTTagByte() {
	}
	
	public NBTTagByte(byte paramByte) {
		this.a = paramByte;
	}
	
	@Override
	void write(DataOutput paramDataOutput) throws IOException {
		paramDataOutput.writeByte(this.a);
	}
	
	@Override
	void read(DataInput paramDataInput) throws IOException {
		this.a = paramDataInput.readByte();
	}
	
	@Override
	public byte getId() {
		return 1;
	}
	
	@Override
	public byte getIdMinecraft() {
		return 1;
	}
	
	@Override
	public String dump() {
		return Byte.toString(this.a);
	}
	
	@Override
	public NBTTagByte clone() {
		return new NBTTagByte(this.a);
	}
	
	@Override
	public String toString() {
		return Byte.toString(this.a);
	}
	
	@Override
	public boolean equals(NBTBase tag) {
		if(tag instanceof NBTTagByte)
			return ((NBTTagByte) tag).a == this.a;
		return false;
	}
	
	@Override
	public int hashCode() {
		return a;
	}
	
	@Override
	public StringBuilder structureDump(StringBuilder store, int depth) {
		if(store == null)
			store = new StringBuilder();
		Util.dump(store, depth, "Byte(" + getId() + "," + getName() + "): " + a + "\n");
		return store;
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
		return a;
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
