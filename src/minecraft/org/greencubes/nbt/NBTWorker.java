package org.greencubes.nbt;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

import org.greencubes.util.Util;

public class NBTWorker {
	
	public static NBTTagCompound read(DataInputStream inputStream) throws IOException {
		try {
			NBTBase localNBTBase = NBTBase.sread(inputStream);
			if(localNBTBase instanceof NBTTagCompound)
				return (NBTTagCompound) localNBTBase;
			throw new IOException("Root tag must be a named compound tag");
		} finally {
			Util.close(inputStream);
		}
	}
	
	public static void write(NBTTagCompound tag, DataOutputStream outputStream) throws IOException {
		try {
			NBTBase.swrite(tag, outputStream);
		} finally {
			Util.close(outputStream);
		}
	}
	
	public static NBTTagCompound readMinecraft(DataInputStream inputStream) throws IOException {
		try {
			NBTBase localNBTBase = NBTBase.sreadMinecraft(inputStream);
			if(localNBTBase instanceof NBTTagCompound)
				return (NBTTagCompound) localNBTBase;
			throw new IOException("Root tag must be a named compound tag");
		} finally {
			Util.close(inputStream);
		}
	}
	
	public static void writeMinecraft(NBTTagCompound tag, DataOutputStream outputStream) throws IOException {
		try {
			NBTBase.swriteMinecraft(tag, outputStream);
		} finally {
			Util.close(outputStream);
		}
	}
	
	public static byte[] toGZIPArray(NBTTagCompound tag) {
		try {
			ByteArrayOutputStream is = new ByteArrayOutputStream();
			DataOutputStream stream = new DataOutputStream(new GZIPOutputStream(is));
			write(tag, stream);
			return is.toByteArray();
		} catch(IOException e) {
			throw new RuntimeException("Impossible exception happend", e);
		}
	}

	public static byte[] toDeflateArray(NBTTagCompound tag) {
		try {
			ByteArrayOutputStream is = new ByteArrayOutputStream();
			DataOutputStream stream = new DataOutputStream(new DeflaterOutputStream(is));
			write(tag, stream);
			return is.toByteArray();
		} catch(IOException e) {
			throw new RuntimeException("Impossible exception happend", e);
		}
	}
	
	public static NBTTagCompound fromGZIPArray(byte[] array) throws IOException {
		DataInputStream localDataInputStream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(array))));
		return read(localDataInputStream);
	}
	
	public static NBTTagCompound fromDeflateArray(byte[] array) throws IOException {
		DataInputStream localDataInputStream = new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(array))));
		return read(localDataInputStream);
	}
}
