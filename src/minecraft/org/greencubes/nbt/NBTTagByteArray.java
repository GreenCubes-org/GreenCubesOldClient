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
import java.util.Arrays;

import org.greencubes.util.Util;

public class NBTTagByteArray extends NBTBase {
	public byte[] a;
	
	public NBTTagByteArray() {
	}
	
	public NBTTagByteArray(byte[] paramArrayOfByte) {
		this.a = paramArrayOfByte;
	}
	
	@Override
	void write(DataOutput paramDataOutput) throws IOException {
		paramDataOutput.writeInt(this.a.length);
		paramDataOutput.write(this.a);
	}
	
	@Override
	void read(DataInput paramDataInput) throws IOException {
		int i = paramDataInput.readInt();
		this.a = new byte[i];
		paramDataInput.readFully(this.a);
	}
	
	@Override
	public byte getId() {
		return 7;
	}
	
	@Override
	public byte getIdMinecraft() {
		return 7;
	}
	
	@Override
	public String dump() {
		return new StringBuilder('(').append(this.a.length).append(" bytes)").toString();
	}
	
	@Override
	public NBTTagByteArray clone() {
		return new NBTTagByteArray(this.a.clone());
	}
	
	@Override
	public String toString() {
		return new StringBuilder('[').append(this.a.length).append(" bytes]").toString();
	}
	
	@Override
	public boolean equals(NBTBase tag) {
		if(tag instanceof NBTTagByteArray) {
			NBTTagByteArray t = (NBTTagByteArray) tag;
			if(t.a.length != a.length)
				return false;
			for(int i = 0; i < a.length; ++i)
				if(a[i] != t.a[i])
					return false;
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(a);
	}
	
	@Override
	public StringBuilder structureDump(StringBuilder store, int depth) {
		if(store == null)
			store = new StringBuilder();
		Util.dump(store, depth, "ByteArray(" + getId() + "," + getName() + "): " + a.length + " elements\n");
		return store;
	}
}
