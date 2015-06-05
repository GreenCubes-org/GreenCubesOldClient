package org.greencubes.nbt;

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.greencubes.util.Util;

public class NBTTagIntObjectMap extends NBTBase {
	
	public TIntObjectHashMap<NBTBase> map;
	
	public NBTTagIntObjectMap() {
		this(5);
	}
	
	public NBTTagIntObjectMap(int size) {
		map = new TIntObjectHashMap<NBTBase>(size);
	}
	
	public NBTTagIntObjectMap(TIntObjectMap<NBTBase> src) {
		this(src.size());
		TIntObjectIterator<NBTBase> iterator = src.iterator();
		while(iterator.hasNext()) {
			iterator.advance();
			map.put(iterator.key(), iterator.value());
		}
	}
	
	private static void writeValue(NBTBase tag, DataOutput output) throws IOException {
		output.writeByte(tag.getId());
		tag.write(output);
	}
	
	private static NBTBase readValue(DataInput input) throws IOException {
		byte id = input.readByte();
		if(id == 0)
			return NBTBase.end;
		NBTBase tag = NBTBase.getType(id);
		if(tag == null)
			throw new IOException("Unrecognized tag id: " + id);
		tag.read(input);
		return tag;
	}
	
	@Override
	void write(DataOutput output) throws IOException {
		TIntObjectIterator<NBTBase> iterator = map.iterator();
		while(iterator.hasNext()) {
			iterator.advance();
			output.writeInt(iterator.key());
			writeValue(iterator.value(), output);
		}
		output.writeInt(0);
		output.writeByte(0);
	}
	
	@Override
	void read(DataInput input) throws IOException {
		map.clear();
		while(true) {
			try {
				int key = input.readInt();
				NBTBase tag = readValue(input);
				if(tag == NBTBase.end)
					break;
				map.put(key, tag);
			} catch(Exception e) {
				throw new IOException("Exception reading content for IntObjectMap " + getName(), e);
			}
		}
	}
	
	@Override
	public byte getId() {
		return 16;
	}
	
	@Override
	public byte getIdMinecraft() {
		throw new UnsupportedOperationException("Minecraft does not support tag " + getName(getId()));
	}
	
	@Override
	public String dump() {
		StringBuilder b = new StringBuilder();
		b.append('{');
		TIntObjectIterator<NBTBase> i = map.iterator();
		boolean first = true;
		while(i.hasNext()) {
			i.advance();
			if(!first)
				b.append(',');
			else
				first = false;
			b.append('(');
			b.append(i.value().getId());
			b.append(')');
			b.append(i.key());
			b.append(':');
			b.append(i.value().dump());
		}
		b.append('}');
		return b.toString();
	}
	
	@Override
	public NBTBase clone() {
		NBTTagIntObjectMap clone = new NBTTagIntObjectMap(size());
		TIntObjectIterator<NBTBase> iterator = map.iterator();
		while(iterator.hasNext()) {
			iterator.advance();
			clone.map.put(iterator.key(), iterator.value().clone());
		}
		return clone;
	}
	
	public int size() {
		return map.size();
	}
	
	@Override
	public boolean equals(NBTBase tag) {
		if(tag == null)
			return false;
		if(tag instanceof NBTTagIntObjectMap) {
			NBTTagIntObjectMap t = (NBTTagIntObjectMap) tag;
			if(t.map.size() != map.size())
				return false;
			TIntObjectIterator<NBTBase> i = map.iterator();
			while(i.hasNext()) {
				i.advance();
				NBTBase b = t.map.get(i.key());
				if(b == null || !b.equals(i.value()))
					return false;
			}
			return true;
		}
		return false;
	}
	
	public void setNBTTag(int key, NBTBase tag) {
		this.map.put(key, tag);
	}
	
	public void setByte(int key, byte paramByte) {
		this.map.put(key, new NBTTagByte(paramByte));
	}
	
	public void setShort(int key, short paramShort) {
		this.map.put(key, new NBTTagShort(paramShort));
	}
	
	public void setInt(int key, int paramInt) {
		this.map.put(key, new NBTTagInt(paramInt));
	}
	
	public void setLong(int key, long paramLong) {
		this.map.put(key, new NBTTagLong(paramLong));
	}
	
	public void setFloat(int key, float paramFloat) {
		this.map.put(key, new NBTTagFloat(paramFloat));
	}
	
