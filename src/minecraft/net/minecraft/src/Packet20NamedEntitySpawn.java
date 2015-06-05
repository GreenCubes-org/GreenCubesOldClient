// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            Packet, EntityPlayer, MathHelper, InventoryPlayer, 
//            ItemStack, NetHandler

public class Packet20NamedEntitySpawn extends Packet {

	public int entityId;
	public String name;
	public String coloredName;
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public byte rotation;
	public byte pitch;

	public Packet20NamedEntitySpawn() {
	}

	public Packet20NamedEntitySpawn(EntityPlayer entity) {
		entityId = entity.entityId;
		name = entity.username;
		coloredName = entity.username;
		xPosition = (int) (entity.posX * 32.D);
		yPosition = (int) (entity.posY * 32.D);
		zPosition = (int) (entity.posZ * 32.D);
		rotation = (byte) (entity.rotationYaw * 32.0F / 45.0F);
		pitch = (byte) (entity.rotationPitch * 32.0F / 45.0F);
	}

	@Override
	public void readPacketData(DataInputStream datainputstream) throws IOException {
		entityId = datainputstream.readInt();
		datainputstream.readInt();
		name = readString(datainputstream, 32);
		xPosition = datainputstream.readInt();
		yPosition = datainputstream.readInt();
		zPosition = datainputstream.readInt();
		rotation = datainputstream.readByte();
		pitch = datainputstream.readByte();
		coloredName = readString(datainputstream, 64);
	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeInt(entityId);
		dataoutputstream.writeInt(0);
		writeString(name, dataoutputstream);
		dataoutputstream.writeInt(xPosition);
		dataoutputstream.writeInt(yPosition);
		dataoutputstream.writeInt(zPosition);
		dataoutputstream.writeByte(rotation);
		dataoutputstream.writeByte(pitch);
		writeString(coloredName, dataoutputstream);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleNamedEntitySpawn(this);
	}

	@Override
	public int getPacketSize() {
		return 28;
	}
}
