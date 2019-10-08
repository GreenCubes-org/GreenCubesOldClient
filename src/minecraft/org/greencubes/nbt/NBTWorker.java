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
