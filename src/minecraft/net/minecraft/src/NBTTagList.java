// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;
import java.util.*;

// Referenced classes of package net.minecraft.src:
//            NBTBase

public class NBTTagList extends NBTBase {

	private List<NBTBase> tagList;
	private byte tagType;

	public NBTTagList() {
		super("");
		tagList = new ArrayList();
	}

	public NBTTagList(String s) {
		super(s);
		tagList = new ArrayList();
	}
	
	public static NBTTagList toList(String[] strings) {
		NBTTagList list = new NBTTagList();
		for(String s : strings)
			list.setTag(new NBTTagString(s));
		return list;
	}

	@Override
	void writeTagContents(DataOutput dataoutput) throws IOException {
		if(tagList.size() > 0) {
			tagType = tagList.get(0).getType();
		} else {
			tagType = 1;
		}
		dataoutput.writeByte(tagType);
		dataoutput.writeInt(tagList.size());
		for(int i = 0; i < tagList.size(); i++) {
			tagList.get(i).writeTagContents(dataoutput);
		}

	}

	@Override
	void readTagContents(DataInput datainput) throws IOException {
		tagType = datainput.readByte();
		int i = datainput.readInt();
		tagList = new ArrayList();
		for(int j = 0; j < i; j++) {
			NBTBase nbtbase = NBTBase.createTagOfType(tagType, null);
			nbtbase.readTagContents(datainput);
			tagList.add(nbtbase);
		}

	}

	@Override
	public byte getType() {
		return 9;
	}

	@Override
	public String toString() {
		return (new StringBuilder()).append("").append(tagList.size()).append(" entries of type ").append(NBTBase.getTagName(tagType)).toString();
	}

	public void setTag(NBTBase nbtbase) {
		tagType = nbtbase.getType();
		tagList.add(nbtbase);
	}

	public NBTBase get(int i) {
		return tagList.get(i);
	}

	public int size() {
		return tagList.size();
	}

	@Override
	public NBTBase clone() {
		NBTTagList nbttaglist = new NBTTagList(getKey());
		nbttaglist.tagType = tagType;
		NBTBase nbtbase1;
		for(Iterator iterator = tagList.iterator(); iterator.hasNext(); nbttaglist.tagList.add(nbtbase1)) {
			NBTBase nbtbase = (NBTBase) iterator.next();
			nbtbase1 = nbtbase.clone();
		}

		return nbttaglist;
	}

	@Override
	public boolean equals(NBTBase obj) {
		if(obj instanceof NBTTagList) {
			NBTTagList t = (NBTTagList) obj;
			if(t.tagList.size() != tagList.size())
				return false;
			for(int i = 0; i < tagList.size(); ++i)
				if(!tagList.get(i).equals(t.tagList.get(i)))
					return false;
			return true;
		}
		return false;
	}

	@Override
	public String dump() {
		StringBuilder sb = new StringBuilder();
		sb.append('(');
		for(int i = 0; i < tagList.size(); ++i) {
			if(i != 0)
				sb.append(',');
			sb.append(tagList.get(i).dump());
		}
		sb.append(')');
		return sb.toString();
	}
}
