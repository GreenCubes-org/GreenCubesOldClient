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

import gnu.trove.map.hash.THashMap;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.greencubes.util.Util;

public class NBTTagCompound extends NBTBase {
	
	private Map<String, NBTBase> tags;
	
	public NBTTagCompound() {
		tags = new THashMap<String, NBTBase>();
	}
	
	public NBTTagCompound(Map<String, NBTBase> tags) {
		this.tags = tags;
	}
	
	public NBTTagCompound(String[] keys, Object[] object) {
		this();
		
	}
	
	@Override
	void write(DataOutput output) throws IOException {
		for(NBTBase nbt : this.tags.values())
			NBTBase.swrite(nbt, output);
		output.writeByte(0);
	}
	
	@Override
	void read(DataInput input) throws IOException {
		this.tags.clear();
		while(true) {
			try {
				NBTBase nbt = NBTBase.sread(input);
				if(nbt == NBTBase.end)
					break;
				this.tags.put(nbt.getName(), nbt);
			} catch(IOException e) {
				throw new IOException("Exception when reading content for compound " + getName(), e);
			}
		}
	}
	
	@Override
	void writeMinecraft(DataOutput output) throws IOException {
		for(NBTBase localNBTBase : this.tags.values())
			NBTBase.swriteMinecraft(localNBTBase, output);
		output.writeByte(0);
	}
	
	@Override
	void readMinecraft(DataInput input) throws IOException {
		this.tags.clear();
		while(true) {
			try {
				NBTBase nbt = NBTBase.sreadMinecraft(input);
				if(nbt == NBTBase.end)
					break;
				this.tags.put(nbt.getName(), nbt);
			} catch(IOException e) {
				throw new IOException("Exception when reading content for compound " + getName(), e);
			}
		}
	}
	
	@Override
	public byte getId() {
		return 10;
	}
	
	@Override
	public byte getIdMinecraft() {
		return 10;
	}
	
	public void setNBTTag(String paramString, NBTBase paramNBTBase) {
		this.tags.put(paramString, paramNBTBase.setName(paramString));
	}
	
	public void setByte(String paramString, byte paramByte) {
		this.tags.put(paramString, new NBTTagByte(paramByte).setName(paramString));
	}
	
	public void setShort(String paramString, short paramShort) {
		this.tags.put(paramString, new NBTTagShort(paramShort).setName(paramString));
	}
	
	public void setInt(String paramString, int paramInt) {
		this.tags.put(paramString, new NBTTagInt(paramInt).setName(paramString));
	}
	
	public void setLong(String paramString, long paramLong) {
		this.tags.put(paramString, new NBTTagLong(paramLong).setName(paramString));
	}
	
	public void setFloat(String paramString, float paramFloat) {
		this.tags.put(paramString, new NBTTagFloat(paramFloat).setName(paramString));
	}
	
	public void setDouble(String paramString, double paramDouble) {
		this.tags.put(paramString, new NBTTagDouble(paramDouble).setName(paramString));
	}
	
	public void setString(String paramString1, String paramString2) {
		this.tags.put(paramString1, new NBTTagString(paramString2).setName(paramString1));
	}
	
	public void setByteArray(String paramString, byte[] paramArrayOfByte) {
		this.tags.put(paramString, new NBTTagByteArray(paramArrayOfByte).setName(paramString));
	}
	
	public void setNBTTagCompound(String paramString, NBTTagCompound paramNBTTagCompound) {
		this.tags.put(paramString, paramNBTTagCompound.setName(paramString));
	}
	
	public void setShortArray(String paramString, short[] array) {
		this.tags.put(paramString, new NBTTagShortArray(array).setName(paramString));
	}
	
	public void setIntArray(String name, int[] array) {
		this.tags.put(name, new NBTTagIntArray(array).setName(name));
	}
	
	public int[] getIntArray(String name) {
		if(!this.tags.containsKey(name))
			return new int[0];
		return ((NBTTagIntArray) this.tags.get(name)).a;
	}
	
	public short[] getShortArray(String name) {
		if(!this.tags.containsKey(name))
			return new short[0];
		return ((NBTTagShortArray) this.tags.get(name)).a;
	}
	
	public void setBoolean(String paramString, boolean paramBoolean) {
		setByte(paramString, (byte) (paramBoolean ? 1 : 0));
	}
	
	public boolean hasKey(String paramString) {
		return this.tags.containsKey(paramString);
	}
	
	public byte getByte(String paramString) {
		if(!this.tags.containsKey(paramString))
			return 0;
		NBTBase base = this.tags.get(paramString);
		return ((NBTNumber) base).getByteValue();
	}
	
	public short getShort(String paramString) {
		if(!this.tags.containsKey(paramString))
			return 0;
		return ((NBTNumber) this.tags.get(paramString)).getShortValue();
	}
	
	public int getInt(String paramString) {
		if(!this.tags.containsKey(paramString))
			return 0;
		return ((NBTNumber) this.tags.get(paramString)).getIntValue();
	}
	
