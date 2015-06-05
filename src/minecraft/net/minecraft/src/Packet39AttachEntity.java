// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            Packet, NetHandler

public class Packet39AttachEntity extends Packet {

	public int entityId;
	public int vehicleEntityId;

	public Packet39AttachEntity() {
	}

	@Override
	public int getPacketSize() {
		return 8;
	}

	@Override
	public void readPacketData(DataInputStream datainputstream) throws IOException {
		entityId = datainputstream.readInt();
		vehicleEntityId = datainputstream.readInt();
	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeInt(entityId);
		dataoutputstream.writeInt(vehicleEntityId);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleAttachEntity(this);
	}
}
