package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet203GiftWindowTitle extends Packet {

	public String newTitle;
	public int windowId;

	public Packet203GiftWindowTitle(int windowId, String newTitle) {
		this.windowId = windowId;
		this.newTitle = newTitle;
	}

	@Override
	public void writePacketData(DataOutputStream output) throws IOException {
		output.write(windowId);;
		writeString(newTitle, output);
	}

	@Override
	public void readPacketData(DataInputStream datainputstream) throws IOException {
	}

	@Override
	public void processPacket(NetHandler nethandler) {

	}

	@Override
	public int getPacketSize() {
		return newTitle.length();
	}
}
