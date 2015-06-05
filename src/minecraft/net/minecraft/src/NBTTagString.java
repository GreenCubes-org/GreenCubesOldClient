// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            NBTBase

public class NBTTagString extends NBTBase {

	public String stringValue;

	public NBTTagString(String s) {
		super(s);
	}

	public NBTTagString(String s, String s1) {
		super(s);
		stringValue = s1;
		if(s1 == null) {
			throw new IllegalArgumentException("Empty string not allowed");
		} else {
			return;
		}
	}

	@Override
	void writeTagContents(DataOutput dataoutput) throws IOException {
		dataoutput.writeUTF(stringValue);
	}

	@Override
	void readTagContents(DataInput datainput) throws IOException {
		stringValue = datainput.readUTF();
	}

	@Override
	public byte getType() {
		return 8;
	}

	@Override
	public String toString() {
		return (new StringBuilder()).append("").append(stringValue).toString();
	}

	@Override
	public NBTBase clone() {
		return new NBTTagString(getKey(), stringValue);
	}

	@Override
	public boolean equals(NBTBase obj) {
		if(obj instanceof NBTTagString)
			return ((NBTTagString) obj).stringValue == stringValue || stringValue != null && stringValue.equals(((NBTTagString) obj).stringValue);
		return false;
	}

	@Override
	public String dump() {
		return stringValue;
	}
}
