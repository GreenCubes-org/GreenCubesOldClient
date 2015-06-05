// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import gnu.trove.iterator.TShortObjectIterator;

import java.io.*;
import java.util.*;

// Referenced classes of package net.minecraft.src:
//            IChunkLoader, CompressedStreamTools, NBTTagCompound, Chunk, 
//            World, WorldInfo, NibbleArray, NBTTagList, 
//            Entity, TileEntity, NextTickListEntry, EntityList

public class ChunkLoader implements IChunkLoader {

	private File saveDir;
	private boolean createIfNecessary;

	public ChunkLoader(File file, boolean flag) {
		saveDir = file;
		createIfNecessary = flag;
	}

	private File chunkFileForXZ(int i, int j) {
		String s = (new StringBuilder()).append("c.").append(Integer.toString(i, 36)).append(".").append(Integer.toString(j, 36)).append(".dat").toString();
		String s1 = Integer.toString(i & 0x3f, 36);
		String s2 = Integer.toString(j & 0x3f, 36);
		File file = new File(saveDir, s1);
		if(!file.exists()) {
			if(createIfNecessary) {
				file.mkdir();
			} else {
				return null;
			}
		}
		file = new File(file, s2);
		if(!file.exists()) {
			if(createIfNecessary) {
				file.mkdir();
			} else {
				return null;
			}
		}
		file = new File(file, s);
		if(!file.exists() && !createIfNecessary) {
			return null;
		} else {
			return file;
		}
	}

