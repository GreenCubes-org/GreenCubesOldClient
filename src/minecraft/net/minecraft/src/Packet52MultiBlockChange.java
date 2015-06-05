// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            Packet, NetHandler

public class Packet52MultiBlockChange extends Packet {

	public int xPosition;
	public int yPosition;
	public int zPosition;
	public short coordinateArray[];
	public short typeArray[]; // GreenCubes
	public byte metadataArray[];
	public int size;

	public Packet52MultiBlockChange() {
		isChunkDataPacket = true;
	}

	@Override
	public void readPacketData(DataInputStream datainputstream) throws IOException {
		xPosition = datainputstream.readInt();
		yPosition = datainputstream.readShort();
		zPosition = datainputstream.readInt();
		size = datainputstream.readShort() & 0xffff;
		coordinateArray = new short[size];
		typeArray = new short[size]; // GreenCubes
		metadataArray = new byte[size];
		for(int i = 0; i < size; i++) {
			coordinateArray[i] = datainputstream.readShort();
		}
		// GreenCubes start
		for(int i = 0; i < size; i++) {
			typeArray[i] = datainputstream.readShort();
		}
		// GreenCubes end
		datainputstream.readFully(metadataArray);
	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeInt(xPosition);
		dataoutputstream.writeInt(zPosition);
		dataoutputstream.writeShort((short) size);
		for(int i = 0; i < size; i++) {
			dataoutputstream.writeShort(coordinateArray[i]);
		}
		// GreenCubes start
		for(int i = 0; i < size; i++) {
			dataoutputstream.writeByte((byte) typeArray[i]);
		}
		// GreenCubes end
		dataoutputstream.write(metadataArray);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleMultiBlockChange(this);
	}

	@Override
	public int getPacketSize() {
		return 10 + size * 5;
	}
}