	public void setDouble(int key, double paramDouble) {
		this.map.put(key, new NBTTagDouble(paramDouble));
	}
	
	public void setString(int key, String paramString2) {
		this.map.put(key, new NBTTagString(paramString2));
	}
	
	public void setByteArray(int key, byte[] paramArrayOfByte) {
		this.map.put(key, new NBTTagByteArray(paramArrayOfByte));
	}
	
	public void setNBTTagCompound(int key, NBTTagCompound paramNBTTagCompound) {
		this.map.put(key, paramNBTTagCompound);
	}
	
	public void setShortArray(int key, short[] array) {
		this.map.put(key, new NBTTagShortArray(array));
	}
	
	public void setIntArray(int key, int[] array) {
		this.map.put(key, new NBTTagIntArray(array));
	}
	
	public int[] getIntArray(int key) {
		if(!this.map.containsKey(key))
			return new int[0];
		return ((NBTTagIntArray) this.map.get(key)).a;
	}
	
	public short[] getShortArray(int key) {
		if(!this.map.containsKey(key))
			return new short[0];
		return ((NBTTagShortArray) this.map.get(key)).a;
	}
	
	public void setBoolean(int key, boolean paramBoolean) {
		setByte(key, (byte) (paramBoolean ? 1 : 0));
	}
	
	public boolean hasKey(int key) {
		return this.map.containsKey(key);
	}
	
	public byte getByte(int key) {
		if(!this.map.containsKey(key))
			return 0;
		return ((NBTNumber) this.map.get(key)).getByteValue();
	}
	
	public short getShort(int key) {
		if(!this.map.containsKey(key))
			return 0;
		return ((NBTNumber) this.map.get(key)).getShortValue();
	}
	
	public int getInt(int key) {
		if(!this.map.containsKey(key))
			return 0;
		return ((NBTNumber) this.map.get(key)).getIntValue();
	}
	
	public long getLong(int key) {
		if(!this.map.containsKey(key))
			return 0L;
		return ((NBTNumber) this.map.get(key)).getLongValue();
	}
	
	public float getFloat(int key) {
		if(!this.map.containsKey(key))
			return 0.0F;
		return ((NBTNumber) this.map.get(key)).getFloatValue();
	}
	
	public double getDouble(int key) {
		if(!this.map.containsKey(key))
			return 0.0D;
		return ((NBTNumber) this.map.get(key)).getDoubleValue();
	}
	
	public String getString(int key) {
		if(!this.map.containsKey(key))
			return "";
		return ((NBTTagString) this.map.get(key)).a;
	}
	
	public byte[] getByteArray(int key) {
		if(!this.map.containsKey(key))
			return new byte[0];
		return ((NBTTagByteArray) this.map.get(key)).a;
	}
	
	public NBTTagCompound getTagCompound(int key) {
		if(!this.map.containsKey(key))
			return new NBTTagCompound();
		return (NBTTagCompound) this.map.get(key);
	}
	
	public NBTTagList getTagList(int key) {
		if(!this.map.containsKey(key))
			return new NBTTagList();
		return (NBTTagList) this.map.get(key);
	}
	
	public NBTBase get(int key) {
		return this.map.get(key);
	}
	
	public boolean getBoolean(int key) {
		if(!this.map.containsKey(key))
			return false;
		return ((NBTNumber) this.map.get(key)).getBooleanValue();
	}
	
	public TIntObjectIterator<NBTBase> iterator() {
		return map.iterator();
	}
	
	@Override
	public StringBuilder structureDump(StringBuilder store, int depth) {
		if(store == null)
			store = new StringBuilder();
		Util.dump(store, depth, "Compound(" + getId() + "," + getName() + "," + size() + ") {" + (size() == 0 ? "}\n" : "\n"));
		if(size() > 0) {
			TIntObjectIterator<NBTBase> iterator = this.map.iterator();
			while(iterator.hasNext()) {
				iterator.advance();
				Util.dump(store, depth, iterator.key() + ":");
				iterator.value().structureDump(store, depth + 1);
			}
			Util.dump(store, depth, "}\n");
		}
		return store;
	}
	
	@Override
	public int hashCode() {
		int result = 1;
		TIntObjectIterator<NBTBase> i = map.iterator();
		while(i.hasNext()) {
			i.advance();
			result = 31 * result + i.key();
			result = 31 * result + i.value().hashCode();
		}
		return result;
	}
}
