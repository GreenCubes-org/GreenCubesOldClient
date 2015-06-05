// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            Packet, NetHandler

public class Packet5PlayerInventory extends Packet {

	public int entityID;
	public int slot;
	public ItemStack item;

	public Packet5PlayerInventory() {
	}

	@Override
	public void readPacketData(DataInputStream datainputstream) throws IOException {
		entityID = datainputstream.readInt();
		slot = datainputstream.readShort();
		item = readItemStack(datainputstream);
	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeInt(entityID);
		dataoutputstream.writeShort(slot);
		writeItemStack(item, dataoutputstream);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handlePlayerInventory(this);
	}

	@Override
	public int getPacketSize() {
		return 8;
	}
}
