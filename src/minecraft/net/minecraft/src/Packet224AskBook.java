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
public class Packet224AskBook extends Packet {

	public int containerSlot;
	public int bookId;

	public Packet224AskBook() {

	}

	public Packet224AskBook(int slot, int book) {
		this.containerSlot = slot;
		this.bookId = book;
	}

	@Override
	public void readPacketData(DataInputStream datainputstream) throws IOException {
	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeShort(containerSlot);
		dataoutputstream.writeInt(bookId);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
	}

	@Override
	public int getPacketSize() {
		return 8;
	}

}
