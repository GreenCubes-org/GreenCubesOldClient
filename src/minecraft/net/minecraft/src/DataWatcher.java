// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.io.*;
import java.util.*;

// Referenced classes of package net.minecraft.src:
//            WatchableObject, Packet, ItemStack, Item, 
//            ChunkCoordinates

public class DataWatcher {

	private static final TObjectIntMap<Class> dataTypes = new TObjectIntHashMap<Class>();
	private final TIntObjectMap<WatchableObject> watchedObjects = new TIntObjectHashMap<WatchableObject>();
	private boolean objectChanged;

	public DataWatcher() {
	}

	public void addObject(int i, Object obj) {
		Class c = obj.getClass();
		if(!dataTypes.containsKey(c))
			throw new IllegalArgumentException((new StringBuilder()).append("Unknown data type: ").append(c).toString());
		int type = dataTypes.get(c);
		if(i > 31)
			throw new IllegalArgumentException((new StringBuilder()).append("Data value id is too big with ").append(i).append("! (Max is ").append(31).append(")").toString());
		if(watchedObjects.containsKey(i)) {
			throw new IllegalArgumentException((new StringBuilder()).append("Duplicate id value for ").append(i).append("!").toString());
		} else {
			WatchableObject watchableobject = new WatchableObject(type, i, obj);
			watchedObjects.put(i, watchableobject);
			return;
		}
	}

	public byte getWatchableObjectByte(int i) {
		return ((Byte) ((WatchableObject) watchedObjects.get(i)).getObject()).byteValue();
	}

	public short func_41062_b(int i) {
		return ((Short) ((WatchableObject) watchedObjects.get(i)).getObject()).shortValue();
	}

	public int getWatchableObjectInt(int i) {
		return ((Integer) ((WatchableObject) watchedObjects.get(i)).getObject()).intValue();
	}

	public String getWatchableObjectString(int i) {
		return (String) ((WatchableObject) watchedObjects.get(i)).getObject();
	}

	public void updateObject(int i, Object obj) {
		WatchableObject watchableobject = (WatchableObject) watchedObjects.get(i);
		if(!obj.equals(watchableobject.getObject())) {
			watchableobject.setObject(obj);
			watchableobject.setWatching(true);
			objectChanged = true;
		}
	}

	public static void writeObjectsInListToStream(List<WatchableObject> list, DataOutputStream dataoutputstream) throws IOException {
		if(list != null) {
			Iterator<WatchableObject> iterator = list.iterator();
			while(iterator.hasNext())
				writeWatchableObject(dataoutputstream, iterator.next());
		}
		dataoutputstream.writeByte(127);
	}

	public void writeWatchableObjects(DataOutputStream dataoutputstream) throws IOException {
		TIntObjectIterator<WatchableObject> iterator = watchedObjects.iterator();
		while(iterator.hasNext()) {
			iterator.advance();
			writeWatchableObject(dataoutputstream, iterator.value());
		}
		dataoutputstream.writeByte(127);
	}

	private static void writeWatchableObject(DataOutputStream dataoutputstream, WatchableObject watchableobject) throws IOException {
		int i = (watchableobject.getObjectType() << 5 | watchableobject.getDataValueId() & 0x1f) & 0xff;
		dataoutputstream.writeByte(i);
		switch(watchableobject.getObjectType()) {
		case 0: // '\0'
			dataoutputstream.writeByte(((Byte) watchableobject.getObject()).byteValue());
			break;

		case 1: // '\001'
			dataoutputstream.writeShort(((Short) watchableobject.getObject()).shortValue());
			break;

		case 2: // '\002'
			dataoutputstream.writeInt(((Integer) watchableobject.getObject()).intValue());
			break;

		case 3: // '\003'
			dataoutputstream.writeFloat(((Float) watchableobject.getObject()).floatValue());
			break;

		case 4: // '\004'
			Packet.writeString((String) watchableobject.getObject(), dataoutputstream);
			break;

		case 5: // '\005'
			ItemStack itemstack = (ItemStack) watchableobject.getObject();
			Packet.writeItemStack(itemstack, dataoutputstream);
			break;

		case 6: // '\006'
			ChunkCoordinates chunkcoordinates = (ChunkCoordinates) watchableobject.getObject();
			dataoutputstream.writeInt(chunkcoordinates.posX);
			dataoutputstream.writeInt(chunkcoordinates.posY);
			dataoutputstream.writeInt(chunkcoordinates.posZ);
			break;
		}
	}

	public static List readWatchableObjects(DataInputStream datainputstream) throws IOException {
		ArrayList arraylist = null;
		for(byte byte0 = datainputstream.readByte(); byte0 != 127; byte0 = datainputstream.readByte()) {
			if(arraylist == null)
				arraylist = new ArrayList();
			int i = (byte0 & 0xe0) >> 5;
			int j = byte0 & 0x1f;
			WatchableObject watchableobject = null;
			switch(i) {
			case 0: // '\0'
				watchableobject = new WatchableObject(i, j, Byte.valueOf(datainputstream.readByte()));
				break;

			case 1: // '\001'
				watchableobject = new WatchableObject(i, j, Short.valueOf(datainputstream.readShort()));
				break;

			case 2: // '\002'
				watchableobject = new WatchableObject(i, j, Integer.valueOf(datainputstream.readInt()));
				break;

			case 3: // '\003'
				watchableobject = new WatchableObject(i, j, Float.valueOf(datainputstream.readFloat()));
				break;

			case 4: // '\004'
				watchableobject = new WatchableObject(i, j, Packet.readString(datainputstream, 64));
				break;

			case 5: // '\005'
				ItemStack itemStack = Packet.readItemStack(datainputstream);
				watchableobject = new WatchableObject(i, j, itemStack);
				break;

			case 6: // '\006'
				int k = datainputstream.readInt();
				int l = datainputstream.readInt();
				int i1 = datainputstream.readInt();
				watchableobject = new WatchableObject(i, j, new ChunkCoordinates(k, l, i1));
				break;
			}
			arraylist.add(watchableobject);
		}

		return arraylist;
	}

	public void updateWatchedObjectsFromList(List<WatchableObject> list) {
		Iterator<WatchableObject> iterator = list.iterator();
		while(iterator.hasNext()) {
			WatchableObject watchableobject = iterator.next();
			WatchableObject watchableobject1 = watchedObjects.get(watchableobject.getDataValueId());
			if(watchableobject1 != null)
				watchableobject1.setObject(watchableobject.getObject());
		}
	}

	public ItemStack getWatchableObjectItemStack(int i) {
		return (ItemStack) ((WatchableObject) watchedObjects.get(i)).getObject();
	}

	static {
		dataTypes.put(java.lang.Byte.class, 0);
		dataTypes.put(java.lang.Short.class, 1);
		dataTypes.put(java.lang.Integer.class, 2);
		dataTypes.put(java.lang.Float.class, 3);
		dataTypes.put(java.lang.String.class, 4);
		dataTypes.put(net.minecraft.src.ItemStack.class, 5);
		dataTypes.put(net.minecraft.src.ChunkCoordinates.class, 6);
	}
}
