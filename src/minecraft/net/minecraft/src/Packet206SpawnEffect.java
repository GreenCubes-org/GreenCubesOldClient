package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet206SpawnEffect extends Packet {

	public String effect;
	public double x;
	public double y;
	public double z;
	public double param1;
	public double param2;
	public double param3;

	@Override
	public void readPacketData(DataInputStream input) throws IOException {
		effect = readString(input, 32);
		x = input.readDouble();
		y = input.readDouble();
		z = input.readDouble();
		param1 = input.readDouble();
		param2 = input.readDouble();
		param3 = input.readDouble();
	}

	@Override
	public void writePacketData(DataOutputStream out) throws IOException {
		writeString(effect, out);
		out.writeDouble(x);
		out.writeDouble(y);
		out.writeDouble(z);
		out.writeDouble(param1);
		out.writeDouble(param2);
		out.writeDouble(param3);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleSpawnParticle(this);
	}

	@Override
	public int getPacketSize() {
		return effect.length() * 2 + 6 * 8;
	}

}
