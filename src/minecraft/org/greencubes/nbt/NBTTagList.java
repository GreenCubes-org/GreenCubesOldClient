package org.greencubes.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.greencubes.util.Util;
import org.greencubes.util.collections.FastList;

public class NBTTagList extends NBTBase {
	
	private List<NBTBase> list;
	private byte b;
	
	public NBTTagList() {
		list = new FastList<NBTBase>();
	}
	
	public NBTTagList(int size) {
		list = new FastList<NBTBase>(size);
	}
	
	public NBTTagList(List<NBTBase> list) {
		this.list = list;
		if(this.list.size() > 0)
			this.b = this.list.get(0).getId();
	}
	
	public NBTTagList(NBTBase[] array) {
		this.list = new FastList<NBTBase>(array);
		if(this.list.size() > 0)
			this.b = this.list.get(0).getId();
	}
	
	@Override
	void writeMinecraft(DataOutput output) throws IOException {
		if(this.list.size() > 0)
			this.b = this.list.get(0).getIdMinecraft();
		else
			this.b = 1;
		output.writeByte(this.b);
		int size = this.list.size();
		output.writeInt(size);
		for(int i = 0; i < size; i++)
			(this.list.get(i)).writeMinecraft(output);
		if(this.list.size() > 0)
			this.b = this.list.get(0).getId();
	}
	
	@Override
	void write(DataOutput paramDataOutput) throws IOException {
		if(this.list.size() > 0)
			this.b = this.list.get(0).getId();
		else
			this.b = 1;
		paramDataOutput.writeByte(this.b);
		int size = this.list.size();
		paramDataOutput.writeInt(size);
		for(int i = 0; i < size; i++)
			(this.list.get(i)).write(paramDataOutput);
	}
	
	@Override
	void readMinecraft(DataInput paramDataInput) throws IOException {
		this.b = paramDataInput.readByte();
		int i = paramDataInput.readInt();
		this.list = new FastList<NBTBase>(i);
		for(int j = 0; j < i; j++) {
			NBTBase localNBTBase = NBTBase.getTypeMinecraft(this.b);
			try {
				localNBTBase.read(paramDataInput);
			} catch(IOException e) {
				throw new IOException("Exception reading content for list " + getName() + ", when trying to read " + localNBTBase, e);
			}
			this.list.add(localNBTBase);
		}
		if(this.list.size() > 0)
			this.b = this.list.get(0).getId();
	}
	
	@Override
	void read(DataInput paramDataInput) throws IOException {
		this.b = paramDataInput.readByte();
		int i = paramDataInput.readInt();
		this.list = new FastList<NBTBase>(i);
		for(int j = 0; j < i; j++) {
			NBTBase localNBTBase = NBTBase.getType(this.b);
			try {
				localNBTBase.read(paramDataInput);
			} catch(IOException e) {
				throw new IOException("Exception reading content for list " + getName() + ", when trying to read " + localNBTBase, e);
			}
			this.list.add(localNBTBase);
		}
	}
	
	@Override
	public byte getId() {
		return 9;
	}
	
	@Override
	public byte getIdMinecraft() {
		return 9;
	}
	
	@Override
	public String dump() {
		StringBuilder sb = new StringBuilder();
		sb.append('(');
		for(int i = 0; i < list.size(); ++i) {
			if(i != 0)
				sb.append(',');
			sb.append(list.get(i).dump());
		}
		sb.append(')');
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return Util.concat(this.getClass().getSimpleName(), "{", getName(), ",", this.list.size(), " entries of type ", getName(b), "}");
	}
	
	public void add(NBTBase paramNBTBase) {
		this.b = paramNBTBase.getId();
		this.list.add(paramNBTBase);
	}
	
	public void set(int i, NBTBase paramNBTBase) {
		this.b = paramNBTBase.getId();
		this.list.set(i, paramNBTBase);
	}
	
	public NBTBase get(int paramInt) {
		return this.list.get(paramInt);
	}
	
	public NBTBase remove(int i) {
		return this.list.remove(i);
	}
	
	public int size() {
		return this.list.size();
	}
	
	public static NBTTagList toList(float[] f) {
		NBTTagList list = new NBTTagList(f.length);
		for(int i = 0; i < f.length; ++i)
			list.add(new NBTTagFloat(f[i]));
		return list;
	}
	
	public static NBTTagList toList(double[] d) {
		NBTTagList list = new NBTTagList(d.length);
		for(int i = 0; i < d.length; ++i)
			list.add(new NBTTagDouble(d[i]));
		return list;
	}
	
	public static NBTBase toList(String[] s) {
		NBTTagList list = new NBTTagList(s.length);
		for(int i = 0; i < s.length; ++i)
			list.add(new NBTTagString(s[i]));
		return list;
	}
	
	@Override
	public NBTTagList clone() {
		ArrayList<NBTBase> newList = new ArrayList<NBTBase>(list.size());
		for(int i = 0; i < list.size(); ++i)
			newList.add(list.get(i).clone());
		return new NBTTagList(newList);
	}
	
	@Override
	public boolean equals(NBTBase tag) {
		if(tag instanceof NBTTagList) {
			// Do not compare by b
			NBTTagList t = (NBTTagList) tag;
			if(t.list.size() != list.size())
				return false;
			for(int i = 0; i < list.size(); ++i)
				if(!list.get(i).equals(t.list.get(i)))
					return false;
			return true;
		}
		return false;
	}
	
	@Override
	public StringBuilder structureDump(StringBuilder store, int depth) {
		if(store == null)
			store = new StringBuilder();
		Util.dump(store, depth, "List(" + getId() + "," + getName() + "," + size() + ") {" + (size() == 0 ? "}\n" : "\n"));
		if(size() > 0) {
			for(int i = 0; i < list.size(); ++i) {
				NBTBase nbt = list.get(i);
				nbt.structureDump(store, depth + 1);
			}
			Util.dump(store, depth, "}\n");
		}
		return store;
	}
	
	@Override
	public int hashCode() {
		int result = 1; // Do not compare by b
		for(int i = 0; i < list.size(); ++i)
			result = 31 * result + list.get(i).hashCode();
		return result;
	}
}
