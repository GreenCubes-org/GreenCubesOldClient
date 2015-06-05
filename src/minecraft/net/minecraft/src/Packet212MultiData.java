package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet212MultiData extends Packet {

	public int type;
	public String destination;
	public byte[] data;

	public Packet212MultiData(int type, String destination, String data) throws IOException {
		this(type, destination, UTF16toByteArray(data));
	}

	public Packet212MultiData(int type, String destination, byte[] data) throws IOException {
		if(data == null)
			data = new byte[0];
		else if(data.length > 32767)
			throw new IOException("Data is too big (> 32767)!");
		this.type = type;
		this.destination = destination == null ? "" : destination;
		this.data = data;
	}

	public Packet212MultiData() {
	}

	@Override
	public void readPacketData(DataInputStream input) throws IOException {
		type = input.readUnsignedByte();
		destination = readString(input, 32767);
		int l = input.readShort();
		data = new byte[l];
		input.readFully(data);
	}

	@Override
	public void writePacketData(DataOutputStream output) throws IOException {
		output.writeByte(type);
		writeString(destination, output);
		output.writeShort(data.length);
		for(int i = 0; i < data.length; ++i)
			output.writeByte(data[i]);
	}

	public static byte[] UTF16toByteArray(String string) throws IOException {
		if(string == null)
			return new byte[0];
		if(string.length() > 32767) {
			throw new IOException("String too big!");
		}
		byte[] out = new byte[string.length() * 2 + 2];
		int l = string.length();
		out[0] = (byte) ((l >>> 8) & 0xFF);
		out[1] = (byte) ((l >>> 0) & 0xFF);
		for(int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			out[i * 2 + 2] = (byte) ((c >>> 8) & 0xFF);
			out[i * 2 + 3] = (byte) ((c >>> 0) & 0xFF);
		}
		return out;
	}

	public static String ByteArrayToUTF16(byte[] array) throws IOException {
		return ByteArrayToUTF16(array, 500);
	}

	public static String ByteArrayToUTF16(byte[] array, int maxLength) throws IOException {
		if(array.length == 0)
			return null;
		short size = (short) (((array[0] & 0xFF) << 8) | (array[1] & 0xFF));
		if(size > maxLength)
			throw new IOException("Bad string!");
		if(size < 0)
			throw new IOException("Bad string!");
		StringBuilder builder = new StringBuilder(size);
		for(int i = 0; i < size; i++)
			builder.append((char) (((array[i * 2 + 2] & 0xFF) << 8) + (array[i * 2 + 3] & 0xFF)));
		return builder.toString();
	}

	@Override
	public void processPacket(NetHandler handler) {
		handler.handleMultiData(this);
	}

	@Override
	public int getPacketSize() {
		return data.length + (destination == null ? 0 : destination.length() * 2) + 5;
	}
}
