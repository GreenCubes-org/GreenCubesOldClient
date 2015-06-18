// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            Packet, NetHandler

public class Packet1Login extends Packet {

	public int entityId;
	public int playerId;
	public byte worldType;

	public Packet1Login() {
	}

	@Override
	public void readPacketData(DataInputStream datainputstream) throws IOException {
		entityId = datainputstream.readInt();
		playerId = datainputstream.readInt();
		worldType = datainputstream.readByte();
	}

	@Override
	public void writePacketData(DataOutputStream out) throws IOException {
		out.writeInt(entityId);
		out.writeInt(playerId);
		out.writeByte(worldType);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleLogin(this);
	}

	@Override
	public int getPacketSize() {
		return 2 + 8;
	}
}
