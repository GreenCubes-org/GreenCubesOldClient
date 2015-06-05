package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet205StatusReport extends Packet {

	public int reportId;

	public Packet205StatusReport() {
	}

	public Packet205StatusReport(int reportId) {
		this.reportId = reportId;
	}

	@Override
	public void readPacketData(DataInputStream datainputstream) throws IOException {
		reportId = datainputstream.readInt();
	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeInt(reportId);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleStatusReport(this);
	}

	@Override
	public int getPacketSize() {
		return 4;
	}

}
