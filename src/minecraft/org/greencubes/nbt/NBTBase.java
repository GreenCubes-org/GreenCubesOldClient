package org.greencubes.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.greencubes.util.Util;

public abstract class NBTBase implements Cloneable {
	
	public static final NBTTagEnd end = new NBTTagEnd();
	
	private String name = null;
	
	abstract void write(DataOutput output) throws IOException;
	
	abstract void read(DataInput input) throws IOException;
	
	void writeMinecraft(DataOutput output) throws IOException {
		write(output);
	}
	
	void readMinecraft(DataInput input) throws IOException {
		read(input);
	}
	
	public abstract byte getId();
	
	public abstract byte getIdMinecraft();
	
	public String getName() {
		if(this.name == null)
			return "";
		return this.name;
	}
	
	public NBTBase setName(String name) {
		this.name = name;
		return this;
	}
	
	public static NBTBase sread(DataInput input) throws IOException {
		byte b;
		try {
			b = input.readByte();
		} catch(IOException e) {
			throw new IOException("Exception when reading first byte", e);
		}
		if(b == 0)
			return end;
		NBTBase nbt = getType(b);
		if(nbt == null)
			throw new IOException("Wrong tag id: " + b);
		try {
			nbt.name = input.readUTF();
		} catch(IOException e) {
			throw new IOException("Exception when reading tag name of " + nbt, e);
		}
		try {
			nbt.read(input);
		} catch(IOException e) {
			throw new IOException("Exception when reading " + nbt, e);
		}
		return nbt;
	}
	
	public static void swrite(NBTBase nbt, DataOutput output) throws IOException {
		output.writeByte(nbt.getId());
		if(nbt.getId() == 0)
			return;
		
		output.writeUTF(nbt.getName());
		
		nbt.write(output);
	}
	
	public static NBTBase sreadMinecraft(DataInput input) throws IOException {
		byte b;
		try {
			b = input.readByte();
		} catch(IOException e) {
			throw new IOException("Exception when reading first byte", e);
		}
		if(b == 0)
			return end;
		NBTBase nbt = getTypeMinecraft(b);
		if(nbt == null)
			throw new IOException("Wrong tag id: " + b);
		try {
			nbt.name = input.readUTF();
		} catch(IOException e) {
			throw new IOException("Exception when reading tag name of " + nbt, e);
		}
		try {
			nbt.readMinecraft(input);
		} catch(IOException e) {
			throw new IOException("Exception when reading " + nbt, e);
		}
		return nbt;
	}
	
	public static void swriteMinecraft(NBTBase nbt, DataOutput output) throws IOException {
		output.writeByte(nbt.getIdMinecraft());
		if(nbt.getId() == 0)
			return;
		
		output.writeUTF(nbt.getName());
		
		nbt.writeMinecraft(output);
	}
	
	public static NBTBase getType(byte id) {
		switch(id) {
		case 0:
			return end;
		case 1:
			return new NBTTagByte();
		case 2:
			return new NBTTagShort();
		case 3:
			return new NBTTagInt();
		case 4:
			return new NBTTagLong();
		case 5:
			return new NBTTagFloat();
		case 6:
			return new NBTTagDouble();
		case 7:
			return new NBTTagByteArray();
		case 8:
			return new NBTTagString();
		case 9:
			return new NBTTagList();
		case 10:
			return new NBTTagCompound();
		case 11:
			return new NBTTagShortArray();
		case 12:
			return new NBTTagIntArray();
		case 15:
			return new NBTTagHSVColor();
		case 16:
			return new NBTTagIntObjectMap();
		case 17:
			return new NBTTagObjectList();
		}
		return null;
	}
	
	public static NBTBase getTypeMinecraft(byte id) {
		switch(id) {
		case 0:
			return end;
		case 1:
			return new NBTTagByte();
		case 2:
			return new NBTTagShort();
		case 3:
			return new NBTTagInt();
		case 4:
			return new NBTTagLong();
		case 5:
			return new NBTTagFloat();
		case 6:
			return new NBTTagDouble();
		case 7:
			return new NBTTagByteArray();
		case 8:
			return new NBTTagString();
		case 9:
			return new NBTTagList();
		case 10:
			return new NBTTagCompound();
		case 11:
			return new NBTTagIntArray();
		}
		return null;
	}
	
	public static String getName(byte id) {
		switch(id) {
		case 0:
			return "TAG_End";
		case 1:
			return "TAG_Byte";
		case 2:
			return "TAG_Short";
		case 3:
			return "TAG_Int";
		case 4:
			return "TAG_Long";
		case 5:
			return "TAG_Float";
		case 6:
			return "TAG_Double";
		case 7:
			return "TAG_Byte_Array";
		case 8:
			return "TAG_String";
		case 9:
			return "TAG_List";
		case 10:
			return "TAG_Map";
		case 11:
			return "TAG_Short_Array";
		case 12:
			return "TAG_Int_Array";
		case 15:
			return "TAG_HSV_Color";
		case 16:
			return "TAG_IntObjectMap";
		case 17:
			return "TAG_ObjectList";
		}
		return "UNKNOWN";
	}
	
	public abstract StringBuilder structureDump(StringBuilder store, int depth);
	
	public abstract String dump();
	
	/**
	 * Should preform deep cloning of NBT
	 * @return deep clone of this tag
	 */
	@Override
	public abstract NBTBase clone();
	
	public abstract boolean equals(NBTBase tag);
	
	@Override
	public String toString() {
		return Util.concat(this.getClass().getSimpleName(), "{", getName(), "}");
	}
	
	@Override
	public final boolean equals(Object o) {
		if(o instanceof NBTBase)
			return equals((NBTBase) o);
		return false;
	}
	
	@Override
	public abstract int hashCode();
}
