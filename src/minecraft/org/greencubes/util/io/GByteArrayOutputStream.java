package org.greencubes.util.io;

import java.io.ByteArrayOutputStream;

public class GByteArrayOutputStream extends ByteArrayOutputStream {

	public GByteArrayOutputStream() {
		super();
	}

	public GByteArrayOutputStream(int len) {
		super(len);
	}

	public byte[] getArray() {
		return buf;
	}
}
