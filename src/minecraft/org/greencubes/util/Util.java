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

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

public class Util {
	
	public static void close(Closeable... r) {
		for(int i = 0; i < r.length; ++i)
			try {
				if(r[i] != null)
					r[i].close();
			} catch(Exception e) {
			}
	}

	public static String concat(String... strings) {
		int length = 0;
		for(int i = 0; i < strings.length; ++i)
			length += strings[i].length();
		StringBuilder buffer = new StringBuilder(length);
		for(int i = 0; i < strings.length; ++i)
			buffer.append(strings[i]);
		return buffer.toString();
	}

	public static String concat(Object... objects) {
		String[] strings = new String[objects.length];
		int length = 0;
		for(int i = 0; i < objects.length; ++i) {
			String s = objects[i].toString();
			length += s.length();
			strings[i] = s;
		}
		StringBuilder buffer = new StringBuilder(length);
		for(int i = 0; i < strings.length; ++i)
			buffer.append(strings[i]);
		return buffer.toString();
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] arrayAppend(T[] src, T... append) {
		T[] newArray = (T[]) Arrays.copyOf(src, src.length + append.length, src.getClass());
		System.arraycopy(append, 0, newArray, src.length, append.length);
		return newArray;
	}

	public static byte[] sha1(byte[] data) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
			digest.update(data);
			return digest.digest();
		} catch (NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		}
	}

	public static void dump(StringBuilder sb, int depth, String string) {
		while(depth-- > 0)
			sb.append("\t");
		sb.append(string);
	}

	public static String toHexString(byte[] array) {
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < array.length; ++i)
			result.append(Integer.toString((array[i] & 0xff) + 0x100, 16).substring(1));
		return result.toString();
	}
	
	public static byte[] deflate(byte[] b) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		OutputStream stream = new DeflaterOutputStream(os);
		stream.write(b);
		stream.close();
		return os.toByteArray();
	}
	
	public static byte[] inflate(byte[] b) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		OutputStream stream = new InflaterOutputStream(os);
		stream.write(b);
		stream.close();
		return os.toByteArray();
	}
	
	public static String join(String[] split) {
		return join(split, "");
	}
	
	public static String join(String[] split, String glue) {
		return join(split, glue, 0);
	}
	
	public static String join(Object[] split, String glue) {
		String[] strs = new String[split.length];
		for(int i = 0; i < split.length; ++i) {
			strs[i] = String.valueOf(split[i]);
		}
		return join(strs, glue, 0);
	}
	
	public static String join(String[] split, String glue, int start) {
		return join(split, glue, start, split.length - 1);
	}
	
	public static String join(String[] split, String glue, int start, int end) {
		if(split.length == 0)
			return "";
		start = start >= split.length ? split.length - 1 : start;
		end = end >= split.length ? split.length - 1 : end;
		int length = glue.length() * (end - start);
		for(int i = start; i <= end; ++i)
			length += split[i].length();
		StringBuilder sb = new StringBuilder(length);
		boolean set = false;
		for(int i = start; i <= end; ++i) {
			if(set)
				sb.append(glue);
			sb.append(split[i]);
			set = true;
		}
		return sb.toString();
	}
}
