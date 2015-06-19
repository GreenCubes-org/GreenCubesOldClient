package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet044PlayerReturn extends Packet {
	
	public double x;
	public double y;
	public double z;
	
	public Packet044PlayerReturn() {
	}
	
	@Override
	public void readPacketData(DataInputStream input) throws IOException {
		this.x = input.readDouble();
		this.y = input.readDouble();
		this.z = input.readDouble();
	}
	
	@Override
	public void writePacketData(DataOutputStream output) throws IOException {
		output.writeDouble(x);
		output.writeDouble(y);
		output.writeDouble(z);
	}
	
	@Override
	public void processPacket(NetHandler handler) {
		handler.handlePlayerReturn(this);
	}
	
	@Override
	public int getPacketSize() {
		return 12;
	}
	
}
