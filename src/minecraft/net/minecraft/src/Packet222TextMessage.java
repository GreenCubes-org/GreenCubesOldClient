/*
 * Copyright 2015 Eiren 'Eirenliel' Rain and GreenCubes.org
 * authors
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
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
