// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            Packet, NetHandler

public class Packet61DoorChange extends Packet {

	public int sfxID;
	public int auxData;
	public int posX;
	public int posY;
	public int posZ;

	public Packet61DoorChange() {
	}

	@Override
	public void readPacketData(DataInputStream datainputstream) throws IOException {
		sfxID = datainputstream.readShort();
		posX = datainputstream.readInt();
		posY = datainputstream.readUnsignedByte();
		posZ = datainputstream.readInt();
		auxData = datainputstream.readInt();
	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeShort(sfxID);
		dataoutputstream.writeInt(posX);
		dataoutputstream.writeByte(posY);
		dataoutputstream.writeInt(posZ);
		dataoutputstream.writeInt(auxData);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleAuxSFX(this);
	}

	@Override
	public int getPacketSize() {
		return 20;
	}
}
