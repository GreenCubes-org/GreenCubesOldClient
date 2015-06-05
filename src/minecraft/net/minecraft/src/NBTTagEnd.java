// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            NBTBase

public class NBTTagEnd extends NBTBase {

	public NBTTagEnd() {
		super(null);
	}

	@Override
	void readTagContents(DataInput datainput) throws IOException {
	}

	@Override
	void writeTagContents(DataOutput dataoutput) throws IOException {
	}

	@Override
	public byte getType() {
		return 0;
	}

	@Override
	public String toString() {
		return "END";
	}

	@Override
	public String dump() {
		return "END";
	}

	@Override
	public NBTBase clone() {
		return new NBTTagEnd();
	}

	@Override
	public boolean equals(NBTBase obj) {
		return obj instanceof NBTTagEnd;
	}
}
