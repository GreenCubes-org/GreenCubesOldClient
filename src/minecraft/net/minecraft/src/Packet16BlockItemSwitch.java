// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            Packet, NetHandler

public class Packet16BlockItemSwitch extends Packet {

	public int id;

	public Packet16BlockItemSwitch() {
	}

	public Packet16BlockItemSwitch(int i) {
		id = i;
	}

	@Override
	public void readPacketData(DataInputStream datainputstream) throws IOException {
		id = datainputstream.readShort();
	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeShort(id);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleBlockItemSwitch(this);
	}

	@Override
	public int getPacketSize() {
		return 2;
	}
}
