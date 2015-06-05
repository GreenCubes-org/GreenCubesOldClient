package org.greencubes.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.greencubes.util.Util;

public class NBTTagDouble extends NBTBase implements NBTNumber {
	
	public double a;
	
	public NBTTagDouble() {
	}
	
	public NBTTagDouble(double paramDouble) {
		this.a = paramDouble;
	}
	
	@Override
	void write(DataOutput paramDataOutput) throws IOException {
		paramDataOutput.writeDouble(this.a);
	}
	
	@Override
	void read(DataInput paramDataInput) throws IOException {
		this.a = paramDataInput.readDouble();
	}
	
	@Override
	public byte getId() {
		return 6;
	}
	
	@Override
	public byte getIdMinecraft() {
		return 6;
	}
	
	@Override
	public String dump() {
		return Double.toString(this.a);
	}
	
	@Override
	public NBTTagDouble clone() {
		return new NBTTagDouble(this.a);
	}
	
	@Override
	public String toString() {
		return Double.toString(this.a);
	}
	
	@Override
	public boolean equals(NBTBase tag) {
		if(tag instanceof NBTTagDouble)
			return ((NBTTagDouble) tag).a == this.a;
		return false;
	}
	
	@Override
	public StringBuilder structureDump(StringBuilder store, int depth) {
		if(store == null)
			store = new StringBuilder();
		Util.dump(store, depth, "Double(" + getId() + "," + getName() + "): " + a + "\n");
		return store;
	}
	
	@Override
	public int hashCode() {
		long bits = Double.doubleToLongBits(a);
		return (int) (bits ^ (bits >>> 32));
	}

	@Override
	public int getIntValue() {
		return (int) a;
	}

	@Override
	public long getLongValue() {
		return (long) a;
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
		return (float) a;
	}

	@Override
	public double getDoubleValue() {
		return a;
	}
}
