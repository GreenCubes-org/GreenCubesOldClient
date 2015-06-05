// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            Packet, NetHandler

public class Packet14BlockDig extends Packet {

	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int face;
	public int status;
	public long timeStamp = 0;

	public Packet14BlockDig(int i, int j, int k, int l, int i1) {
		status = i;
		xPosition = j;
		yPosition = k;
		zPosition = l;
		face = i1;
	}

	public Packet14BlockDig(int i, int j, int k, int l, int i1, long ts) {
		status = i;
		xPosition = j;
		yPosition = k;
		zPosition = l;
		face = i1;
		timeStamp = ts;
	}

	@Override
	public void readPacketData(DataInputStream datainputstream) throws IOException {

	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.write(status);
		dataoutputstream.writeInt(xPosition);
		dataoutputstream.writeShort(yPosition);
		dataoutputstream.writeInt(zPosition);
		dataoutputstream.write(face);
		dataoutputstream.writeLong(timeStamp);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleBlockDig(this);
	}

	@Override
	public int getPacketSize() {
		return 19;
	}
}