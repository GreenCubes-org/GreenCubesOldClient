package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * GreenCubes class
 * @author Rena
 *
 */
public class Packet221ConnectionReady extends Packet {

	public String c1;
	public byte[] c2;
	public byte[] c3;

	public Packet221ConnectionReady() {
	}

	@Override
	public void readPacketData(DataInputStream datainputstream) {
	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		writeString(c1, dataoutputstream);
		dataoutputstream.writeShort(c2.length);
		dataoutputstream.write(c2);
		dataoutputstream.writeShort(c3.length);
		dataoutputstream.write(c3);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
	}

	@Override
	public int getPacketSize() {
		return 4 + c1.length() * 2 + c2.length;
	}

}
