// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            Packet, NetHandler

public class Packet130UpdateSign extends Packet {

	public int xPosition;
	public int yPosition;
	public int zPosition;
	public String signLines[];

	public Packet130UpdateSign() {
		isChunkDataPacket = true;
	}

	public Packet130UpdateSign(int i, int j, int k, String as[]) {
		isChunkDataPacket = true;
		xPosition = i;
		yPosition = j;
		zPosition = k;
		signLines = as;
	}

	@Override
	public void readPacketData(DataInputStream datainputstream) throws IOException {
		xPosition = datainputstream.readInt();
		yPosition = datainputstream.readShort();
		zPosition = datainputstream.readInt();
		signLines = new String[4];
		for(int i = 0; i < 4; i++) {
			signLines[i] = readString(datainputstream, 15);
		}

	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeInt(xPosition);
		dataoutputstream.writeShort(yPosition);
		dataoutputstream.writeInt(zPosition);
		for(int i = 0; i < 4; i++) {
			writeString(signLines[i], dataoutputstream);
		}

	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleUpdateSign(this);
	}

	@Override
	public int getPacketSize() {
		int i = 0;
		for(int j = 0; j < 4; j++) {
			i += signLines[j].length();
		}

		return i;
	}
}