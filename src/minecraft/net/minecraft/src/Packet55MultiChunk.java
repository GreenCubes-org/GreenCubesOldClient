package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class Packet55MultiChunk extends Packet {

	public byte[] data;
	public List<ChunkCoordIntPair> chunksCoordinates = new ArrayList<ChunkCoordIntPair>();
	public int dataLength;
	private byte[] delfatedData;

	@Override
	public void readPacketData(DataInputStream input) throws IOException {
		int count = input.read();
		for(int i = 0; i < count; ++i) {
			chunksCoordinates.add(new ChunkCoordIntPair(input.readInt(), input.readInt()));
		}
		dataLength = input.readInt();
		delfatedData = new byte[dataLength];
		input.readFully(delfatedData);
		data = new byte[Chunk.chunkSize * count];
		Inflater inflater = new Inflater();
		inflater.setInput(delfatedData);
		try {
			inflater.inflate(data);
		} catch (DataFormatException dataformatexception) {
			dataformatexception.printStackTrace();
			throw new IOException("Bad compressed data format");
		} finally {
			inflater.end();
		}
	}

	@Override
	public void writePacketData(DataOutputStream out) throws IOException {
		out.writeByte(chunksCoordinates.size());
		for(int i = 0; i < chunksCoordinates.size(); ++i) {
			ChunkCoordIntPair c = chunksCoordinates.get(i);
			out.writeInt(c.chunkXPos);
			out.writeInt(c.chunkZPos);
		}
		out.write(delfatedData);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleMultiChunk(this);
	}

	@Override
	public int getPacketSize() {
		return 5 + dataLength + chunksCoordinates.size() * 8;
	}

}
