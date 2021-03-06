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

import org.greencubes.util.Util;

public class NBTTagDouble extends NBTBase implements NBTNumber {
	
	public double a;
	
	public NBTTagDouble() {
	}
	
	public NBTTagDouble(double paramDouble) {
		this.a = paramDouble;
	}
	
	@Override
	void write(DataOutput paramDataOutput) throws IOException {
		paramDataOutput.writeDouble(this.a);
	}
	
	@Override
	void read(DataInput paramDataInput) throws IOException {
		this.a = paramDataInput.readDouble();
	}
	
	@Override
	public byte getId() {
		return 6;
	}
	
	@Override
	public byte getIdMinecraft() {
		return 6;
	}
	
	@Override
	public String dump() {
		return Double.toString(this.a);
	}
	
	@Override
	public NBTTagDouble clone() {
		return new NBTTagDouble(this.a);
	}
	
	@Override
	public String toString() {
		return Double.toString(this.a);
	}
	
	@Override
	public boolean equals(NBTBase tag) {
		if(tag instanceof NBTTagDouble)
			return ((NBTTagDouble) tag).a == this.a;
		return false;
	}
	
	@Override
	public StringBuilder structureDump(StringBuilder store, int depth) {
		if(store == null)
			store = new StringBuilder();
		Util.dump(store, depth, "Double(" + getId() + "," + getName() + "): " + a + "\n");
		return store;
	}
	
	@Override
	public int hashCode() {
		long bits = Double.doubleToLongBits(a);
		return (int) (bits ^ (bits >>> 32));
	}

	@Override
	public int getIntValue() {
		return (int) a;
	}

	@Override
	public long getLongValue() {
		return (long) a;
	}

	@Override
	public short getShortValue() {
		return (short) a;
	}

	@Override
	public boolean getBooleanValue() {
		return a != 0;
	}

	@Override
	public byte getByteValue() {
		return (byte) a;
	}

	@Override
	public float getFloatValue() {
		return (float) a;
	}

	@Override
	public double getDoubleValue() {
		return a;
	}
}
