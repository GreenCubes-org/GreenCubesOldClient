package net.minecraft.src;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;

import java.io.*;
import java.net.*;
import java.util.*;

import org.greencubes.compatibility.BlockDurabilityFactory;
import org.greencubes.launcher.util.Util;
import org.greencubes.nbt.NBTWorker;
import org.greencubes.util.ChatColor;

import com.jme3.math.ColorRGBA;

import net.lahwran.wecui.CuboidRegion;
import net.minecraft.client.Minecraft;

public class NetClientHandler extends NetHandler {

	public static int protocolVersion = 160;
	public static int chunksToLoad = 49;
	private long lastReport = 0;

	protected boolean disconnected = false;
	protected INetworkManager netManager;
	public String field_1209_a;
	protected final Minecraft mc;
	protected WorldClient worldClient;
	public boolean field_1210_g = false;
	public MapStorage mapStorage = new MapStorage(null);
	private Map<String, GuiSavingLevelString> playersOnline = new HashMap<String, GuiSavingLevelString>();
	public List<GuiSavingLevelString> onlineStrings = new ArrayList<GuiSavingLevelString>();
	protected Random rand = new Random();
	public String connectionStatus = "Устанавливаю соединение";
	public int chunksRecieved = 0;

	public ReplayWriter replay;
	public boolean canFly = false;
	public TIntObjectMap<String> entityStatus = new TIntObjectHashMap<String>();
	public TIntSet notificationsPending = new TIntHashSet();

	public NetClientHandler(Minecraft minecraft, String s, int i) throws UnknownHostException, IOException {
		this.mc = minecraft;
		this.mc.ingameGUI.questData.clear();
		//if(minecraft.gameSettings.replay)
		//	replay = new ReplayWriter(s + ", " + GCUtil.fileDateFormat.format(new Date()));
		Socket socket = new Socket(InetAddress.getByName(s), i);
		netManager = new NetworkManager(socket, "Client", this);
	}

	public void processReadPackets() {
		if(!disconnected)
			netManager.processReadPackets();
		netManager.wakeThreads();
	}

	// GreenCubes start
	
	@Override
	public void handlePlayerReturn(Packet044PlayerReturn packet) {
		EntityPlayerSP entityplayersp = mc.thePlayer;
		double d = packet.x;
		double d1 = packet.y;
		double d2 = packet.z;
		float f = entityplayersp.rotationYaw;
		float f1 = entityplayersp.rotationPitch;
		Packet13PlayerLookMove packet10flying = new Packet13PlayerLookMove();
		entityplayersp.ySize = 0.0F;
		entityplayersp.motionX = entityplayersp.motionY = entityplayersp.motionZ = 0.0D;
		entityplayersp.setPositionAndRotation(d, d1, d2, f, f1);
		packet10flying.xPosition = entityplayersp.posX;
		packet10flying.yPosition = entityplayersp.boundingBox.minY;
		packet10flying.zPosition = entityplayersp.posZ;
		packet10flying.stance = entityplayersp.posY;
		netManager.addToSendQueue(packet10flying);
	}
	
	@Override
	public void handleEntityHealthChange(Packet035EntityHealthChange packet) {
		Entity e = worldClient.getEntityById(packet.entityId);
		if(e != null && e instanceof EntityLiving) {
			EntityFXGC e2 = new EntityDamageFXGC(e, e.posX, e.posY + e.height + 0.2d, e.posZ, (packet.changeType.isNegative ? ChatColor.DARK_RED : ChatColor.GREEN) + String.valueOf(packet.changeValue));
			mc.effectRenderer.addEffect(e2);
		}
	}
	
	@Override
	public void handleEntityHealth(Packet036EntityHealth packet) {
		Entity e = worldClient.getEntityById(packet.entityId);
		if(e != null && e instanceof EntityLiving) {
			((EntityLiving) e).maxHealth = packet.maxHealth;
			((EntityLiving) e).setEntityHealth(packet.currentHealth);
		}
	}
	
