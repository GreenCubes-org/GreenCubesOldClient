package org.greencubes.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

import org.greencubes.util.Util;

public class NBTTagShortArray extends NBTBase {
	
	public short[] a;
	
	public NBTTagShortArray() {
	}
	
	public NBTTagShortArray(short[] paramArrayOfByte) {
		this.a = paramArrayOfByte;
	}
	
	@Override
	void write(DataOutput paramDataOutput) throws IOException {
		paramDataOutput.writeInt(this.a.length);
		for(int i = 0; i < this.a.length; ++i) {
			paramDataOutput.writeShort(this.a[i]);
		}
	}
	
	@Override
	void read(DataInput paramDataInput) throws IOException {
		int i = paramDataInput.readInt();
		this.a = new short[i];
		for(int i1 = 0; i1 < i; ++i1)
			this.a[i1] = paramDataInput.readShort();
	}
	
	@Override
	public byte getId() {
		return 11;
	}
	
	@Override
	public byte getIdMinecraft() {
		throw new UnsupportedOperationException("Minecraft does not support tag " + getName(getId()));
	}
	
	@Override
	public String dump() {
		return new StringBuilder('(').append(this.a.length).append(" shorts)").toString();
	}
	
	@Override
	public NBTTagShortArray clone() {
		return new NBTTagShortArray(this.a.clone());
	}
	
	@Override
	public String toString() {
		return new StringBuilder('[').append(this.a.length).append(" shorts]").toString();
	}
	
	@Override
	public boolean equals(NBTBase tag) {
		if(tag instanceof NBTTagShortArray) {
			NBTTagShortArray t = (NBTTagShortArray) tag;
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
	public StringBuilder structureDump(StringBuilder store, int depth) {
		if(store == null)
			store = new StringBuilder();
		Util.dump(store, depth, "ShortArray(" + getId() + "," + getName() + "): " + a.length + " elements\n");
		return store;
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(a);
	}
}
