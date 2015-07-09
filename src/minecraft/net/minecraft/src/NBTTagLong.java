// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            NBTBase

public class NBTTagLong extends NBTBase implements NBTTagNumber {

	public long longValue;

	public NBTTagLong(String s) {
		super(s);
	}

	public NBTTagLong(String s, long l) {
		super(s);
		longValue = l;
	}

	@Override
	void writeTagContents(DataOutput dataoutput) throws IOException {
		dataoutput.writeLong(longValue);
	}

	@Override
	void readTagContents(DataInput datainput) throws IOException {
		longValue = datainput.readLong();
	}

	@Override
	public byte getType() {
		return 4;
	}

	@Override
	public String toString() {
		return (new StringBuilder()).append("").append(longValue).toString();
	}

	@Override
	public NBTBase clone() {
		return new NBTTagLong(getKey(), longValue);
	}

	@Override
	public boolean equals(NBTBase obj) {
		if(obj instanceof NBTTagLong)
			return ((NBTTagLong) obj).longValue == longValue;
		return false;
	}

	@Override
	public String dump() {
		return Long.toString(longValue);
	}
	
	@Override
	public int getIntValue() {
		return (int) longValue;
	}

	@Override
	public long getLongValue() {
		return longValue;
	}

	@Override
	public short getShortValue() {
		return (short) longValue;
	}

	@Override
	public byte getByteValue() {
		return (byte) longValue;
	}

	@Override
	public boolean getBooleanValue() {
		return longValue != 0;
	}

	@Override
	public float getFloatValue() {
		return longValue;
	}

	@Override
	public double getDoubleValue() {
		return longValue;
	}
}
