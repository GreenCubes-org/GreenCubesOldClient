package org.greencubes.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.greencubes.util.Util;

public class NBTTagFloat extends NBTBase implements NBTNumber {
	
	public float a;
	
	public NBTTagFloat() {
	}
	
	public NBTTagFloat(float paramFloat) {
		this.a = paramFloat;
	}
	
	@Override
	void write(DataOutput paramDataOutput) throws IOException {
		paramDataOutput.writeFloat(this.a);
	}
	
	@Override
	void read(DataInput paramDataInput) throws IOException {
		this.a = paramDataInput.readFloat();
	}
	
	@Override
	public byte getId() {
		return 5;
	}
	
	@Override
	public byte getIdMinecraft() {
		return 5;
	}
	
	@Override
	public String dump() {
		return Float.toString(this.a);
	}
	
	@Override
	public String toString() {
		return Float.toString(this.a);
	}
	
	@Override
	public NBTTagFloat clone() {
		return new NBTTagFloat(this.a);
	}
	
	@Override
	public boolean equals(NBTBase tag) {
		if(tag instanceof NBTTagFloat)
			return ((NBTTagFloat) tag).a == this.a;
		return false;
	}
	
	@Override
	public StringBuilder structureDump(StringBuilder store, int depth) {
		if(store == null)
			store = new StringBuilder();
		Util.dump(store, depth, "Float(" + getId() + "," + getName() + "): " + a + "\n");
		return store;
	}
	
	@Override
	public int hashCode() {
		return Float.floatToIntBits(a);
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
		return a;
	}

	@Override
	public double getDoubleValue() {
		return a;
	}
}
