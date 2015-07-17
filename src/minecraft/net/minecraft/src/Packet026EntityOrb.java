// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            Packet, EntityXPOrb, MathHelper, NetHandler

public class Packet026EntityOrb extends Packet {

	public int entityId;
	public int posX;
	public int posY;
	public int posZ;
	public int color;

	public Packet026EntityOrb() {
	}

	public Packet026EntityOrb(EntityOrb entityxporb) {
		entityId = entityxporb.entityId;
		posX = MathHelper.floor_double(entityxporb.posX * 32D);
		posY = MathHelper.floor_double(entityxporb.posY * 32D);
		posZ = MathHelper.floor_double(entityxporb.posZ * 32D);
		color = entityxporb.getXPValue();
	}

	@Override
	public void readPacketData(DataInputStream datainputstream) throws IOException {
		entityId = datainputstream.readInt();
		posX = datainputstream.readInt();
		posY = datainputstream.readInt();
		posZ = datainputstream.readInt();
		color = datainputstream.readInt();
	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeInt(entityId);
		dataoutputstream.writeInt(posX);
		dataoutputstream.writeInt(posY);
		dataoutputstream.writeInt(posZ);
		dataoutputstream.writeInt(color);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleXPOrb(this);
	}

	@Override
	public int getPacketSize() {
		return 20;
	}
}
