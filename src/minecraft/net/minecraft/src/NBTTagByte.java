// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            NBTBase

public class NBTTagByte extends NBTBase implements NBTTagNumber {

	public byte byteValue;

	public NBTTagByte(String s) {
		super(s);
	}

	public NBTTagByte(String s, byte byte0) {
		super(s);
		byteValue = byte0;
	}

	@Override
	void writeTagContents(DataOutput dataoutput) throws IOException {
		dataoutput.writeByte(byteValue);
	}

	@Override
	void readTagContents(DataInput datainput) throws IOException {
		byteValue = datainput.readByte();
	}

	@Override
	public byte getType() {
		return 1;
	}

	@Override
	public String toString() {
		return (new StringBuilder()).append("").append(byteValue).toString();
	}

	@Override
	public boolean equals(NBTBase obj) {
		if(obj instanceof NBTTagByte)
			return ((NBTTagByte) obj).byteValue == byteValue;
		return false;
	}

	@Override
	public NBTBase clone() {
		return new NBTTagByte(getKey(), byteValue);
	}

	@Override
	public String dump() {
		return Byte.toString(this.byteValue);
	}

	@Override
	public int getIntValue() {
		return byteValue;
	}

	@Override
	public long getLongValue() {
		return byteValue;
	}

	@Override
	public short getShortValue() {
		return byteValue;
	}

	@Override
	public byte getByteValue() {
		return byteValue;
	}

	@Override
	public boolean getBooleanValue() {
		return byteValue != 0;
	}

	@Override
	public float getFloatValue() {
		return byteValue;
	}

	@Override
	public double getDoubleValue() {
		return byteValue;
	}
}
