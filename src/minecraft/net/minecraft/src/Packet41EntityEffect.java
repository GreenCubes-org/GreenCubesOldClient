// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            Packet, NetHandler

public class Packet41EntityEffect extends Packet {

	public int entityId;
	public Buff buff;
	public BuffEffect[] bo;
	public int id;
	public long timeLeft;

	public Packet41EntityEffect() {
	}

	@Override
	public void readPacketData(DataInputStream input) throws IOException {
		entityId = input.readInt();
		id = input.readInt();
		buff = Buff.byId[input.readShort()];
		timeLeft = input.readLong();
		bo = new BuffEffect[input.read()];
		for(int i = 0; i < bo.length; ++i) {
			bo[i] = new BuffEffect(BuffEffectType.byId[input.readShort()], input.readFloat());
		}
	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeInt(this.entityId);
		dataoutputstream.writeInt(id);
		dataoutputstream.writeShort(buff.id);
		dataoutputstream.writeLong(timeLeft);
		dataoutputstream.writeByte(bo.length);
		for(BuffEffect e : bo) {
			dataoutputstream.writeShort(e.type.id);
			dataoutputstream.writeFloat(e.multipler);
		}
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleEntityEffect(this);
	}

	@Override
	public int getPacketSize() {
		return 8;
	}
}
