// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            Packet, NetHandler

public class Packet53BlockChange extends Packet {

	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int type;
	public int metadata;

	public Packet53BlockChange() {
		isChunkDataPacket = true;
	}

	@Override
	public void readPacketData(DataInputStream datainputstream) throws IOException {
		xPosition = datainputstream.readInt();
		yPosition = datainputstream.readShort();
		zPosition = datainputstream.readInt();
		type = datainputstream.readShort();
		metadata = datainputstream.read();
	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeInt(xPosition);
		dataoutputstream.writeShort(yPosition);
		dataoutputstream.writeInt(zPosition);
		dataoutputstream.writeShort(type);
		dataoutputstream.write(metadata);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleBlockChange(this);
	}

	@Override
	public int getPacketSize() {
		return 11;
	}
}
