package org.greencubes.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.greencubes.util.Util;

public class NBTTagInt extends NBTBase implements NBTNumber {
	
	public int a;
	
	public NBTTagInt() {
	}
	
	public NBTTagInt(int paramInt) {
		this.a = paramInt;
	}
	
	@Override
	void write(DataOutput paramDataOutput) throws IOException {
		paramDataOutput.writeInt(this.a);
	}
	
	@Override
	void read(DataInput paramDataInput) throws IOException {
		this.a = paramDataInput.readInt();
	}
	
	@Override
	public byte getId() {
		return 3;
	}
	
	@Override
	public byte getIdMinecraft() {
		return 3;
	}
	
	@Override
	public String dump() {
		return Integer.toString(this.a);
	}
	
	@Override
	public String toString() {
		return Integer.toString(this.a);
	}
	
	@Override
	public NBTTagInt clone() {
		return new NBTTagInt(this.a);
	}
	
	@Override
	public boolean equals(NBTBase tag) {
		if(tag instanceof NBTTagInt)
			return ((NBTTagInt) tag).a == this.a;
		return false;
	}
	
	@Override
	public StringBuilder structureDump(StringBuilder store, int depth) {
		if(store == null)
			store = new StringBuilder();
		Util.dump(store, depth, "Int(" + getId() + "," + getName() + "): " + dump() + "\n");
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
		return (short) a;
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
