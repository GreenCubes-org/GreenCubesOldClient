// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            Packet, NetHandler

public class Packet9Respawn extends Packet {

	public int respawnDimension;

	public Packet9Respawn() {
	}

	public Packet9Respawn(byte byte0, byte byte1, long l, int i, int j) {
		respawnDimension = byte0;
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleRespawn(this);
	}

	@Override
	public void readPacketData(DataInputStream datainputstream) throws IOException {
		respawnDimension = datainputstream.readByte();
	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeByte(respawnDimension);
	}

	@Override
	public int getPacketSize() {
		return 1;
	}
}
