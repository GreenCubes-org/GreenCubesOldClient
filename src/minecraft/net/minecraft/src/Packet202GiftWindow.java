package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet202GiftWindow extends Packet100OpenWindow {

	public String giftTitle;

	@Override
	public void readPacketData(DataInputStream datainputstream) throws IOException {
		super.readPacketData(datainputstream);
		giftTitle = readString(datainputstream, 128);
	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		super.writePacketData(dataoutputstream);
		writeString(giftTitle, dataoutputstream);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleGiftWindow(this);
	}

	@Override
	public int getPacketSize() {
		return super.getPacketSize() + giftTitle.length() * 2 + 3;
	}

}