	@Override
	public Chunk loadChunk(World world, int i, int j) throws IOException {
		File file = chunkFileForXZ(i, j);
		if(file != null && file.exists()) {
			try {
				FileInputStream fileinputstream = new FileInputStream(file);
				NBTTagCompound nbttagcompound = CompressedStreamTools.loadGzippedCompoundFromOutputStream(fileinputstream);
				if(!nbttagcompound.hasKey("Level")) {
					System.out.println((new StringBuilder()).append("Chunk file at ").append(i).append(",").append(j).append(" is missing level data, skipping").toString());
					return null;
				}
				if(!nbttagcompound.getCompoundTag("Level").hasKey("Blocks")) {
					System.out.println((new StringBuilder()).append("Chunk file at ").append(i).append(",").append(j).append(" is missing block data, skipping").toString());
					return null;
				}
				Chunk chunk = loadChunkIntoWorldFromCompound(world, nbttagcompound.getCompoundTag("Level"));
				if(!chunk.isAtLocation(i, j)) {
					System.out.println((new StringBuilder()).append("Chunk file at ").append(i).append(",").append(j).append(" is in the wrong location; relocating. (Expected ").append(i).append(", ").append(j).append(", got ").append(chunk.xPosition).append(", ").append(chunk.zPosition).append(")").toString());
					nbttagcompound.setInteger("xPos", i);
					nbttagcompound.setInteger("zPos", j);
					chunk = loadChunkIntoWorldFromCompound(world, nbttagcompound.getCompoundTag("Level"));
				}
				chunk.func_25124_i();
				return chunk;
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void saveChunk(World world, Chunk chunk) throws IOException {
		world.checkSessionLock();
		File file = chunkFileForXZ(chunk.xPosition, chunk.zPosition);
		if(file.exists()) {
			WorldInfo worldinfo = world.getWorldInfo();
			worldinfo.setSizeOnDisk(worldinfo.getSizeOnDisk() - file.length());
		}
		try {
			File file1 = new File(saveDir, "tmp_chunk.dat");
			FileOutputStream fileoutputstream = new FileOutputStream(file1);
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			nbttagcompound.setTag("Level", nbttagcompound1);
			storeChunkInCompound(chunk, world, nbttagcompound1);
			CompressedStreamTools.writeGzippedCompoundToOutputStream(nbttagcompound, fileoutputstream);
			fileoutputstream.close();
			if(file.exists()) {
				file.delete();
			}
			file1.renameTo(file);
			WorldInfo worldinfo1 = world.getWorldInfo();
			worldinfo1.setSizeOnDisk(worldinfo1.getSizeOnDisk() + file.length());
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static void storeChunkInCompound(Chunk chunk, World world, NBTTagCompound tag) {
		world.checkSessionLock();
		tag.setInteger("Version", 1);
		tag.setInteger("xPos", chunk.xPosition);
		tag.setInteger("zPos", chunk.zPosition);
		tag.setLong("LastUpdate", world.getWorldTime());
		tag.setByteArray("Blocks", chunk.blocks);
		tag.setByteArray("BlocksAdd", chunk.addData.data); // GreenCubes
		tag.setByteArray("Metadata", chunk.metadata);
		tag.setByteArray("SkyLight", chunk.skylightMap.data);
		tag.setByteArray("BlockLight", chunk.blocklightMap.data);
		tag.setByteArray("HeightMap", chunk.heightMap);
		tag.setBoolean("TerrainPopulated", chunk.isTerrainPopulated);
		chunk.hasEntities = false;
		NBTTagList entitiesList = new NBTTagList();
		label0: for(int i = 0; i < chunk.entities.length; i++) {
			Iterator iterator = chunk.entities[i].iterator();
			do {
				if(!iterator.hasNext()) {
					continue label0;
				}
				Entity entity = (Entity) iterator.next();
				chunk.hasEntities = true;
				NBTTagCompound entityTag = new NBTTagCompound();
				if(entity.addEntityID(entityTag))
					entitiesList.setTag(entityTag);
			} while(true);
		}
		tag.setTag("Entities", entitiesList);

		NBTTagList tileEntityList = new NBTTagList();
		TShortObjectIterator<TileEntity> iterator = chunk.tiles.iterator();
		while(iterator.hasNext()) {
			iterator.advance();
			NBTTagCompound tileEntityTag = new NBTTagCompound();
			iterator.value().writeToNBT(tileEntityTag);
			tileEntityList.setTag(tileEntityTag);
		}
		tag.setTag("TileEntities", tileEntityList);

		List list = world.func_41081_a(chunk, false);
		if(list != null) {
			long l = world.getWorldTime();
			NBTTagList nbttaglist2 = new NBTTagList();
			NBTTagCompound nbttagcompound3;
			for(Iterator iterator2 = list.iterator(); iterator2.hasNext(); nbttaglist2.setTag(nbttagcompound3)) {
				NextTickListEntry nextticklistentry = (NextTickListEntry) iterator2.next();
				nbttagcompound3 = new NBTTagCompound();
				nbttagcompound3.setInteger("i", nextticklistentry.blockID);
				nbttagcompound3.setInteger("x", nextticklistentry.xCoord);
				nbttagcompound3.setInteger("y", nextticklistentry.yCoord);
				nbttagcompound3.setInteger("z", nextticklistentry.zCoord);
				nbttagcompound3.setInteger("t", (int) (nextticklistentry.scheduledTime - l));
			}

			tag.setTag("TileTicks", nbttaglist2);
		}
	}

	public static Chunk loadChunkIntoWorldFromCompound(World world, NBTTagCompound tag) {
		int version = tag.getInteger("Version");
		int i = tag.getInteger("xPos");
		int j = tag.getInteger("zPos");
		Chunk chunk = new Chunk(world, i, j);
		chunk.blocks = tag.getByteArray("Blocks");
		if(version == 0) {
			chunk.metadata = new byte[chunk.blocks.length];
			NibbleArray n = new NibbleArray(tag.getByteArray("Data"), world.field_35473_a);
			if(n.isValid())
				for(int x = 0; x < 16; ++x)
					for(int z = 0; z < 16; ++z)
						for(int y = 0; y < 128; ++y)
							chunk.metadata[x << world.field_35471_b | z << world.field_35473_a | y] = (byte) (n.getNibble(x, y, z) & 0xFF);
		} else
			chunk.metadata = tag.getByteArray("Metadata");
		if(tag.hasKey("BlocksAdd"))
			chunk.addData = new NibbleArray(tag.getByteArray("BlocksAdd"), world.field_35473_a);
		else
			chunk.addData = new NibbleArray(chunk.blocks.length, world.field_35473_a);
		chunk.skylightMap = new NibbleArray(tag.getByteArray("SkyLight"), world.field_35473_a);
		chunk.blocklightMap = new NibbleArray(tag.getByteArray("BlockLight"), world.field_35473_a);
		chunk.heightMap = tag.getByteArray("HeightMap");
		chunk.isTerrainPopulated = tag.getBoolean("TerrainPopulated");
		if(chunk.heightMap == null || !chunk.skylightMap.isValid()) {
			chunk.heightMap = new byte[256];
			chunk.skylightMap = new NibbleArray(chunk.blocks.length, world.field_35473_a);
			chunk.generateSkylightMap();
		}
		if(!chunk.blocklightMap.isValid()) {
			chunk.blocklightMap = new NibbleArray(chunk.blocks.length, world.field_35473_a);
			chunk.func_1014_a();
		}
		NBTTagList nbttaglist = tag.getTagList("Entities");
		if(nbttaglist != null) {
			for(int k = 0; k < nbttaglist.size(); k++) {
				NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.get(k);
				Entity entity = EntityList.createEntityFromNBT(nbttagcompound1, world);
				chunk.hasEntities = true;
				if(entity != null)
					chunk.addEntity(entity);
			}
		}
		NBTTagList nbttaglist1 = tag.getTagList("TileEntities");
		if(nbttaglist1 != null) {
			for(int l = 0; l < nbttaglist1.size(); l++) {
				NBTTagCompound nbttagcompound2 = (NBTTagCompound) nbttaglist1.get(l);
				TileEntity tileentity = TileEntity.createAndLoadEntity(nbttagcompound2);
				if(tileentity != null)
					chunk.addTileEntity(tileentity);
			}
		}
		if(tag.hasKey("TileTicks")) {
			NBTTagList nbttaglist2 = tag.getTagList("TileTicks");
			if(nbttaglist2 != null)
				for(int i1 = 0; i1 < nbttaglist2.size(); i1++) {
					NBTTagCompound nbttagcompound3 = (NBTTagCompound) nbttaglist2.get(i1);
					world.func_41083_e(nbttagcompound3.getInteger("x"), nbttagcompound3.getInteger("y"), nbttagcompound3.getInteger("z"), nbttagcompound3.getInteger("i"), nbttagcompound3.getInteger("t"));
				}
		}
		return chunk;
	}

	@Override
	public void func_814_a() {
	}

	@Override
	public void saveExtraData() {
	}

	@Override
	public void saveExtraChunkData(World world, Chunk chunk) throws IOException {
	}
}