	public long getLong(String paramString) {
		if(!this.tags.containsKey(paramString))
			return 0L;
		return ((NBTNumber) this.tags.get(paramString)).getLongValue();
	}
	
	public float getFloat(String paramString) {
		if(!this.tags.containsKey(paramString))
			return 0.0F;
		return ((NBTNumber) this.tags.get(paramString)).getFloatValue();
	}
	
	public double getDouble(String paramString) {
		if(!this.tags.containsKey(paramString))
			return 0.0D;
		return ((NBTNumber) this.tags.get(paramString)).getDoubleValue();
	}
	
	public String getString(String paramString) {
		if(!this.tags.containsKey(paramString))
			return "";
		return ((NBTTagString) this.tags.get(paramString)).a;
	}
	
	public byte[] getByteArray(String paramString) {
		if(!this.tags.containsKey(paramString))
			return new byte[0];
		return ((NBTTagByteArray) this.tags.get(paramString)).a;
	}
	
	public NBTTagCompound getTagCompound(String paramString) {
		if(!this.tags.containsKey(paramString))
			return new NBTTagCompound();
		return (NBTTagCompound) this.tags.get(paramString);
	}
	
	public NBTTagList getTagList(String paramString) {
		if(!this.tags.containsKey(paramString))
			return new NBTTagList();
		return (NBTTagList) this.tags.get(paramString);
	}
	
	public NBTBase get(String str) {
		return this.tags.get(str);
	}
	
	public boolean getBoolean(String paramString) {
		if(!this.tags.containsKey(paramString))
			return false;
		return ((NBTNumber) this.tags.get(paramString)).getBooleanValue();
	}
	
	@Override
	public String dump() {
		StringBuilder b = new StringBuilder();
		b.append('{');
		Iterator<Entry<String, NBTBase>> i = tags.entrySet().iterator();
		boolean first = true;
		while(i.hasNext()) {
			Entry<String, NBTBase> e = i.next();
			if(!first)
				b.append(',');
			else
				first = false;
			b.append('(');
			b.append(e.getValue().getId());
			b.append(')');
			b.append(e.getKey());
			b.append(':');
			b.append(e.getValue().dump());
		}
		b.append('}');
		return b.toString();
	}
	
	public void remove(String key) {
		tags.remove(key);
	}
	
	@Override
	public NBTTagCompound clone() {
		Map<String, NBTBase> newTags = new THashMap<String, NBTBase>(tags.size());
		Iterator<Entry<String, NBTBase>> i = tags.entrySet().iterator();
		while(i.hasNext()) {
			Entry<String, NBTBase> e = i.next();
			newTags.put(e.getKey(), e.getValue().clone().setName(e.getKey()));
		}
		return new NBTTagCompound(newTags);
	}
	
	public Iterator<Entry<String, NBTBase>> iterator() {
		return tags.entrySet().iterator();
	}
	
	public void merge(NBTTagCompound tag) {
		merge(tag.tags);
	}
	
	public void merge(Map<String, NBTBase> map) {
		this.tags.putAll(map);
	}
	
	@Override
	public String toString() {
		return Util.concat(this.getClass().getSimpleName(), "{", getName(), ",", this.tags.size(), " entries}");
	}
	
	public String structureDump() {
		return structureDump(null, 0).toString();
	}
	
	@Override
	public StringBuilder structureDump(StringBuilder store, int depth) {
		if(store == null)
			store = new StringBuilder();
		Util.dump(store, depth, "Compound(" + getId() + "," + getName() + "," + size() + ") {" + (size() == 0 ? "}\n" : "\n"));
		if(size() > 0) {
			Iterator<Entry<String, NBTBase>> i = tags.entrySet().iterator();
			while(i.hasNext()) {
				Entry<String, NBTBase> e = i.next();
				e.getValue().structureDump(store, depth + 1);
			}
			Util.dump(store, depth, "}\n");
		}
		return store;
	}
	
	@Override
	public boolean equals(NBTBase tag) {
		if(tag == null)
			return false;
		if(tag instanceof NBTTagCompound) {
			NBTTagCompound t = (NBTTagCompound) tag;
			if(t.tags.size() != tags.size())
				return false;
			Iterator<Entry<String, NBTBase>> i = tags.entrySet().iterator();
			while(i.hasNext()) {
				Entry<String, NBTBase> e = i.next();
				NBTBase b = t.tags.get(e.getKey());
				if(b == null || !b.equals(e.getValue()))
					return false;
			}
			return true;
		}
		return false;
	}
	
	public int size() {
		return tags.size();
	}
	
	@Override
	public int hashCode() {
		int result = 1;
		Iterator<Entry<String, NBTBase>> i = tags.entrySet().iterator();
		while(i.hasNext()) {
			Entry<String, NBTBase> e = i.next();
			result = 31 * result + e.getKey().hashCode();
			result = 31 * result + e.getValue().hashCode();
		}
		return result;
	}
}
