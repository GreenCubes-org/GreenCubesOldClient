// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

import net.lahwran.ChatEvent;
import net.lahwran.fevents.EventManager;

// Referenced classes of package net.minecraft.src:
//            Packet, NetHandler

public class Packet3Chat extends Packet {

	public String message;
	public int channel = 1;

	public Packet3Chat() {
	}

	public Packet3Chat(String s) {
		message = s;
	}

	public Packet3Chat(String s, int channel) {
		this(s);
		this.channel = channel;
	}

	@Override
	public void readPacketData(DataInputStream input) throws IOException {
		message = readString(input, 30000);
		channel = input.readInt();
	}

	@Override
	public void writePacketData(DataOutputStream output) throws IOException {
		writeString(message, output);
		output.writeInt(channel);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		ChatEvent chatevent = new ChatEvent(this.message);
		EventManager.callEvent(chatevent);
		if(!chatevent.isCancelled())
			nethandler.handleChat(this);
	}

	@Override
	public int getPacketSize() {
		return message.length() + 6;
	}
}
