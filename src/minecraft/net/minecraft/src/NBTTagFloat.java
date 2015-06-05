// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            NBTBase

public class NBTTagFloat extends NBTBase implements NBTTagNumber {

	public float floatValue;

	public NBTTagFloat(String s) {
		super(s);
	}

	public NBTTagFloat(String s, float f) {
		super(s);
		floatValue = f;
	}

	@Override
	void writeTagContents(DataOutput dataoutput) throws IOException {
		dataoutput.writeFloat(floatValue);
	}

	@Override
	void readTagContents(DataInput datainput) throws IOException {
		floatValue = datainput.readFloat();
	}

	@Override
	public byte getType() {
		return 5;
	}

	@Override
	public String toString() {
		return (new StringBuilder()).append("").append(floatValue).toString();
	}

	@Override
	public NBTBase clone() {
		return new NBTTagFloat(getKey(), floatValue);
	}

	@Override
	public boolean equals(NBTBase obj) {
		if(obj instanceof NBTTagFloat)
			return ((NBTTagFloat) obj).floatValue == floatValue;
		return false;
	}

	@Override
	public String dump() {
		return Float.toString(floatValue);
	}
	
	@Override
	public int getIntValue() {
		return (int) floatValue;
	}

	@Override
	public long getLongValue() {
		return (long) floatValue;
	}

	@Override
	public short getShortValue() {
		return (short) floatValue;
	}

	@Override
	public byte getByteValue() {
		return (byte) floatValue;
	}

	@Override
	public boolean getBooleanValue() {
		return floatValue != 0;
	}

	@Override
	public float getFloatValue() {
		return (float) floatValue;
	}

	@Override
	public double getDoubleValue() {
		return floatValue;
	}
}
