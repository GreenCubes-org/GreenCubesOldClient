package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet208NotifyAnswer extends Packet {
	
	/**
	 * Possible values:<br>
	 * <b>0</b> - default notify answer.<br>
	 * <b>1</b> - open journal.
	 */
	public int answerId = 0;
	
	public Packet208NotifyAnswer() {
	}
	
	public Packet208NotifyAnswer(int answerId) {
		this.answerId = answerId;
	}

	@Override
	public void readPacketData(DataInputStream input) throws IOException {
		answerId = input.readUnsignedByte();
	}

	@Override
	public void writePacketData(DataOutputStream output) throws IOException {
		output.writeByte(answerId);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
	}

	@Override
	public int getPacketSize() {
		return 1;
	}

}
