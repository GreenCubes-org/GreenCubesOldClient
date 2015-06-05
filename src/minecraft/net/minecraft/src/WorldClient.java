// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.*;

import net.minecraft.client.Minecraft;

// Referenced classes of package net.minecraft.src:
//            World, SaveHandlerMP, WorldProvider, IntHashMap, 
//            ChunkCoordinates, NetClientHandler, Entity, WorldBlockPositionType, 
//            ChunkProviderClient, Packet255KickDisconnect, WorldInfo, WorldSettings, 
//            IChunkProvider

public class WorldClient extends World {

	private LinkedList<WorldBlockPositionType> blocksToReceive = new LinkedList<WorldBlockPositionType>();
	private TIntObjectHashMap<Entity> entityHashSet = new TIntObjectHashMap<Entity>();
	private Set<Entity> entityList = new HashSet<Entity>();

	private NetClientHandler sendQueue;
	private ChunkProviderClient field_20915_C;

	public WorldClient(NetClientHandler netclienthandler, WorldSettings worldsettings, int i, int j) {
		super(new SaveHandlerMP(), "MpServer", WorldProvider.getProviderForDimension(i), worldsettings);
		sendQueue = netclienthandler;
		difficultySetting = j;
		setSpawnPoint(new ChunkCoordinates(8, 64, 8));
		mapStorage = netclienthandler.mapStorage;
	}

	@Override
	public void tick() {
		setWorldTime(getWorldTime() + 1L);
		sendQueue.processReadPackets();
		for(int j = 0; j < blocksToReceive.size(); j++) {
			WorldBlockPositionType worldblockpositiontype = blocksToReceive.get(j);
			if(--worldblockpositiontype.acceptCountdown == 0) {
				super.setBlockAndMetadata(worldblockpositiontype.posX, worldblockpositiontype.posY, worldblockpositiontype.posZ, worldblockpositiontype.blockID, worldblockpositiontype.metadata);
				super.markBlockNeedsUpdate(worldblockpositiontype.posX, worldblockpositiontype.posY, worldblockpositiontype.posZ);
				blocksToReceive.remove(j--);
			}
		}
	}

	public void invalidateBlockReceiveRegion(int i, int j, int k, int l, int i1, int j1) {
		boolean invalidateAll = false;
		for(int k1 = 0; k1 < blocksToReceive.size(); k1++) {
			WorldBlockPositionType block = blocksToReceive.get(k1);
			if(block.posX >= i && block.posY >= j && block.posZ >= k && block.posX <= l && block.posY <= i1 && block.posZ <= j1)
				blocksToReceive.remove(k1--);
		}
	}

	@Override
	protected IChunkProvider getChunkProvider() {
		field_20915_C = new ChunkProviderClient(this);
		return field_20915_C;
	}

	@Override
	public void setSpawnLocation() {
		setSpawnPoint(new ChunkCoordinates(8, 64, 8));
	}

	@Override
	protected void updateBlocksAndPlayCaveSounds() {
	}

	@Override
	public void scheduleBlockUpdate(int i, int j, int k, int l, int i1) {
	}

	@Override
	public boolean tickUpdates(boolean flag) {
		return false;
	}

	public void doPreChunk(int i, int j, boolean flag) {
		if(flag) {
			field_20915_C.loadChunk(i, j);
		} else {
			field_20915_C.func_539_c(i, j);
		}
		if(!flag) {
			markBlocksDirty(i * 16, 0, j * 16, i * 16 + 15, field_35472_c, j * 16 + 15);
		}
	}

	@Override
	public boolean entityJoinedWorld(Entity entity) {
		if(entity == Minecraft.theMinecraft.thePlayer || entity instanceof net.lahwran.wecui.obf.RenderEntity) {
			super.entityJoinedWorld(entity);
			entityList.add(entity);
			entityHashSet.put(entity.entityId, entity);
			return true;
		}
		// Только NetClientHandler может спавнить Entity в мультиплеере
		return false;
	}

	@Override
	public void setEntityDead(Entity entity) {
		removeEntity(entity);
	}

	@Override
	protected void obtainEntitySkin(Entity entity) {
		super.obtainEntitySkin(entity);
	}

