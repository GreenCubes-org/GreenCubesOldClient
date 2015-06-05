// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            Packet, NetHandler

public class Packet29DestroyEntity extends Packet {

	public int entityId;

	public Packet29DestroyEntity() {
	}

	@Override
	public void readPacketData(DataInputStream datainputstream) throws IOException {
		entityId = datainputstream.readInt();
	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeInt(entityId);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleDestroyEntity(this);
	}

	@Override
	public int getPacketSize() {
		return 4;
	}
}
