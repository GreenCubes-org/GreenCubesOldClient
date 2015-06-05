package org.greencubes.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.greencubes.util.Util;

public class NBTTagLong extends NBTBase implements NBTNumber {
	
	public long a;
	
	public NBTTagLong() {
	}
	
	public NBTTagLong(long paramLong) {
		this.a = paramLong;
	}
	
	@Override
	void write(DataOutput paramDataOutput) throws IOException {
		paramDataOutput.writeLong(this.a);
	}
	
	@Override
	void read(DataInput paramDataInput) throws IOException {
		this.a = paramDataInput.readLong();
	}
	
	@Override
	public byte getId() {
		return 4;
	}
	
	@Override
	public byte getIdMinecraft() {
		return 4;
	}
	
	@Override
	public String dump() {
		return Long.toString(this.a);
	}
	
	@Override
	public String toString() {
		return Long.toString(this.a);
	}
	
	@Override
	public NBTTagLong clone() {
		return new NBTTagLong(this.a);
	}
	
	@Override
	public boolean equals(NBTBase tag) {
		if(tag instanceof NBTTagLong)
			return ((NBTTagLong) tag).a == this.a;
		return false;
	}
	
	@Override
	public StringBuilder structureDump(StringBuilder store, int depth) {
		if(store == null)
			store = new StringBuilder();
		Util.dump(store, depth, "Long(" + getId() + "," + getName() + "): " + dump() + "\n");
		return store;
	}
	
	@Override
	public int hashCode() {
		return (int) (a ^ (a >>> 32));
	}

	@Override
	public int getIntValue() {
		return (int) a;
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
