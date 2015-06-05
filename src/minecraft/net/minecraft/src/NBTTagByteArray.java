// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            NBTBase

public class NBTTagByteArray extends NBTBase {

	public byte byteArray[];

	public NBTTagByteArray(String s) {
		super(s);
	}

	public NBTTagByteArray(String s, byte abyte0[]) {
		super(s);
		byteArray = abyte0;
	}

	@Override
	void writeTagContents(DataOutput dataoutput) throws IOException {
		dataoutput.writeInt(byteArray.length);
		dataoutput.write(byteArray);
	}

	@Override
	void readTagContents(DataInput datainput) throws IOException {
		int i = datainput.readInt();
		byteArray = new byte[i];
		datainput.readFully(byteArray);
	}

	@Override
	public byte getType() {
		return 7;
	}

	@Override
	public String toString() {
		return (new StringBuilder()).append("[").append(byteArray.length).append(" bytes]").toString();
	}

	@Override
	public boolean equals(NBTBase tag) {
		if(tag instanceof NBTTagByteArray) {
			NBTTagByteArray t = (NBTTagByteArray) tag;
			if(t.byteArray.length != byteArray.length)
				return false;
			for(int i = 0; i < byteArray.length; ++i)
				if(byteArray[i] != t.byteArray[i])
					return false;
			return true;
		}
		return false;
	}

	@Override
	public NBTBase clone() {
		byte abyte0[] = new byte[byteArray.length];
		System.arraycopy(byteArray, 0, abyte0, 0, byteArray.length);
		return new NBTTagByteArray(getKey(), abyte0);
	}

	@Override
	public String dump() {
		return new StringBuilder().append('(').append(this.byteArray.length).append(" bytes)").toString();
	}
}
