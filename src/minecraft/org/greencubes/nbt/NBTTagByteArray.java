package org.greencubes.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

import org.greencubes.util.Util;

public class NBTTagByteArray extends NBTBase {
	public byte[] a;
	
	public NBTTagByteArray() {
	}
	
	public NBTTagByteArray(byte[] paramArrayOfByte) {
		this.a = paramArrayOfByte;
	}
	
	@Override
	void write(DataOutput paramDataOutput) throws IOException {
		paramDataOutput.writeInt(this.a.length);
		paramDataOutput.write(this.a);
	}
	
	@Override
	void read(DataInput paramDataInput) throws IOException {
		int i = paramDataInput.readInt();
		this.a = new byte[i];
		paramDataInput.readFully(this.a);
	}
	
	@Override
	public byte getId() {
		return 7;
	}
	
	@Override
	public byte getIdMinecraft() {
		return 7;
	}
	
	@Override
	public String dump() {
		return new StringBuilder('(').append(this.a.length).append(" bytes)").toString();
	}
	
	@Override
	public NBTTagByteArray clone() {
		return new NBTTagByteArray(this.a.clone());
	}
	
	@Override
	public String toString() {
		return new StringBuilder('[').append(this.a.length).append(" bytes]").toString();
	}
	
	@Override
	public boolean equals(NBTBase tag) {
		if(tag instanceof NBTTagByteArray) {
			NBTTagByteArray t = (NBTTagByteArray) tag;
			if(t.a.length != a.length)
				return false;
			for(int i = 0; i < a.length; ++i)
				if(a[i] != t.a[i])
					return false;
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(a);
	}
	
	@Override
	public StringBuilder structureDump(StringBuilder store, int depth) {
		if(store == null)
			store = new StringBuilder();
		Util.dump(store, depth, "ByteArray(" + getId() + "," + getName() + "): " + a.length + " elements\n");
		return store;
	}
}
