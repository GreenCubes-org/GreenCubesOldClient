package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet204Channel extends Packet {

	public int channelId;
	public String channelName;
	public String channelAlias;
	public int type;
	public boolean update;

	@Override
	public void readPacketData(DataInputStream input) throws IOException {
		update = input.readBoolean();
		channelId = input.readInt();
		if(update) {
			channelName = readString(input, 32);
			channelAlias = readString(input, 16);
			type = input.readByte();
		}
	}

	@Override
	public void writePacketData(DataOutputStream out) throws IOException {
		out.writeBoolean(update);
		out.writeInt(channelId);
		if(update) {
			writeString(channelName, out);
			writeString(channelAlias, out);
			out.writeByte(type);
		}
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleChannel(this);
	}

	@Override
	public int getPacketSize() {
		return 0;
	}

}
