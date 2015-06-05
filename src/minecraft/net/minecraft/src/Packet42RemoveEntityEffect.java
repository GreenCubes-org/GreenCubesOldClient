// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            Packet, NetHandler

public class Packet42RemoveEntityEffect extends Packet {

	public int entityId;
	public int bufftId;

	public Packet42RemoveEntityEffect() {
	}

	@Override
	public void readPacketData(DataInputStream datainputstream) throws IOException {
		entityId = datainputstream.readInt();
		bufftId = datainputstream.readInt();
	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeInt(entityId);
		dataoutputstream.writeInt(bufftId);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleRemoveEntityEffect(this);
	}

	@Override
	public int getPacketSize() {
		return 5;
	}
}
