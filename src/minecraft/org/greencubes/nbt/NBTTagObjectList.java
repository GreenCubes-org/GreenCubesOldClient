/*
 * Copyright 2015 Eiren 'Eirenliel' Rain and GreenCubes.org
 * authors
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.greencubes.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

import org.greencubes.util.Util;
import org.greencubes.util.collections.FastList;

public class NBTTagObjectList extends NBTBase {
	
	private List<NBTBase> list;
	
	public NBTTagObjectList() {
		list = new FastList<NBTBase>();
	}
	
	public NBTTagObjectList(int size) {
		list = new FastList<NBTBase>(size);
	}
	
	public NBTTagObjectList(List<NBTBase> list) {
		this.list = list;
	}
	
	public NBTTagObjectList(NBTBase[] array) {
		this.list = new FastList<NBTBase>(array);
	}

	@Override
	void write(DataOutput output) throws IOException {
		int size = this.list.size();
		output.writeInt(size);
		for(int i = 0; i < size; i++) {
			output.writeByte(this.list.get(i).getId());
			this.list.get(i).write(output);
		}
	}
	
	@Override
	void read(DataInput input) throws IOException {
		int i = input.readInt();
		this.list = new FastList<NBTBase>(i);
		for(int j = 0; j < i; j++) {
			try {
				byte b = input.readByte();
				NBTBase nbt = NBTBase.getType(b);
				nbt.read(input);
				list.add(nbt);
			} catch(IOException e) {
				throw new IOException("Exception when reading content for object list " + getName() + " at index " + j, e);
			}
		}
	}

	@Override
	public byte getId() {
		return 17;
	}

	@Override
	public byte getIdMinecraft() {
		return 0;
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
	public NBTTagObjectList clone() {
		FastList<NBTBase> newList = new FastList<NBTBase>(list.size());
		for(int i = 0; i < list.size(); ++i)
			newList.add(list.get(i).clone());
		return new NBTTagObjectList(newList);
	}

	@Override
	public boolean equals(NBTBase tag) {
		if(tag instanceof NBTTagObjectList) {
			NBTTagObjectList t = (NBTTagObjectList) tag;
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
	public int hashCode() {
		int result = 1;
		for(int i = 0; i < list.size(); ++i)
			result = 31 * result + list.get(i).hashCode();
		return result;
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
		return Util.concat(this.getClass().getSimpleName(), "{", getName(), ",", this.list.size(), " entries}");
	}
	
	public void add(NBTBase paramNBTBase) {
		this.list.add(paramNBTBase);
	}
	
	public void set(int i, NBTBase paramNBTBase) {
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
	
}
