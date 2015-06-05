package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet62PlaySound extends Packet {

	public String soundName;
	public int x;
	public int y;
	public int z;
	public float volume;
	public float pitch;

	@Override
	public void readPacketData(DataInputStream input) throws IOException {
		soundName = readString(input, 64);
		x = input.readInt();
		y = input.readInt();
		z = input.readInt();
		volume = input.readFloat();
		pitch = input.readFloat();
	}

	@Override
	public void writePacketData(DataOutputStream out) throws IOException {
		writeString(soundName, out);
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);
		out.writeFloat(volume);
		out.writeFloat(pitch);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handlePlaySound(this);
	}

	@Override
	public int getPacketSize() {
		return soundName.length() * 2 + 20;
	}

}
