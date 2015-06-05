// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            NBTBase

public class NBTTagDouble extends NBTBase implements NBTTagNumber {

	public double doubleValue;

	public NBTTagDouble(String s) {
		super(s);
	}

	public NBTTagDouble(String s, double d) {
		super(s);
		doubleValue = d;
	}

	@Override
	void writeTagContents(DataOutput dataoutput) throws IOException {
		dataoutput.writeDouble(doubleValue);
	}

	@Override
	void readTagContents(DataInput datainput) throws IOException {
		doubleValue = datainput.readDouble();
	}

	@Override
	public byte getType() {
		return 6;
	}

	@Override
	public String toString() {
		return (new StringBuilder()).append("").append(doubleValue).toString();
	}

	@Override
	public NBTBase clone() {
		return new NBTTagDouble(getKey(), doubleValue);
	}

	@Override
	public boolean equals(NBTBase obj) {
		if(obj instanceof NBTTagDouble)
			return ((NBTTagDouble) obj).doubleValue == doubleValue;
		return false;
	}

	@Override
	public String dump() {
		return Double.toString(doubleValue);
	}
	
	@Override
	public int getIntValue() {
		return (int) doubleValue;
	}

	@Override
	public long getLongValue() {
		return (long) doubleValue;
	}

	@Override
	public short getShortValue() {
		return (short) doubleValue;
	}

	@Override
	public byte getByteValue() {
		return (byte) doubleValue;
	}

	@Override
	public boolean getBooleanValue() {
		return doubleValue != 0;
	}

	@Override
	public float getFloatValue() {
		return (float) doubleValue;
	}

	@Override
	public double getDoubleValue() {
		return doubleValue;
	}
}
