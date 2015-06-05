package net.minecraft.src;

public class abpa$3 implements Runnable {

	private final NetClientHandler netClientHandler;
	private final Packet51MapChunk packet51mapchunk;

	public abpa$3(NetClientHandler netClientHandler, Packet51MapChunk packet51mapchunk) {
		this.netClientHandler = netClientHandler;
		this.packet51mapchunk = packet51mapchunk;
	}

	@Override
	public void run() {
		netClientHandler.worldClient.invalidateBlockReceiveRegion(packet51mapchunk.xPosition, packet51mapchunk.yPosition, packet51mapchunk.zPosition, (packet51mapchunk.xPosition + packet51mapchunk.xSize) - 1, (packet51mapchunk.yPosition + packet51mapchunk.ySize) - 1, (packet51mapchunk.zPosition + packet51mapchunk.zSize) - 1);
		if(packet51mapchunk.isFull)
			netClientHandler.worldClient.setFullChunkData(packet51mapchunk.xPosition >> 4, packet51mapchunk.zPosition >> 4, packet51mapchunk.chunk);
		else
			netClientHandler.worldClient.setChunkData(packet51mapchunk.xPosition, packet51mapchunk.yPosition, packet51mapchunk.zPosition, packet51mapchunk.xSize, packet51mapchunk.ySize, packet51mapchunk.zSize, packet51mapchunk.chunk);

	}

}
