package org.greencubes.nbt;

import java.io.DataInput;
import java.io.DataOutput;

public class NBTTagEnd extends NBTBase {
	
	@Override
	void read(DataInput paramDataInput) {
	}
	
	@Override
	void write(DataOutput paramDataOutput) {
	}
	
	@Override
	public byte getId() {
		return 0;
	}
	
	@Override
	public byte getIdMinecraft() {
		return 0;
	}
	
	@Override
	public String dump() {
		return "END";
	}
	
	@Override
	public String toString() {
		return "END";
	}
	
	@Override
	public NBTTagEnd clone() {
		return new NBTTagEnd();
	}
	
	@Override
	public boolean equals(NBTBase tag) {
		if(tag instanceof NBTTagEnd)
			return true;
		return false;
	}
	
	@Override
	public StringBuilder structureDump(StringBuilder store, int depth) {
		if(store == null)
			store = new StringBuilder();
		return store;
	}
	
	@Override
	public int hashCode() {
		return 0;
	}
}