	@Override
	public void handleTile(Packet132TileData packet) {
		try {
			NBTTagCompound tag = CompressedStreamTools.fromDeflateArray(packet.deflatedData);
			TileEntity t = worldClient.getBlockTileEntity(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"));
			if(t != null)
				t.readFromNBT(tag);
			worldClient.updateTileEntityChunkAndDoNothing(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"), t);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void handleMultiData(Packet212MultiData packet) {
		if(packet.type == Packet212MultiData.ACTIVE_QUESTS) {
			// Quests
			try {
				String data = Packet212MultiData.byteArrayToUTF16(packet.data);
				if(data == null) {
					if(packet.destination.equals("all"))
						mc.ingameGUI.questData.clear();
					else
						mc.ingameGUI.questData.remove(packet.destination);
				} else
					mc.ingameGUI.questData.put(packet.destination, data);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else if(packet.type == Packet212MultiData.ENTITY_STATUS_LINE) {
			// Entity Statuses
			try {
				String data = Packet212MultiData.byteArrayToUTF16(packet.data);
				if(data == null) {
					if(packet.destination.equals("all"))
						entityStatus.clear();
					else
						entityStatus.remove(Integer.parseInt(packet.destination));
				} else
					entityStatus.put(Integer.parseInt(packet.destination), data);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else if(packet.type == Packet212MultiData.DURABILITY) {
			try {
				BlockDurabilityFactory.loadFromTag(NBTWorker.fromGZIPArray(packet.data));
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
		} else if(packet.type == Packet212MultiData.GREETINGS) {
			ColorRGBA color;
			if(packet.data.length != 12)
				color = new ColorRGBA(1.0f, 0.6f, 0.0f, 1.0f);
			else
				color = new ColorRGBA(Float.intBitsToFloat((packet.data[0] << 24) | (packet.data[1] << 16) | (packet.data[2] << 8) | packet.data[3]),
						Float.intBitsToFloat((packet.data[4] << 24) | (packet.data[5] << 16) | (packet.data[6] << 8) | packet.data[7]),
						Float.intBitsToFloat((packet.data[8] << 24) | (packet.data[9] << 16) | (packet.data[10] << 8) | packet.data[11]),
						1.0f);
			mc.ingameGUI.setGreetingsMessage(packet.destination, color);
		}
	}

	@Override
	public void handleTextMessage(Packet222TextMessage packet) {
		mc.displayGuiScreen(new GuiBook(packet));
	}

	@Override
	public void handleGiftWindow(Packet202GiftWindow packet) {
		InventoryBasic inventorybasic = new InventoryBasic(packet.windowTitle, packet.slotsCount);
		mc.thePlayer.displayGUIGift(inventorybasic, packet.giftTitle, packet.inventoryType == 5);
		mc.thePlayer.craftingInventory.windowId = packet.windowId;
	}

	@Override
	public void handleChannel(Packet204Channel packet) {
		mc.chat.handleChannel(packet);
	}

	public void sendStatusReport(int status) {
		if(lastReport + 10000 > System.currentTimeMillis())
			return;
		lastReport = System.currentTimeMillis();
		sendStatusReportUnwait(status);
	}

	@Override
	public void handleStatusReport(Packet205StatusReport packet) {
		if(packet.reportId == 1) {
			ModLoader.unloadAllMods();
		} else if(packet.reportId == 3) {
			Minecraft.theMinecraft.noCollidePlayers = true;
		}
	}

	public void sendStatusReportUnwait(int status) {
		addToSendQueue(new Packet205StatusReport(status));
	}

	@Override
	public void handleMultiChunk(Packet55MultiChunk packet) {
		int l = 0;
		for(int i = 0; i < packet.chunksCoordinates.size(); ++i) {
			ChunkCoordIntPair coord = packet.chunksCoordinates.get(i);
			worldClient.invalidateBlockReceiveRegion(coord.chunkXPos * 16, 0, coord.chunkZPos * 16, coord.chunkXPos * 16 + 15, 127, coord.chunkZPos * 16 + 15);
			l = worldClient.addFullChunkData(coord.chunkXPos, coord.chunkZPos, packet.data, l);
		}
	}

	@Override
	public void handleSpawnParticle(Packet206SpawnEffect packet) {
		worldClient.spawnParticle(packet.effect, packet.x, packet.y, packet.z, packet.param1, packet.param2, packet.param3);
	}

	@Override
	public void handlePlaySound(Packet62PlaySound packet) {
		worldClient.playSoundEffect(packet.x / 32.0D, packet.y / 32.0D, packet.z / 32.0D, packet.soundName, packet.volume, packet.pitch);
	}

	@Override
	public void handleNotify(Packet207Notify packet) {
		GCNotify notify = new GCNotify(packet);
		mc.ingameGUI.newNotify = notify;
	}

	@Override
	public void handleDialog(Packet209Dialog packet) {
		mc.displayGuiScreen(new GuiDialog(packet));
	}

	// GreenCubes end

	@Override
	public void handleLogin(Packet1Login packet1login) {
		this.connectionStatus = "Получение информации";
		mc.playerController = new PlayerControllerMP(mc, this);
		mc.statFileWriter.readStat(StatList.joinMultiplayerStat, 1);
		worldClient = new WorldClient(this, new WorldSettings(0, 0, false, false), packet1login.worldType, 0);
		worldClient.multiplayerWorld = true;
		mc.changeWorld1(worldClient);
		mc.thePlayer.dimension = packet1login.worldType;
		mc.thePlayer.playerId = packet1login.playerId;
		mc.displayGuiScreen(new GuiDownloadTerrain(this));
		worldClient.respawnPlayer(mc.thePlayer, packet1login.entityId);
		((PlayerControllerMP) mc.playerController).func_35648_a(false);
		if(ModLoader.reportId > 0) {
			sendStatusReportUnwait(ModLoader.reportId);
		}
		try {
			addToSendQueue(new Packet212MultiData(3, null, BlockDurabilityFactory.durabilityDataHash));
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public void handlePickupSpawn(Packet21PickupSpawn packet21pickupspawn) {
		if(packet21pickupspawn.item == null)
			return;
		double d = packet21pickupspawn.xPosition / 32D;
		double d1 = packet21pickupspawn.yPosition / 32D;
		double d2 = packet21pickupspawn.zPosition / 32D;
		EntityItem entityitem = new EntityItem(worldClient, d, d1, d2, packet21pickupspawn.item);
		entityitem.motionX = packet21pickupspawn.rotation / 128D;
		entityitem.motionY = packet21pickupspawn.pitch / 128D;
		entityitem.motionZ = packet21pickupspawn.roll / 128D;
		entityitem.serverPosX = packet21pickupspawn.xPosition;
		entityitem.serverPosY = packet21pickupspawn.yPosition;
		entityitem.serverPosZ = packet21pickupspawn.zPosition;
		worldClient.addEntityWithId(packet21pickupspawn.entityId, entityitem);
	}

	@Override
	public void handleVehicleSpawn(Packet23VehicleSpawn packet23vehiclespawn) {
		double d = packet23vehiclespawn.xPosition / 32D;
		double d1 = packet23vehiclespawn.yPosition / 32D;
		double d2 = packet23vehiclespawn.zPosition / 32D;
		Entity obj = null;
		if(packet23vehiclespawn.type == 10) {
			obj = new EntityMinecart(worldClient, d, d1, d2, 0);
		}
		if(packet23vehiclespawn.type == 11) {
			obj = new EntityMinecart(worldClient, d, d1, d2, 1);
		}
		if(packet23vehiclespawn.type == 12) {
			obj = new EntityMinecart(worldClient, d, d1, d2, 2);
		}
		if(packet23vehiclespawn.type == 90) {
			obj = new EntityFishHook(worldClient, d, d1, d2);
		}
		if(packet23vehiclespawn.type == 60) {
			obj = new EntityArrow(worldClient, d, d1, d2);
		}
		if(packet23vehiclespawn.type == 61) {
			obj = new EntitySnowball(worldClient, d, d1, d2);
		}
		if(packet23vehiclespawn.type == 65) {
			obj = new EntityEnderPearl(worldClient, d, d1, d2);
		}
		if(packet23vehiclespawn.type == 72) {
			obj = new EntityEnderEye(worldClient, d, d1, d2);
		} else if(packet23vehiclespawn.type == 76) {
			obj = new EntityFireworkRocket(this.worldClient, d, d1, d2, (ItemStack) null);
		}
		if(packet23vehiclespawn.type == 63) {
			obj = new EntityFireball(worldClient, d, d1, d2, packet23vehiclespawn.field_28047_e / 8000D, packet23vehiclespawn.field_28046_f / 8000D, packet23vehiclespawn.field_28045_g / 8000D);
			packet23vehiclespawn.field_28044_i = 0;
		}
		if(packet23vehiclespawn.type == 64) {
			obj = new EntitySmallFireball(worldClient, d, d1, d2, packet23vehiclespawn.field_28047_e / 8000D, packet23vehiclespawn.field_28046_f / 8000D, packet23vehiclespawn.field_28045_g / 8000D);
			packet23vehiclespawn.field_28044_i = 0;
		}
		if(packet23vehiclespawn.type == 62) {
			obj = new EntityEgg(worldClient, d, d1, d2);
		}
		if(packet23vehiclespawn.type == 73) {
			obj = new EntityPotion(worldClient, d, d1, d2, packet23vehiclespawn.field_28044_i);
			packet23vehiclespawn.field_28044_i = 0;
		}
		if(packet23vehiclespawn.type == 1) {
			obj = new EntityBoat(worldClient, d, d1, d2);
		}
		if(packet23vehiclespawn.type == 50) {
			obj = new EntityTNTPrimed(worldClient, d, d1, d2);
		}
		if(packet23vehiclespawn.type == 51) {
			obj = new EntityEnderCrystal(worldClient, d, d1, d2);
		}
		if(packet23vehiclespawn.type == 70) {
			obj = new EntityFallingSand(worldClient, d, d1, d2, packet23vehiclespawn.field_28044_i >> World.metadataLength);
			((EntityFallingSand) obj).blockData = packet23vehiclespawn.field_28044_i & World.metadataMult;
		}
		if(packet23vehiclespawn.type == 77) {
			obj = new EntityFireworkTrace(worldClient, d, d1, d2);
		}
		if(packet23vehiclespawn.type == 101) {
			obj = new EntityArrow(worldClient, d, d1, d2);
			((EntityArrow) obj).arrowType = 2;
		}
		if(packet23vehiclespawn.type == 102) {
			obj = new EntityArrow(worldClient, d, d1, d2);
			((EntityArrow) obj).arrowType = 3;
		}
		if(obj != null) {
			obj.serverPosX = packet23vehiclespawn.xPosition;
			obj.serverPosY = packet23vehiclespawn.yPosition;
			obj.serverPosZ = packet23vehiclespawn.zPosition;
			obj.rotationYaw = 0.0F;
			obj.rotationPitch = 0.0F;
			Entity aentity[] = obj.func_40048_X();
			if(aentity != null) {
				int i = packet23vehiclespawn.entityId - obj.entityId;
				for(int j = 0; j < aentity.length; j++) {
					aentity[j].entityId += i;
					System.out.println(aentity[j].entityId);
				}

			}
			obj.entityId = packet23vehiclespawn.entityId;
			worldClient.addEntityWithId(packet23vehiclespawn.entityId, obj);
			if(packet23vehiclespawn.field_28044_i > 0) {
				if(packet23vehiclespawn.type == 60) {
					Entity entity = getEntityByID(packet23vehiclespawn.field_28044_i);
					if(entity instanceof EntityLiving) {
						((EntityArrow) obj).shootingEntity = entity;
					}
				}
				obj.setVelocity(packet23vehiclespawn.field_28047_e / 8000D, packet23vehiclespawn.field_28046_f / 8000D, packet23vehiclespawn.field_28045_g / 8000D);
			}
		}
	}

	@Override
	public void handleXPOrb(Packet26EntityExpOrb packet26entityexporb) {
		EntityXPOrb entityxporb = new EntityXPOrb(worldClient, packet26entityexporb.posX, packet26entityexporb.posY, packet26entityexporb.posZ, packet26entityexporb.count);
		entityxporb.serverPosX = packet26entityexporb.posX;
		entityxporb.serverPosY = packet26entityexporb.posY;
		entityxporb.serverPosZ = packet26entityexporb.posZ;
		entityxporb.rotationYaw = 0.0F;
		entityxporb.rotationPitch = 0.0F;
		entityxporb.entityId = packet26entityexporb.entityId;
		worldClient.addEntityWithId(packet26entityexporb.entityId, entityxporb);
	}

	@Override
	public void handleWeather(Packet71Weather packet71weather) {
		double d = packet71weather.posX / 32D;
		double d1 = packet71weather.posY / 32D;
		double d2 = packet71weather.posZ / 32D;
		EntityLightningBolt entitylightningbolt = null;
		if(packet71weather.isLightningBolt == 1) {
			entitylightningbolt = new EntityLightningBolt(worldClient, d, d1, d2);
		}
		if(entitylightningbolt != null) {
			entitylightningbolt.serverPosX = packet71weather.posX;
			entitylightningbolt.serverPosY = packet71weather.posY;
			entitylightningbolt.serverPosZ = packet71weather.posZ;
			entitylightningbolt.rotationYaw = 0.0F;
			entitylightningbolt.rotationPitch = 0.0F;
			entitylightningbolt.entityId = packet71weather.entityID;
			worldClient.addWeatherEffect(entitylightningbolt);
		}
	}

	@Override
	public void handleEntityPainting(Packet25EntityPainting packet25entitypainting) {
		EntityPainting entitypainting = new EntityPainting(worldClient, packet25entitypainting.xPosition, packet25entitypainting.yPosition, packet25entitypainting.zPosition, packet25entitypainting.direction, packet25entitypainting.title);
		worldClient.addEntityWithId(packet25entitypainting.entityId, entitypainting);
	}

	@Override
	public void handleEntityVelocity(Packet28EntityVelocity packet28entityvelocity) {
		Entity entity = getEntityByID(packet28entityvelocity.entityId);
		if(entity == null) {
			return;
		} else {
			entity.setVelocity(packet28entityvelocity.motionX / 8000D, packet28entityvelocity.motionY / 8000D, packet28entityvelocity.motionZ / 8000D);
			return;
		}
	}

	@Override
	public void handleEntityMetadata(Packet40EntityMetadata packet40entitymetadata) {
		Entity entity = getEntityByID(packet40entitymetadata.entityId);
		if(entity != null && packet40entitymetadata.getMetadata() != null) {
			entity.getDataWatcher().updateWatchedObjectsFromList(packet40entitymetadata.getMetadata());
		}
	}

	@Override
	public void handleNamedEntitySpawn(Packet20NamedEntitySpawn packet20namedentityspawn) {
		double d = packet20namedentityspawn.xPosition / 32D;
		double d1 = packet20namedentityspawn.yPosition / 32D;
		double d2 = packet20namedentityspawn.zPosition / 32D;
		float f = (packet20namedentityspawn.rotation * 360) / 256F;
		float f1 = (packet20namedentityspawn.pitch * 360) / 256F;
		EntityOtherPlayerMP entityotherplayermp = new EntityOtherPlayerMP(mc.theWorld, packet20namedentityspawn.name);
		entityotherplayermp.prevPosX = entityotherplayermp.lastTickPosX = entityotherplayermp.serverPosX = packet20namedentityspawn.xPosition;
		entityotherplayermp.prevPosY = entityotherplayermp.lastTickPosY = entityotherplayermp.serverPosY = packet20namedentityspawn.yPosition;
		entityotherplayermp.prevPosZ = entityotherplayermp.lastTickPosZ = entityotherplayermp.serverPosZ = packet20namedentityspawn.zPosition;
		entityotherplayermp.inventory.mainInventory[entityotherplayermp.inventory.currentItem] = null;
		entityotherplayermp.setSpawnPosition(d, d1, d2);
		entityotherplayermp.setPositionAndRotation(d, d1, d2, f, f1);
		entityotherplayermp.coloredName = packet20namedentityspawn.coloredName;
		entityotherplayermp.organizationId = packet20namedentityspawn.organizationId;
		if(entityotherplayermp.organizationId > 0) {
			entityotherplayermp.organizationUrl = "http://greenusercontent.net/mc/org/logo/" + entityotherplayermp.organizationId +".png";
		}
		worldClient.addEntityWithId(packet20namedentityspawn.entityId, entityotherplayermp);
	}

	@Override
	public void handleEntityTeleport(Packet34EntityTeleport packet34entityteleport) {
		Entity entity = getEntityByID(packet34entityteleport.entityId);
		if(entity == null) {
			return;
		} else {
			entity.serverPosX = packet34entityteleport.xPosition;
			entity.serverPosY = packet34entityteleport.yPosition;
			entity.serverPosZ = packet34entityteleport.zPosition;
			double d = entity.serverPosX / 32D;
			double d1 = entity.serverPosY / 32D + 0.015625D;
			double d2 = entity.serverPosZ / 32D;
			float f = (packet34entityteleport.yaw * 360) / 256F;
			float f1 = (packet34entityteleport.pitch * 360) / 256F;
			entity.setPositionAndRotation2(d, d1, d2, f, f1, 3);
			return;
		}
	}

	@Override
	public void handleEntity(Packet30Entity packet30entity) {
		Entity entity = getEntityByID(packet30entity.entityId);
		if(entity == null) {
			return;
		} else {
			entity.serverPosX += packet30entity.xPosition;
			entity.serverPosY += packet30entity.yPosition;
			entity.serverPosZ += packet30entity.zPosition;
			double d = entity.serverPosX / 32D;
			double d1 = entity.serverPosY / 32D;
			double d2 = entity.serverPosZ / 32D;
			float f = packet30entity.rotating ? (packet30entity.yaw * 360) / 256F : entity.rotationYaw;
			float f1 = packet30entity.rotating ? (packet30entity.pitch * 360) / 256F : entity.rotationPitch;
			entity.setPositionAndRotation2(d, d1, d2, f, f1, 3);
			return;
		}
	}

	@Override
	public void handleDestroyEntity(Packet29DestroyEntity packet29destroyentity) {
		Entity e = worldClient.removeEntityFromWorld(packet29destroyentity.entityId);
	}

	@Override
	public void handleFlying(Packet10Flying packet10flying) {
		EntityPlayerSP entityplayersp = mc.thePlayer;
		double d = entityplayersp.posX;
		double d1 = entityplayersp.posY;
		double d2 = entityplayersp.posZ;
		float f = entityplayersp.rotationYaw;
		float f1 = entityplayersp.rotationPitch;
		if(packet10flying.moving) {
			d = packet10flying.xPosition;
			d1 = packet10flying.yPosition;
			d2 = packet10flying.zPosition;
		}
		if(packet10flying.rotating) {
			f = packet10flying.yaw;
			f1 = packet10flying.pitch;
		}
		entityplayersp.ySize = 0.0F;
		entityplayersp.motionX = entityplayersp.motionY = entityplayersp.motionZ = 0.0D;
		entityplayersp.setPositionAndRotation(d, d1, d2, f, f1);
		packet10flying.xPosition = entityplayersp.posX;
		packet10flying.yPosition = entityplayersp.boundingBox.minY;
		packet10flying.zPosition = entityplayersp.posZ;
		packet10flying.stance = entityplayersp.posY;
		netManager.addToSendQueue(packet10flying);
		if(!field_1210_g) {
			this.connectionStatus = "Готово";
			mc.thePlayer.prevPosX = mc.thePlayer.posX;
			mc.thePlayer.prevPosY = mc.thePlayer.posY;
			mc.thePlayer.prevPosZ = mc.thePlayer.posZ;
			field_1210_g = true;
			mc.displayGuiScreen(null);
		}
	}

	@Override
	public void handlePreChunk(Packet50PreChunk packet50prechunk) {
		worldClient.doPreChunk(packet50prechunk.xPosition, packet50prechunk.yPosition, packet50prechunk.mode);
	}

	@Override
	public void handleMultiBlockChange(Packet52MultiBlockChange packet52multiblockchange) {
		Chunk chunk = worldClient.getChunkFromChunkCoords(packet52multiblockchange.xPosition, packet52multiblockchange.zPosition);
		int i = packet52multiblockchange.xPosition * 16;
		int j = packet52multiblockchange.zPosition * 16;
		boolean sended = false;
		for(int k = 0; k < packet52multiblockchange.size; k++) {
			short word0 = packet52multiblockchange.coordinateArray[k];
			int l = packet52multiblockchange.typeArray[k];
			byte byte0 = packet52multiblockchange.metadataArray[k];
			int i1 = word0 >> 12 & 0xf;
			int j1 = word0 >> 8 & 0xf;
			int k1 = word0 & 0xff;
			// GreenCubes start
			int nid = ChunkBlockMap.clearId(l);
			if(nid != l) {
				l = 0;
				byte0 = 0;
			}
			// GreenCubes end
			chunk.setBlockIDWithMetadata(i1, k1, j1, l, byte0 & 0xFF);
			worldClient.invalidateBlockReceiveRegion(i1 + i, k1, j1 + j, i1 + i, k1, j1 + j);
			worldClient.markBlocksDirty(i1 + i, k1, j1 + j, i1 + i, k1, j1 + j);
		}

	}

	@Override
	public void handleMapChunk(Packet51MapChunk packet51mapchunk) {
		worldClient.invalidateBlockReceiveRegion(packet51mapchunk.xPosition, packet51mapchunk.yPosition, packet51mapchunk.zPosition, (packet51mapchunk.xPosition + packet51mapchunk.xSize) - 1, (packet51mapchunk.yPosition + packet51mapchunk.ySize) - 1, (packet51mapchunk.zPosition + packet51mapchunk.zSize) - 1);
		if(packet51mapchunk.isFull)
			worldClient.setFullChunkData(packet51mapchunk.xPosition >> 4, packet51mapchunk.zPosition >> 4, packet51mapchunk.chunk);
		else
			worldClient.setChunkData(packet51mapchunk.xPosition, packet51mapchunk.yPosition, packet51mapchunk.zPosition, packet51mapchunk.xSize, packet51mapchunk.ySize, packet51mapchunk.zSize, packet51mapchunk.chunk);
	}

	public static boolean isRegionsOverlaps(CuboidRegion cuboid, int x1, int y1, int z1, int x2, int y2, int z2) {
		if(cuboid.x2 <= x1 || x2 <= cuboid.x1)
			return false;
		if(cuboid.z2 <= z1 || z2 <= cuboid.z1)
			return false;
		return cuboid.y2 >= y1 && cuboid.y1 <= y2;
	}

	public static boolean isBlockInRegion(CuboidRegion r, int x, int y, int z) {
		return x >= r.x1 && x <= r.x2 && y >= r.y1 && y <= r.y2 && z >= r.z1 && z <= r.z2;
	}

	@Override
	public void handleBlockChange(Packet53BlockChange packet53blockchange) {
		// GreenCubes start
		int nid = ChunkBlockMap.clearId(packet53blockchange.type);
		if(nid != packet53blockchange.type) {
			packet53blockchange.type = 0;
			packet53blockchange.metadata = 0;
		}
		// GreenCubes end
		worldClient.setBlockAndMetadataAndInvalidate(packet53blockchange.xPosition, packet53blockchange.yPosition, packet53blockchange.zPosition, packet53blockchange.type, packet53blockchange.metadata);
	}

	@Override
	public void handleKickDisconnect(Packet255KickDisconnect packet255kickdisconnect) {
		netManager.networkShutdown("disconnect.kicked", new Object[0]);
		disconnected = true;
		mc.changeWorld1(null);
		mc.displayGuiScreen(new GuiDisconnected("disconnect.disconnected", "disconnect.genericReason", new Object[]{packet255kickdisconnect.reason}));
	}

	@Override
	public void handleErrorMessage(String s, Object aobj[], String addInfo) {
		if(disconnected) {
			return;
		} else {
			disconnected = true;
			mc.changeWorld1(null);
			mc.displayGuiScreen(new GuiDisconnected("disconnect.lost", s, aobj, addInfo));
			return;
		}
	}

	public void func_28117_a(Packet packet) {
		if(disconnected) {
			return;
		} else {
			netManager.addToSendQueue(packet);
			netManager.shutdown();
			return;
		}
	}

	public void addToSendQueue(Packet packet) {
		if(disconnected) {
			return;
		} else {
			netManager.addToSendQueue(packet);
			return;
		}
	}

	@Override
	public void handleCollect(Packet22Collect packet22collect) {
		Entity entity = getEntityByID(packet22collect.collectedEntityId);
		Object obj = getEntityByID(packet22collect.collectorEntityId);
		if(obj == null) {
			obj = mc.thePlayer;
		}
		if(entity != null) {
			worldClient.playSoundAtEntity(entity, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			mc.effectRenderer.addEffect(new EntityPickupFXGC(mc.theWorld, entity, ((Entity) (obj)), -0.5F));
			worldClient.removeEntityFromWorld(packet22collect.collectedEntityId);
		}
	}

	@Override
	public void handleChat(Packet3Chat packet3chat) {
		mc.chat.handleChat(packet3chat);
	}

	@Override
	public void handleArmAnimation(Packet18Animation packet18animation) {
		Entity entity = getEntityByID(packet18animation.entityId);
		if(entity == null) {
			return;
		}
		if(packet18animation.animate == 1) {
			EntityLiving entityplayer = (EntityLiving) entity;
			entityplayer.swingItem();
		} else if(packet18animation.animate == 2) {
			entity.performHurtAnimation();
		} else if(packet18animation.animate == 3) {
			EntityPlayer entityplayer1 = (EntityPlayer) entity;
			entityplayer1.wakeUpPlayer(false, false, false);
		} else if(packet18animation.animate == 4) {
			EntityPlayer entityplayer2 = (EntityPlayer) entity;
			entityplayer2.func_6420_o();
		} else if(packet18animation.animate == 6) {
			mc.effectRenderer.addEffect(new EntityCrit2FXGC(mc.theWorld, entity));
		} else if(packet18animation.animate == 7) {
			EntityCrit2FXGC entitycrit2fx = new EntityCrit2FXGC(mc.theWorld, entity, "magicCrit");
			mc.effectRenderer.addEffect(entitycrit2fx);
		} else if(packet18animation.animate == 5) {
			if(!(entity instanceof EntityOtherPlayerMP))
				;
		}
	}

	@Override
	public void handleSleep(Packet17Sleep packet17sleep) {
		Entity entity = getEntityByID(packet17sleep.entityID);
		if(entity == null) {
			return;
		}
		if(packet17sleep.field_22046_e == 0) {
			EntityPlayer entityplayer = (EntityPlayer) entity;
			entityplayer.sleepInBedAt(packet17sleep.bedX, packet17sleep.bedY, packet17sleep.bedZ);
		}
	}

	@Override
	public void handleHandshake(final Packet2Handshake packet2handshake) {
		this.connectionStatus = "Авторизация";
		if(packet2handshake.username == null || packet2handshake.username.trim().length() == 0) {
			netManager.networkShutdown("disconnect.genericReason", new Object[]{"The server responded with an invalid server key"});
			return;
		} else if(!packet2handshake.username.equals("-")) {
			new abpa(this, netManager, packet2handshake).start();
		}
	}

	public void disconnect() {
		disconnected = true;
		netManager.wakeThreads();
		netManager.networkShutdown("disconnect.closed", new Object[0]);
	}

	@Override
	public void handleMobSpawn(Packet24MobSpawn packet24mobspawn) {
		double d = packet24mobspawn.xPosition / 32D;
		double d1 = packet24mobspawn.yPosition / 32D;
		double d2 = packet24mobspawn.zPosition / 32D;
		float f = (packet24mobspawn.yaw * 360) / 256F;
		float f1 = (packet24mobspawn.pitch * 360) / 256F;
		EntityLiving entityliving = (EntityLiving) EntityList.createEntity(packet24mobspawn.type, mc.theWorld);
		entityliving.serverPosX = packet24mobspawn.xPosition;
		entityliving.serverPosY = packet24mobspawn.yPosition;
		entityliving.serverPosZ = packet24mobspawn.zPosition;
		Entity aentity[] = entityliving.func_40048_X();
		if(aentity != null) {
			int i = packet24mobspawn.entityId - entityliving.entityId;
			for(int j = 0; j < aentity.length; j++) {
				aentity[j].entityId += i;
			}

		}
		entityliving.entityId = packet24mobspawn.entityId;
		entityliving.setSpawnPosition(d, d1, d2);
		entityliving.setPositionAndRotation(d, d1, d2, f, f1);
		entityliving.isMultiplayerEntity = true;
		List<WatchableObject> list = packet24mobspawn.getMetadata();
		if(list != null) {
			entityliving.getDataWatcher().updateWatchedObjectsFromList(list);
		}
		worldClient.addEntityWithId(packet24mobspawn.entityId, entityliving);
	}

	@Override
	public void handleUpdateTime(Packet4UpdateTime packet4updatetime) {
		mc.theWorld.setWorldTime(packet4updatetime.time);
	}

	@Override
	public void handleSpawnPosition(Packet6SpawnPosition packet6spawnposition) {
		mc.thePlayer.setPlayerSpawnCoordinate(new ChunkCoordinates(packet6spawnposition.xPosition, packet6spawnposition.yPosition, packet6spawnposition.zPosition));
		mc.theWorld.getWorldInfo().setSpawn(packet6spawnposition.xPosition, packet6spawnposition.yPosition, packet6spawnposition.zPosition);
	}

	@Override
	public void handleAttachEntity(Packet39AttachEntity packet39attachentity) {
		Object obj = getEntityByID(packet39attachentity.entityId);
		Entity entity = getEntityByID(packet39attachentity.vehicleEntityId);
		if(packet39attachentity.entityId == mc.thePlayer.entityId) {
			obj = mc.thePlayer;
		}
		if(obj == null) {
			return;
		} else {
			((Entity) (obj)).mountEntity(entity);
			return;
		}
	}

	@Override
	public void handleEntityStatus(Packet38EntityStatus packet38entitystatus) {
		Entity entity = getEntityByID(packet38entitystatus.entityId);
		if(entity != null) {
			entity.handleHealthUpdate(packet38entitystatus.entityStatus);
		}
	}

	private Entity getEntityByID(int i) {
		if(i == mc.thePlayer.entityId) {
			return mc.thePlayer;
		} else {
			return worldClient.getEntityById(i);
		}
	}

	@Override
	public void handleHealth(Packet8UpdateHealth packet8updatehealth) {
		mc.thePlayer.setHealth(packet8updatehealth.healthMP);
		mc.thePlayer.getFoodStats().setFoodLevel(packet8updatehealth.food);
		mc.thePlayer.getFoodStats().setFoodSaturationLevel(packet8updatehealth.foodSaturation);
	}

	@Override
	public void handleExperience(Packet43Experience packet43experience) {
		mc.thePlayer.setXPStats(packet43experience.experience, packet43experience.experienceTotal, packet43experience.experienceLevel);
	}

	@Override
	public void handleRespawn(Packet9Respawn packet9respawn) {
		field_1210_g = false;
		mc.displayGuiScreen(new GuiDownloadTerrain(this));
		mc.respawn(true, packet9respawn.respawnDimension, false);
		((PlayerControllerMP) mc.playerController).func_35648_a(false);
	}

	@Override
	public void handleExplosion(Packet60Explosion packet60explosion) {
		Explosion explosion = new Explosion(mc.theWorld, null, packet60explosion.explosionX, packet60explosion.explosionY, packet60explosion.explosionZ, packet60explosion.explosionSize);
		explosion.destroyedBlockPositions = packet60explosion.destroyedBlockPositions;
		explosion.doExplosionB(true);
	}

	@Override
	public void handleOpenWindow(Packet100OpenWindow packet100openwindow) {
		if(packet100openwindow.inventoryType == 0) {
			InventoryBasic inventorybasic = new InventoryBasic(packet100openwindow.windowTitle, packet100openwindow.slotsCount);
			mc.thePlayer.displayGUIChest(inventorybasic);
			mc.thePlayer.craftingInventory.windowId = packet100openwindow.windowId;
		} else if(packet100openwindow.inventoryType == 2) {
			TileEntityFurnace tileentityfurnace = new TileEntityFurnace();
			mc.thePlayer.displayGUIFurnace(tileentityfurnace);
			mc.thePlayer.craftingInventory.windowId = packet100openwindow.windowId;
		} else if(packet100openwindow.inventoryType == 3) {
			TileEntityDispenser tileentitydispenser = new TileEntityDispenser();
			tileentitydispenser.setName(packet100openwindow.windowTitle);
			mc.thePlayer.displayGUIDispenser(tileentitydispenser);
			mc.thePlayer.craftingInventory.windowId = packet100openwindow.windowId;
		} else if(packet100openwindow.inventoryType == 1) {
			EntityPlayerSP entityplayersp = mc.thePlayer;
			mc.thePlayer.displayWorkbenchGUI(MathHelper.floor_double(((EntityPlayer) (entityplayersp)).posX), MathHelper.floor_double(((EntityPlayer) (entityplayersp)).posY), MathHelper.floor_double(((EntityPlayer) (entityplayersp)).posZ));
			mc.thePlayer.craftingInventory.windowId = packet100openwindow.windowId;
		} else if(packet100openwindow.inventoryType == 4) {
			InventoryBasic inventorybasic = new InventoryBasic(packet100openwindow.windowTitle, packet100openwindow.slotsCount);
			mc.thePlayer.displayGUIBookShelf(packet100openwindow.canEdit, inventorybasic);
			mc.thePlayer.craftingInventory.windowId = packet100openwindow.windowId;
		} else if(packet100openwindow.inventoryType == 5 || packet100openwindow.inventoryType == 6) {
			return; // Gift have it's own packet
			//InventoryBasic inventorybasic = new InventoryBasic(packet100openwindow.windowTitle, packet100openwindow.slotsCount);
			//mc.thePlayer.displayGUIGift(inventorybasic, packet100openwindow.windowTitle, packet100openwindow.inventoryType == 5);
			//mc.thePlayer.craftingInventory.windowId = packet100openwindow.windowId;
		}
		if(packet100openwindow.inventoryType != 4) // Bookshelf handles this by itslef
			mc.thePlayer.craftingInventory.canEdit = packet100openwindow.canEdit;
	}

	@Override
	public void handleSetSlot(Packet103SetSlot packet103setslot) {
		if(packet103setslot.windowId == -1) {
			mc.thePlayer.inventory.setItemStack(packet103setslot.myItemStack);
		} else if(packet103setslot.windowId == 0 && packet103setslot.itemSlot >= 0 && packet103setslot.itemSlot < 51) {
			ItemStack itemstack = mc.thePlayer.inventorySlots.getSlot(packet103setslot.itemSlot).getStack();
			if(packet103setslot.myItemStack != null && (itemstack == null || itemstack.stackSize < packet103setslot.myItemStack.stackSize)) {
				packet103setslot.myItemStack.animationsToGo = 5;
			}
			mc.thePlayer.inventorySlots.putStackInSlot(packet103setslot.itemSlot, packet103setslot.myItemStack);
		} else if(packet103setslot.windowId == mc.thePlayer.craftingInventory.windowId) {
			mc.thePlayer.craftingInventory.putStackInSlot(packet103setslot.itemSlot, packet103setslot.myItemStack);
		}
	}

	@Override
	public void handleContainerTransaction(Packet106Transaction packet106transaction) {
		Container container = null;
		if(packet106transaction.windowId == 0) {
			container = mc.thePlayer.inventorySlots;
		} else if(packet106transaction.windowId == mc.thePlayer.craftingInventory.windowId) {
			container = mc.thePlayer.craftingInventory;
		}
		if(container != null) {
			if(packet106transaction.accepted) {
				container.func_20113_a(packet106transaction.shortWindowId);
			} else {
				container.func_20110_b(packet106transaction.shortWindowId);
				addToSendQueue(new Packet106Transaction(packet106transaction.windowId, packet106transaction.shortWindowId, true));
			}
		}
	}

	@Override
	public void handleWindowItems(Packet104WindowItems packet104windowitems) {
		if(packet104windowitems.windowId == 0) {
			mc.thePlayer.inventorySlots.putStacksInSlots(packet104windowitems.itemStack);
		} else if(packet104windowitems.windowId == mc.thePlayer.craftingInventory.windowId) {
			mc.thePlayer.craftingInventory.putStacksInSlots(packet104windowitems.itemStack);
		}
	}

	@Override
	public void handleUpdateSign(Packet130UpdateSign packet130updatesign) {
		if(mc.theWorld.blockExists(packet130updatesign.xPosition, packet130updatesign.yPosition, packet130updatesign.zPosition)) {
			TileEntity tileentity = mc.theWorld.getBlockTileEntity(packet130updatesign.xPosition, packet130updatesign.yPosition, packet130updatesign.zPosition);
			if(tileentity instanceof TileEntitySign) {
				TileEntitySign tileentitysign = (TileEntitySign) tileentity;
				for(int i = 0; i < 4; i++) {
					tileentitysign.signText[i] = packet130updatesign.signLines[i];
				}

				tileentitysign.onInventoryChanged();
			} else
				System.out.println("Recieved sign \"" + Util.join(packet130updatesign.signLines, "\\") + "\", but tile at " + packet130updatesign.xPosition + "," + packet130updatesign.yPosition + "," + packet130updatesign.zPosition + " is " + tileentity + " (block: " + mc.theWorld.getBlockId(packet130updatesign.xPosition, packet130updatesign.yPosition, packet130updatesign.zPosition) + ")");
		} else {
			System.out.println("Recieved sign \"" + Util.join(packet130updatesign.signLines, "\\") + "\", but block " + packet130updatesign.xPosition + "," + packet130updatesign.yPosition + "," + packet130updatesign.zPosition + " is not exists");
		}
	}

	@Override
	public void handleCraftingProgress(Packet105UpdateProgressbar packet105updateprogressbar) {
		registerPacket(packet105updateprogressbar);
		if(mc.thePlayer.craftingInventory != null && mc.thePlayer.craftingInventory.windowId == packet105updateprogressbar.windowId) {
			mc.thePlayer.craftingInventory.updateProgressBar(packet105updateprogressbar.progressBar, packet105updateprogressbar.progressBarValue);
		}
	}

	@Override
	public void handlePlayerInventory(Packet5PlayerInventory packet5playerinventory) {
		Entity entity = getEntityByID(packet5playerinventory.entityID);
		if(entity != null) {
			entity.outfitWithItem(packet5playerinventory.slot, packet5playerinventory.item);
		}
	}

	@Override
	public void handleCloseWindow(Packet101CloseWindow packet101closewindow) {
		mc.thePlayer.closeScreen();
	}

	@Override
	public void handleNotePlay(Packet54PlayNoteBlock packet54playnoteblock) {
		mc.theWorld.playNoteAt(packet54playnoteblock.xLocation, packet54playnoteblock.yLocation, packet54playnoteblock.zLocation, packet54playnoteblock.instrumentType, packet54playnoteblock.pitch);
	}

	@Override
	public void handleBedUpdate(Packet70Bed packet70bed) {
		int i = packet70bed.bedState;
		if(i >= 0 && i < Packet70Bed.bedChat.length && Packet70Bed.bedChat[i] != null) {
			mc.thePlayer.addChatMessage(Packet70Bed.bedChat[i]);
		}
		if(i == 1) {
			worldClient.getWorldInfo().setIsRaining(true);
			worldClient.setRainStrength(1.0F);
		} else if(i == 2) {
			worldClient.getWorldInfo().setIsRaining(false);
			worldClient.setRainStrength(0.0F);
		} else if(i == 3) {
			((PlayerControllerMP) mc.playerController).func_35648_a(packet70bed.gameMode == 1);
		} else if(i == 4) {
			mc.displayGuiScreen(new GuiWinGame());
		}
	}

	@Override
	public void processItemData(Packet131MapData packet131mapdata) {
		if(packet131mapdata.itemID == Item.map.shiftedIndex) {
			ItemMap.getMPMapData(packet131mapdata.uniqueID, mc.theWorld).func_28171_a(packet131mapdata.itemData);
		} else {
			System.out.println((new StringBuilder()).append("Unknown itemid: ").append(packet131mapdata.uniqueID).toString());
		}
	}

	@Override
	public void handleAuxSFX(Packet61DoorChange packet61doorchange) {
		mc.theWorld.playAuxSFX(packet61doorchange.sfxID, packet61doorchange.posX, packet61doorchange.posY, packet61doorchange.posZ, packet61doorchange.auxData);
	}

	@Override
	public void handleStatistic(Packet200Statistic packet200statistic) {
		((EntityClientPlayerMP) mc.thePlayer).func_27027_b(StatList.func_27361_a(packet200statistic.statisticId), packet200statistic.amount);
	}

	@Override
	public void handleEntityEffect(Packet41EntityEffect packet) {
		Entity entity = getEntityByID(packet.entityId);
		if(entity == null || !(entity instanceof EntityLiving)) {
			return;
		} else {
			BuffActive ba = new BuffActive(packet.id, packet.buff, (EntityLiving) entity, packet.bo, packet.timeLeft);
			((EntityLiving) entity).activeBuffs.put(packet.id, ba);
			return;
		}
	}

	@Override
	public void handleRemoveEntityEffect(Packet42RemoveEntityEffect packet42removeentityeffect) {
		Entity entity = getEntityByID(packet42removeentityeffect.entityId);
		if(entity == null || !(entity instanceof EntityLiving)) {
			return;
		} else {
			((EntityLiving) entity).activeBuffs.remove(packet42removeentityeffect.bufftId);
			return;
		}
	}

	@Override
	public boolean isServerHandler() {
		return false;
	}

	@Override
	public void handlePlayerInfo(Packet201PlayerInfo packet201playerinfo) {
		GuiSavingLevelString guisavinglevelstring = playersOnline.get(packet201playerinfo.playerName);
		if(guisavinglevelstring == null && packet201playerinfo.isConnected) {
			guisavinglevelstring = new GuiSavingLevelString(packet201playerinfo.playerName);
			playersOnline.put(packet201playerinfo.playerName, guisavinglevelstring);
			onlineStrings.add(guisavinglevelstring);
		}
		if(guisavinglevelstring != null && !packet201playerinfo.isConnected) {
			playersOnline.remove(packet201playerinfo.playerName);
			onlineStrings.remove(guisavinglevelstring);
		}
		if(packet201playerinfo.isConnected && guisavinglevelstring != null) {
			guisavinglevelstring.field_35623_b = packet201playerinfo.ping;
		}
	}

	@Override
	public void handleKeepAlive(Packet0KeepAlive packet0keepalive) {
		addToSendQueue(new Packet0KeepAlive(packet0keepalive.randomId));
	}

	static {
		try {
			if(!ModLoader.getPrivateValue(Block.class, Block.fenceJungle, "blockName").equals("tile.fencePlanksJungle"))
				throw new OutOfMemoryError("Mem2");
		} catch (Exception e) {
			try {
				if(!ModLoader.getPrivateValue(Block.class, Block.fenceJungle, "a").equals("tile.fencePlanksJungle"))
					throw new OutOfMemoryError("Mem2");
			} catch (Exception e2) {
				throw new OutOfMemoryError("Mem1");
			}
		}
	}
}