	@Override
	protected void releaseEntitySkin(Entity entity) {
		super.releaseEntitySkin(entity);
	}

	public void respawnPlayer(Entity player, int newId) {
		entityHashSet.remove(player.entityId);
		player.entityId = newId;
		entityHashSet.put(newId, player);
	}

	public void removeEntity(Entity e) {
		if(e.ridingEntity != null) {
			e.ridingEntity.riddenByEntity = null;
			e.ridingEntity = null;
		}
		if(e.riddenByEntity != null) {
			e.riddenByEntity.ridingEntity = null;
			e.riddenByEntity = null;
		}
		int j1 = e.chunkCoordX;
		int l1 = e.chunkCoordZ;
		if(e.addedToChunk && chunkProvider.chunkExists(j1, l1)) {
			getChunkFromChunkCoords(j1, l1).removeEntity(e);
		}
		e.setEntityDead();
		entityList.remove(e);
		entityHashSet.remove(e.entityId);
		loadedEntityList.remove(e);
		if(e instanceof EntityPlayer) {
			playerEntities.remove(e);
		}
		releaseEntitySkin(e);
	}

	public void addEntityWithId(int i, Entity entity) {
		Entity entity1 = getEntityById(i);
		if(entity1 != null)
			removeEntity(entity1);
		entity.entityId = i;
		if(!super.entityJoinedWorld(entity))
			return;
		entityList.add(entity);
		entityHashSet.put(i, entity);
	}

	public Entity getEntityById(int i) {
		return entityHashSet.get(i);
	}

	public Entity removeEntityFromWorld(int i) {
		Entity entity = entityHashSet.remove(i);
		if(entity != null)
			removeEntity(entity);
		return entity;
	}

	@Override
	public boolean setBlockMetadata(int i, int j, int k, int l) {
		int i1 = getBlockId(i, j, k);
		int j1 = getBlockMetadata(i, j, k);
		if(super.setBlockMetadata(i, j, k, l)) {
			blocksToReceive.add(new WorldBlockPositionType(this, i, j, k, i1, j1, i1, l));
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean setBlockAndMetadata(int i, int j, int k, int l, int i1) {
		int j1 = getBlockId(i, j, k);
		int k1 = getBlockMetadata(i, j, k);
		if(super.setBlockAndMetadata(i, j, k, l, i1)) {
			blocksToReceive.add(new WorldBlockPositionType(this, i, j, k, j1, k1, l, i1));
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean setBlock(int i, int j, int k, int l) {
		int i1 = getBlockId(i, j, k);
		int j1 = getBlockMetadata(i, j, k);
		if(super.setBlock(i, j, k, l)) {
			blocksToReceive.add(new WorldBlockPositionType(this, i, j, k, i1, j1, l, 0));
			return true;
		} else {
			return false;
		}
	}

	public boolean setBlockAndMetadataAndInvalidate(int i, int j, int k, int l, int i1) {
		invalidateBlockReceiveRegion(i, j, k, i, j, k);
		if(super.setBlockAndMetadata(i, j, k, l, i1)) {
			notifyBlockChange(i, j, k, l);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void sendQuittingDisconnectingPacket() {
		sendQueue.func_28117_a(new Packet255KickDisconnect("Quitting"));
	}

	@Override
	protected void updateWeather() {
		if(worldProvider.hasNoSky) {
			return;
		}
		if(lastLightningBolt > 0) {
			lastLightningBolt--;
		}
		prevRainingStrength = rainingStrength;
		if(worldInfo.getIsRaining()) {
			rainingStrength += 0.01D;
		} else {
			rainingStrength -= 0.01D;
		}
		if(rainingStrength < 0.0F) {
			rainingStrength = 0.0F;
		}
		if(rainingStrength > 1.0F) {
			rainingStrength = 1.0F;
		}
		prevThunderingStrength = thunderingStrength;
		if(worldInfo.getIsThundering()) {
			thunderingStrength += 0.01D;
		} else {
			thunderingStrength -= 0.01D;
		}
		if(thunderingStrength < 0.0F) {
			thunderingStrength = 0.0F;
		}
		if(thunderingStrength > 1.0F) {
			thunderingStrength = 1.0F;
		}
	}
}
