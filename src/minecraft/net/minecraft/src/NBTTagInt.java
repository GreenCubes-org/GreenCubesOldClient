// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            NBTBase

public class NBTTagInt extends NBTBase implements NBTTagNumber {

	public int intValue;

	public NBTTagInt(String s) {
		super(s);
	}

	public NBTTagInt(String s, int i) {
		super(s);
		intValue = i;
	}

	@Override
	void writeTagContents(DataOutput dataoutput) throws IOException {
		dataoutput.writeInt(intValue);
	}

	@Override
	void readTagContents(DataInput datainput) throws IOException {
		intValue = datainput.readInt();
	}

	@Override
	public byte getType() {
		return 3;
	}

	@Override
	public String toString() {
		return (new StringBuilder()).append("").append(intValue).toString();
	}

	@Override
	public NBTBase clone() {
		return new NBTTagInt(getKey(), intValue);
	}

	@Override
	public boolean equals(NBTBase obj) {
		if(obj instanceof NBTTagInt)
			return ((NBTTagInt) obj).intValue == intValue;
		return false;
	}

	@Override
	public String dump() {
		return Integer.toString(intValue);
	}
	
	@Override
	public int getIntValue() {
		return (int) intValue;
	}

	@Override
	public long getLongValue() {
		return (long) intValue;
	}

	@Override
	public short getShortValue() {
		return (short) intValue;
	}

	@Override
	public byte getByteValue() {
		return (byte) intValue;
	}

	@Override
	public boolean getBooleanValue() {
		return intValue != 0;
	}

	@Override
	public float getFloatValue() {
		return (float) intValue;
	}

	@Override
	public double getDoubleValue() {
		return intValue;
	}
}
