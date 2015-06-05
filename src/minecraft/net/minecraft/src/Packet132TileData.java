package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet132TileData extends Packet {
	
	public byte[] deflatedData;
	
	public Packet132TileData() {
	}
	
	public Packet132TileData(TileEntity t) {
		NBTTagCompound tag = new NBTTagCompound();
		t.writeToNBT(tag);
		this.deflatedData = CompressedStreamTools.toDeflateArray(tag);
	}
	
	
	@Override
	public void readPacketData(DataInputStream input) throws IOException {
		int length = input.readInt();
		deflatedData = new byte[length];
		input.readFully(deflatedData);
	}
	
	@Override
	public void writePacketData(DataOutputStream output) throws IOException {
		output.writeInt(deflatedData.length);
		output.write(deflatedData);
	}

	@Override
	public void processPacket(NetHandler handler) {
		handler.handleTile(this);
	}

	@Override
	public int getPacketSize() {
		return deflatedData.length + 4;
	}
	
}
