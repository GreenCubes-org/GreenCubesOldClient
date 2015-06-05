package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * GreenCubes class
 * @author Rena
 *
 */
public class hwaa extends Packet {

	public String hardwareId;

	public hwaa(int protocol, String hardwareId) {
		this.hardwareId = hardwareId;
	}

	@Override
	public void readPacketData(DataInputStream datainputstream) {
	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		writeString(hardwareId, dataoutputstream);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
	}

	@Override
	public int getPacketSize() {
		return 3 + hardwareId.length() * 2;
	}

}
