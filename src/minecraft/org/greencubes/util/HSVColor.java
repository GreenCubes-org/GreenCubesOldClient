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
package org.greencubes.util;

import java.awt.Color;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class HSVColor {

	public final float h;
	public final float s;
	public final float v;
	
	public HSVColor(float h, float s, float v) {
		this.h = h;
		this.s = s;
		this.v = v;
	}
	
	public int getRGB() {
		return Color.HSBtoRGB(h, s, v);
	}

	@Override
	public String toString() {
		return "HSVColor{" + h + "," + s + "," + v + "}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(h);
		result = prime * result + Float.floatToIntBits(s);
		result = prime * result + Float.floatToIntBits(v);
		return result;
	}
	
	public boolean equalsPacked(HSVColor color) {
		return Float.floatToIntBits(h) == Float.floatToIntBits(color.h) && ((int) (s * 255F)) == ((int) (color.s * 255F)) && ((int) (v * 255F)) == ((int) (color.v * 255F));
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(!(obj instanceof HSVColor))
			return false;
		HSVColor other = (HSVColor) obj;
		if(Float.floatToIntBits(h) != Float.floatToIntBits(other.h))
			return false;
		if(Float.floatToIntBits(s) != Float.floatToIntBits(other.s))
			return false;
		if(Float.floatToIntBits(v) != Float.floatToIntBits(other.v))
			return false;
		return true;
	}
	
	public static void writePacked(HSVColor color, DataOutput ds) throws IOException {
		ds.writeFloat(color.h);
		ds.writeByte((int) (color.s * 255F));
		ds.writeByte((int) (color.v * 255F));
	}
	
	public static HSVColor readPacked(DataInput ds) throws IOException {
		float h = ds.readFloat();
		int s = ds.readUnsignedByte();
		int v = ds.readUnsignedByte();
		return new HSVColor(h, s / 255F, v / 255F);
	}
	
	public static HSVColor fromRGB(int rgb) {
		float[] arr = Color.RGBtoHSB((rgb >> 16) & 255, (rgb >> 8) & 255, rgb & 255, null);
		return new HSVColor(arr[0],arr[1],arr[2]);
	}
	
	public static HSVColor fromInts(int h, int s, int v) {
		return new HSVColor(h / 360F, s / 100F, v / 100F);
	}
}
