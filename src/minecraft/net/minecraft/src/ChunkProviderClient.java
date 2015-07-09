package net.minecraft.src;

import java.util.Arrays;
import java.util.List;

import org.greencubes.util.collections.SquareMap;

public class ChunkProviderClient implements IChunkProvider {

	private SquareMap<Chunk> GCChunkMap = new SquareMap<Chunk>(-2000, -1000, 1000, 1000);

	private Chunk blankChunk;
	//private TLongObjectHashMap<Chunk> chunkMapping;
	private World worldObj;

	public ChunkProviderClient(World world) {
		//chunkMapping = new TLongObjectHashMap<Chunk>();
		blankChunk = new EmptyChunk(world, new byte[256 * World.chunkHeight], 0, 0);
		worldObj = world;
	}

	@Override
	public boolean chunkExists(int i, int j) {
		return true;
	}

	public void func_539_c(int i, int j) {
		Chunk chunk = provideChunk(i, j);
		if(!chunk.isEmptyChunk())
			chunk.onChunkUnload();
		GCChunkMap.remove(i, j); // chunkMapping.remove(ChunkCoordIntPair.chunkXZ2Int(i, j));
	}

	@Override
	public Chunk loadChunk(int i, int j) {
		byte abyte0[] = new byte[256 * World.chunkHeight];
		Chunk chunk = new Chunk(worldObj, abyte0, i, j);
		Arrays.fill(chunk.skylightMap.data, (byte) -1);
		GCChunkMap.put(i, j, chunk); // chunkMapping.put(ChunkCoordIntPair.chunkXZ2Int(i, j), chunk);
		chunk.isChunkLoaded = true;
		return chunk;
	}

	@Override
	public Chunk provideChunk(int i, int j) {
		Chunk chunk = GCChunkMap.get(i, j); // chunkMapping.get(ChunkCoordIntPair.chunkXZ2Int(i, j));
		if(chunk == null)
			return blankChunk;
		else
			return chunk;
	}

	@Override
	public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate) {
		return true;
	}

	@Override
	public boolean unload100OldestChunks() {
		return false;
	}

	@Override
	public boolean canSave() {
		return false;
	}

	@Override
	public void populate(IChunkProvider ichunkprovider, int i, int j) {
	}

	@Override
	public String makeString() {
		return new StringBuilder().append("MultiplayerChunkCache: ").append(GCChunkMap.size()).toString();
	}

	@Override
	public List func_40377_a(EnumCreatureType enumcreaturetype, int i, int j, int k) {
		return null;
	}

	@Override
	public ChunkPosition func_40376_a(World world, String s, int i, int j, int k) {
		return null;
	}
}
