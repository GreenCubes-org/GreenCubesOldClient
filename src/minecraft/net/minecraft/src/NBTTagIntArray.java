// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            NBTBase

public class NBTTagIntArray extends NBTBase {

	public int array[];

	public NBTTagIntArray(String s) {
		super(s);
	}

	public NBTTagIntArray(String s, int abyte0[]) {
		super(s);
		array = abyte0;
	}

	@Override
	void writeTagContents(DataOutput dataoutput) throws IOException {
		dataoutput.writeInt(array.length);
		for(int i = 0; i < array.length; ++i)
			dataoutput.writeInt(array[i]);
	}

	@Override
	void readTagContents(DataInput datainput) throws IOException {
		int i = datainput.readInt();
		array = new int[i];
		for(int n = 0; n < i; ++n)
			array[n] = datainput.readInt();
	}

	@Override
	public byte getType() {
		return 12;
	}

	@Override
	public String toString() {
		return (new StringBuilder()).append("[").append(array.length).append(" ints]").toString();
	}

	@Override
	public boolean equals(NBTBase tag) {
		if(tag instanceof NBTTagIntArray) {
			NBTTagIntArray t = (NBTTagIntArray) tag;
			if(t.array.length != array.length)
				return false;
			for(int i = 0; i < array.length; ++i)
				if(array[i] != t.array[i])
					return false;
			return true;
		}
		return false;
	}

	@Override
	public NBTBase clone() {
		int abyte0[] = new int[array.length];
		System.arraycopy(array, 0, abyte0, 0, array.length);
		return new NBTTagIntArray(getKey(), abyte0);
	}

	@Override
	public String dump() {
		return new StringBuilder().append('(').append(this.array.length).append(" ints)").toString();
	}
}
