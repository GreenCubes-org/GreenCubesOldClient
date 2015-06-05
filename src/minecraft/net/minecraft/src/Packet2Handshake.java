// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            Packet, NetHandler

public class Packet2Handshake extends Packet {

	public String username;
	public int protocol;

	public Packet2Handshake() {
	}

	public Packet2Handshake(String s, int protocol) {
		this.protocol = protocol;
		username = s;
	}

	@Override
	public void readPacketData(DataInputStream datainputstream) throws IOException {
		protocol = datainputstream.readUnsignedByte();
		username = readString(datainputstream, 32);
	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.write(protocol);
		writeString(username, dataoutputstream);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleHandshake(this);
	}

	@Override
	public int getPacketSize() {
		return 5 + username.length() + 4;
	}
}
