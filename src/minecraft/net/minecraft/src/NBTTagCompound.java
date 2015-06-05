// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import gnu.trove.map.hash.THashMap;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

// Referenced classes of package net.minecraft.src:
//            NBTBase, NBTTagByte, NBTTagShort, NBTTagInt, 
//            NBTTagLong, NBTTagFloat, NBTTagDouble, NBTTagString, 
//            NBTTagByteArray, NBTTagList

public class NBTTagCompound extends NBTBase {

	private THashMap<String, NBTBase> tagMap;

	public NBTTagCompound() {
		this("");
	}

	public NBTTagCompound(String s) {
		super(s);
		tagMap = new THashMap<String, NBTBase>();
	}

	@Override
	void writeTagContents(DataOutput dataoutput) throws IOException {
		NBTBase nbtbase;
		for(Iterator<NBTBase> iterator = tagMap.values().iterator(); iterator.hasNext(); NBTBase.writeTag(nbtbase, dataoutput)) {
			nbtbase = iterator.next();
		}

		dataoutput.writeByte(0);
	}

	@Override
	void readTagContents(DataInput datainput) throws IOException {
		tagMap.clear();
		NBTBase nbtbase;
		for(; (nbtbase = NBTBase.readTag(datainput)).getType() != 0; tagMap.put(nbtbase.getKey(), nbtbase)) {
		}
	}

	public Collection func_28110_c() {
		return tagMap.values();
	}

	@Override
	public byte getType() {
		return 10;
	}

	public void setTag(String s, NBTBase nbtbase) {
		tagMap.put(s, nbtbase.setKey(s));
	}

	public void setByte(String s, byte byte0) {
		tagMap.put(s, new NBTTagByte(s, byte0));
	}

	public void setShort(String s, short word0) {
		tagMap.put(s, new NBTTagShort(s, word0));
	}

	public void setInteger(String s, int i) {
		tagMap.put(s, new NBTTagInt(s, i));
	}

	public void setLong(String s, long l) {
		tagMap.put(s, new NBTTagLong(s, l));
	}

	public void setFloat(String s, float f) {
		tagMap.put(s, new NBTTagFloat(s, f));
	}

	public void setDouble(String s, double d) {
		tagMap.put(s, new NBTTagDouble(s, d));
	}

	public void setString(String s, String s1) {
		tagMap.put(s, new NBTTagString(s, s1));
	}

	public void setByteArray(String s, byte abyte0[]) {
		tagMap.put(s, new NBTTagByteArray(s, abyte0));
	}

	public void setIntArray(String s, int array[]) {
		tagMap.put(s, new NBTTagIntArray(s, array));
	}

	public void setCompoundTag(String s, NBTTagCompound nbttagcompound) {
		tagMap.put(s, nbttagcompound.setKey(s));
	}

	public void setBoolean(String s, boolean flag) {
		setByte(s, ((byte) (flag ? 1 : 0)));
	}

	public NBTBase getTag(String s) {
		return tagMap.get(s);
	}

	public boolean hasKey(String s) {
		return tagMap.containsKey(s);
	}

	public byte getByte(String s) {
		if(!tagMap.containsKey(s)) {
			return 0;
		} else {
			return ((NBTTagNumber) tagMap.get(s)).getByteValue();
		}
	}

	public short getShort(String s) {
		if(!tagMap.containsKey(s)) {
			return 0;
		} else {
			return ((NBTTagNumber) tagMap.get(s)).getShortValue();
		}
	}

	public int getInteger(String s) {
		if(!tagMap.containsKey(s)) {
			return 0;
		} else {
			return ((NBTTagNumber) tagMap.get(s)).getIntValue();
		}
	}

	public long getLong(String s) {
		if(!tagMap.containsKey(s)) {
			return 0L;
		} else {
			return ((NBTTagNumber) tagMap.get(s)).getLongValue();
		}
	}

	public float getFloat(String s) {
		if(!tagMap.containsKey(s)) {
			return 0.0F;
		} else {
			return ((NBTTagNumber) tagMap.get(s)).getFloatValue();
		}
	}

	public double getDouble(String s) {
		if(!tagMap.containsKey(s)) {
			return 0.0D;
		} else {
			return ((NBTTagNumber) tagMap.get(s)).getDoubleValue();
		}
	}

	public String getString(String s) {
		if(!tagMap.containsKey(s)) {
			return "";
		} else {
			return ((NBTTagString) tagMap.get(s)).stringValue;
		}
	}

	public byte[] getByteArray(String s) {
		if(!tagMap.containsKey(s)) {
			return new byte[0];
		} else {
			return ((NBTTagByteArray) tagMap.get(s)).byteArray;
		}
	}

	public int[] getIntArray(String s) {
		if(!tagMap.containsKey(s)) {
			return new int[0];
		} else {
			return ((NBTTagIntArray) tagMap.get(s)).array;
		}
	}

	public NBTTagCompound getCompoundTag(String s) {
		if(!tagMap.containsKey(s)) {
			return new NBTTagCompound(s);
		} else {
			return (NBTTagCompound) tagMap.get(s);
		}
	}

	public NBTTagList getTagList(String s) {
		if(!tagMap.containsKey(s)) {
			return new NBTTagList(s);
		} else {
			return (NBTTagList) tagMap.get(s);
		}
	}

	public boolean getBoolean(String s) {
		if(!tagMap.containsKey(s)) {
			return false;
		} else {
			return ((NBTTagNumber) tagMap.get(s)).getBooleanValue();
		}
	}

	@Override
	public String toString() {
		return (new StringBuilder()).append("").append(tagMap.size()).append(" entries").toString();
	}

	public NBTBase remove(String key) {
		return tagMap.remove(key);
	}

	@Override
	public NBTBase clone() {
		NBTTagCompound nbttagcompound = new NBTTagCompound(getKey());
		String s;
		for(Iterator<String> iterator = tagMap.keySet().iterator(); iterator.hasNext(); nbttagcompound.setTag(s, tagMap.get(s).clone())) {
			s = iterator.next();
		}

		return nbttagcompound;
	}

	@Override
	public boolean equals(NBTBase tag) {
		if(tag instanceof NBTTagCompound) {
			NBTTagCompound t = (NBTTagCompound) tag;
			if(t.tagMap.size() != tagMap.size())
				return false;
			Iterator<Entry<String, NBTBase>> i = tagMap.entrySet().iterator();
			while(i.hasNext()) {
				Entry<String, NBTBase> e = i.next();
				NBTBase b = t.tagMap.get(e.getKey());
				if(b == null || !b.equals(e.getValue()))
					return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public String dump() {
		StringBuilder b = new StringBuilder();
		b.append('{');
		Iterator<Entry<String, NBTBase>> i = tagMap.entrySet().iterator();
		boolean first = true;
		while(i.hasNext()) {
			Entry<String, NBTBase> e = i.next();
			if(!first)
				b.append(',');
			else
				first = false;
			b.append('(');
			b.append(e.getValue().getType());
			b.append(')');
			b.append(e.getKey());
			b.append(':');
			b.append(e.getValue().dump());
		}
		b.append('}');
		return b.toString();
	}
}
