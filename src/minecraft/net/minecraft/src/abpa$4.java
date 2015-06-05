package net.minecraft.src;

public class abpa$4 implements Runnable {

	private final NetClientHandler netClientHandler;
	private final Packet55MultiChunk packet;

	public abpa$4(NetClientHandler netClientHandler, Packet55MultiChunk packet) {
		this.netClientHandler = netClientHandler;
		this.packet = packet;
	}

	@Override
	public void run() {
		int l = 0;
		for(int i = 0; i < packet.chunksCoordinates.size(); ++i) {
			ChunkCoordIntPair coord = packet.chunksCoordinates.get(i);
			netClientHandler.worldClient.invalidateBlockReceiveRegion(coord.chunkXPos * 16, 0, coord.chunkZPos * 16, coord.chunkXPos * 16 + 15, 127, coord.chunkZPos * 16 + 15);
			l = netClientHandler.worldClient.addFullChunkData(coord.chunkXPos, coord.chunkZPos, packet.data, l);
		}
	}

}
