package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet210DialogAnswer extends Packet {

	public int answer;

	public Packet210DialogAnswer(int answer) {
		this.answer = answer;
	}

	@Override
	public void readPacketData(DataInputStream input) throws IOException {

	}

	@Override
	public void writePacketData(DataOutputStream output) throws IOException {
		output.write(answer);
	}

	@Override
	public void processPacket(NetHandler nethandler) {

	}

	@Override
	public int getPacketSize() {
		return 1;
	}

}
