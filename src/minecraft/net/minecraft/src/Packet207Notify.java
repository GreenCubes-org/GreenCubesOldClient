package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet207Notify extends Packet {

	public ItemStack item;
	public String text;

	@Override
	public void readPacketData(DataInputStream input) throws IOException {
		item = readItemStack(input);
		text = readString(input, 128);
	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {

	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleNotify(this);
	}

	@Override
	public int getPacketSize() {
		return 0;
	}

}
