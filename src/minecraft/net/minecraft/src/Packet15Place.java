// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            Packet, NetHandler, ItemStack

public class Packet15Place extends Packet {

	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int direction;
	public int state;

	public Packet15Place() {
	}

	public Packet15Place(ItemState state) {
		this(state.blockX, state.blockY, state.blockZ, state.blockFace, state.state);
	}

	public Packet15Place(int i, int j, int k, int l, int state) {
		xPosition = i;
		yPosition = j;
		zPosition = k;
		direction = l;
		this.state = state;
	}

	@Override
	public void readPacketData(DataInputStream datainputstream) throws IOException {
		xPosition = datainputstream.readInt();
		yPosition = datainputstream.readShort();
		zPosition = datainputstream.readInt();
		direction = datainputstream.read();
		state = datainputstream.read();
	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeInt(xPosition);
		dataoutputstream.writeShort(yPosition);
		dataoutputstream.writeInt(zPosition);
		dataoutputstream.write(direction);
		dataoutputstream.write(state);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handlePlace(this);
	}

	@Override
	public int getPacketSize() {
		return 15;
	}
}
