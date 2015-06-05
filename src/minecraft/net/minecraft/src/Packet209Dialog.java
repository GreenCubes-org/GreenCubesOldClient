package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet209Dialog extends Packet {

	public boolean ok = false;
	public boolean cancel = false;
	public String title;
	public NBTTagCompound data;

	@Override
	public void readPacketData(DataInputStream input) throws IOException {
		title = readString(input, 64);
		byte b = input.readByte();
		cancel = (b & 2) != 0;
		ok = (b & 1) != 0;
		int l = input.readInt();
		byte[] arr = new byte[l];
		input.readFully(arr);
		data = CompressedStreamTools.func_40592_a(arr);
	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleDialog(this);
	}

	@Override
	public int getPacketSize() {
		return 0;
	}

}
