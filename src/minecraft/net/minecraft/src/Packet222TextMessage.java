package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * GreenCubes
 * Books mod
 * @author Rena
 *
 */
public class Packet222TextMessage extends Packet {

	public static int STATE_CLOSE = 0;
	public static int STATE_EDITED = 1;

	public boolean editable;
	public byte type;
	public String message;
	public String title;
	public String subtitle;
	public byte state;

	public Packet222TextMessage() {

	}

	public Packet222TextMessage(int state) {
		this.state = (byte) state;
	}

	public Packet222TextMessage(int state, String title, String text) {
		this.state = (byte) state;
		this.title = title;
		this.message = text;
	}

	@Override
	public void readPacketData(DataInputStream input) throws IOException {
		type = input.readByte();
		editable = input.readBoolean();
		title = readString(input, 1024);
		subtitle = readString(input, 1024);
		message = readString(input, 16536);
		state = (byte) STATE_EDITED;
	}

	@Override
	public void writePacketData(DataOutputStream output) throws IOException {   
		output.writeByte(state);
		if(state == STATE_EDITED) {
			writeString(title, output);
			writeString(message, output);
		}
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleTextMessage(this);
	}

	@Override
	public int getPacketSize() {
		return state == STATE_CLOSE ? 1 : 1 + title.length() + message.length();
	}

}
