// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            NBTBase

public class NBTTagShort extends NBTBase implements NBTTagNumber {

	public short shortValue;

	public NBTTagShort(String s) {
		super(s);
	}

	public NBTTagShort(String s, short word0) {
		super(s);
		shortValue = word0;
	}

	@Override
	void writeTagContents(DataOutput dataoutput) throws IOException {
		dataoutput.writeShort(shortValue);
	}

	@Override
	void readTagContents(DataInput datainput) throws IOException {
		shortValue = datainput.readShort();
	}

	@Override
	public byte getType() {
		return 2;
	}

	@Override
	public String toString() {
		return (new StringBuilder()).append("").append(shortValue).toString();
	}

	@Override
	public NBTBase clone() {
		return new NBTTagShort(getKey(), shortValue);
	}

	@Override
	public boolean equals(NBTBase obj) {
		if(obj instanceof NBTTagShort)
			return ((NBTTagShort) obj).shortValue == shortValue;
		return false;
	}

	@Override
	public String dump() {
		return Short.toString(shortValue);
	}
	
	@Override
	public int getIntValue() {
		return shortValue;
	}

	@Override
	public long getLongValue() {
		return shortValue;
	}

	@Override
	public short getShortValue() {
		return shortValue;
	}

	@Override
	public byte getByteValue() {
		return (byte) shortValue;
	}

	@Override
	public boolean getBooleanValue() {
		return shortValue != 0;
	}

	@Override
	public float getFloatValue() {
		return shortValue;
	}

	@Override
	public double getDoubleValue() {
		return shortValue;
	}
}
