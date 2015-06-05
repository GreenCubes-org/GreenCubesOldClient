package org.greencubes.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.greencubes.util.Util;

public class NBTTagString extends NBTBase {
	
	public String a;
	
	public NBTTagString() {
	}
	
	public NBTTagString(String paramString) {
		this.a = paramString;
		if(paramString == null)
			throw new IllegalArgumentException("Empty string not allowed");
	}
	
	@Override
	void write(DataOutput paramDataOutput) throws IOException {
		paramDataOutput.writeUTF(this.a);
	}
	
	@Override
	void read(DataInput paramDataInput) throws IOException {
		this.a = paramDataInput.readUTF();
	}
	
	@Override
	public byte getId() {
		return 8;
	}
	
	@Override
	public byte getIdMinecraft() {
		return 8;
	}
	
	@Override
	public String dump() {
		return '"' + this.a + '"';
	}
	
	@Override
	public NBTTagString clone() {
		return new NBTTagString(this.a);
	}
	
	@Override
	public String toString() {
		return this.a;
	}
	
	@Override
	public boolean equals(NBTBase tag) {
		if(tag instanceof NBTTagString)
			return ((NBTTagString) tag).a.equals(this.a);
		return false;
	}
	
	@Override
	public StringBuilder structureDump(StringBuilder store, int depth) {
		if(store == null)
			store = new StringBuilder();
		Util.dump(store, depth, "String(" + getId() + "," + getName() + "): (" + (a != null ? a.length() : 0) + ") " + dump() + "\n");
		return store;
	}
	
	@Override
	public int hashCode() {
		return a.hashCode();
	}
}
