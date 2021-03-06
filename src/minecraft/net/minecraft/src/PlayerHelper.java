// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.awt.Desktop;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URLClassLoader;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;
import java.util.Vector;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import paulscode.sound.SoundSystem;

// Referenced classes of package net.minecraft.src:
//            Item, EntityPlayerSP, InventoryPlayer, SPCPluginManager, 
//            World, SaveHandler, ISaveHandler, Block, 
//            Settings, StringTranslate, CompressedStreamTools, NBTTagCompound, 
//            NBTTagList, EntityList, EntityLiving, SPCVersion, 
//            SPCPlugin, ItemStack, EntityItem, MathHelper, 
//            WorldInfo, DamageSource, GuiIngame, GameSettings, 
//            SoundManager, AxisAlignedBB, Entity, EntityAnimal, 
//            IMob, EntityTNTPrimed, MovingObjectPosition, Vec3D, 
//            BlockFire, WorldGenTrees, WorldGenBigTree, WorldGenerator, 
//            InventoryLargeChest, TileEntityChest, WorldSettings, LoadingScreenRenderer, 
//            GuiMainMenu, FurnaceRecipes, IInventory, WorldChunkManager, 
//            BiomeGenBase, SPCObjectHit, EntityLightningBolt, Teleporter, 
//            EntityCreature, EntityPainting, EnumArt, AchievementList, 
//            Achievement, StatBase, SPCEntityCamera, SPCCheckVersion, 
//            PlayerControllerCreative, PlayerControllerSP, FoodStats, EntityXPOrb, 
//            Enchantment, StatCollector, EntityEnderman, BlockGrass, 
//            BlockFlower, BlockMycelium, EnumOptions, Potion, 
//            PotionEffect, TileEntityMobSpawner, ItemMap, MapData, 
//            SPCEntity, SPCPoint, KeyBinding, GuiChat, 
//            BlockSapling, EntityPlayer, EntityCreeper, IProgressUpdate, 
//            WorldProvider, SpawnListEntry

public class PlayerHelper {

	public static boolean DEBUG = false;
	public Minecraft mc;
	public EntityPlayerSP ep;
	public HashMap commands;
	private HashMap entitylist;
	private HashMap entityidlist;
	private java.util.List spawnignore;
	private double prevx;
	private double prevy;
	private double prevz;
	public boolean instant;
	public boolean falldamage;
	public boolean prevfalldamage;
	public boolean waterdamage;
	public boolean prevwaterdamage;
	public boolean damage;
	public boolean prevdamage;
	public boolean prevfiredamage;
	public boolean flying;
	public boolean prevflying;
	public boolean noClip;
	public boolean prevnoclip;
	public boolean dropitems;
	public boolean isinvisible;
	public boolean mobsfrozen;
	public boolean mobdamage;
	public boolean norepair;
	public boolean infiniteitems;
	public boolean keepitems;
	public boolean instantkill;
	public boolean watermovement;
	public String sessionusername;
	public double gravity;
	public double speed;
	public int timeschedule[];
	public int lastrift;
	public float reachdistance;
	public boolean opened;
	public boolean disablecommands;
	public boolean leftbutton;
	public boolean rightbutton;
	public long lastleftcall;
	public long lastrightcall;
	public boolean ismultiplayer;
	public Vector keyboard;
	public Settings bound;
	public boolean light;
	public boolean instantplant;
	public boolean instantgrow;
	public String startup;
	public String engine;
	public boolean output;
	public boolean monsterspawn;
	public boolean animalspawn;
	private SoundSystem sound;
	public double prevspeed;
	public double prevgravity;
	public double superpunch;
	public Settings alias;
	public boolean toggledropgive;
	public int sizewidth;
	public int sizeheight;
	public boolean moveplayer;
	public boolean movecamera;
	public float freezecamyaw;
	public float freezecampitch;
	public int timespeed;
	public boolean updateson;
	public char textcolornormal;
	public char textcolorerror;
	public String textcolorrandom;
	public String lastcommand;
	public boolean limitstack;
	public float walked;
	public String flymode;
	public boolean ladderMode;
	public boolean sprinting;
	public double playerSize;
	public boolean criticalHit;
	public boolean disableHunger;
	public static PlayerHelper PH;
	public static final HashMap CMDS;
	public static Vector ITEMNAMES;
	public static SPCPluginManager PLUGIN_MANAGER;
	public static File MODDIR = new File(Minecraft.getMinecraftDir(), "mods/sppcommands/");
	public static Object HAS_STARTED_UP = null;
	public static String VALID_COLOURS = "0123456789abcdef";
	public static int MAGICNUMBER = 0xff801299;
	public static Method PLUGIN_HANDLECOMMAND;
	public static Method PLUGIN_HANDLELEFTCLICK;
	public static Method PLUGIN_HANDLERIGHTCLICK;
	public static Method PLUGIN_HANDLELEFTBUTTONDOWN;
	public static Method PLUGIN_HANDLERIGHTBUTTONDOWN;
	public static Method PLUGIN_ATUPDATE;
	public static Method PLUGIN_SETHELPER;
	public static Method PLUGIN_HANDLECUIEVENT;
	public static String ERRMSG_PARAM = "Invalid number of parameters.";
	public static String ERRMSG_PARSE = "Could not parse input.";
	public static String ERRMSG_NOTSET = "WorldEdit points not set.";
	public static String ERRMSG_OSNOTSUPPORTED = "Your operating system does not support this function.";
	public static String ERRMSG_NPCNOTFOUND = "The specified NPC was not found.";
	public static HashMap WAYPOINTS;
	public static int ITEM_MAX_DAMAGE[];
	public static InventoryPlayer INV_BEFORE_DEATH;

	public PlayerHelper(Minecraft minecraft, EntityPlayerSP entityplayersp) {
		mc = minecraft;
		ep = entityplayersp;
		PH = this;
		ismultiplayer = minecraft.isMultiplayerWorld();
		initialise();
		createDirs();
		WAYPOINTS = new HashMap();
		loadConfig();
		loadSettings();
		loadBindings();
		loadAlias();
		populateItemNames();
		if(ITEM_MAX_DAMAGE == null) {
			ITEM_MAX_DAMAGE = new int[Item.itemsList.length];
			for(int i = 0; i < ITEM_MAX_DAMAGE.length; i++) {
				ITEM_MAX_DAMAGE[i] = 1;
			}

		}
		setup();
		if(INV_BEFORE_DEATH == null) {
			INV_BEFORE_DEATH = new InventoryPlayer(null);
		}
		if(PLUGIN_MANAGER == null) {
			PLUGIN_MANAGER = new SPCPluginManager(this);
			PLUGIN_MANAGER.loadPlugins();
		}
		PLUGIN_MANAGER.callPluginMethods(PLUGIN_SETHELPER, new Object[]{this});
	}

	public static boolean addToClasspath(File file) {
		if(!file.exists()) {
			return false;
		}
		URLClassLoader urlclassloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class class1 = java.net.URLClassLoader.class;
		try {
			Method method = class1.getDeclaredMethod("addURL", new Class[]{java.net.URL.class});
			method.setAccessible(true);
			method.invoke(urlclassloader, new Object[]{file.toURI().toURL()});
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			return false;
		}
		return true;
	}

	public File getWorldDir() {
		ISaveHandler isavehandler = mc.theWorld.saveHandler;
		if(isavehandler instanceof SaveHandler) {
			return ((SaveHandler) isavehandler).getSaveDirectory();
		} else {
			return new File("");
		}
	}

	public boolean createDirs() {
		try {
			if(!MODDIR.exists()) {
				MODDIR.mkdirs();
			}
			File file = new File(MODDIR, "macros/");
			if(!file.exists()) {
				file.mkdirs();
			}
			file = new File(MODDIR, "saves/");
			if(!file.exists()) {
				file.mkdirs();
			}
		} catch (Exception exception) {
			return false;
		}
		return true;
	}

	public void initialise() {
		noClip = false;
		flying = false;
		damage = true;
		waterdamage = true;
		ep.isImmuneToFire = false;
		falldamage = true;
		speed = 1.0D;
		gravity = 1.0D;
		timeschedule = null;
		reachdistance = 4F;
		opened = false;
		prevx = -1D;
		prevy = -1D;
		prevz = -1D;
		instant = false;
		prevfalldamage = true;
		prevwaterdamage = true;
		prevdamage = true;
		prevfiredamage = true;
		prevflying = false;
		prevnoclip = false;
		dropitems = true;
		mobsfrozen = false;
		mobdamage = true;
		keepitems = false;
		instantkill = false;
		watermovement = true;
		sessionusername = "";
		keyboard = new Vector();
		light = false;
		instantgrow = false;
		instantplant = false;
		startup = "";
		engine = "JavaScript";
		output = true;
		monsterspawn = true;
		animalspawn = true;
		prevspeed = 1.0D;
		prevgravity = 1.0D;
		superpunch = -1D;
		ep.stepHeight = 0.5F;
		Block.blocksList[0] = null;
		toggledropgive = true;
		moveplayer = true;
		movecamera = true;
		mc.renderViewEntity = ep;
		freezecampitch = 0.0F;
		freezecamyaw = 0.0F;
		timespeed = 0;
		updateson = true;
		textcolornormal = 'f';
		textcolorerror = '4';
		textcolorrandom = VALID_COLOURS;
		limitstack = false;
		flymode = "dynamic";
		ladderMode = false;
		sprinting = false;
		criticalHit = false;
		disableHunger = false;
	}

	public void setup() {
		if(ismultiplayer) {
			return;
		}
		toggleLight(light);
		setItemMaxDamage();
		if(keepitems && INV_BEFORE_DEATH != null) {
			for(int i = 0; i < INV_BEFORE_DEATH.mainInventory.length; i++) {
				ep.inventory.mainInventory[i] = INV_BEFORE_DEATH.mainInventory[i];
			}

			for(int j = 0; j < INV_BEFORE_DEATH.armorInventory.length; j++) {
				ep.inventory.armorInventory[j] = INV_BEFORE_DEATH.armorInventory[j];
			}

		}
		mc.theWorld.setAllowedMobSpawns(monsterspawn, animalspawn);
	}

	public void loadAlias() {
		loadAlias(MODDIR);
	}

	public void loadAlias(File file) {
		alias = new Settings(new File(file, "alias.properties"));
	}

	public void loadBindings() {
		loadBindings(MODDIR);
	}

	public void loadBindings(File file) {
		bound = new Settings(new File(file, "bindings.properties"));
	}

	public void loadConfig() {
		loadConfig(MODDIR);
	}

	public void loadConfig(File file) {
		commands = new HashMap();
		File file1 = new File(file, "sppcommands.properties");
		Settings settings = new Settings(file1);
		Iterator iterator = CMDS.keySet().iterator();
		Settings settings1 = new Settings(file1);
		String s;
		String s1;
		for(; iterator.hasNext(); settings1.set(s, s1)) {
			s = (String) iterator.next();
			s1 = settings.getString(s, s);
			commands.put(s1, s);
		}

		if(!settings1.save("Single Player Commands - Rename command names: COMMANDNAME=NEWCOMMANDNAME")) {
			sendDebug("ERROR: A problem occurred saving the command names to file.");
		}
	}

	public void populateItemNames() {
		if(ITEMNAMES != null) {
			return;
		}
		ITEMNAMES = new Vector();
		for(int i = 0; i < Item.itemsList.length; i++) {
			if(Item.itemsList[i] == null) {
				ITEMNAMES.add(null);
			} else {
				ITEMNAMES.add(StringTranslate.getInstance().translateNamedKey(Item.itemsList[i].getItemName()).toString().trim().toLowerCase());
			}
		}

		File file = new File(MODDIR, "itemnames.properties");
		Settings settings = new Settings(file, false);
		if(file.exists() && settings.load()) {
			for(Iterator iterator = settings.keySet().iterator(); iterator.hasNext();) {
				try {
					String s = (String) iterator.next();
					int j = Integer.parseInt(s);
					ITEMNAMES.setElementAt(settings.getProperty(s), j);
				} catch (Exception exception) {
				}
			}

		}
		writeItemNamesToFile(file);
	}

	public void writeItemNamesToFile(File file) {
		Iterator iterator = ITEMNAMES.iterator();
		Settings settings = new Settings(file, false);
		int i = 0;
		Object obj = null;
		while(iterator.hasNext()) {
			String s;
			if((s = (String) iterator.next()) != null) {
				settings.set((new StringBuilder()).append("").append(i).toString(), s);
			}
			i++;
		}
		if(!settings.save("Single Player Commands - Rename item names: ITEMID=ITEMNAME")) {
			sendDebug("ERROR: Unable to write item names to file.");
		}
	}

	public void loadSettings() {
		File file = getWorldDir();
		if(!(new File(file, "spc.settings")).exists()) {
			file = MODDIR;
		}
		loadSettings(file);
	}

	public void loadSettings(File file) {
		if(ismultiplayer) {
			initialise();
			return;
		}
		Settings settings = new Settings(new File(file, "spc.settings"));
		instant = settings.getBoolean("instant", false);
		prevx = settings.getDouble("previousx", 0.0D);
		prevy = settings.getDouble("previousy", 0.0D);
		prevz = settings.getDouble("previousz", 0.0D);
		gravity = settings.getDouble("gravity", 1.0D);
		speed = settings.getDouble("speed", 1.0D);
		falldamage = settings.getBoolean("falldamage", true);
		waterdamage = settings.getBoolean("waterdamage", true);
		ep.isImmuneToFire = settings.getBoolean("firedamage", false);
		damage = settings.getBoolean("damage", true);
		lastrift = settings.getInteger("lastrift", -1);
		noClip = settings.getBoolean("noclip", false);
		flying = settings.getBoolean("fly", false);
		reachdistance = settings.getFloat("reachdistance", 4F);
		disablecommands = settings.getBoolean("disablecommands", false);
		dropitems = settings.getBoolean("harvestitems", true);
		mobsfrozen = settings.getBoolean("mobsfrozen", false);
		mobdamage = settings.getBoolean("mobdamage", true);
		norepair = settings.getBoolean("norepair", false);
		infiniteitems = settings.getBoolean("infiniteitems", false);
		keepitems = settings.getBoolean("keepitems", false);
		instantkill = settings.getBoolean("instantkill", false);
		instantgrow = settings.getBoolean("instantgrow", false);
		instantplant = settings.getBoolean("instantplant", false);
		watermovement = settings.getBoolean("watermovement", true);
		startup = settings.getString("startup", "");
		engine = settings.getString("engine", "JavaScript");
		output = settings.getBoolean("output", true);
		animalspawn = settings.getBoolean("animalspawn", true);
		monsterspawn = settings.getBoolean("monsterspawn", true);
		prevspeed = settings.getDouble("prevspeed", 1.0D);
		prevgravity = settings.getDouble("prevgravity", 1.0D);
		superpunch = settings.getDouble("superpunch", -1D);
		ep.stepHeight = settings.getFloat("stepheight", ep.stepHeight);
		toggledropgive = settings.getBoolean("toggledropgive", true);
		sizewidth = settings.getInteger("sizewidth", mc.mcCanvas.getParent().getWidth());
		sizeheight = settings.getInteger("sizeheight", mc.mcCanvas.getParent().getHeight());
		timespeed = settings.getInteger("timespeed", 0);
		updateson = settings.getBoolean("updateson", true);
		textcolornormal = settings.getCharacter("textcolornormal", 'f');
		textcolorerror = settings.getCharacter("textcolorerror", '4');
		textcolorrandom = settings.getString("textcolorrandom", VALID_COLOURS);
		flymode = settings.getString("flymode", "dynamic");
		lastcommand = settings.getString("lastcommand", "");
		limitstack = settings.getBoolean("limitstack", false);
		ladderMode = settings.getBoolean("laddermode", false);
		sprinting = settings.getBoolean("sprinting", false);
		criticalHit = settings.getBoolean("criticalhit", false);
		disableHunger = settings.getBoolean("disablehunger", false);
		if(lastrift > -1) {
			timeschedule = new int[4];
			for(int i = 0; i < timeschedule.length; i++) {
				timeschedule[i] = settings.getInteger((new StringBuilder()).append("timeschedule").append(i).toString(), 0);
			}

		}
	}

	public void saveSettings() {
		saveSettings(getWorldDir());
	}

	public void saveSettings(File file) {
		Settings settings = new Settings(new File(file, "spc.settings"));
		settings.set("instant", instant);
		settings.set("instant", instant);
		settings.set("previousx", prevx);
		settings.set("previousy", prevy);
		settings.set("previousz", prevz);
		settings.set("gravity", gravity);
		settings.set("speed", speed);
		settings.set("falldamage", falldamage);
		settings.set("waterdamage", waterdamage);
		settings.set("firedamage", ep.isImmuneToFire);
		settings.set("damage", damage);
		settings.set("lastrift", lastrift);
		settings.set("noclip", noClip);
		settings.set("fly", flying);
		settings.set("reachdistance", reachdistance);
		settings.set("disablecommands", disablecommands);
		settings.set("mobdamage", mobdamage);
		settings.set("mobsfrozen", mobsfrozen);
		settings.set("harvestitems", dropitems);
		settings.set("norepair", norepair);
		settings.set("infiniteitems", infiniteitems);
		settings.set("keepitems", keepitems);
		settings.set("instantkill", instantkill);
		settings.set("instantplant", instantplant);
		settings.set("instantgrow", instantgrow);
		settings.set("watermovement", watermovement);
		settings.set("startup", startup);
		settings.set("engine", engine);
		settings.set("output", output);
		settings.set("animalspawn", animalspawn);
		settings.set("monsterspawn", monsterspawn);
		settings.set("prevspeed", prevspeed);
		settings.set("prevgravity", prevgravity);
		settings.set("superpunch", superpunch);
		settings.set("stepheight", ep.stepHeight);
		settings.set("toggledropgive", toggledropgive);
		settings.set("sizeheight", sizeheight);
		settings.set("sizewidth", sizewidth);
		settings.set("timespeed", timespeed);
		settings.set("updateson", updateson);
		settings.set("textcolornormal", textcolornormal);
		settings.set("textcolorerror", textcolorerror);
		settings.set("textcolorrandom", textcolorrandom);
		settings.set("flymode", flymode);
		settings.set("lastcommand", lastcommand);
		settings.set("limitstack", limitstack);
		settings.set("laddermode", ladderMode);
		settings.set("sprinting", sprinting);
		settings.set("criticalhit", criticalHit);
		settings.set("disablehunger", disableHunger);
		if(timeschedule != null) {
			for(int i = 0; i < 4; i++) {
				settings.set((new StringBuilder()).append("timeschedule").append(i).toString(), timeschedule[i]);
			}

		}
		settings.save("Single Player Commands - Settings");
	}

	public void readWaypointsFromNBT(File file) {
		File file1 = new File(file, "waypoints.dat");
		if(!file1.exists()) {
			return;
		}
		WAYPOINTS.clear();
		NBTTagCompound nbttagcompound;
		try {
			nbttagcompound = CompressedStreamTools.loadGzippedCompoundFromOutputStream(new FileInputStream(file1));
		} catch (Exception exception) {
			return;
		}
		NBTTagList nbttaglist = nbttagcompound.getTagList("waypoints");
		for(int i = 0; i < nbttaglist.size(); i++) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.get(i);
			String s = nbttagcompound1.getString("Name");
			double d = nbttagcompound1.getDouble("X");
			double d1 = nbttagcompound1.getDouble("Y");
			double d2 = nbttagcompound1.getDouble("Z");
			WAYPOINTS.put(s, new double[]{d, d1, d2});
		}

	}

	public void writeWaypointsToNBT(File file) {
		if(WAYPOINTS.size() == 0) {
			return;
		}
		NBTTagList nbttaglist = new NBTTagList();
		NBTTagCompound nbttagcompound;
		for(Iterator iterator = WAYPOINTS.keySet().iterator(); iterator.hasNext(); nbttaglist.setTag(nbttagcompound)) {
			nbttagcompound = new NBTTagCompound();
			String s = (String) iterator.next();
			nbttagcompound.setString("Name", s);
			nbttagcompound.setDouble("X", ((double[]) WAYPOINTS.get(s))[0]);
			nbttagcompound.setDouble("Y", ((double[]) WAYPOINTS.get(s))[1]);
			nbttagcompound.setDouble("Z", ((double[]) WAYPOINTS.get(s))[2]);
		}

		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
		nbttagcompound1.setTag("waypoints", nbttaglist);
		File file1 = new File(file, "waypoints.dat_new");
		File file2 = new File(file, "waypoints.dat_old");
		File file3 = new File(file, "waypoints.dat");
		try {
			CompressedStreamTools.writeGzippedCompoundToOutputStream(nbttagcompound1, new FileOutputStream(file1));
			if(file2.exists()) {
				file2.delete();
			}
			file3.renameTo(file2);
			if(file3.exists()) {
				file3.delete();
			}
			file1.renameTo(file3);
			if(file1.exists()) {
				file1.delete();
			}
		} catch (Exception exception) {
		}
	}

	public Map getEntityList() {
		if(entitylist == null) {
			setEntityLists();
		}
		return entitylist;
	}

	public Map getEntityIdList() {
		if(entityidlist == null) {
			setEntityLists();
		}
		return entityidlist;
	}

	private void setEntityLists() {
		try {
			Field afield[] = (net.minecraft.src.EntityList.class).getDeclaredFields();
			for(int i = 0; i < afield.length; i++) {
				afield[i].setAccessible(true);
				if(Modifier.isStatic(afield[i].getModifiers())) {
					Object obj = afield[i].get(null);
					if(obj instanceof Map) {
						Map map = (Map) obj;
						try {
							if(map.keySet().iterator().next() instanceof String) {
								entitylist = (HashMap) map;
							} else if(map.keySet().iterator().next() instanceof Integer) {
								entityidlist = (HashMap) map;
							}
						} catch (Exception exception1) {
						}
					}
				}
			}

		} catch (Exception exception) {
			entitylist = new HashMap();
			entityidlist = new HashMap();
			return;
		}
		spawnignore = new Vector();
		Iterator iterator = entitylist.values().iterator();
		do {
			if(!iterator.hasNext()) {
				break;
			}
			Class class1 = (Class) iterator.next();
			if(!(net.minecraft.src.EntityLiving.class).isAssignableFrom(class1)) {
				spawnignore.add(class1);
			}
		} while(true);
		spawnignore.add(net.minecraft.src.EntityLiving.class);
	}

	public String getEntityName(Integer integer) {
		Class class1 = (Class) getEntityIdList().get(integer);
		if(class1 == null || spawnignore.contains(class1)) {
			return null;
		}
		for(Iterator iterator = getEntityList().keySet().iterator(); iterator.hasNext();) {
			String s = (String) iterator.next();
			if(getEntityList().get(s) == class1) {
				return s;
			}
		}

		return null;
	}

	public Class getEntity(String s) {
		Class class1 = null;
		Iterator iterator = getEntityList().keySet().iterator();
		do {
			if(!iterator.hasNext()) {
				break;
			}
			String s1 = (String) iterator.next();
			if(!s.equalsIgnoreCase(s1)) {
				continue;
			}
			class1 = (Class) getEntityList().get(s1);
			break;
		} while(true);
		return class1;
	}

	public void setCurrentPosition() {
		prevx = ep.posX;
		prevy = ep.posY;
		prevz = ep.posZ;
	}

	public void processCommand(String s) {
		if(ismultiplayer || s == null || s.equalsIgnoreCase("")) {
			return;
		}
		if(disablecommands) {
			sendMessage("Nice try, but you disabled your commands in this world.");
			return;
		}
		try {
			boolean flag = false;
			boolean flag1 = false;
			boolean flag2 = output;
			String s1 = convertInput(s);
			sendDebug(s1);
			if(s1.startsWith("@")) {
				s1 = s1.substring(1);
				flag1 = true;
				output = false;
			}
			if(s1.startsWith("/")) {
				s1 = s1.substring(1);
				flag = true;
			}
			boolean flag3 = false;
			String as[] = s1.trim().split(" ");
			as[0] = (String) commands.get(as[0].toLowerCase());
			if(as[0] == null || !as[0].equalsIgnoreCase("repeat")) {
				lastcommand = s;
			}
			if(as[0] != null) {
				processCommands(s1);
				flag3 = true;
			}
			as = s1.trim().split(" ");
			if(flag) {
				as[0] = (new StringBuilder()).append("/").append(as[0]).toString();
			}
			flag3 = PLUGIN_MANAGER.handleCommand(as) ? true : flag3;
			flag3 = PLUGIN_MANAGER.callPluginMethods(PLUGIN_HANDLECOMMAND, new Object[]{as}) ? true : flag3;
			if(!flag3) {
				sendError((new StringBuilder()).append("Command not found - ").append(as[0]).toString());
			}
			if(flag1) {
				output = flag2;
				saveSettings();
			}
		} catch (Throwable throwable) {
			sendError((new StringBuilder()).append("UNHANDLED COMMANDS EXCEPTION - ").append(throwable.getMessage()).toString());
			throwable.printStackTrace();
			PrintWriter printwriter = null;
			try {
				File file = new File(MODDIR, (new StringBuilder()).append("spcexception-").append(System.currentTimeMillis()).append(".log").toString());
				printwriter = new PrintWriter(new FileOutputStream(file));
				throwable.printStackTrace(printwriter);
				printwriter.println();
				printwriter.println((new StringBuilder()).append("Command = ").append(s).toString());
				printwriter.println((new StringBuilder()).append("Version = ").append(EntityPlayerSP.SPCVERSION.getVersion()).toString());
				printwriter.println((new StringBuilder()).append("Time = ").append(System.currentTimeMillis()).toString());
				String s2 = "";
				for(Iterator iterator = PLUGIN_MANAGER.getPlugins().iterator(); iterator.hasNext();) {
					SPCPlugin spcplugin = (SPCPlugin) iterator.next();
					s2 = (new StringBuilder()).append(s2).append(spcplugin.getName()).append(" | ").append(spcplugin.getVersion()).append("\n").toString();
				}

				printwriter.println((new StringBuilder()).append("---Plugins---\n").append(s2).toString());
				sendError((new StringBuilder()).append("Error log written at: ").append(file.getAbsolutePath()).toString());
			} catch (Exception exception) {
				sendError("Could not write error log.");
			} finally {
				if(printwriter != null) {
					printwriter.close();
				}
			}
		}
	}

	public boolean isItemEqual(ItemStack itemstack, ItemStack itemstack1) {
		if(itemstack == null || itemstack1 == null || itemstack.getItem() == null || itemstack1.getItem() == null) {
			return false;
		}
		try {
			if(itemstack.getItem().getItemName().equals(itemstack1.getItem().getItemName())) {
				return itemstack.isItemEqual(itemstack1);
			}
		} catch (Exception exception) {
		}
		return false;
	}

	public boolean addItemStackToInv(ItemStack itemstack) {
		if(itemstack == null) {
			return true;
		}
		if(limitstack) {
			return ep.inventory.addItemStackToInventory(itemstack);
		}
		int i = -1;
		for(int j = 0; j < ep.inventory.mainInventory.length; j++) {
			ItemStack itemstack1 = ep.inventory.mainInventory[j];
			if(itemstack1 != null) {
				if(isItemEqual(itemstack1, itemstack)) {
					itemstack1.stackSize += itemstack.stackSize;
					return true;
				}
				continue;
			}
			if(i == -1) {
				i = j;
			}
		}

		if(i != -1) {
			ep.inventory.mainInventory[i] = itemstack;
			return true;
		} else {
			return false;
		}
	}

	public void givePlayerItem(ItemStack itemstack) {
		givePlayerItem(itemstack, toggledropgive);
	}

	public void givePlayerItem(ItemStack itemstack, boolean flag) {
		if(itemstack == null) {
			return;
		}
		if(flag && addItemStackToInv(itemstack)) {
			return;
		}
		EntityItem entityitem = new EntityItem(mc.theWorld, ep.posX, (ep.posY - 0.30000001192092896D) + ep.getEyeHeight(), ep.posZ, itemstack);
		float f = 0.3F;
		entityitem.motionX = -MathHelper.sin((ep.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((ep.rotationPitch / 180F) * 3.141593F) * f;
		entityitem.motionZ = MathHelper.cos((ep.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((ep.rotationPitch / 180F) * 3.141593F) * f;
		entityitem.motionY = -MathHelper.sin((ep.rotationPitch / 180F) * 3.141593F) * f + 0.1F;
		f = 0.02F;
		Random random = new Random();
		float f1 = random.nextFloat() * 3.141593F * 2.0F;
		f *= random.nextFloat();
		entityitem.motionX += Math.cos(f1) * f;
		entityitem.motionY += (random.nextFloat() - random.nextFloat()) * 0.1F;
		entityitem.motionZ += Math.sin(f1) * f;
		if(!dropitems) {
			entityitem.age = -6000;
		}
		entityitem.delayBeforeCanPickup = 40;
		mc.theWorld.entityJoinedWorld(entityitem);
	}

	public void givePlayerItemNaturally(ItemStack itemstack) {
		givePlayerItem(itemstack, false);
	}

	public void processCommands(String s) throws Exception {
		String as[] = s.trim().split(" ");
		as[0] = (String) commands.get(as[0].toLowerCase());
		if(as[0] == null) {
			sendError("Command does not exist.");
			return;
		}
		if(as[0].equalsIgnoreCase("item") || as[0].equalsIgnoreCase("i") || as[0].equalsIgnoreCase("give")) {
			if(as.length < 2) {
				sendError(ERRMSG_PARAM);
				return;
			}
			String as1[] = new String[as.length - 1];
			for(int k8 = 0; k8 < as1.length; k8++) {
				as1[k8] = as[k8 + 1];
			}

			ItemStack itemstack1 = getItemStack(as1);
			if(itemstack1 != null) {
				givePlayerItem(itemstack1);
			} else {
				sendError("Could not find specified item.");
			}
		} else if(as[0].equalsIgnoreCase("tele") || as[0].equalsIgnoreCase("t")) {
			if(as.length < 4) {
				sendError(ERRMSG_PARAM);
				return;
			}
			try {
				double d = Double.parseDouble(as[1]);
				double d11 = Double.parseDouble(as[2]);
				double d19 = Double.parseDouble(as[3]);
				setCurrentPosition();
				ep.setPosition(d, d11, d19);
			} catch (Exception exception) {
				sendError(ERRMSG_PARSE);
			}
		} else if(as[0].equalsIgnoreCase("pos") || as[0].equalsIgnoreCase("p")) {
			sendMessage((new StringBuilder()).append("Current Position: ").append(positionAsString()).toString());
		} else if(as[0].equalsIgnoreCase("setspawn")) {
			int i = mc.theWorld.worldInfo.getSpawnX();
			int l8 = mc.theWorld.worldInfo.getSpawnY();
			int j15 = mc.theWorld.worldInfo.getSpawnZ();
			if(as.length == 1) {
				i = (int) ep.posX;
				l8 = (int) ep.posY;
				j15 = (int) ep.posZ;
			} else if(as.length == 4) {
				try {
					i = (int) Double.parseDouble(as[1]);
					l8 = (int) Double.parseDouble(as[2]);
					j15 = (int) Double.parseDouble(as[3]);
				} catch (Exception exception32) {
					sendError(ERRMSG_PARSE);
					return;
				}
			} else {
				sendError(ERRMSG_PARAM);
				return;
			}
			mc.theWorld.worldInfo.setSpawn(i, l8, j15);
			sendMessage((new StringBuilder()).append("Spawn set at (").append(i).append(",").append(l8).append(",").append(j15).append(")").toString());
		} else if(as[0].equalsIgnoreCase("set") || as[0].equalsIgnoreCase("s")) {
			if(as.length < 2) {
				sendError(ERRMSG_PARAM);
				return;
			}
			String s1 = s.substring(4).trim();
			WAYPOINTS.put(s1, new double[]{ep.posX, ep.posY, ep.posZ});
			writeWaypointsToNBT(getWorldDir());
			sendMessage((new StringBuilder()).append("Waypoint \"").append(s1).append("\" set at: ").append(positionAsString()).toString());
		} else if(as[0].equalsIgnoreCase("goto")) {
			if(as.length < 2) {
				sendError(ERRMSG_PARAM);
				return;
			}
			String s2 = s.substring(5).trim();
			if(WAYPOINTS.containsKey(s2)) {
				double ad[] = (double[]) WAYPOINTS.get(s2);
				String s34 = positionAsString();
				setCurrentPosition();
				ep.setPosition(ad[0], ad[1], ad[2]);
				sendMessage((new StringBuilder()).append("Moved from: ").append(s34).append(" to: ").append(positionAsString()).toString());
			} else {
				sendError("Could not find specified waypoint.");
			}
		} else if(as[0].equalsIgnoreCase("rem")) {
			if(as.length < 2) {
				sendError(ERRMSG_PARAM);
				return;
			}
			String s3 = s.substring(4).trim();
			if(WAYPOINTS.containsKey(s3)) {
				WAYPOINTS.remove(s3);
				writeWaypointsToNBT(getWorldDir());
				sendMessage((new StringBuilder()).append("Waypoint \"").append(s3).append("\" removed.").toString());
			} else {
				sendError("Could not find specified waypoint.");
			}
		} else if(as[0].equalsIgnoreCase("home")) {
			setCurrentPosition();
			ep.setLocationAndAngles(mc.theWorld.worldInfo.getSpawnX() + 0.5D, mc.theWorld.worldInfo.getSpawnY() + 1.0D, mc.theWorld.worldInfo.getSpawnZ() + 0.5D, 0.0F, 0.0F);
			ep.preparePlayerToSpawn();
		} else if(as[0].equalsIgnoreCase("kill")) {
			ep.attackEntityFrom(DamageSource.causePlayerDamage(ep), 0x7fffffff);
		} else if(as[0].equalsIgnoreCase("listwaypoints") || as[0].equalsIgnoreCase("l")) {
			int j = WAYPOINTS.size();
			if(j == 0) {
				sendMessage("No waypoints found.");
				return;
			}
			Iterator iterator2 = WAYPOINTS.keySet().iterator();
			String s35;
			for(s35 = ""; iterator2.hasNext(); s35 = (new StringBuilder()).append(s35).append((String) iterator2.next()).append(", ").toString()) {
			}
			sendMessage((new StringBuilder()).append("Waypoints (").append(j).append("): ").toString());
			sendMessage(s35);
		} else if(as[0].equalsIgnoreCase("clear")) {
			mc.ingameGUI.clearChatMessages();
		} else if(as[0].equalsIgnoreCase("time")) {
			if(s.trim().length() == 4 || s.trim().length() == 8 && s.trim().startsWith("time get")) {
				printCurrentTime();
			}
			if(as.length > 2) {
				byte byte0 = -1;
				if(as[1].equalsIgnoreCase("speed")) {
					boolean flag2 = false;
					try {
						int i9 = Integer.parseInt(as[2]);
						timespeed = i9 - 1;
						sendMessage((new StringBuilder()).append("The speed of time set to ").append(as[2]).append("x normal.").toString());
					} catch (Exception exception21) {
						if(as[2].equalsIgnoreCase("reset")) {
							timespeed = 0;
							sendMessage("The speed of time was reset to default.");
						}
					}
					saveSettings();
					return;
				}
				if(as[2].equalsIgnoreCase("day")) {
					byte0 = 2;
				} else if(as[2].equalsIgnoreCase("hour")) {
					byte0 = 1;
				} else if(as[2].equalsIgnoreCase("minute")) {
					byte0 = 0;
				}
				if(byte0 == -1) {
					sendError((new StringBuilder()).append("Invalid time command: ").append(as[2]).toString());
					return;
				}
				int j9 = getTime()[2];
				int k15 = getTime()[0];
				int i22 = getTime()[1];
				if(as[1].equalsIgnoreCase("get")) {
					sendMessage((new StringBuilder()).append(as[2].toUpperCase()).append(": ").append(getTime()[byte0]).toString());
				} else if(as[1].equalsIgnoreCase("set") && as.length > 3) {
					int j26 = -1;
					try {
						j26 = Integer.parseInt(as[3]);
					} catch (Exception exception47) {
						sendError(ERRMSG_PARSE);
						return;
					}
					if(j26 < 0) {
						return;
					}
					if(byte0 == 0) {
						j26 = (int) (((j26 % 60) / 60D) * 1000D);
						mc.theWorld.worldInfo.setWorldTime(j9 * 24000 + i22 * 1000 + j26);
					} else if(byte0 == 1) {
						j26 = (j26 % 24) * 1000;
						mc.theWorld.worldInfo.setWorldTime((long) ((j9 * 24000 + j26) + (k15 / 60D) * 1000D));
					} else if(byte0 == 2) {
						j26 *= 24000;
						mc.theWorld.worldInfo.setWorldTime((long) ((j26 + i22 * 1000) + (k15 / 60D) * 1000D));
					} else {
						sendError((new StringBuilder()).append("Invalid time command: ").append(as[2]).toString());
						return;
					}
					printCurrentTime();
				}
			} else if(as.length == 2) {
				int k = getTime()[2];
				if(as[1].equalsIgnoreCase("day")) {
					mc.theWorld.worldInfo.setWorldTime((k + 1) * 24000);
					printCurrentTime();
				} else if(as[1].equalsIgnoreCase("night")) {
					mc.theWorld.worldInfo.setWorldTime(k * 24000 + 13000);
					printCurrentTime();
				}
			}
		} else if(as[0].equalsIgnoreCase("health")) {
			if(as.length < 2) {
				sendError(ERRMSG_PARAM);
				return;
			}
			if(as[1].equalsIgnoreCase("max")) {
				ep.health = 20;
			} else if(as[1].equalsIgnoreCase("min")) {
				ep.health = 1;
			} else if(as[1].equalsIgnoreCase("infinite") || as[1].equalsIgnoreCase("inf")) {
				ep.health = 32767;
			} else {
				sendError((new StringBuilder()).append("Invalid health command: ").append(as[1]).toString());
				return;
			}
			sendMessage((new StringBuilder()).append("Health set at ").append(as[1]).append(" (").append(ep.health).append(")").toString());
		} else if(as[0].equalsIgnoreCase("heal")) {
			if(as.length < 2) {
				sendError(ERRMSG_PARAM);
				return;
			}
			try {
				ep.heal(Integer.parseInt(as[1]));
			} catch (Exception exception1) {
				sendError(ERRMSG_PARSE);
				return;
			}
			sendMessage("Player healed");
		} else if(as[0].equalsIgnoreCase("spawnstack")) {
			if(as.length < 2) {
				sendError(ERRMSG_PARAM);
				return;
			}
			if(as[1].equalsIgnoreCase("list")) {
				String s4 = "";
				Iterator iterator3 = getEntityIdList().keySet().iterator();
				do {
					if(!iterator3.hasNext()) {
						break;
					}
					Integer integer = (Integer) iterator3.next();
					String s43 = getEntityName(integer);
					if(s43 != null) {
						s4 = (new StringBuilder()).append(s4).append(s43).append(" (").append(integer).append("), ").toString();
					}
				} while(true);
				sendMessage(s4);
				return;
			}
			Object obj = null;
			Object obj1 = null;
			int l15 = 1;
			String s44 = "";
			do {
				EntityLiving entityliving;
				try {
					Class class4 = null;
					if(as[l15].equalsIgnoreCase("random") || as[l15].equalsIgnoreCase("r")) {
						Object aobj1[] = getEntityIdList().values().toArray();
						class4 = (Class) aobj1[(new Random()).nextInt(aobj1.length)];
					} else {
						try {
							Integer integer3 = new Integer(as[l15]);
							class4 = (Class) getEntityIdList().get(integer3);
						} catch (Exception exception48) {
							class4 = getEntity(as[l15]);
						}
					}
					if(class4 == null || spawnignore.contains(class4)) {
						if(class4 == null) {
							s44 = (new StringBuilder()).append(s44).append(as[l15]).append(", ").toString();
						}
						continue;
					}
					entityliving = (EntityLiving) class4.getConstructor(new Class[]{net.minecraft.src.World.class}).newInstance(new Object[]{mc.theWorld});
				} catch (Exception exception44) {
					continue;
				}
				entityliving.setLocationAndAngles(ep.posX + 3D, ep.posY, ep.posZ + 3D, ep.rotationYaw, 0.0F);
				mc.theWorld.entityJoinedWorld(entityliving);
				if(obj1 != null) {
					entityliving.mountEntity(((Entity) (obj1)));
				}
				obj1 = entityliving;
			} while(l15++ < as.length - 1);
			if(!s44.equalsIgnoreCase("")) {
				sendError((new StringBuilder()).append("Could not find: ").append(s44).toString());
			}
		} else if(as[0].equalsIgnoreCase("spawn")) {
			if(as.length < 2) {
				sendError(ERRMSG_PARAM);
				return;
			}
			if(as[1].equalsIgnoreCase("list")) {
				String s5 = "";
				Iterator iterator4 = getEntityIdList().keySet().iterator();
				do {
					if(!iterator4.hasNext()) {
						break;
					}
					Integer integer1 = (Integer) iterator4.next();
					String s45 = getEntityName(integer1);
					if(s45 != null) {
						s5 = (new StringBuilder()).append(s5).append(s45).append(" (").append(integer1).append("), ").toString();
					}
				} while(true);
				sendMessage(s5);
				return;
			}
			Class class1 = null;
			int k9 = 1;
			if(as.length > 2) {
				try {
					k9 = Integer.parseInt(as[2]);
				} catch (Exception exception22) {
					k9 = 1;
				}
			}
			try {
				Integer integer2 = new Integer(as[1]);
				class1 = (Class) getEntityIdList().get(integer2);
			} catch (Exception exception23) {
				class1 = getEntity(as[1]);
			}
			if(spawnignore.contains(class1) || class1 == null && !as[1].equalsIgnoreCase("random") && !as[1].equalsIgnoreCase("r")) {
				sendError(ERRMSG_NPCNOTFOUND);
				return;
			}
			try {
				Random random = new Random();
				for(int j22 = 0; j22 < k9; j22++) {
					if(as[1].equalsIgnoreCase("random") || as[1].equalsIgnoreCase("r")) {
						Object aobj[] = getEntityIdList().values().toArray();
						int j29 = (new Random()).nextInt(aobj.length);
						class1 = (Class) aobj[j29];
					}
					if(class1 == null || spawnignore.contains(class1)) {
						j22--;
					} else {
						EntityLiving entityliving1 = (EntityLiving) class1.getConstructor(new Class[]{net.minecraft.src.World.class}).newInstance(new Object[]{mc.theWorld});
						entityliving1.setLocationAndAngles(ep.posX + random.nextInt(5), ep.posY, ep.posZ + random.nextInt(5), ep.rotationYaw, 0.0F);
						mc.theWorld.entityJoinedWorld(entityliving1);
					}
				}

			} catch (Exception exception24) {
				return;
			}
		} else if(as[0].equalsIgnoreCase("music")) {
			if(as.length < 2) {
				playRandomMusic();
			} else if(as[1].equalsIgnoreCase("next") || as[1].equalsIgnoreCase("skip")) {
				if(getSoundSystem().playing("BgMusic")) {
					getSoundSystem().stop("BgMusic");
				}
				if(getSoundSystem().playing("streaming")) {
					getSoundSystem().stop("streaming");
				}
				playRandomMusic();
			} else if(as[1].equalsIgnoreCase("pause")) {
				if(getSoundSystem().playing("BgMusic")) {
					getSoundSystem().pause("BgMusic");
				}
				if(getSoundSystem().playing("streaming")) {
					getSoundSystem().pause("streaming");
				}
			} else if(as[1].equalsIgnoreCase("play")) {
				if(!getSoundSystem().playing("BgMusic")) {
					getSoundSystem().play("BgMusic");
				}
				if(!getSoundSystem().playing("BgMusic")) {
					playRandomMusic();
				}
			} else if(as[1].equalsIgnoreCase("stop")) {
				if(getSoundSystem().playing("BgMusic")) {
					getSoundSystem().stop("BgMusic");
				}
				if(getSoundSystem().playing("streaming")) {
					getSoundSystem().stop("streaming");
				}
			} else {
				try {
					int l = Integer.parseInt(as[1]);
					if(l < 0) {
						l = 0;
					} else if(l > 100) {
						l = 100;
					}
					mc.gameSettings.musicVolume = l / 100F;
					mc.sndManager.onSoundOptionsChanged();
				} catch (Exception exception2) {
					sendError(ERRMSG_PARSE);
					return;
				}
			}
		} else if(as[0].equalsIgnoreCase("difficulty") || as[0].equalsIgnoreCase("diff")) {
			if(as.length < 2) {
				sendError(ERRMSG_PARAM);
				return;
			}
			try {
				int i1 = Integer.parseInt(as[1]);
				if(i1 < 0) {
					i1 = 0;
				} else if(i1 > 3) {
					i1 = 3;
				}
				mc.gameSettings.difficulty = i1;
			} catch (Exception exception3) {
				sendError(ERRMSG_PARSE);
				return;
			}
		} else if(as[0].equalsIgnoreCase("killnpc")) {
			int j1 = 16;
			byte byte3 = 0;
			String s36 = "Nearby NPCs are now dead.";
			if(as.length > 1) {
				if(as[1].equalsIgnoreCase("all")) {
					j1 = 1024;
					s36 = "All NPCs are now dead.";
				} else if(as[1].equalsIgnoreCase("animal")) {
					byte3 = 1;
					j1 = 1024;
					s36 = "All animals are now dead.";
				} else if(as[1].equalsIgnoreCase("monster")) {
					byte3 = 2;
					j1 = 1024;
					s36 = "All monsters are now dead.";
				}
			}
			java.util.List list3 = mc.theWorld.getEntitiesWithinAABBExcludingEntity(ep, AxisAlignedBB.getBoundingBox(ep.posX - j1, ep.posY - j1, ep.posZ - j1, ep.posX + j1, ep.posY + j1, ep.posZ + j1));
			for(int k26 = 0; k26 < list3.size(); k26++) {
				Entity entity7 = (Entity) list3.get(k26);
				if(byte3 == 0 && (entity7 instanceof EntityLiving)) {
					((EntityLiving) entity7).damageEntity(DamageSource.causePlayerDamage(ep), 0x7fffffff);
					continue;
				}
				if(byte3 == 1 && (entity7 instanceof EntityAnimal)) {
					((EntityLiving) entity7).damageEntity(DamageSource.causePlayerDamage(ep), 0x7fffffff);
					continue;
				}
				if(byte3 == 2 && (entity7 instanceof IMob)) {
					((EntityLiving) entity7).damageEntity(DamageSource.causePlayerDamage(ep), 0x7fffffff);
				}
			}

			sendMessage(s36);
		} else if(as[0].equalsIgnoreCase("ascend") || as[0].equalsIgnoreCase("descend")) {
			boolean flag = false;
			boolean flag3 = true;
			if(as[0].equalsIgnoreCase("descend")) {
				flag3 = false;
			}
			double d12 = ep.posX;
			double d20 = ep.posY;
			double d25 = ep.posZ;
			setCurrentPosition();
			if(flag3 && ep.posY < 0.0D) {
				ep.posY = 0.0D;
			} else if(!flag3 && ep.posY > 130D) {
				ep.posY = 130D;
			}
			do {
				if(ep.posY < 0.0D || ep.posY > 130D) {
					ep.setPosition(d12, d20, d25);
					break;
				}
				ep.setPosition(ep.posX, ep.posY, ep.posZ);
				if(mc.theWorld.getCollidingBoundingBoxes(ep, ep.boundingBox).size() == 0) {
					if(flag3 && flag) {
						break;
					}
					if(!flag3 && flag) {
						ep.setPosition(ep.posX, --ep.posY, ep.posZ);
						if(mc.theWorld.getCollidingBoundingBoxes(ep, ep.boundingBox).size() != 0) {
							ep.setPosition(ep.posX, ++ep.posY, ep.posZ);
							break;
						}
						ep.posY++;
					}
				} else {
					flag = true;
				}
				if(flag3) {
					ep.posY++;
				} else {
					ep.posY--;
				}
			} while(true);
			ep.motionX = ep.motionY = ep.motionZ = 0.0D;
			ep.rotationPitch = 0.0F;
		} else if(as[0].equalsIgnoreCase("repair")) {
			if(as.length > 1 && as[1].equalsIgnoreCase("all")) {
				for(int k1 = 0; k1 < ep.inventory.mainInventory.length; k1++) {
					ItemStack itemstack2 = ep.inventory.mainInventory[k1];
					if(itemstack2 != null) {
						itemstack2.damageItem(-itemstack2.getItemDamage(), null);
					}
				}

				for(int l1 = 0; l1 < ep.inventory.armorInventory.length; l1++) {
					ItemStack itemstack3 = ep.inventory.armorInventory[l1];
					if(itemstack3 != null) {
						itemstack3.damageItem(-itemstack3.getItemDamage(), null);
					}
				}

				return;
			}
			ItemStack itemstack = ep.inventory.getCurrentItem();
			if(itemstack != null) {
				itemstack.damageItem(-itemstack.getItemDamage(), null);
			}
		} else if(as[0].equalsIgnoreCase("duplicate") || as[0].equalsIgnoreCase("dupe")) {
			if(as.length > 1 && as[1].equalsIgnoreCase("all")) {
				for(int i2 = 0; i2 < ep.inventory.mainInventory.length; i2++) {
					if(ep.inventory.mainInventory[i2] != null) {
						givePlayerItem(ep.inventory.mainInventory[i2].copy());
					}
				}

				for(int j2 = 0; j2 < ep.inventory.armorInventory.length; j2++) {
					if(ep.inventory.armorInventory[j2] != null) {
						givePlayerItem(ep.inventory.armorInventory[j2].copy());
					}
				}

				return;
			}
			if(ep.inventory.getCurrentItem() != null) {
				if(as.length > 1) {
					try {
						int k2 = Integer.parseInt(as[1]);
						k2 = k2 <= 256 ? k2 >= 0 ? k2 : 0 : 256;
						for(int l9 = 0; l9 < k2; l9++) {
							givePlayerItem(ep.inventory.getCurrentItem().copy());
						}

					} catch (Exception exception4) {
						sendError(ERRMSG_PARSE);
					}
					return;
				}
				givePlayerItem(ep.inventory.getCurrentItem().copy());
			}
		} else if(as[0].equalsIgnoreCase("destroy")) {
			if(as.length > 1 && as[1].equalsIgnoreCase("all")) {
				destroyInventory();
				return;
			}
			ep.inventory.mainInventory[ep.inventory.currentItem] = null;
		} else if(as[0].equalsIgnoreCase("itemstack")) {
			if(as.length < 2) {
				sendError(ERRMSG_PARAM);
				return;
			}
			String as2[] = new String[as.length - 1];
			for(int i10 = 0; i10 < as2.length; i10++) {
				as2[i10] = as[i10 + 1];
			}

			ItemStack itemstack4 = getItemStack(as2);
			if(itemstack4 != null) {
				int i16 = itemstack4.stackSize;
				itemstack4.stackSize = itemstack4.getMaxStackSize();
				for(int k22 = 0; k22 < i16; k22++) {
					givePlayerItem(new ItemStack(itemstack4.getItem(), itemstack4.getMaxStackSize(), itemstack4.getItemDamageForDisplay()));
				}

			} else {
				sendError("Could not find specified item.");
			}
		} else if(as[0].equalsIgnoreCase("defuse")) {
			int l2 = 16;
			if(as.length > 1 && as[1].equalsIgnoreCase("all")) {
				l2 = 1024;
			}
			java.util.List list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(ep, AxisAlignedBB.getBoundingBox(ep.posX - l2, ep.posY - l2, ep.posZ - l2, ep.posX + l2, ep.posY + l2, ep.posZ + l2));
			for(int j16 = 0; j16 < list.size(); j16++) {
				Entity entity4 = (Entity) list.get(j16);
				if(entity4 instanceof EntityTNTPrimed) {
					EntityItem entityitem = new EntityItem(mc.theWorld, entity4.posX, entity4.posY, entity4.posZ, new ItemStack(Item.itemsList[46], 1));
					mc.theWorld.setEntityDead(entity4);
					mc.theWorld.entityJoinedWorld(entityitem);
				}
			}

		} else if(as[0].equalsIgnoreCase("jump")) {
			try {
				MovingObjectPosition movingobjectposition = ep.rayTrace(1024D, 1.0F);
				int j10 = MathHelper.floor_double(movingobjectposition.hitVec.xCoord);
				int k16 = MathHelper.floor_double(movingobjectposition.hitVec.yCoord);
				int l22 = MathHelper.floor_double(movingobjectposition.hitVec.zCoord);
				int l26 = mc.theWorld.getBlockId(j10, k16, l22);
				int k29 = mc.theWorld.getBlockId(j10, k16 + 1, l22);
				if(movingobjectposition.hitVec.xCoord % j10 == 0.0D && ep.posX > movingobjectposition.hitVec.xCoord) {
					j10--;
				} else if(movingobjectposition.hitVec.zCoord % l22 == 0.0D && ep.posZ > movingobjectposition.hitVec.zCoord) {
					l22--;
				}
				for(; k16 < 128 && (!mc.theWorld.isAirBlock(j10, k16, l22) || !mc.theWorld.isAirBlock(j10, k16 + 1, l22)); k16++) {
				}
				if(mc.theWorld.isAirBlock(MathHelper.floor_double(ep.posX), MathHelper.floor_double(ep.posY) - 1, MathHelper.floor_double(ep.posZ))) {
					for(; k16 > 0 && mc.theWorld.isAirBlock(j10, k16 - 1, l22); k16--) {
					}
				}
				double d26 = 0.5D;
				double d28 = 0.5D;
				ep.setPosition(j10 + d26, k16 + ep.height, l22 + d28);
			} catch (Exception exception5) {
				sendError("Unknown problem");
				return;
			}
		} else if(as[0].equalsIgnoreCase("return")) {
			if(prevy <= 0.0D) {
				return;
			}
			double d1 = prevx;
			double d13 = prevy;
			double d21 = prevz;
			setCurrentPosition();
			ep.setPosition(d1, d13, d21);
			saveSettings(getWorldDir());
		} else if(as[0].equalsIgnoreCase("instantmine")) {
			instant = !instant;
			sendMessage((new StringBuilder()).append("Instant mine now ").append(instant ? "on" : "off").toString());
			saveSettings();
		} else if(as[0].equalsIgnoreCase("setjump")) {
			if(as.length < 2) {
				sendError(ERRMSG_PARAM);
				return;
			}
			if(as[1].equalsIgnoreCase("reset")) {
				gravity = 1.0D;
				falldamage = true;
			} else {
				try {
					double d2 = Double.parseDouble(as[1]);
					gravity = d2 <= 0.0D ? 1.0D : d2;
					prevgravity = gravity;
					falldamage = gravity <= 1.0D;
				} catch (Exception exception6) {
					sendError(ERRMSG_PARSE);
					return;
				}
			}
			sendMessage((new StringBuilder()).append("Player jump set at: ").append(gravity).toString());
			saveSettings();
		} else if(as[0].equalsIgnoreCase("setspeed")) {
			if(as.length < 2) {
				sendError(ERRMSG_PARAM);
				return;
			}
			if(as[1].equalsIgnoreCase("reset")) {
				speed = 1.0D;
			} else {
				try {
					float f = Float.parseFloat(as[1]);
					speed = f <= 0.0F ? 1.0D : f;
					prevspeed = speed;
				} catch (Exception exception7) {
					sendError(ERRMSG_PARSE);
					return;
				}
			}
			sendMessage((new StringBuilder()).append("Player speed set at: ").append(speed).toString());
			saveSettings();
		} else if(as[0].equalsIgnoreCase("falldamage")) {
			falldamage = !falldamage;
			sendMessage((new StringBuilder()).append("Fall damage now ").append(falldamage ? "on" : "off").toString());
			saveSettings();
		} else if(as[0].equalsIgnoreCase("waterdamage")) {
			waterdamage = !waterdamage;
			sendMessage((new StringBuilder()).append("Water damage now ").append(waterdamage ? "on" : "off").toString());
			saveSettings();
		} else if(as[0].equalsIgnoreCase("firedamage")) {
			ep.isImmuneToFire = !ep.isImmuneToFire;
			sendMessage((new StringBuilder()).append("Fire damage now ").append(ep.isImmuneToFire ? "off" : "on").toString());
			saveSettings();
		} else if(as[0].equalsIgnoreCase("damage")) {
			damage = !damage;
			sendMessage((new StringBuilder()).append("Damage now ").append(damage ? "on" : "off").toString());
			saveSettings();
		} else if(as[0].equalsIgnoreCase("ext") || as[0].equalsIgnoreCase("extinguish")) {
			char c = '\020';
			if(as.length > 1 && as[1].equalsIgnoreCase("all")) {
				c = '\200';
			}
			int k10 = MathHelper.floor_double(ep.posX);
			int l16 = MathHelper.floor_double(ep.posY);
			int i23 = MathHelper.floor_double(ep.posZ);
			for(int i27 = 0; i27 < c; i27++) {
				for(int l29 = 0; l29 < c; l29++) {
					if(l16 - l29 < 0 || l16 + l29 > 128) {
						continue;
					}
					for(int l30 = 0; l30 < c; l30++) {
						if(mc.theWorld.getBlockId(k10 + i27, l16 + l29, i23 + l30) == Block.fire.blockID) {
							mc.theWorld.setBlockWithNotify(k10 + i27, l16 + l29, i23 + l30, 0);
						}
						if(mc.theWorld.getBlockId(k10 - i27, l16 + l29, i23 + l30) == Block.fire.blockID) {
							mc.theWorld.setBlockWithNotify(k10 - i27, l16 + l29, i23 + l30, 0);
						}
						if(mc.theWorld.getBlockId(k10 - i27, l16 + l29, i23 - l30) == Block.fire.blockID) {
							mc.theWorld.setBlockWithNotify(k10 - i27, l16 + l29, i23 - l30, 0);
						}
						if(mc.theWorld.getBlockId(k10 + i27, l16 + l29, i23 - l30) == Block.fire.blockID) {
							mc.theWorld.setBlockWithNotify(k10 + i27, l16 + l29, i23 - l30, 0);
						}
						if(mc.theWorld.getBlockId(k10 + i27, l16 - l29, i23 + l30) == Block.fire.blockID) {
							mc.theWorld.setBlockWithNotify(k10 + i27, l16 - l29, i23 + l30, 0);
						}
						if(mc.theWorld.getBlockId(k10 - i27, l16 - l29, i23 + l30) == Block.fire.blockID) {
							mc.theWorld.setBlockWithNotify(k10 - i27, l16 - l29, i23 + l30, 0);
						}
						if(mc.theWorld.getBlockId(k10 - i27, l16 - l29, i23 - l30) == Block.fire.blockID) {
							mc.theWorld.setBlockWithNotify(k10 - i27, l16 - l29, i23 - l30, 0);
						}
						if(mc.theWorld.getBlockId(k10 + i27, l16 - l29, i23 - l30) == Block.fire.blockID) {
							mc.theWorld.setBlockWithNotify(k10 + i27, l16 - l29, i23 - l30, 0);
						}
					}

				}

			}

			ep.fireResistance = 0;
			sendMessage("Fire extinguished");
		} else if(as[0].equalsIgnoreCase("explode")) {
			float f1 = 4F;
			if(as.length > 1) {
				try {
					f1 = Integer.parseInt(as[1]);
				} catch (Exception exception12) {
					f1 = 4F;
				}
			}
			mc.theWorld.createExplosion(ep, ep.posX, ep.posY, ep.posZ, f1);
		} else if(as[0].equalsIgnoreCase("timeschedule")) {
			if(as.length < 2) {
				sendError(ERRMSG_PARAM);
				return;
			}
			if(as[1].equalsIgnoreCase("reset")) {
				timeschedule = null;
				lastrift = -1;
				sendMessage("Timeschedule reset");
				saveSettings();
				return;
			}
			if(as.length < 3) {
				sendError(ERRMSG_PARAM);
				return;
			}
			timeschedule = new int[4];
			try {
				timeschedule[2] = Integer.parseInt(as[1].split(":")[0]);
				timeschedule[3] = Integer.parseInt(as[1].split(":")[1]);
				timeschedule[0] = Integer.parseInt(as[2].split(":")[0]);
				timeschedule[1] = Integer.parseInt(as[2].split(":")[1]);
				timeschedule[2] = timeschedule[2] >= 0 ? timeschedule[2] <= 23 ? timeschedule[2] : 23 : 0;
				timeschedule[3] = timeschedule[3] >= 0 ? timeschedule[3] <= 59 ? timeschedule[3] : 59 : 0;
				timeschedule[0] = timeschedule[0] >= 0 ? timeschedule[0] <= 23 ? timeschedule[0] : 23 : 0;
				timeschedule[1] = timeschedule[1] >= 0 ? timeschedule[1] <= 59 ? timeschedule[1] : 59 : 0;
				lastrift = -1;
			} catch (Exception exception8) {
				sendError(ERRMSG_PARSE);
				return;
			}
			sendMessage((new StringBuilder()).append("Timeschedule set. From: ").append(as[1]).append(" To: ").append(as[2]).toString());
			saveSettings();
		} else if(as[0].equalsIgnoreCase("search")) {
			if(as.length < 2) {
				sendError(ERRMSG_PARAM);
				return;
			}
			String s6 = "";
			for(int l10 = 0; l10 < ITEMNAMES.size(); l10++) {
				String s37;
				if((s37 = (String) ITEMNAMES.elementAt(l10)) != null) {
					s6 = s37.indexOf(as[1].trim().toLowerCase()) == -1 ? s6 : (new StringBuilder()).append(s6).append(" ").append(s37).append("(").append(l10).append(")").toString();
				}
			}

			if(s6.equalsIgnoreCase("")) {
				sendMessage("No results found");
			} else {
				sendMessage(s6);
			}
		} else if(as[0].equalsIgnoreCase("msg")) {
			if(as.length < 2) {
				sendError(ERRMSG_PARAM);
				return;
			}
			String s7 = "";
			for(int i11 = 1; i11 < as.length; i11++) {
				s7 = (new StringBuilder()).append(s7).append(as[i11]).append(" ").toString();
			}

			sendMessage(s7.trim());
		} else if(as[0].equalsIgnoreCase("grow")) {
			char c1 = '\020';
			if(as.length > 1 && as[1].equalsIgnoreCase("all")) {
				c1 = '\200';
			}
			int j11 = MathHelper.floor_double(ep.posX);
			int i17 = MathHelper.floor_double(ep.posY);
			int j23 = MathHelper.floor_double(ep.posZ);
			WorldGenTrees worldgentrees = new WorldGenTrees(true);
			WorldGenBigTree worldgenbigtree = new WorldGenBigTree(true);
			WorldGenTrees worldgentrees1 = worldgentrees;
			Random random2 = new Random();
			for(int i33 = 0; i33 < c1; i33++) {
				label0: for(int k33 = 0; k33 < c1; k33++) {
					if(i17 - k33 < 0 || i17 + k33 > 128) {
						continue;
					}
					int j34 = 0;
					do {
						if(j34 >= c1) {
							continue label0;
						}
						Object obj5;
						if(random2.nextInt(10) == 0) {
							obj5 = worldgenbigtree;
						} else {
							obj5 = worldgentrees;
						}
						growPlant(j11 + i33, i17 + k33, j23 + j34, random2, ((WorldGenerator) (obj5)));
						growPlant(j11 - i33, i17 + k33, j23 + j34, random2, ((WorldGenerator) (obj5)));
						growPlant(j11 - i33, i17 + k33, j23 - j34, random2, ((WorldGenerator) (obj5)));
						growPlant(j11 + i33, i17 + k33, j23 - j34, random2, ((WorldGenerator) (obj5)));
						growPlant(j11 + i33, i17 - k33, j23 + j34, random2, ((WorldGenerator) (obj5)));
						growPlant(j11 - i33, i17 - k33, j23 + j34, random2, ((WorldGenerator) (obj5)));
						growPlant(j11 - i33, i17 - k33, j23 - j34, random2, ((WorldGenerator) (obj5)));
						growPlant(j11 + i33, i17 - k33, j23 - j34, random2, ((WorldGenerator) (obj5)));
						j34++;
					} while(true);
				}

			}

			sendMessage("Plants have now grown.");
		} else if(as[0].equalsIgnoreCase("itemname")) {
			if(ep.inventory.mainInventory[ep.inventory.currentItem] == null) {
				sendMessage("No item currently selected.");
				return;
			}
			int i3 = ep.inventory.getCurrentItem().itemID;
			String s23 = (String) ITEMNAMES.elementAt(i3);
			s23 = s23 != null ? s23 : "Unknown";
			String s38 = ep.inventory.getCurrentItem().isItemStackDamageable() || ep.inventory.getCurrentItem().getItemDamage() <= 0 ? "" : (new StringBuilder()).append(":").append(ep.inventory.getCurrentItem().getItemDamage()).toString();
			sendMessage((new StringBuilder()).append(s23).append(" (").append(i3).append(s38).append(")").toString());
		} else if(as[0].equalsIgnoreCase("useportal")) {
			if(as.length < 2) {
				sendError(ERRMSG_PARAM);
				return;
			}
			try {
				mc.usePortal(Integer.parseInt(as[1]));
				return;
			} catch (Exception exception9) {
			}
			if(as[1].equalsIgnoreCase("normal")) {
				mc.usePortal(0);
			} else if(as[1].equalsIgnoreCase("nether")) {
				mc.usePortal(-1);
			} else if(as[1].equalsIgnoreCase("end")) {
				mc.usePortal(1);
			} else {
				sendError(ERRMSG_PARSE);
			}
		} else if(as[0].equalsIgnoreCase("refill")) {
			if(as.length > 1 && as[1].equalsIgnoreCase("all")) {
				for(int j3 = 0; j3 < ep.inventory.mainInventory.length; j3++) {
					if(ep.inventory.mainInventory[j3] != null) {
						ep.inventory.mainInventory[j3].stackSize = ep.inventory.mainInventory[j3].getMaxStackSize();
					}
				}

				for(int k3 = 0; k3 < ep.inventory.armorInventory.length; k3++) {
					if(ep.inventory.armorInventory[k3] != null) {
						ep.inventory.armorInventory[k3].stackSize = ep.inventory.armorInventory[k3].getMaxStackSize();
					}
				}

				return;
			}
			if(ep.inventory.mainInventory[ep.inventory.currentItem] != null) {
				ep.inventory.mainInventory[ep.inventory.currentItem].stackSize = ep.inventory.mainInventory[ep.inventory.currentItem].getMaxStackSize();
			}
		} else if(as[0].equalsIgnoreCase("dropstore")) {
			mc.theWorld.setBlock((int) ep.posX + 1, (int) ep.posY - 1, (int) ep.posZ, Block.chest.blockID);
			mc.theWorld.setBlockWithNotify((int) ep.posX + 1, (int) ep.posY - 1, (int) ep.posZ + 1, Block.chest.blockID);
			InventoryLargeChest inventorylargechest = new InventoryLargeChest("Large chest", (TileEntityChest) mc.theWorld.getBlockTileEntity((int) ep.posX + 1, (int) ep.posY - 1, (int) ep.posZ + 1), (TileEntityChest) mc.theWorld.getBlockTileEntity((int) ep.posX + 1, (int) ep.posY - 1, (int) ep.posZ));
			int k11 = 0;
			for(int j17 = 0; j17 < ep.inventory.mainInventory.length; j17++) {
				inventorylargechest.setInventorySlotContents(k11++, ep.inventory.mainInventory[j17]);
				ep.inventory.mainInventory[j17] = null;
			}

			for(int k17 = 0; k17 < ep.inventory.armorInventory.length; k17++) {
				inventorylargechest.setInventorySlotContents(k11++, ep.inventory.armorInventory[k17]);
				ep.inventory.armorInventory[k17] = null;
			}

		} else if(as[0].equalsIgnoreCase("removedrops")) {
			int l3 = 16;
			if(as.length > 1 && as[1].equalsIgnoreCase("all")) {
				l3 = 1024;
			}
			java.util.List list1 = mc.theWorld.getEntitiesWithinAABBExcludingEntity(ep, AxisAlignedBB.getBoundingBox(ep.posX - l3, ep.posY - l3, ep.posZ - l3, ep.posX + l3, ep.posY + l3, ep.posZ + l3));
			for(int l17 = 0; l17 < list1.size(); l17++) {
				Entity entity5 = (Entity) list1.get(l17);
				if(entity5 instanceof EntityItem) {
					((EntityItem) entity5).setEntityDead();
				}
			}

		} else if(as[0].equalsIgnoreCase("fly")) {
			double d3 = -1D;
			boolean flag5 = true;
			ep.fallDistance = 0.0F;
			if(!noClip) {
				if(as.length == 2) {
					try {
						double d16 = Double.parseDouble(as[1]);
						d3 = d16;
					} catch (Exception exception33) {
						sendError(ERRMSG_PARSE);
						return;
					}
					if(d3 != speed) {
						if(flying) {
							flag5 = false;
						}
						flying = true;
						sendMessage((new StringBuilder()).append("Flying is enabled at speed ").append(d3).toString());
					} else {
						flying = false;
						sendMessage("Flying now turned off");
					}
				} else {
					flying = !flying;
					sendMessage((new StringBuilder()).append("Flying now turned ").append(flying ? "on" : "off").toString());
				}
				if(flying) {
					if(flag5) {
						prevspeed = speed;
						prevgravity = gravity;
						prevfalldamage = falldamage;
					}
					if(d3 > 0.0D) {
						speed = d3;
						gravity = d3;
					}
					falldamage = false;
				} else {
					falldamage = prevfalldamage;
					speed = prevspeed;
					gravity = prevgravity;
				}
				saveSettings();
			}
		} else if(as[0].equalsIgnoreCase("noclip")) {
			noClip = !noClip;
			noclip(noClip);
			sendMessage((new StringBuilder()).append("Noclip now turned ").append(noClip ? "on" : "off").toString());
			saveSettings();
		} else if(as[0].equalsIgnoreCase("reach")) {
			if(as.length < 2) {
				sendError(ERRMSG_PARAM);
				return;
			}
			try {
				reachdistance = Float.parseFloat(as[1]);
				sendMessage((new StringBuilder()).append("Reach distance set at ").append(reachdistance).toString());
			} catch (Exception exception10) {
				if(as[1].equalsIgnoreCase("reset")) {
					reachdistance = 4F;
					return;
				}
				sendError(ERRMSG_PARSE);
			}
			saveSettings();
		} else if(as[0].equalsIgnoreCase("reset")) {
			initialise();
			saveSettings();
			sendMessage("Settings reset to default.");
		} else if(as[0].equalsIgnoreCase("macro") || as[0].equalsIgnoreCase("sc")) {
			if(as.length < 2) {
				sendError(ERRMSG_PARAM);
				return;
			}
			File file = new File(MODDIR, "macros/");
			if(!file.exists() || !file.isDirectory()) {
				sendError("Macro directory could not be found.");
				return;
			}
			if(as[1].equalsIgnoreCase("create") || as[1].equalsIgnoreCase("edit") || as[1].equalsIgnoreCase("delete") || as[1].equalsIgnoreCase("folder") || as[1].equalsIgnoreCase("list") || as[1].equalsIgnoreCase("dir") || as[1].equalsIgnoreCase("engine")) {
				if(as[1].equalsIgnoreCase("list")) {
					String s24 = "";
					File afile1[] = file.listFiles();
					int k23 = afile1.length;
					for(int j27 = 0; j27 < k23; j27++) {
						File file10 = afile1[j27];
						String s52 = "";
						if((s52 = file10.getName()).endsWith(".txt")) {
							s24 = (new StringBuilder()).append(s24).append(s52.substring(0, s52.length() - 4)).append(", ").toString();
						}
					}

					if(!s24.equalsIgnoreCase("")) {
						sendMessage("Installed Macros:");
						sendMessage(s24);
					} else {
						sendMessage("No macros found.");
					}
					return;
				}
				if(as[1].equalsIgnoreCase("folder") || as[1].equalsIgnoreCase("dir")) {
					if(Desktop.isDesktopSupported()) {
						try {
							Desktop desktop = Desktop.getDesktop();
							desktop.open(file);
						} catch (Exception exception13) {
							sendError(ERRMSG_OSNOTSUPPORTED);
						}
					} else {
						sendError(ERRMSG_OSNOTSUPPORTED);
					}
					return;
				}
				if(as.length < 3) {
					sendError(ERRMSG_PARAM);
					return;
				}
				String s25 = join(as, 2, as.length);
				File file8 = new File(file, (new StringBuilder()).append(s25).append(".txt").toString());
				if(as[1].equalsIgnoreCase("delete")) {
					if(file8.exists() && file8.isFile()) {
						try {
							file8.delete();
							sendMessage("Macro successfully deleted");
						} catch (Exception exception34) {
							sendError((new StringBuilder()).append("Could not delete specified macro ").append(s25).toString());
						}
					} else {
						sendError((new StringBuilder()).append("Could not delete specified macro ").append(s25).toString());
					}
					return;
				}
				if(as[1].equalsIgnoreCase("engine")) {
					ScriptEngineManager scriptenginemanager = new ScriptEngineManager();
					ScriptEngine scriptengine = scriptenginemanager.getEngineByName(s25);
					if(scriptengine == null) {
						sendError("Specified language engine could not be loaded.");
						return;
					} else {
						engine = s25;
						saveSettings();
						sendMessage((new StringBuilder()).append("Script engine changed to ").append(s25).toString());
						return;
					}
				}
				if(!file8.exists()) {
					try {
						file8.createNewFile();
					} catch (Exception exception35) {
						sendError("Could not open the macro for editing");
					}
				}
				if(Desktop.isDesktopSupported()) {
					try {
						Desktop desktop1 = Desktop.getDesktop();
						desktop1.edit(file8);
					} catch (Exception exception36) {
						sendError(ERRMSG_OSNOTSUPPORTED);
					}
				} else {
					sendError(ERRMSG_OSNOTSUPPORTED);
				}
				return;
			}
			if(as[0].equalsIgnoreCase("sc")) {
				File file4 = new File(file, as[1]);
				if(!file4.exists()) {
					file4 = new File(as[1]);
				}
				String as8[] = new String[as.length - 1];
				for(int l23 = 1; l23 < as.length; l23++) {
					as8[l23 - 1] = as[l23];
				}

				ScriptEngineManager scriptenginemanager1 = new ScriptEngineManager();
				ScriptEngine scriptengine1 = scriptenginemanager1.getEngineByName(engine);
				scriptengine1.put("args", as8);
				scriptengine1.put("player", ep);
				scriptengine1.put("helper", this);
				scriptengine1.put("world", mc.theWorld);
				scriptengine1.put("minecraft", mc);
				FileInputStream fileinputstream = new FileInputStream(file4);
				try {
					InputStreamReader inputstreamreader = new InputStreamReader(fileinputstream);
					scriptengine1.eval(inputstreamreader);
				} catch (Exception exception51) {
					exception51.printStackTrace();
				}
			} else {
				File file5 = new File(file, (new StringBuilder()).append(as[1]).append(".txt").toString());
				if(!file5.exists()) {
					file5 = new File(as[1]);
				}
				if(!file5.exists() || !file5.isFile()) {
					sendError("Could not find specified file.");
					return;
				}
				as[1] = file5.getAbsolutePath();
				BufferedReader bufferedreader = new BufferedReader(new FileReader(file5));
				for(String s46 = null; (s46 = bufferedreader.readLine()) != null;) {
					for(int k27 = 0; k27 < as.length - 1; k27++) {
						s46 = s46.replaceAll((new StringBuilder()).append("\\$_").append(k27).toString(), as[k27 + 1]);
					}

					s46 = s46.replaceAll("\\$_[0-9]+", "");
					ep.sendChatMessage(s46);
				}
				bufferedreader.close();

			}
			lastcommand = join(as, 0, as.length);
			sendMessage("Macro finished processing.");
		} else if(as[0].equalsIgnoreCase("maxstack")) {
			if(as.length < 2) {
				Item item = ep.inventory.getCurrentItem().getItem();
				if(item == null) {
					return;
				} else {
					item.maxStackSize = 64;
					sendMessage("Current items stack size changed to 64");
					return;
				}
			}
			String as3[] = new String[as.length - 1];
			for(int l11 = 0; l11 < as3.length; l11++) {
				as3[l11] = as[l11 + 1];
			}

			ItemStack itemstack5 = getItemStack(as3);
			if(itemstack5 == null) {
				if(as[1].equalsIgnoreCase("all")) {
					int i18 = 64;
					if(as.length > 2) {
						try {
							i18 = Integer.parseInt(as[2]);
						} catch (Exception exception37) {
							i18 = 64;
						}
					}
					i18 = i18 <= 64 && i18 >= 1 ? i18 : 64;
					for(int i24 = 0; i24 < Item.itemsList.length; i24++) {
						if(Item.itemsList[i24] != null) {
							Item.itemsList[i24].maxStackSize = i18;
						}
					}

					sendMessage((new StringBuilder()).append("All items MAX stack size set at ").append(i18).toString());
					return;
				} else {
					sendError(ERRMSG_PARSE);
					return;
				}
			}
			int j18 = itemstack5.getItem().maxStackSize != itemstack5.stackSize ? itemstack5.stackSize >= 0 && itemstack5.stackSize <= 64 ? itemstack5.stackSize : 64 : 64;
			itemstack5.getItem().maxStackSize = j18;
			sendMessage((new StringBuilder()).append("Items stack size changed to ").append(j18).toString());
		} else if(as[0].equalsIgnoreCase("world")) {
			if(as.length < 2) {
				sendError(ERRMSG_PARAM);
				return;
			}
			if(as[1].equalsIgnoreCase("load")) {
				if(as.length < 3) {
					sendError(ERRMSG_PARAM);
					return;
				}
				File file1 = new File(Minecraft.getMinecraftDir(), "saves");
				File file6 = new File(file1, as[2]);
				if(!file6.exists()) {
					file1 = new File(as[2]);
					file6 = file1;
					file1 = file1.getParentFile();
				}
				if(file1 == null || file6 == null) {
					sendError("Could not find the specified save.");
					return;
				}
				String s39 = file6.getAbsolutePath();
				mc.changeWorld2(new World(new SaveHandler(file1, file6.getName(), false), file6.getName(), new WorldSettings((new Random()).nextLong(), 0, true, false)), (new StringBuilder()).append("Changing World to ").append(s39).toString());
			} else if(as[1].equalsIgnoreCase("save") || as[1].equalsIgnoreCase("backup")) {
				LoadingScreenRenderer loadingscreenrenderer = new LoadingScreenRenderer(mc);
				loadingscreenrenderer.printText("Please wait... Saving level");
				mc.theWorld.saveWorld(true, loadingscreenrenderer);
				if(as[1].equalsIgnoreCase("backup")) {
					LoadingScreenRenderer loadingscreenrenderer1 = new LoadingScreenRenderer(mc);
					loadingscreenrenderer1.printText("Please wait... World is being backed up");
					String s26 = mc.theWorld.worldInfo.getWorldName();
					SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS");
					String s47 = simpledateformat.format(new Date());
					copyDirectory(getWorldDir(), new File(Minecraft.getMinecraftDir(), (new StringBuilder()).append("backup/").append(s26).append("/").append(s47).toString()), loadingscreenrenderer1);
				}
			} else if(as[1].equalsIgnoreCase("exit")) {
				mc.displayGuiScreen(new GuiMainMenu());
				mc.theWorld = null;
				mc.changeWorld(null, "", null);
			} else if(as[1].equalsIgnoreCase("new")) {
				long l4 = 0L;
				boolean flag6 = false;
				if(as.length > 3) {
					try {
						l4 = Long.parseLong(as[3]);
						flag6 = true;
					} catch (Exception exception38) {
						sendError(ERRMSG_PARSE);
						return;
					}
				}
				File file9 = new File(Minecraft.getMinecraftDir(), "saves");
				String s49 = as.length <= 2 ? (new StringBuilder()).append("").append(System.currentTimeMillis()).toString() : as[2];
				File file11 = new File(file9, s49);
				if(file11.exists()) {
					sendError("Cannot create new world right now, the World already exists.");
					return;
				}
				World world = null;
				if(flag6) {
					world = new World(new SaveHandler(file9, file11.getName(), false), file11.getName(), new WorldSettings(l4, 0, true, false));
				} else {
					world = new World(new SaveHandler(file9, file11.getName(), false), file11.getName(), new WorldSettings((new Random()).nextLong(), 0, true, false));
				}
				mc.changeWorld2(world, (new StringBuilder()).append("Creating a new world at: ").append(file11.getAbsolutePath()).toString());
			} else if(as[1].equalsIgnoreCase("list")) {
				File file2 = new File(Minecraft.getMinecraftDir(), "saves");
				File afile[] = file2.listFiles();
				String s40 = null;
				for(int j24 = 0; j24 < afile.length; j24++) {
					if(!afile[j24].isDirectory()) {
						continue;
					}
					if(s40 == null) {
						s40 = afile[j24].getName();
					} else {
						s40 = (new StringBuilder()).append(s40).append(", ").append(afile[j24].getName()).toString();
					}
				}

				sendMessage("World Saves:");
				sendMessage(s40);
			} else if(as[1].equalsIgnoreCase("seed")) {
				if(as.length == 2) {
					sendMessage((new StringBuilder()).append("The current world seed is ").append(mc.theWorld.worldInfo.getRandomSeed()).toString());
					return;
				}
				long l5 = -1L;
				try {
					l5 = Long.parseLong(as[2]);
				} catch (Exception exception25) {
					sendError(ERRMSG_PARSE);
					return;
				}
				NBTTagCompound nbttagcompound2 = mc.theWorld.worldInfo.getNBTTagCompound();
				nbttagcompound2.setLong("RandomSeed", l5);
				mc.theWorld.worldInfo = new WorldInfo(nbttagcompound2);
				sendMessage((new StringBuilder()).append("World seed set to ").append(l5).toString());
			} else if(as[1].equalsIgnoreCase("name")) {
				if(as.length > 2) {
					String s8 = join(as, 2, as.length);
					mc.theWorld.worldInfo.setWorldName(s8);
				}
				sendMessage((new StringBuilder()).append("Your current world's name is ").append(mc.theWorld.worldInfo.getWorldName()).toString());
			} else {
				sendError((new StringBuilder()).append("Unknown world command: ").append(as[1]).toString());
			}
		} else if(as[0].equalsIgnoreCase("bind") || as[0].equalsIgnoreCase("bindid")) {
			if(as.length < 2) {
				sendError(ERRMSG_PARAM);
				return;
			}
			if(as[1].equalsIgnoreCase("list")) {
				sendBindList();
				return;
			}
			if(as.length < 3) {
				sendError(ERRMSG_PARAM);
				return;
			}
			int i4 = -1;
			if(as[0].equalsIgnoreCase("bind")) {
				try {
					i4 = Keyboard.getKeyIndex(as[1].toUpperCase());
					if(i4 == 0) {
						i4 = -1;
					}
				} catch (Exception exception14) {
					sendError(ERRMSG_PARSE);
					return;
				}
			} else {
				try {
					i4 = Integer.parseInt(as[1]);
				} catch (Exception exception15) {
					sendError(ERRMSG_PARSE);
					return;
				}
			}
			if(i4 < 0) {
				sendError("Invalid keycode.");
				return;
			}
			String s27 = as[2];
			for(int k18 = 3; k18 < as.length; k18++) {
				s27 = (new StringBuilder()).append(s27).append(" ").append(as[k18]).toString();
			}

			bound.set((new StringBuilder()).append("").append(i4).toString(), s27);
			if(bound.save(new File(MODDIR, "bindings.properties"), "Single Player Commands - Key Bindings")) {
				sendMessage((new StringBuilder()).append("Key ").append(as[1]).append(" successfully bound to specified command.").toString());
			} else {
				sendError("Unable to save bindings to file. Key binding will work until world closes.");
			}
		} else if(as[0].equalsIgnoreCase("unbind") || as[0].equalsIgnoreCase("unbindid")) {
			if(as.length < 2) {
				sendError(ERRMSG_PARAM);
				return;
			}
			if(as[1].equalsIgnoreCase("list")) {
				sendBindList();
				return;
			}
			if(as[0].equalsIgnoreCase("all")) {
				bound.clear();
				if(bound.save(new File(MODDIR, "bindings.properties"), "Single Player Commands - Key Bindings")) {
					sendMessage("All keys were successfully unbound.");
				} else {
					sendError("Unable to save bindings to file. Key bindings have been removed until world closes.");
				}
				return;
			}
			int j4 = -1;
			if(as[0].equalsIgnoreCase("unbind")) {
				try {
					j4 = Keyboard.getKeyIndex(as[1].toUpperCase());
					if(j4 == 0) {
						j4 = -1;
					}
				} catch (Exception exception16) {
					sendError(ERRMSG_PARSE);
					return;
				}
			} else {
				try {
					j4 = Integer.parseInt(as[1]);
				} catch (Exception exception17) {
					sendError(ERRMSG_PARSE);
					return;
				}
			}
			if(j4 < 0) {
				sendError("Invalid keycode.");
				return;
			}
			Object obj2 = bound.remove((new StringBuilder()).append(j4).append("").toString());
			if(obj2 == null) {
				sendError((new StringBuilder()).append("Could not find a binding for key ").append(as[1]).toString());
				return;
			}
			if(bound.save(new File(MODDIR, "bindings.properties"), "Single Player Commands - Key Bindings")) {
				sendMessage((new StringBuilder()).append("Key ").append(as[1]).append(" was successfully unbound.").toString());
			} else {
				sendError("Unable to save bindings to file. Key binding has been removed until world closes.");
			}
		} else if(as[0].equalsIgnoreCase("rename")) {
			if(as.length < 3) {
				sendError(ERRMSG_PARAM);
				return;
			}
			if(!commands.containsKey(as[1])) {
				sendError("Could not find specified command.");
				return;
			}
			String s9 = (String) commands.get(as[1]);
			commands.remove(as[1]);
			commands.put(as[2], s9);
			sendMessage((new StringBuilder()).append("Command ").append(as[1]).append(" renamed to ").append(as[2]).append(".").toString());
		} else if(as[0].equalsIgnoreCase("superheat")) {
			Field afield[] = (net.minecraft.src.FurnaceRecipes.class).getDeclaredFields();
			Map map = null;
			int l18 = 0;
			do {
				if(l18 >= afield.length) {
					break;
				}
				afield[l18].setAccessible(true);
				Object obj4 = afield[l18].get(FurnaceRecipes.smelting());
				if(obj4 instanceof Map) {
					map = (Map) obj4;
					break;
				}
				l18++;
			} while(true);
			if(map == null) {
				sendError("Could not retrieve smelting list.");
				return;
			}
			boolean flag7 = false;
			if(as.length >= 2 && as[1].equalsIgnoreCase("all")) {
				flag7 = true;
			}
			int k24 = flag7 ? ep.inventory.mainInventory.length : 1;
			int l27 = flag7 ? 0 : ep.inventory.currentItem;
			for(int i30 = l27; i30 < l27 + k24; i30++) {
				if(ep.inventory.mainInventory[i30] != null) {
					int i31 = ep.inventory.mainInventory[i30].itemID;
					if(map.containsKey(Integer.valueOf(i31)) && map.get(Integer.valueOf(i31)) != null) {
						int l31 = ep.inventory.mainInventory[i30].stackSize;
						ItemStack itemstack9 = (ItemStack) map.get(Integer.valueOf(i31));
						int l33 = itemstack9.itemID;
						int k34 = itemstack9.getItemDamage();
						ItemStack itemstack10 = new ItemStack(l33, l31, k34);
						ep.inventory.mainInventory[i30] = itemstack10;
					}
				}
			}

		} else if(as[0].equalsIgnoreCase("light")) {
			light = !light;
			toggleLight(light);
			sendMessage((new StringBuilder()).append("Light now turned ").append(light ? "on" : "off").append(".").toString());
			saveSettings();
		} else if(as[0].equalsIgnoreCase("drops")) {
			dropitems = !dropitems;
			sendMessage((new StringBuilder()).append("Blocks ").append(dropitems ? "now" : "will not").append(" drop items.").toString());
			saveSettings();
		} else if(as[0].equalsIgnoreCase("invisible")) {
			isinvisible = !isinvisible;
			sendMessage((new StringBuilder()).append("Toggling invisibility: ").append(isinvisible).toString());
			saveSettings();
		} else if(as[0].equalsIgnoreCase("freeze")) {
			mobsfrozen = !mobsfrozen;
			sendMessage((new StringBuilder()).append("All mobs are ").append(mobsfrozen ? "now" : "no longer").append(" frozen").toString());
			saveSettings();
		} else if(as[0].equalsIgnoreCase("mobdamage")) {
			mobdamage = !mobdamage;
			sendMessage((new StringBuilder()).append("Mob damage is now ").append(mobdamage ? "on" : "off").toString());
			saveSettings();
		} else if(as[0].equalsIgnoreCase("itemdamage")) {
			norepair = !norepair;
			for(int k4 = 0; k4 < Item.itemsList.length; k4++) {
				if(!(Item.itemsList[k4] instanceof Item)) {
					continue;
				}
				if(norepair) {
					System.out.println("Max damage is now: -1");
					if(Item.itemsList[k4].isDamageable()) {
						Item.itemsList[k4].setMaxDamage(MAGICNUMBER);
					}
					continue;
				}
				if(Item.itemsList[k4].getMaxDamage() == MAGICNUMBER) {
					Item.itemsList[k4].setMaxDamage(ITEM_MAX_DAMAGE[k4]);
				}
			}

			sendMessage((new StringBuilder()).append("Items now ").append(norepair ? "don't " : "").append("require repair.").toString());
			saveSettings();
		} else if(as[0].equalsIgnoreCase("infiniteitems")) {
			infiniteitems = !infiniteitems;
			sendMessage((new StringBuilder()).append("Items are ").append(infiniteitems ? "now" : "no longer").append(" infinite.").toString());
			saveSettings();
		} else if(as[0].equalsIgnoreCase("keepitems")) {
			keepitems = !keepitems;
			sendMessage((new StringBuilder()).append("Items will").append(keepitems ? " be" : " not be").append(" kept on death.").toString());
			saveSettings();
		} else if(as[0].equalsIgnoreCase("instantkill")) {
			instantkill = !instantkill;
			saveSettings();
			sendMessage((new StringBuilder()).append("Instant kill is turned ").append(instantkill ? "on." : "off.").toString());
		} else if(as[0].equalsIgnoreCase("instantplant")) {
			instantgrow = false;
			if(as.length > 1 && as[1].equalsIgnoreCase("grow")) {
				instantgrow = true;
			}
			if(!instantgrow) {
				instantplant = !instantplant;
			} else {
				instantplant = true;
			}
			sendMessage((new StringBuilder()).append("Instant plant turned ").append(instantplant ? "on" : "off").append(", instant grow is ").append(instantgrow ? "on" : "off").toString());
		} else if(as[0].equalsIgnoreCase("plugin")) {
			if(as.length < 2) {
				sendError(ERRMSG_PARAM);
				return;
			}
			if(as.length == 2) {
				if(as[1].equalsIgnoreCase("list") || as[1].equalsIgnoreCase("dlist")) {
					String s10 = "";
					Iterator iterator5 = null;
					String s41 = "";
					if(as[1].equalsIgnoreCase("dlist")) {
						iterator5 = PLUGIN_MANAGER.getDisabledPlugins().iterator();
						s41 = "Disabled plugins:";
					} else {
						iterator5 = PLUGIN_MANAGER.getPlugins().iterator();
						s41 = "Loaded Plugins: ";
					}
					while(iterator5.hasNext()) {
						s10 = (new StringBuilder()).append(s10).append(((SPCPlugin) iterator5.next()).getName()).append(", ").toString();
					}
					if(s10.equalsIgnoreCase("")) {
						sendMessage("No plugins found.");
					} else {
						sendMessage((new StringBuilder()).append(s41).append(s10).toString());
					}
				} else if(as[1].equalsIgnoreCase("enable")) {
					PLUGIN_MANAGER.setEnabled(true);
					sendMessage("Plugins have been enabled.");
				} else if(as[1].equalsIgnoreCase("disable")) {
					PLUGIN_MANAGER.setEnabled(false);
					sendMessage("Plugins have been disabled.");
				}
				return;
			}
			if(as.length >= 3 && (as[1].equalsIgnoreCase("disable") || as[1].equalsIgnoreCase("enable"))) {
				boolean flag1 = true;
				if(as[1].equalsIgnoreCase("disable")) {
					flag1 = false;
				}
				String s28 = as[2];
				for(int i19 = 3; i19 < as.length; i19++) {
					s28 = (new StringBuilder()).append(s28).append(" ").append(as[i19]).toString();
				}

				SPCPlugin aspcplugin[] = PLUGIN_MANAGER.getPlugin(s28);
				if(aspcplugin.length == 0) {
					sendError((new StringBuilder()).append("Plugin with name \"").append(s28).append("\" could not be found.").toString());
					return;
				}
				boolean flag8 = false;
				SPCPlugin aspcplugin1[] = aspcplugin;
				int j30 = aspcplugin1.length;
				for(int j31 = 0; j31 < j30; j31++) {
					SPCPlugin spcplugin = aspcplugin1[j31];
					if(flag1) {
						if(PLUGIN_MANAGER.enablePlugin(spcplugin)) {
							flag8 = true;
						}
						continue;
					}
					if(PLUGIN_MANAGER.disablePlugin(spcplugin)) {
						flag8 = true;
					}
				}

				if(flag8) {
					sendMessage((new StringBuilder()).append("Plugin with name \"").append(s28).append("\" was ").append(flag1 ? "enabled." : "disabled.").toString());
				} else {
					sendError((new StringBuilder()).append("Plugin with name \"").append(s28).append("\" could not be ").append(flag1 ? "enabled." : "disabled.").toString());
				}
			}
		} else if(as[0].equalsIgnoreCase("watermovement")) {
			watermovement = !watermovement;
			sendMessage((new StringBuilder()).append("Liquid slowdown and current effects are ").append(watermovement ? "on." : "off.").toString());
			saveSettings();
		} else if(!as[0].equalsIgnoreCase("confuse")) {
			if(as[0].equalsIgnoreCase("skin")) {
				if(as.length > 1) {
					if(as[1].equalsIgnoreCase("reset")) {
						ep.skinUrl = (new StringBuilder()).append("http://s3.amazonaws.com/MinecraftSkins/").append(sessionusername).append(".png").toString();
						return;
					}
					String s11 = "";
					for(int i12 = 1; i12 < as.length; i12++) {
						s11 = (new StringBuilder()).append(s11).append(as[i12]).toString();
						if(i12 + 1 < as.length) {
							s11 = (new StringBuilder()).append(s11).append(" ").toString();
						}
					}

					s11.trim();
					changeSkin(ep, s11);
				}
			} else if(as[0].equalsIgnoreCase("startup")) {
				if(as.length < 2) {
					sendError(ERRMSG_PARAM);
					return;
				}
				String s12 = as[1];
				for(int j12 = 2; j12 < as.length; j12++) {
					s12 = (new StringBuilder()).append(s12).append(" ").append(as[j12]).toString();
				}

				startup = s12;
				saveSettings();
				sendMessage("Startup command was successfully set.");
			} else if(as[0].equalsIgnoreCase("ride")) {
				if(ep.ridingEntity != null) {
					ep.mountEntity(null);
					return;
				}
				MovingObjectPosition movingobjectposition1 = mc.objectMouseOver;
				if(movingobjectposition1 == null) {
					sendError(ERRMSG_NPCNOTFOUND);
					return;
				}
				Entity entity = movingobjectposition1.entityHit;
				if(entity != null && (entity instanceof EntityLiving)) {
					ep.mountEntity(entity);
					sendMessage("Mounted the entity. Type /ride again to unmount");
				} else {
					sendError(ERRMSG_NPCNOTFOUND);
				}
			} else if(as[0].equalsIgnoreCase("exterminate")) {
				int i5 = 4;
				MovingObjectPosition movingobjectposition3 = mc.objectMouseOver;
				if(movingobjectposition3 == null) {
					sendError(ERRMSG_NPCNOTFOUND);
					return;
				}
				if(as.length > 1) {
					try {
						i5 = Integer.parseInt(as[1]);
					} catch (Exception exception26) {
						i5 = 4;
					}
				}
				if(i5 < 0) {
					i5 = 4;
				}
				Entity entity2 = movingobjectposition3.entityHit;
				if(entity2 != null && (entity2 instanceof EntityLiving)) {
					mc.theWorld.createExplosion(ep, entity2.posX, entity2.posY, entity2.posZ, i5);
					if(i5 > 3) {
						sendMessage("BOOM!");
					} else {
						sendMessage("puff");
					}
				} else {
					sendError(ERRMSG_NPCNOTFOUND);
				}
			} else if(as[0].equalsIgnoreCase("cannon")) {
				EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(mc.theWorld);
				entitytntprimed.setLocationAndAngles(ep.posX, ep.posY, ep.posZ, ep.rotationYaw, ep.rotationPitch);
				entitytntprimed.fuse = 40;
				entitytntprimed.motionX = -MathHelper.sin((entitytntprimed.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((entitytntprimed.rotationPitch / 180F) * 3.141593F);
				entitytntprimed.motionZ = MathHelper.cos((entitytntprimed.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((entitytntprimed.rotationPitch / 180F) * 3.141593F);
				entitytntprimed.motionY = -MathHelper.sin((entitytntprimed.rotationPitch / 180F) * 3.141593F);
				double d9 = 1.0D;
				if(as.length > 1) {
					try {
						d9 = Double.parseDouble(as[1]);
					} catch (Exception exception39) {
						d9 = 1.0D;
					}
				}
				entitytntprimed.motionX *= d9;
				entitytntprimed.motionY *= d9;
				entitytntprimed.motionZ *= d9;
				mc.theWorld.entityJoinedWorld(entitytntprimed);
			} else if(as[0].equalsIgnoreCase("output")) {
				output = !output;
				saveSettings();
				sendMessage((new StringBuilder()).append("Output now turned ").append(output ? "on" : "off").toString());
			} else if(as[0].equalsIgnoreCase("config")) {
				if(as.length < 2) {
					sendError(ERRMSG_PARAM);
					return;
				}
				if(as[1].equalsIgnoreCase("setglobal")) {
					File file3 = new File(MODDIR, "spc.settings");
					if(as.length > 2 && as[2].equalsIgnoreCase("reset")) {
						try {
							file3.delete();
							sendMessage("Global settings reset to default.");
						} catch (Exception exception18) {
							sendError("Unable to reset global settings.");
						}
						return;
					}
					File file7 = new File(getWorldDir(), "spc.settings");
					if(copyFile(file7, file3)) {
						sendMessage("Global configuration successfully set as current configuration.");
					} else {
						sendMessage("Unable to set global configuration.");
					}
				}
			} else if(as[0].equalsIgnoreCase("chest")) {
				if(as.length < 2) {
					sendError(ERRMSG_PARAM);
					return;
				}
				MovingObjectPosition movingobjectposition4 = ep.rayTrace(reachdistance, 1.0F);
				if(movingobjectposition4 == null) {
					sendError("No block within range.");
					return;
				}
				int j5 = movingobjectposition4.blockX;
				int k12 = movingobjectposition4.blockY;
				int j19 = movingobjectposition4.blockZ;
				int l24 = movingobjectposition4.blockX + 1;
				int i28 = movingobjectposition4.blockY;
				int k30 = movingobjectposition4.blockZ;
				if(as[1].equalsIgnoreCase("drop")) {
					k12++;
					i28++;
					mc.theWorld.setBlock(j5, k12, j19, Block.chest.blockID);
					mc.theWorld.setBlockWithNotify(l24, i28, k30, Block.chest.blockID);
				} else if(as[1].equalsIgnoreCase("fill") || as[1].equalsIgnoreCase("get") || as[1].equalsIgnoreCase("swap") || as[1].equalsIgnoreCase("clear")) {
					if(mc.theWorld.getBlockId(j5, k12, j19) == Block.chest.blockID) {
						if(mc.theWorld.getBlockId(l24, i28, k30) != Block.chest.blockID) {
							if(mc.theWorld.getBlockId(j5 - 1, k12, j19) == Block.chest.blockID) {
								l24 = j5 - 1;
							} else if(mc.theWorld.getBlockId(j5, k12, j19 + 1) == Block.chest.blockID) {
								l24 = j5;
								k30 = j19 + 1;
							} else if(mc.theWorld.getBlockId(j5, k12, j19 - 1) == Block.chest.blockID) {
								l24 = j5;
								k30 = j19 - 1;
							} else {
								i28 = -1;
							}
						}
					} else {
						sendError("Chest not found. You need to point at a chest to fill.");
						return;
					}
				}
				Object obj7 = null;
				if(i28 > -1) {
					obj7 = new InventoryLargeChest("Large chest", (TileEntityChest) mc.theWorld.getBlockTileEntity(j5, k12, j19), (TileEntityChest) mc.theWorld.getBlockTileEntity(l24, i28, k30));
				} else {
					obj7 = mc.theWorld.getBlockTileEntity(j5, k12, j19);
				}
				if(as[1].equalsIgnoreCase("drop") || as[1].equalsIgnoreCase("fill")) {
					transferInventory(ep.inventory, ((IInventory) (obj7)));
				} else if(as[1].equalsIgnoreCase("get")) {
					transferInventory(((IInventory) (obj7)), ep.inventory);
				} else if(as[1].equalsIgnoreCase("clear")) {
					transferInventory(((IInventory) (obj7)), null);
				} else if(as[1].equalsIgnoreCase("swap")) {
					InventoryPlayer inventoryplayer = new InventoryPlayer(ep);
					for(int i34 = 0; i34 < inventoryplayer.getSizeInventory(); i34++) {
						inventoryplayer.setInventorySlotContents(i34, ep.inventory.getStackInSlot(i34));
						ep.inventory.setInventorySlotContents(i34, null);
					}

					transferInventory(((IInventory) (obj7)), ep.inventory);
					transferInventory(inventoryplayer, ((IInventory) (obj7)));
				}
			} else if(as[0].equalsIgnoreCase("biome")) {
				BiomeGenBase biomegenbase = mc.theWorld.getBiomeAt((int) ep.posX, (int) ep.posY, (int) ep.posZ);
				sendMessage((new StringBuilder()).append("Current Biome: ").append(biomegenbase.biomeName).toString());
			} else if(as[0].equalsIgnoreCase("platform")) {
				int k5 = MathHelper.floor_double(ep.posX);
				int l12 = MathHelper.floor_double(ep.posY);
				int k19 = MathHelper.floor_double(ep.posZ);
				mc.theWorld.setBlockWithNotify(k5, l12 - 2, k19, Block.glass.blockID);
			} else if(as[0].equalsIgnoreCase("spawncontrol")) {
				if(as.length < 2) {
					sendError(ERRMSG_PARAM);
					return;
				}
				if(as.length == 2) {
					if(as[1].equalsIgnoreCase("all")) {
						animalspawn = !animalspawn;
						monsterspawn = animalspawn;
					} else if(as[1].equalsIgnoreCase("monster") || as[1].equalsIgnoreCase("agressive")) {
						monsterspawn = !monsterspawn;
					} else if(as[1].equalsIgnoreCase("animal") || as[1].equalsIgnoreCase("friendly")) {
						animalspawn = !animalspawn;
					}
					mc.theWorld.setAllowedMobSpawns(monsterspawn, animalspawn);
					saveSettings();
					sendMessage((new StringBuilder()).append("Friendly mobs will ").append(animalspawn ? "now spawn" : "not spawn").append(". Agressive mobs will ").append(monsterspawn ? "now spawn" : "not spawn").toString());
					return;
				}
			} else if(as[0].equalsIgnoreCase("reskin")) {
				if(as.length < 2) {
					sendError(ERRMSG_PARAM);
					return;
				}
				MovingObjectPosition movingobjectposition2 = mc.objectMouseOver;
				if(movingobjectposition2 == null || movingobjectposition2.entityHit == null) {
					sendError("No entity found.");
					return;
				}
				if(movingobjectposition2.entityHit instanceof EntityLiving) {
					((EntityLiving) movingobjectposition2.entityHit).texture = as[1];
				}
				sendMessage((new StringBuilder()).append("Reskined to ").append(as[1]).toString());
			} else {
				if(as[0].equalsIgnoreCase("helmet")) {
					String as4[] = new String[as.length - 1];
					System.arraycopy(as, 1, as4, 0, as.length - 1);
					ItemStack itemstack6 = getItemStack(as4);
					if(itemstack6 == null) {
						sendError("Invalid item.");
						return;
					} else {
						mc.thePlayer.inventory.armorInventory[3] = itemstack6;
						return;
					}
				}
				if(as[0].equalsIgnoreCase("slippery")) {
					float f2 = 0.6F;
					if(as.length != 2 && as.length != 3) {
						sendError("Invalid syntax: expected: blockname [slipperiness]");
						return;
					}
					if(as.length == 3) {
						try {
							f2 = Float.parseFloat(as[2]);
						} catch (NumberFormatException numberformatexception) {
							sendError("Invalid number format: expected: blockname [slipperiness]");
							return;
						}
					}
					int i13 = getBlockID(as[1]);
					if(i13 == -1 || Block.blocksList[i13] == null) {
						sendError("Invalid block.");
						return;
					} else {
						Block.blocksList[i13].slipperiness = f2;
						sendMessage(String.format("%s.slipperiness = %.2f", new Object[]{as[1], Float.valueOf(f2)}));
						return;
					}
				}
				if(as[0].equalsIgnoreCase("longerlegs")) {
					String s13;
					if(ep.stepHeight == 1.0F) {
						ep.stepHeight = 0.5F;
						s13 = "off";
					} else {
						ep.stepHeight = 1.0F;
						s13 = "on";
					}
					sendMessage((new StringBuilder()).append("Longer legs ").append(s13).toString());
				} else if(as[0].equalsIgnoreCase("atlantis")) {
					String s14;
					if(Block.blocksList[0] != null) {
						Block.blocksList[0] = null;
						s14 = "off";
					} else {
						Block.blocksList[0] = Block.blocksList[8];
						s14 = "on";
					}
					sendMessage((new StringBuilder()).append("Atlantis mode: ").append(s14).toString());
				} else {
					if(as[0].equalsIgnoreCase("weather")) {
						if(as.length < 2) {
							sendError(ERRMSG_PARAM);
							return;
						}
						WorldInfo worldinfo = mc.theWorld.worldInfo;
						if(as[1].equalsIgnoreCase("rain")) {
							String s29;
							if(worldinfo.getIsRaining()) {
								s29 = "off";
								worldinfo.setIsRaining(false);
							} else {
								s29 = "on";
								worldinfo.setIsRaining(true);
							}
							sendMessage((new StringBuilder()).append("Rain has been turned ").append(s29).toString());
							return;
						}
						if(as[1].equalsIgnoreCase("thunder")) {
							String s30;
							if(worldinfo.getIsThundering()) {
								s30 = "off";
								worldinfo.setIsThundering(false);
								worldinfo.setIsRaining(false);
							} else {
								s30 = "on";
								worldinfo.setIsThundering(true);
								worldinfo.setIsRaining(true);
							}
							sendMessage((new StringBuilder()).append("Thunder has been turned ").append(s30).toString());
							return;
						}
						if(as[1].equalsIgnoreCase("lightning")) {
							Object obj3 = null;
							SPCObjectHit spcobjecthit1 = new SPCObjectHit(ep.rayTrace(128D, 1.0F));
							if(spcobjecthit1.blocky == -1) {
								return;
							} else {
								EntityLightningBolt entitylightningbolt = new EntityLightningBolt(mc.theWorld, spcobjecthit1.blockx, spcobjecthit1.blocky, spcobjecthit1.blockz);
								mc.theWorld.addWeatherEffect(entitylightningbolt);
								return;
							}
						}
						if(as[1].equalsIgnoreCase("sun")) {
							worldinfo.setIsThundering(false);
							worldinfo.setIsRaining(false);
							return;
						} else {
							sendError((new StringBuilder()).append("Unknown weather command: ").append(as[1]).toString());
							return;
						}
					}
					if(as[0].equalsIgnoreCase("spawnportal")) {
						(new Teleporter()).createPortal(mc.theWorld, mc.thePlayer);
						sendMessage("Spawned a portal");
					} else if(as[0].equalsIgnoreCase("clone")) {
						int i6 = 1;
						if(as.length > 1) {
							try {
								i6 = Integer.parseInt(as[1]);
							} catch (NumberFormatException numberformatexception1) {
								sendError((new StringBuilder()).append("Invalid count: ").append(as[1]).toString());
								return;
							}
						}
						if(mc.objectMouseOver == null || mc.objectMouseOver.entityHit == null) {
							sendError(ERRMSG_NPCNOTFOUND);
							return;
						}
						Entity entity1 = mc.objectMouseOver.entityHit;
						for(int l19 = 0; l19 < i6; l19++) {
							Entity entity6 = null;
							try {
								entity6 = entity1.getClass().getConstructor(new Class[]{net.minecraft.src.World.class}).newInstance(new Object[]{mc.theWorld});
							} catch (Throwable throwable2) {
								sendError("An error occurred cloning the mob");
								return;
							}
							entity6.setPosition(entity1.posX, entity1.posY, entity1.posZ);
							NBTTagCompound nbttagcompound4 = new NBTTagCompound();
							entity1.writeToNBT(nbttagcompound4);
							entity6.readFromNBT(nbttagcompound4);
							mc.theWorld.entityJoinedWorld(entity6);
						}

						sendMessage((new StringBuilder()).append("Cloned ").append(i6).append(" ").append(EntityList.getEntityString(entity1)).toString());
					} else if(as[0].equalsIgnoreCase("killall")) {
						Class class2 = net.minecraft.src.Entity.class;
						if(as.length >= 2) {
							class2 = getEntity(as[1]);
							if(class2 == null) {
								sendError((new StringBuilder()).append("Invalid mob: ").append(as[1]).toString());
								return;
							}
						}
						java.util.List list2 = getAllMobs(class2, -1D);
						Entity entity3;
						for(Iterator iterator8 = list2.iterator(); iterator8.hasNext(); entity3.setEntityDead()) {
							entity3 = (Entity) iterator8.next();
						}

						sendMessage((new StringBuilder()).append("Killed ").append(list2.size()).append(" ").append(as.length > 1 ? as[1] : "entities").toString());
					} else if(as[0].equalsIgnoreCase("flammable")) {
						int j6 = 0;
						int j13 = 0;
						if(as.length != 2 && as.length != 4) {
							sendError("Invalid format. Expected: blockname spread catch");
							sendDebug(Integer.toString(as.length));
							return;
						}
						int i20 = getBlockID(as[1]);
						if(i20 == -1) {
							sendError((new StringBuilder()).append("Unknown block: ").append(as[1]).toString());
							return;
						}
						if(as.length == 4) {
							try {
								j6 = Integer.parseInt(as[2]);
								j13 = Integer.parseInt(as[3]);
							} catch (NumberFormatException numberformatexception7) {
								sendError("Invalid format. Expected: blockname spread catch");
								sendDebug(Integer.toString(as.length));
								return;
							}
						}
						try {
							Method amethod[] = (net.minecraft.src.BlockFire.class).getDeclaredMethods();
							Method method1 = null;
							Method amethod1[] = amethod;
							int k31 = amethod1.length;
							int i32 = 0;
							do {
								if(i32 >= k31) {
									break;
								}
								Method method2 = amethod1[i32];
								method2.setAccessible(true);
								java.lang.reflect.Type atype[] = method2.getGenericParameterTypes();
								if(atype.length == 3 && atype[0].equals(Integer.TYPE) && atype[1].equals(Integer.TYPE) && atype[2].equals(Integer.TYPE)) {
									method1 = method2;
									break;
								}
								i32++;
							} while(true);
							if(method1 == null) {
								throw new Exception("Could not find method.");
							}
							method1.setAccessible(true);
							method1.invoke((BlockFire) Block.blocksList[51], new Object[]{Integer.valueOf(i20), Integer.valueOf(j6), Integer.valueOf(j13)});
						} catch (Throwable throwable1) {
							sendError("Could not invoke method");
							printStackTrace(throwable1);
							return;
						}
						sendMessage(String.format("Set flammability of %d to %d %d", new Object[]{Integer.valueOf(i20), Integer.valueOf(j6), Integer.valueOf(j13)}));
					} else if(as[0].equalsIgnoreCase("clearwater")) {
						String s15;
						if(Block.lightOpacity[8] == 0) {
							Block.lightOpacity[8] = Block.lightOpacity[9] = 3;
							s15 = "off";
						} else {
							Block.lightOpacity[8] = Block.lightOpacity[9] = 0;
							s15 = "on";
						}
						sendMessage((new StringBuilder()).append("Clear water: ").append(s15).toString());
					} else if(as[0].equalsIgnoreCase("superpunch")) {
						double d4 = superpunch != -1D ? -1D : 4D;
						String s42 = "4";
						if(as.length == 2) {
							try {
								d4 = Double.parseDouble(as[1]);
								s42 = as[1];
							} catch (Exception exception40) {
								if(as[1].equalsIgnoreCase("reset")) {
									d4 = -1D;
								}
							}
						}
						if(d4 <= 1.0D) {
							superpunch = -1D;
						} else {
							superpunch = d4 / 10D + 1.0D;
						}
						saveSettings();
						sendMessage((new StringBuilder()).append("Superpunch ").append(superpunch > 1.0D ? (new StringBuilder()).append("is set at ").append(s42).toString() : "is disabled").toString());
					} else if(as[0].equalsIgnoreCase("confusesuicide")) {
						double d5 = 10D;
						if(as.length >= 2) {
							try {
								d5 = Double.parseDouble(as[1]);
							} catch (NumberFormatException numberformatexception2) {
								sendError((new StringBuilder()).append("Invalid distance: ").append(as[1]).toString());
								return;
							}
						}
						double d14 = d5 * d5;
						java.util.List list4 = getAllMobs(net.minecraft.src.EntityCreature.class, d5 * d5);
						EntityCreature entitycreature;
						for(Iterator iterator9 = list4.iterator(); iterator9.hasNext(); confuse(entitycreature, entitycreature)) {
							Entity entity8 = (Entity) iterator9.next();
							entitycreature = (EntityCreature) entity8;
						}

						sendMessage(String.format("Confused %d within radius %.1f ", new Object[]{Integer.valueOf(list4.size()), Double.valueOf(d5)}));
					} else if(as[0].equalsIgnoreCase("confuse")) {
						double d6 = 10D;
						if(as.length >= 2) {
							try {
								d6 = Double.parseDouble(as[1]);
							} catch (NumberFormatException numberformatexception3) {
								sendError((new StringBuilder()).append("Invalid distance: ").append(as[1]).toString());
								return;
							}
						}
						double d15 = d6 * d6;
						java.util.List list5 = getAllMobs(net.minecraft.src.EntityCreature.class, d6 * d6);
						if(list5.size() < 2) {
							sendError("Not enough mobs around.");
							return;
						}
						EntityCreature entitycreature1 = null;
						EntityCreature entitycreature2 = null;
						for(Iterator iterator10 = list5.iterator(); iterator10.hasNext();) {
							Entity entity9 = (Entity) iterator10.next();
							EntityCreature entitycreature3 = (EntityCreature) entity9;
							if(entitycreature2 == null) {
								entitycreature1 = entitycreature3;
								entitycreature2 = entitycreature3;
							} else {
								confuse(entitycreature2, entitycreature3);
								entitycreature2 = entitycreature3;
							}
						}

						if(entitycreature2 != null && entitycreature1 != entitycreature2) {
							confuse(entitycreature2, entitycreature1);
						}
						sendMessage(String.format("Confused %d within radius %.1f ", new Object[]{Integer.valueOf(list5.size()), Double.valueOf(d6)}));
					} else if(as[0].equalsIgnoreCase("cyclepainting")) {
						byte byte1 = ((byte) (mc.thePlayer.isSneaking() ? -1 : 1));
						if(mc.objectMouseOver == null || !(mc.objectMouseOver.entityHit instanceof EntityPainting) || mc.objectMouseOver.entityHit.isDead) {
							sendError("No painting under cursor.");
							return;
						}
						EntityPainting entitypainting = (EntityPainting) mc.objectMouseOver.entityHit;
						EnumArt enumart = entitypainting.art;
						int i25 = entitypainting.direction;
						int j28 = 0;
						ArrayList arraylist2 = new ArrayList();
						EnumArt aenumart[] = EnumArt.values();
						for(int j32 = 0; j32 < aenumart.length; j32++) {
							EnumArt enumart1 = aenumart[j32];
							entitypainting.art = enumart1;
							entitypainting.func_412_b(i25);
							if(!entitypainting.canStay()) {
								continue;
							}
							arraylist2.add(enumart1);
							if(enumart == enumart1) {
								j28 = j32;
							}
						}

						if(arraylist2.size() <= 1) {
							entitypainting.art = enumart;
							entitypainting.func_412_b(i25);
							sendError("No other painting choices available.");
							return;
						}
						int k32 = byte1 != -1 ? j28 != arraylist2.size() - 1 ? j28 + 1 : 0 : j28 != 0 ? j28 - 1 : arraylist2.size() - 1;
						entitypainting.art = (EnumArt) arraylist2.get(k32);
						entitypainting.func_412_b(i25);
					} else if(as[0].equalsIgnoreCase("bring")) {
						int k6 = -1;
						Class class3 = net.minecraft.src.Entity.class;
						if(as.length >= 2) {
							class3 = getClass(as[1]);
							if(class3 == null) {
								sendError((new StringBuilder()).append("Invalid mob: ").append(as[1]).toString());
								return;
							}
							if(as.length >= 3) {
								try {
									k6 = Integer.parseInt(as[2]);
								} catch (NumberFormatException numberformatexception4) {
									sendError((new StringBuilder()).append("Invalid count: ").append(as[2]).toString());
									return;
								}
							}
						}
						Vec3D vec3d = mc.thePlayer.getLook(1.0F);
						double d17 = 5D;
						double d22 = mc.thePlayer.posX + vec3d.xCoord * d17;
						double d27 = mc.thePlayer.posY + vec3d.yCoord * d17;
						double d29 = mc.thePlayer.posZ + vec3d.zCoord * d17;
						java.util.List list6 = getAllMobs(class3, -1D);
						if(k6 == -1) {
							k6 = list6.size();
						}
						for(int l34 = 0; l34 < k6; l34++) {
							((Entity) list6.get(l34)).setPosition(d22, d27, d29);
						}

						sendMessage((new StringBuilder()).append("Brought ").append(k6).append(" ").append(as.length > 1 ? as[1] : "entities").toString());
					} else if(as[0].equalsIgnoreCase("phelp")) {
						if(as.length == 1) {
							String as5[] = PLUGIN_MANAGER.getCommands();
							String s31 = "";
							Arrays.sort(as5);
							String as9[] = as5;
							int j25 = as9.length;
							for(int k28 = 0; k28 < j25; k28++) {
								String s51 = as9[k28];
								s31 = (new StringBuilder()).append(s31).append(s51).append(", ").toString();
							}

							if(s31.equalsIgnoreCase("")) {
								sendMessage("No plugin commands found.");
								return;
							}
							s31 = s31.substring(0, s31.length() - 2);
							sendMessage("Plugin Commands:");
							sendMessage(s31);
						} else {
							String as6[] = PLUGIN_MANAGER.getHelp(as[1]);
							if(as6 == null) {
								return;
							}
							helpMessage(as6[0], (new StringBuilder()).append(as[1]).append(" ").append(as6[1]).toString(), (new StringBuilder()).append(as[1]).append(" ").append(as6[2]).toString());
						}
					} else if(as[0].equalsIgnoreCase("achievement")) {
						if(as.length < 2) {
							sendError(ERRMSG_PARAM);
							return;
						}
						if(as[1].equalsIgnoreCase("list")) {
							Iterator iterator = AchievementList.achievementList.iterator();
							String s32;
							for(s32 = ""; iterator.hasNext(); s32 = (new StringBuilder()).append(s32).append(((Achievement) iterator.next()).statName).append(", ").toString()) {
							}
							if(s32.equalsIgnoreCase("")) {
								sendError("No achievements found.");
							} else {
								sendMessage("Achievements: ");
								sendMessage(s32);
							}
						} else if(as[1].equalsIgnoreCase("unlock")) {
							for(Iterator iterator1 = AchievementList.achievementList.iterator(); iterator1.hasNext(); ep.triggerAchievement((StatBase) iterator1.next())) {
							}
							sendMessage("All achievements unlocked, cheater.");
						}
					} else if(as[0].equalsIgnoreCase("alias")) {
						if(as.length == 2 && as[1].equalsIgnoreCase("list")) {
							String s16 = "";
							for(Iterator iterator6 = alias.keySet().iterator(); iterator6.hasNext();) {
								s16 = (new StringBuilder()).append(s16).append(", ").append(iterator6.next()).toString();
							}

							s16 = s16.startsWith(", ") ? s16.substring(2) : s16.trim();
							sendMessage("All aliases:");
							sendMessage(s16.equalsIgnoreCase("") ? "None found" : s16);
							return;
						}
						if(as.length < 3 && as[1].equalsIgnoreCase("all")) {
							sendError(ERRMSG_PARAM);
							return;
						}
						String s17 = join(as, 2, as.length);
						addAlias(as[1], s17);
						sendMessage((new StringBuilder()).append("Alias \"").append(as[1]).append("\" successfully assigned to: ").append(s17).toString());
					} else if(as[0].equalsIgnoreCase("ralias")) {
						if(as.length < 2) {
							sendError(ERRMSG_PARAM);
							return;
						}
						if(as[1].equalsIgnoreCase("all")) {
							alias.clear();
							alias.save();
							sendMessage("All aliases removed");
						} else if(alias.containsKey(as[1])) {
							alias.remove(as[1]);
							alias.save();
							sendMessage((new StringBuilder()).append("Alias \"").append(as[1]).append("\" successfully removed").toString());
						} else {
							sendError((new StringBuilder()).append("Could not find specified alias \"").append(as[1]).append("\"").toString());
						}
					} else if(as[0].equalsIgnoreCase("resize")) {
						System.out.println((new StringBuilder()).append(mc.mcCanvas.getParent().getWidth()).append(" ").append(mc.mcCanvas.getParent().getHeight()).toString());
						Frame frame = Frame.getFrames()[0];
						if(as.length == 1) {
							setWindowSize(sizewidth, sizeheight);
						} else if(as[1].equalsIgnoreCase("1080p")) {
							setWindowSize(1920, 1080);
						} else if(as[1].equalsIgnoreCase("720p")) {
							setWindowSize(1280, 720);
						} else if(as[1].equalsIgnoreCase("480p")) {
							setWindowSize(640, 480);
						} else {
							if(as[1].equalsIgnoreCase("setdefault")) {
								if(as.length == 4) {
									int k13 = -1;
									int j20 = -1;
									try {
										j20 = Integer.parseInt(as[2]);
										k13 = Integer.parseInt(as[3]);
									} catch (Exception exception41) {
										sendError(ERRMSG_PARSE);
										return;
									}
									sizewidth = j20;
									sizeheight = k13;
								} else {
									sizewidth = frame.getWidth();
									sizeheight = frame.getHeight();
								}
								saveSettings();
								sendMessage((new StringBuilder()).append("Default screen resolution set to ").append(sizewidth).append("x").append(sizeheight).toString());
								return;
							}
							if(as.length == 3) {
								int l13 = -1;
								int k20 = -1;
								try {
									k20 = Integer.parseInt(as[1]);
									l13 = Integer.parseInt(as[2]);
								} catch (Exception exception42) {
									sendError(ERRMSG_PARSE);
									return;
								}
								setWindowSize(k20, l13);
							}
						}
						frame.setLocation(0, 0);
						sendMessage((new StringBuilder()).append("Screensize set to ").append(frame.getWidth()).append("x").append(frame.getHeight()).toString());
					} else if(as[0].equalsIgnoreCase("moveplayer")) {
						if(as.length < 3) {
							sendError(ERRMSG_PARAM);
							return;
						}
						double d7 = 0.0D;
						try {
							d7 = Double.parseDouble(as[1]);
						} catch (Exception exception27) {
							sendError(ERRMSG_PARSE);
							return;
						}
						setCurrentPosition();
						Boolean boolean1 = null;
						if(as[2].startsWith("+")) {
							boolean1 = Boolean.valueOf(true);
							as[2] = as[2].substring(1);
						} else if(as[2].startsWith("-")) {
							boolean1 = Boolean.valueOf(false);
							as[2] = as[2].substring(1);
						}
						if(as[2].startsWith("n")) {
							ep.setPosition(ep.posX - d7, ep.posY, ep.posZ);
						} else if(as[2].startsWith("s")) {
							ep.setPosition(ep.posX + d7, ep.posY, ep.posZ);
						} else if(as[2].startsWith("e")) {
							ep.setPosition(ep.posX, ep.posY, ep.posZ - d7);
						} else if(as[2].startsWith("w")) {
							ep.setPosition(ep.posX, ep.posY, ep.posZ + d7);
						} else if(as[2].startsWith("u")) {
							ep.setPosition(ep.posX, ep.posY + d7, ep.posZ);
						} else if(as[2].startsWith("d")) {
							ep.setPosition(ep.posX, ep.posY - d7, ep.posZ);
						} else {
							sendMessage(ERRMSG_PARSE);
							return;
						}
						if(boolean1 != null) {
							if(boolean1.booleanValue()) {
								processCommands("ascend");
							} else {
								processCommands("descend");
							}
						}
					} else if(as[0].equalsIgnoreCase("freecam")) {
						if(mc.renderViewEntity instanceof SPCEntityCamera) {
							mc.renderViewEntity = ep;
							flying = prevflying;
							speed = prevspeed;
							gravity = prevgravity;
							noClip = prevnoclip;
							noclip(prevnoclip);
							movecamera = false;
							moveplayer = true;
						} else {
							prevflying = flying;
							flying = true;
							prevspeed = speed;
							prevgravity = gravity;
							speed = 5D;
							gravity = 5D;
							prevnoclip = noClip;
							if(noClip) {
								noClip = false;
							}
							ep.motionX = 0.0D;
							ep.motionY = 0.0D;
							ep.motionZ = 0.0D;
							mc.renderViewEntity = new SPCEntityCamera(mc, mc.theWorld, mc.session, ep.dimension);
							mc.renderViewEntity.setPositionAndRotation(ep.posX, ep.posY, ep.posZ, ep.rotationYaw, ep.rotationPitch);
							movecamera = true;
							moveplayer = false;
						}
					} else if(as[0].equalsIgnoreCase("freezecam")) {
						if(movecamera) {
							if(!(mc.renderViewEntity instanceof SPCEntityCamera)) {
								SPCEntityCamera spcentitycamera = new SPCEntityCamera(mc, mc.theWorld, mc.session, ep.dimension);
								freezecamyaw = ep.rotationYaw;
								freezecampitch = ep.rotationPitch;
								spcentitycamera.setPositionAndRotation(ep.posX, ep.posY, ep.posZ, freezecamyaw, freezecampitch);
								spcentitycamera.setCamera(0.0D, 0.0D, 0.0D, freezecamyaw, freezecampitch);
								mc.renderViewEntity = spcentitycamera;
							}
							movecamera = false;
							moveplayer = true;
						} else {
							if(mc.renderViewEntity instanceof SPCEntityCamera) {
								mc.renderViewEntity.kill();
								mc.renderViewEntity = ep;
							}
							movecamera = true;
							moveplayer = true;
						}
					} else if(as[0].equalsIgnoreCase("update")) {
						if(as.length < 2) {
							sendError(ERRMSG_PARAM);
							return;
						}
						if(as[1].equalsIgnoreCase("enable")) {
							updateson = true;
						} else if(as[1].equalsIgnoreCase("disable")) {
							updateson = false;
						} else {
							sendMessage("Invalid update command.");
							return;
						}
						sendMessage((new StringBuilder()).append("Updates now ").append(as[1]).append(".").toString());
					} else if(as[0].equalsIgnoreCase("textcolor")) {
						String s18 = "";
						if(as.length == 2 && as[1].equalsIgnoreCase("reset")) {
							textcolornormal = 'f';
							textcolorerror = '4';
							textcolorrandom = VALID_COLOURS;
							s18 = "Text colors were reset to default.";
						} else {
							if(as.length != 3) {
								sendError(ERRMSG_PARAM);
								return;
							}
							char c2 = as[2].charAt(0);
							s18 = (new StringBuilder()).append(" text set to ").append(c2).toString();
							if(as[1].equalsIgnoreCase("normal")) {
								textcolornormal = c2;
								s18 = (new StringBuilder()).append("Normal").append(s18).toString();
							} else if(as[1].equalsIgnoreCase("error")) {
								textcolorerror = c2;
								s18 = (new StringBuilder()).append("Error").append(s18).toString();
							} else if(as[1].equalsIgnoreCase("setrandom")) {
								textcolorrandom = as[2];
								s18 = (new StringBuilder()).append("Random colors set to ").append(textcolorrandom).toString();
							} else {
								sendError(ERRMSG_PARSE);
								return;
							}
						}
						sendMessage(s18);
						saveSettings();
					} else if(as[0].equalsIgnoreCase("flymode")) {
						if(as.length != 2) {
							sendError(ERRMSG_PARAM);
							return;
						}
						if(as[1].equalsIgnoreCase("standard")) {
							flymode = "standard";
						} else if(as[1].equalsIgnoreCase("dynamic") || as[1].equalsIgnoreCase("reset")) {
							flymode = "dynamic";
						} else if(as[1].equalsIgnoreCase("minecraft")) {
							flymode = "minecraft";
						} else {
							sendError(ERRMSG_PARSE);
							return;
						}
						sendMessage((new StringBuilder()).append(flymode).append(" fly mode enabled. Use /fly to turn fly on/off").toString());
						saveSettings();
					} else if(as[0].equalsIgnoreCase("repeat")) {
						if(lastcommand == null) {
							sendError("Previous command cannot be repeated.");
							return;
						}
						ep.sendChatMessage(lastcommand);
					} else if(as[0].equalsIgnoreCase("stacklimit")) {
						if(as.length >= 2) {
							if(as[1].equalsIgnoreCase("on")) {
								limitstack = true;
							} else if(as[1].equalsIgnoreCase("off")) {
								limitstack = false;
							} else {
								sendError(ERRMSG_PARAM);
								return;
							}
						} else {
							limitstack = !limitstack;
						}
						sendMessage((new StringBuilder()).append("Stack size limited turned ").append(limitstack ? "on" : "off").toString());
					} else if(as[0].equalsIgnoreCase("stackcombine")) {
						for(int l6 = 0; l6 < ep.inventory.mainInventory.length; l6++) {
							ItemStack itemstack7 = ep.inventory.mainInventory[l6];
							if(itemstack7 != null) {
								for(int l20 = l6 + 1; l20 < ep.inventory.mainInventory.length; l20++) {
									ItemStack itemstack8 = ep.inventory.mainInventory[l20];
									if(itemstack8 != null && isItemEqual(itemstack7, itemstack8)) {
										itemstack7.stackSize += itemstack8.stackSize;
										ep.inventory.mainInventory[l20] = null;
									}
								}

							}
						}

					} else if(as[0].equalsIgnoreCase("gamemode")) {
						if(as.length < 2) {
							sendError(ERRMSG_PARAM);
							return;
						}
						byte byte2 = -1;
						if(as[1].equalsIgnoreCase("survival") || as[1].equalsIgnoreCase("0")) {
							byte2 = 0;
						} else if(as[1].equalsIgnoreCase("creative") || as[1].equalsIgnoreCase("1")) {
							byte2 = 1;
						}
						if(byte2 == -1) {
							sendError(ERRMSG_PARSE);
							return;
						}
						ArrayList arraylist1 = new ArrayList();
						arraylist1.add(ep);
						NBTTagCompound nbttagcompound3 = mc.theWorld.worldInfo.getNBTTagCompoundWithPlayer(arraylist1);
						nbttagcompound3.setInteger("GameType", byte2);
						if(byte2 == 1) {
							mc.playerController = new PlayerControllerCreative(mc);
							PlayerControllerCreative.func_35646_d(ep);
						} else {
							mc.playerController = new PlayerControllerSP(mc);
							PlayerControllerCreative.func_35645_e(ep);
						}
						WorldInfo worldinfo2 = new WorldInfo(nbttagcompound3);
						mc.theWorld.worldInfo = worldinfo2;
						sendMessage((new StringBuilder()).append("Game mode changed to ").append(byte2 != 1 ? "survival" : "creative").toString());
					} else if(as[0].equalsIgnoreCase("worldinfo")) {
						if(as.length < 3) {
							sendError(ERRMSG_PARAM);
							return;
						}
						ArrayList arraylist = new ArrayList();
						arraylist.add(ep);
						NBTTagCompound nbttagcompound1 = mc.theWorld.worldInfo.getNBTTagCompoundWithPlayer(arraylist);
						nbttagcompound1.setString(as[1], as[2]);
						WorldInfo worldinfo1 = new WorldInfo(nbttagcompound1);
						mc.theWorld.worldInfo = worldinfo1;
						sendMessage((new StringBuilder()).append("WorldInfo setting ").append(as[1]).append(" set as ").append(as[2]).toString());
					} else if(as[0].equalsIgnoreCase("hunger")) {
						if(as.length < 2) {
							sendError(ERRMSG_PARAM);
							return;
						}
						int i7 = 0;
						if(as[1].equalsIgnoreCase("empty")) {
							i7 = 0;
						} else if(as[1].equalsIgnoreCase("full")) {
							i7 = 20;
						} else if(as[1].equalsIgnoreCase("inf") || as[1].equalsIgnoreCase("infinite")) {
							i7 = 0x7fffffff;
						} else {
							if(as[1].equalsIgnoreCase("enable")) {
								disableHunger = true;
								sendMessage("Hunger was enabled");
								return;
							}
							if(as[1].equalsIgnoreCase("disable")) {
								disableHunger = false;
								sendMessage("Hunger was disabled");
								return;
							} else {
								sendError(ERRMSG_PARSE);
								return;
							}
						}
						ep.foodStats.setFoodLevel(i7);
						sendMessage((new StringBuilder()).append("Hunger level set to ").append(as[1]).append("(").append(i7).append(")").toString());
					} else if(as[0].equalsIgnoreCase("feed")) {
						if(as.length < 2) {
							sendError(ERRMSG_PARAM);
							return;
						}
						int j7 = 0;
						try {
							j7 = Integer.parseInt(as[1]);
						} catch (Exception exception19) {
							sendError(ERRMSG_PARSE);
							return;
						}
						ep.foodStats.setFoodLevel(ep.foodStats.getFoodLevel() + j7);
						sendMessage((new StringBuilder()).append("Food level set to ").append(ep.foodStats.getFoodLevel()).toString());
					} else if(as[0].equalsIgnoreCase("hardcore")) {
						if(as.length < 2) {
							sendError(ERRMSG_PARAM);
							return;
						}
						NBTTagCompound nbttagcompound = mc.theWorld.worldInfo.getNBTTagCompound();
						if(as[1].equalsIgnoreCase("enable")) {
							nbttagcompound.setBoolean("hardcore", true);
							sendMessage("Hardcore mode enabled! Don't die!");
						} else if(as[1].equalsIgnoreCase("disable")) {
							nbttagcompound.setBoolean("hardcore", false);
							sendMessage("Hardcore mode disabled.");
						} else {
							sendError(ERRMSG_PARSE);
							return;
						}
						mc.theWorld.worldInfo = new WorldInfo(nbttagcompound);
					} else if(as[0].equalsIgnoreCase("xp")) {
						if(as.length < 2) {
							sendError(ERRMSG_PARAM);
							return;
						}
						if(as[1].equalsIgnoreCase("get")) {
							int k7 = (int) (ep.currentXP * 100F);
							sendMessage((new StringBuilder()).append("Current player XP is ").append(ep.totalXP).append(" at level ").append(ep.playerLevel).append(", ").append(k7).append("% to next level.").toString());
							return;
						}
						if(as.length < 3) {
							sendError(ERRMSG_PARAM);
							return;
						}
						int l7 = 0;
						try {
							l7 = Integer.parseInt(as[2]);
						} catch (Exception exception20) {
							sendError(ERRMSG_PARSE);
							return;
						}
						if(as[1].equalsIgnoreCase("add")) {
							for(int i14 = l7; i14 > 0;) {
								i14 -= ep.xpBarCap();
								ep.increaseXP(i14 >= 0 ? ep.xpBarCap() : ep.xpBarCap() + i14);
							}

							sendMessage((new StringBuilder()).append(l7).append(" xp was added to your experience").toString());
						} else if(as[1].equalsIgnoreCase("set")) {
							ep.currentXP = 0.0F;
							ep.totalXP = 0;
							ep.playerLevel = 0;
							for(int j14 = l7; j14 > 0;) {
								j14 -= ep.xpBarCap();
								ep.increaseXP(j14 >= 0 ? ep.xpBarCap() : ep.xpBarCap() + j14);
							}

							sendMessage((new StringBuilder()).append("Your experience was set at ").append(l7).toString());
						} else if(as[1].equalsIgnoreCase("give")) {
							int k14 = 1;
							if(as.length > 3) {
								try {
									k14 = Integer.parseInt(as[3]);
								} catch (Exception exception28) {
									sendError(ERRMSG_PARSE);
									return;
								}
							}
							Random random1 = new Random();
							for(int k25 = 0; k25 < k14; k25++) {
								mc.theWorld.entityJoinedWorld(new EntityOrb(mc.theWorld, ep.posX + (random1.nextInt(6) - 3), ep.posY + random1.nextInt(3), ep.posZ + (random1.nextInt(6) - 3), l7));
							}

						} else {
							sendError(ERRMSG_PARSE);
							return;
						}
					} else if(as[0].equalsIgnoreCase("enchant")) {
						if(as.length < 2) {
							sendError(ERRMSG_PARAM);
							return;
						}
						if(as[1].equalsIgnoreCase("list")) {
							String s19 = "";
							for(int l14 = 0; l14 < Enchantment.enchantmentsList.length; l14++) {
								if(Enchantment.enchantmentsList[l14] != null) {
									s19 = (new StringBuilder()).append(s19).append(StatCollector.translateToLocal(Enchantment.enchantmentsList[l14].getName()).replace(' ', '_')).append(" (").append(l14).append("), ").toString();
								}
							}

							sendMessage("Enchantments [name (id)]:");
							sendMessage(s19);
							return;
						}
						if(ep.inventory.mainInventory[ep.inventory.currentItem] == null) {
							sendError("No currently selected item");
							return;
						}
						if(ep.inventory.mainInventory[ep.inventory.currentItem].isStackable()) {
							sendError("This item cannot be enchanted");
							return;
						}
						if(as[1].equalsIgnoreCase("remove")) {
							if(ep.inventory.mainInventory[ep.inventory.currentItem].hasEnchantment()) {
								ep.inventory.mainInventory[ep.inventory.currentItem].nbtData = null;
								sendMessage("Enchantment(s) for the current item were removed");
							} else {
								sendMessage("Current item doesn't have any enchantments");
							}
							return;
						}
						if(as.length < 3) {
							sendError(ERRMSG_PARAM);
							return;
						}
						if(as[1].equalsIgnoreCase("add")) {
							int i8 = -1;
							Enchantment enchantment = null;
							try {
								i8 = Integer.parseInt(as[2]);
								enchantment = Enchantment.enchantmentsList[i8];
							} catch (NumberFormatException numberformatexception5) {
							} catch (Exception exception29) {
								sendError((new StringBuilder()).append("Unknown enchantment id ").append(i8).toString());
								return;
							}
							for(int i21 = 0; i21 < Enchantment.enchantmentsList.length; i21++) {
								if(Enchantment.enchantmentsList[i21] != null && as[2].equalsIgnoreCase(StatCollector.translateToLocal(Enchantment.enchantmentsList[i21].getName()).replace(' ', '_'))) {
									enchantment = Enchantment.enchantmentsList[i21];
								}
							}

							if(enchantment == null) {
								sendError((new StringBuilder()).append("Could not find specified enchantment: ").append(as[2]).toString());
								return;
							}
							int j21 = 0;
							if(as.length == 4) {
								try {
									j21 = Integer.parseInt(as[3]);
								} catch (Exception exception43) {
									sendError(ERRMSG_PARSE);
									return;
								}
								if(j21 > 127) {
									sendMessage("Maximum level of 127 was set");
								} else if(j21 < -128) {
									sendMessage("Minimum level of -128 was set");
								}
							}
							ep.inventory.mainInventory[ep.inventory.currentItem].addEnchantment(enchantment, j21);
							sendMessage((new StringBuilder()).append("The ").append(enchantment.getTranslatedName(j21)).append(" enchantment was added to the current item.").toString());
						} else {
							sendError(ERRMSG_PARSE);
							return;
						}
					} else if(as[0].equalsIgnoreCase("enderman")) {
						if(as.length < 3) {
							sendError(ERRMSG_PARAM);
							return;
						}
						if(as[1].equalsIgnoreCase("pickup")) {
							Field afield1[] = (net.minecraft.src.EntityEnderman.class).getDeclaredFields();
							boolean flag4 = as[2].equalsIgnoreCase("enable");
							Field afield2[] = afield1;
							int l25 = afield2.length;
							int l28 = 0;
							do {
								if(l28 >= l25) {
									break;
								}
								Field field = afield2[l28];
								field.setAccessible(true);
								Object obj6 = null;
								if((obj6 = field.get(null)) instanceof boolean[]) {
									boolean aflag[] = (boolean[]) obj6;
									aflag[Block.grass.blockID] = flag4;
									aflag[Block.dirt.blockID] = flag4;
									aflag[Block.sand.blockID] = flag4;
									aflag[Block.gravel.blockID] = flag4;
									aflag[Block.plantYellow.blockID] = flag4;
									aflag[Block.plantRed.blockID] = flag4;
									aflag[Block.mushroomBrown.blockID] = flag4;
									aflag[Block.mushroomRed.blockID] = flag4;
									aflag[Block.tnt.blockID] = flag4;
									aflag[Block.cactus.blockID] = flag4;
									aflag[Block.blockClay.blockID] = flag4;
									aflag[Block.pumpkin.blockID] = flag4;
									aflag[Block.melon.blockID] = flag4;
									aflag[Block.mycelium.blockID] = flag4;
									field.set(null, aflag);
									break;
								}
								l28++;
							} while(true);
							sendMessage((new StringBuilder()).append("Enderman pickup ").append(as[2]).toString());
						} else {
							sendError(ERRMSG_PARSE);
							return;
						}
					} else if(as[0].equalsIgnoreCase("fog")) {
						if(as.length == 1) {
							mc.gameSettings.setOptionValue(EnumOptions.RENDER_DISTANCE, 3);
						} else if(as[2].equalsIgnoreCase("tiny")) {
							mc.gameSettings.renderDistance = 3;
						} else if(as[2].equalsIgnoreCase("small")) {
							mc.gameSettings.renderDistance = 2;
						} else if(as[2].equalsIgnoreCase("normal")) {
							mc.gameSettings.renderDistance = 1;
						} else if(as[2].equalsIgnoreCase("far")) {
							mc.gameSettings.renderDistance = 0;
						} else {
							sendError(ERRMSG_PARSE);
							return;
						}
					} else if(as[0].equalsIgnoreCase("spawner")) {
						if(as.length < 2) {
							sendError(ERRMSG_PARAM);
							return;
						}
						SPCObjectHit spcobjecthit = getObjectHit();
						if(spcobjecthit == null) {
							sendError("Your cursor needs to be hitting a block");
							return;
						}
						String s33 = "";
						try {
							s33 = getEntityName(Integer.valueOf(Integer.parseInt(as[1])));
						} catch (Exception exception31) {
							s33 = as[1];
						}
						if(getEntity(s33) == null) {
							sendError("Invalid mob name, please try again");
							return;
						}
						if(mc.theWorld.getBlockId(spcobjecthit.blockx, spcobjecthit.blocky, spcobjecthit.blockz) != Block.mobSpawner.blockID) {
							mc.theWorld.setBlockWithNotify(spcobjecthit.blockx, spcobjecthit.blocky, spcobjecthit.blockz, Block.mobSpawner.blockID);
						}
						TileEntity tileentity = mc.theWorld.getBlockTileEntity(spcobjecthit.blockx, spcobjecthit.blocky, spcobjecthit.blockz);
						if(tileentity != null && (tileentity instanceof TileEntityMobSpawner)) {
							((TileEntityMobSpawner) tileentity).setMobID(s33);
						}
					} else if(as[0].equalsIgnoreCase("climb")) {
						if(as.length < 2) {
							ladderMode = !ladderMode;
						} else if(as[1].equalsIgnoreCase("enable")) {
							ladderMode = true;
						} else if(as[1].equalsIgnoreCase("disable")) {
							ladderMode = true;
						}
						sendMessage((new StringBuilder()).append("Climb mode is ").append(ladderMode ? "enabled" : "disabled").toString());
					} else if(as[0].equalsIgnoreCase("sprinting")) {
						if(as.length < 2) {
							sprinting = !sprinting;
						} else if(as[1].equalsIgnoreCase("enable")) {
							sprinting = true;
						} else if(as[1].equalsIgnoreCase("disable")) {
							sprinting = false;
						}
						sendMessage((new StringBuilder()).append("Sprinting mode is ").append(sprinting ? "enabled" : "disabled").toString());
					} else if(as[0].equalsIgnoreCase("calc") || as[0].equalsIgnoreCase("=")) {
						double d8 = 0.0D;
						byte byte4 = -1;
						boolean flag9 = false;
						for(int i29 = 1; i29 < as.length; i29++) {
							if(!flag9 && byte4 == -1) {
								try {
									d8 = Double.parseDouble(as[i29]);
									flag9 = true;
									continue;
								} catch (Exception exception49) {
									sendError(ERRMSG_PARSE);
								}
								return;
							}
							if(byte4 == -1) {
								if(as[i29].equalsIgnoreCase("+")) {
									byte4 = 0;
									continue;
								}
								if(as[i29].equalsIgnoreCase("-")) {
									byte4 = 1;
									continue;
								}
								if(as[i29].equalsIgnoreCase("*")) {
									byte4 = 2;
									continue;
								}
								if(as[i29].equalsIgnoreCase("/")) {
									byte4 = 3;
								}
								continue;
							}
							try {
								double d23 = Double.parseDouble(as[i29]);
								switch(byte4) {
								case 0: // '\0'
									d8 += d23;
									break;

								case 1: // '\001'
									d8 -= d23;
									break;

								case 2: // '\002'
									d8 *= d23;
									break;

								case 3: // '\003'
									d8 /= d23;
									break;

								default:
									sendError(ERRMSG_PARSE);
									return;
								}
								byte4 = -1;
								continue;
							} catch (Exception exception50) {
								sendError(ERRMSG_PARSE);
							}
							return;
						}

						sendMessage((new StringBuilder()).append("").append(d8).toString());
					} else if(as[0].equalsIgnoreCase("criticalhit")) {
						if(as.length == 1) {
							criticalHit = !criticalHit;
						} else if(as[1].equalsIgnoreCase("enable")) {
							criticalHit = true;
						} else if(as[1].equalsIgnoreCase("disable")) {
							criticalHit = false;
						} else {
							sendError(ERRMSG_PARSE);
							return;
						}
						sendMessage(criticalHit ? "Every hit is a critical hit!" : "Critical hit mode disabled");
					} else if(as[0].equalsIgnoreCase("size")) {
						if(as.length < 2) {
							sendError(ERRMSG_PARAM);
							return;
						}
						if(as[1].equalsIgnoreCase("reset")) {
							playerSize = 2D;
							sendMessage("Player size was reset");
						} else {
							try {
								playerSize = Double.parseDouble(as[1]);
								ep.boundingBox.contract(1.0D, 1.0D, 1.0D);
								System.out.println((new StringBuilder()).append(ep.boundingBox.minX).append(" ").append(ep.boundingBox.minY).append(" ").append(ep.boundingBox.minZ).append(" ").append(ep.boundingBox.maxX).append(" ").append(ep.boundingBox.maxY).append(" ").append(ep.boundingBox.maxZ).toString());
								sendMessage((new StringBuilder()).append("Player size set to ").append(as[1]).toString());
							} catch (Exception exception11) {
								sendError(ERRMSG_PARSE);
							}
						}
					} else if(as[0].equalsIgnoreCase("clouds")) {
						if(as.length == 1) {
							mc.gameSettings.clouds = !mc.gameSettings.clouds;
						} else if(as[1].equalsIgnoreCase("enable")) {
							mc.gameSettings.clouds = true;
						} else if(as[1].equalsIgnoreCase("disable")) {
							mc.gameSettings.clouds = false;
						} else {
							sendError(ERRMSG_PARSE);
							return;
						}
						sendMessage((new StringBuilder()).append("Clouds are now ").append(mc.gameSettings.clouds ? "enabled" : "disabled").toString());
					} else if(!as[0].equalsIgnoreCase("test")) {
						if(as[0].equalsIgnoreCase("test2")) {
							MapData mapdata = Item.map.getMapData(ep.inventory.mainInventory[ep.inventory.currentItem], mc.theWorld);
							double d10 = ep.posX;
							double d18 = ep.posY;
							double d24 = ep.posZ;
							for(int l32 = -512; l32 < 512; l32 += 32) {
								for(int j33 = -512; j33 < 512; j33 += 32) {
									ep.setPosition(mapdata.xCenter + l32, 127D, mapdata.zCenter + j33);
									Item.map.updateMapData(mc.theWorld, ep, mapdata);
								}

							}

							ep.setPosition(d10, d18, d24);
						} else if(as[0].equalsIgnoreCase("help") || as[0].equalsIgnoreCase("h")) {
							if(as.length < 2) {
								String s21 = "";
								TreeSet treeset = new TreeSet(commands.keySet());
								Iterator iterator7 = treeset.iterator();
								do {
									if(!iterator7.hasNext()) {
										break;
									}
									String s48 = (String) iterator7.next();
									if(!s48.startsWith("/")) {
										String s50 = (String) commands.get(s48);
										if(iterator7.hasNext()) {
											s21 = (new StringBuilder()).append(s21).append(s50).append(", ").toString();
										} else {
											s21 = (new StringBuilder()).append(s21).append(s50).toString();
										}
									}
								} while(true);
								sendMessage("Commands:");
								sendMessage(s21);
							} else if(commands.containsKey(as[1])) {
								String s22 = (String) commands.get(as[1]);
								if(s22 == null) {
									return;
								}
								String as7[] = (String[]) CMDS.get(s22);
								if(as7 == null || as7.length != 3) {
									return;
								}
								helpMessage(as7[0], (new StringBuilder()).append(as[1]).append(" ").append(as7[1]).toString(), (new StringBuilder()).append("/").append(as[1]).append(" ").append(as7[2]).toString());
							}
						}
					}
				}
			}
		}
	}

	public void setWindowSize(int i, int j) {
		Frame frame = Frame.getFrames()[0];
		frame.setSize(i, j);
	}

	public Class getClass(String s) {
		Class class1 = getEntity(s);
		if(class1 != null) {
			return class1;
		}
		try {
			int i = Integer.parseInt(s);
			Class class2 = (Class) getEntityIdList().get(s);
			if(class2 != null) {
				return class2;
			}
		} catch (NumberFormatException numberformatexception) {
		}
		try {
			return Class.forName(s).asSubclass(net.minecraft.src.Entity.class);
		} catch (Throwable throwable) {
			sendDebug((new StringBuilder()).append("Invalid mob name: ").append(s).toString());
		}
		return null;
	}

	static void setFinal(Field field, Object obj, Object obj1) {
		try {
			field.setAccessible(true);
			Field field1 = (java.lang.reflect.Field.class).getDeclaredField("modifiers");
			field1.setAccessible(true);
			field1.setInt(field, field.getModifiers() & 0xffffffef);
			field.set(obj1, obj);
		} catch (Exception exception) {
		}
	}

	public void confuse(EntityCreature entitycreature, EntityCreature entitycreature1) {
		entitycreature.setEntityToAttack(entitycreature1);
	}

	public java.util.List getAllMobs(Class class1, double d) {
		ArrayList arraylist = new ArrayList();
		for(int i = 0; i < mc.theWorld.loadedEntityList.size(); i++) {
			Entity entity = mc.theWorld.loadedEntityList.get(i);
			if(entity != mc.thePlayer && !entity.isDead && class1.isInstance(entity) && (d <= 0.0D || mc.thePlayer.getDistanceSqToEntity(entity) <= d)) {
				arraylist.add(entity);
			}
		}

		Collections.sort(arraylist, new Comparator() {

			public int compare(Entity entity1, Entity entity2) {
				double d1 = mc.thePlayer.getDistanceSqToEntity(entity1) - mc.thePlayer.getDistanceSqToEntity(entity2);
				return d1 < 0.0D ? -1 : ((byte) (d1 > 0.0D ? 1 : 0));
			}

			@Override
			public int compare(Object obj, Object obj1) {
				return compare((Entity) obj, (Entity) obj1);
			}

		});
		return arraylist;
	}

	public Class getEntityClass(String s) {
		Class class1 = (Class) getEntityList().get(s);
		if(class1 != null) {
			return class1;
		}
		try {
			int i = Integer.parseInt(s);
			Class class2 = (Class) getEntityIdList().get(s);
			if(class2 != null) {
				return class2;
			}
		} catch (NumberFormatException numberformatexception) {
		}
		try {
			return Class.forName(s).asSubclass(net.minecraft.src.Entity.class);
		} catch (Throwable throwable) {
			sendError((new StringBuilder()).append("Invalid mob name: ").append(s).toString());
		}
		return null;
	}

	public SoundSystem getSoundSystem() {
		if(sound != null) {
			return sound;
		}
		try {
			Field afield[] = (net.minecraft.src.SoundManager.class).getDeclaredFields();
			Field afield1[] = afield;
			int i = afield1.length;
			for(int j = 0; j < i; j++) {
				Field field = afield1[j];
				field.setAccessible(true);
				Object obj = field.get(mc.sndManager);
				if(obj instanceof SoundSystem) {
					sound = (SoundSystem) obj;
					return sound;
				}
			}

		} catch (Throwable throwable) {
		}
		return null;
	}

	public boolean playRandomMusic() {
		boolean flag = false;
		try {
			Field afield[] = (net.minecraft.src.SoundManager.class).getDeclaredFields();
			int i = 0;
			do {
				if(i >= 2) {
					break;
				}
				Field afield1[] = afield;
				int k = afield1.length;
				for(int l = 0; l < k;) {
					Field field = afield1[l];
					field.setAccessible(true);
					try {
						if(field.getInt(mc.sndManager) <= 255) {
							continue;
						}
						field.set(mc.sndManager, Integer.valueOf(0));
						flag = true;
						break;
					} catch (Exception exception1) {
						l++;
					}
				}

				if(flag) {
					break;
				}
				for(int j = 0; j < 256; j++) {
					mc.sndManager.playRandomMusicIfReady();
				}

				i++;
			} while(true);
		} catch (Exception exception) {
			printStackTrace(exception);
		}
		mc.sndManager.playRandomMusicIfReady();
		return flag;
	}

	public Settings getSettings(String s) {
		return new Settings(new File(MODDIR, (new StringBuilder()).append(s).append(".properties").toString()));
	}

	public void delete(File file) {
		if(file.isFile()) {
			file.delete();
			return;
		}
		File afile[] = file.listFiles();
		File afile1[] = afile;
		int i = afile1.length;
		for(int j = 0; j < i; j++) {
			File file1 = afile1[j];
			if(file1.isFile()) {
				file1.delete();
			} else {
				delete(file1);
			}
		}

		file.delete();
	}

	public void transferInventory(IInventory iinventory, IInventory iinventory1) {
		int i = 0;
		if(iinventory == null) {
			return;
		}
		try {
			int j = 0;
			do {
				if(j >= iinventory.getSizeInventory()) {
					break;
				}
				if(iinventory1 == null) {
					iinventory.setInventorySlotContents(j, null);
				} else {
					try {
						for(; iinventory1.getStackInSlot(i) != null; i++) {
						}
						if(i > iinventory1.getInventoryStackLimit()) {
							break;
						}
						iinventory1.setInventorySlotContents(i, iinventory.getStackInSlot(j));
						iinventory.setInventorySlotContents(j, null);
					} catch (Exception exception1) {
						break;
					}
				}
				j++;
			} while(true);
		} catch (Exception exception) {
			printStackTrace(exception);
		}
	}

	public String join(String as[], int i, int j) {
		String s = "";
		for(int k = i; k < j || k < as.length; k++) {
			s = (new StringBuilder()).append(s).append(as[k]).append(" ").toString();
		}

		return s.trim();
	}

	public void sendMessage(String s) {
		if(!output) {
			return;
		} else {
			sendMessage(s, textcolornormal);
			return;
		}
	}

	public void sendMessage(String s, char c) {
		if(!output) {
			return;
		}
		String s1 = VALID_COLOURS;
		int i = s1.indexOf(c);
		if(i < 0 || i > 15) {
			if(c == 'r' || c == 'R') {
				c = textcolorrandom.charAt((new Random()).nextInt(textcolorrandom.length()));
			} else {
				c = 'f';
			}
		}
		ep.addChatMessage((new StringBuilder()).append("\247").append(c).append(s).toString());
	}

	public void sendError(String s) {
		if(!output) {
			return;
		} else {
			sendMessage(s, textcolorerror);
			return;
		}
	}

	public void sendDebug(String s) {
		if(DEBUG) {
			System.out.println((new StringBuilder()).append(System.currentTimeMillis()).append("-DEBUG: ").append(s).toString());
		}
	}

	public static void printStackTrace(Throwable throwable) {
		if(DEBUG) {
			throwable.printStackTrace();
		}
	}

	public void printCurrentTime() {
		int ai[] = getTime();
		String s = ai[1] >= 10 ? (new StringBuilder()).append("").append(ai[1]).toString() : (new StringBuilder()).append("0").append(ai[1]).toString();
		String s1 = ai[0] >= 10 ? (new StringBuilder()).append("").append(ai[0]).toString() : (new StringBuilder()).append("0").append(ai[0]).toString();
		sendMessage((new StringBuilder()).append("Day: ").append(ai[2]).append(" at ").append(s).append(":").append(s1).toString());
	}

	public int[] getTime() {
		long l = mc.theWorld.worldInfo.getWorldTime();
		int i = (int) (l / 1000L / 24L);
		int j = (int) ((l / 1000L) % 24L);
		int k = (int) (((l % 1000L) / 1000D) * 60D);
		return (new int[]{k, j, i});
	}

	public void helpMessage(String s, String s1, String s2) {
		if(s != null) {
			sendMessage("Description:");
			sendMessage((new StringBuilder()).append("\t").append(s).toString());
		}
		if(s1 != null) {
			sendMessage("Syntax:");
			sendMessage((new StringBuilder()).append("\t").append(s1).toString());
		}
		if(s2 != null) {
			sendMessage("Example:");
			sendMessage((new StringBuilder()).append("\t").append(s2).toString());
		}
	}

	public String positionAsString() {
		DecimalFormat decimalformat = new DecimalFormat("#.##");
		return (new StringBuilder()).append("(").append(decimalformat.format(ep.posX)).append(",").append(decimalformat.format(ep.posY)).append(",").append(decimalformat.format(ep.posZ + 1.0D)).append(")").toString();
	}

	public SPCObjectHit getObjectHit() {
		return new SPCObjectHit(mc.objectMouseOver);
	}

	public void handleLeftClick() {
		try {
			SPCObjectHit spcobjecthit = getObjectHit();
			PLUGIN_MANAGER.callPluginMethods(PLUGIN_HANDLELEFTCLICK, new Object[]{spcobjecthit});
			PLUGIN_MANAGER.callPluginMethods(PLUGIN_HANDLELEFTBUTTONDOWN, new Object[]{spcobjecthit});
			lastleftcall = System.currentTimeMillis();
		} catch (Exception exception) {
			printStackTrace(exception);
		}
	}

	public void handleLeftButtonDown() {
		SPCObjectHit spcobjecthit = getObjectHit();
		if(superpunch > 0.0D && spcobjecthit != null && spcobjecthit.entity != null) {
			SPCEntity spcentity = spcobjecthit.entity;
			spcentity.setRotation(ep.rotationYaw, ep.rotationPitch);
			double d = (-MathHelper.sin((spcentity.getYaw() / 180F) * 3.141593F) * MathHelper.cos((spcentity.getPitch() / 180F) * 3.141593F)) * superpunch;
			double d1 = (MathHelper.cos((spcentity.getYaw() / 180F) * 3.141593F) * MathHelper.cos((spcentity.getPitch() / 180F) * 3.141593F)) * superpunch;
			double d2 = (-MathHelper.sin((spcentity.getPitch() / 180F) * 3.141593F)) * superpunch;
			spcentity.setMotion(new SPCPoint(d, d1, d2));
		}
		try {
			if(System.currentTimeMillis() > lastleftcall + 200L) {
				lastleftcall = System.currentTimeMillis();
				PLUGIN_MANAGER.callPluginMethods(PLUGIN_HANDLELEFTBUTTONDOWN, new Object[]{spcobjecthit});
			}
		} catch (Exception exception) {
			printStackTrace(exception);
		}
	}

	public void handleRightClick() {
		try {
			SPCObjectHit spcobjecthit = getObjectHit();
			PLUGIN_MANAGER.callPluginMethods(PLUGIN_HANDLERIGHTCLICK, new Object[]{spcobjecthit});
			PLUGIN_MANAGER.callPluginMethods(PLUGIN_HANDLERIGHTBUTTONDOWN, new Object[]{spcobjecthit});
			lastrightcall = System.currentTimeMillis();
		} catch (Exception exception) {
			printStackTrace(exception);
		}
	}

	public void handleRightButtonDown() {
		try {
			SPCObjectHit spcobjecthit = getObjectHit();
			if(System.currentTimeMillis() > lastrightcall + 200L) {
				lastrightcall = System.currentTimeMillis();
				PLUGIN_MANAGER.callPluginMethods(PLUGIN_HANDLERIGHTBUTTONDOWN, new Object[]{spcobjecthit});
			}
		} catch (Exception exception) {
			printStackTrace(exception);
		}
	}

	public void fly(int i) {
		if(!flying) {
			return;
		}
		if(flymode.equalsIgnoreCase("minecraft")) {
			ep.motionY = 0.0D;
			ep.onGround = false;
			return;
		}
		float f = ep.rotationPitch;
		GameSettings gamesettings = mc.gameSettings;
		boolean flag = false;
		boolean flag1 = false;
		ep.onGround = true;
		if(i == gamesettings.keyBindForward.keyCode) {
			f = -f;
			flag = true;
		} else if(i == gamesettings.keyBindBack.keyCode) {
			flag = true;
		} else {
			if(i == gamesettings.keyBindSneak.keyCode) {
				ep.jump();
				ep.motionY = -ep.motionY;
				flag1 = true;
				return;
			}
			if(i == gamesettings.keyBindJump.keyCode) {
				ep.jump();
				flag1 = true;
				return;
			}
		}
		if(flymode.equalsIgnoreCase("standard")) {
			ep.motionY = 0.0D;
			return;
		}
		if((MathHelper.sqrt_double(ep.motionX * ep.motionX) > 0.01D || MathHelper.sqrt_double(ep.motionZ * ep.motionZ) > 0.01D) && (flag || flag1)) {
			ep.motionY += (f / 360F) * speed;
		} else if(!flag1) {
			ep.motionY = 0.0D;
		}
	}

	public void handleKeyPress(int i) {
		GameSettings gamesettings = mc.gameSettings;
		if(i == gamesettings.keyBindChat.keyCode) {
			if(mc.currentScreen == null && !opened) {
				mc.displayGuiScreen(new GuiChat(false));
				opened = true;
			}
			opened = false;
		} else if(i == gamesettings.keyBindForward.keyCode) {
			fly(i);
		} else if(i == gamesettings.keyBindBack.keyCode) {
			fly(i);
		} else if(i == gamesettings.keyBindSneak.keyCode) {
			fly(i);
		} else if(i == gamesettings.keyBindJump.keyCode) {
			fly(i);
		}
		if(bound.containsKey((new StringBuilder()).append("").append(i).toString())) {
			ep.sendChatMessage(bound.getProperty((new StringBuilder()).append("").append(i).toString()));
		}
	}

	public void handleKeyDown(int i) {
		GameSettings gamesettings = mc.gameSettings;
		if(i == gamesettings.keyBindForward.keyCode) {
			fly(i);
		} else if(i == gamesettings.keyBindBack.keyCode) {
			fly(i);
		} else if(i == gamesettings.keyBindSneak.keyCode) {
			fly(i);
		} else if(i == gamesettings.keyBindJump.keyCode) {
			fly(i);
		}
	}

	public void startup() {
		if(!startup.equalsIgnoreCase(""))
			processCommand(startup);
	}

	public void beforeUpdate() {
		if(ismultiplayer) {
			return;
		}
		if(HAS_STARTED_UP == null) {
			HAS_STARTED_UP = new Object();
			startup();
		}
		if(flying) {
			walked = ep.distanceWalkedModified;
		}
		if(mc.currentScreen == null) {
			if(Mouse.isButtonDown(0)) {
				if(!leftbutton) {
					handleLeftClick();
				} else {
					handleLeftButtonDown();
				}
				leftbutton = true;
			} else {
				leftbutton = false;
			}
			if(Mouse.isButtonDown(1)) {
				if(!rightbutton) {
					handleRightClick();
				} else {
					handleRightButtonDown();
				}
				rightbutton = true;
			} else {
				rightbutton = false;
			}
		}
		if(mc.currentScreen == null) {
			GameSettings gamesettings = mc.gameSettings;
			Vector vector = new Vector();
			vector.add(Integer.valueOf(gamesettings.keyBindChat.keyCode));
			vector.add(Integer.valueOf(gamesettings.keyBindForward.keyCode));
			vector.add(Integer.valueOf(gamesettings.keyBindBack.keyCode));
			vector.add(Integer.valueOf(gamesettings.keyBindSneak.keyCode));
			vector.add(Integer.valueOf(gamesettings.keyBindJump.keyCode));
			for(Iterator iterator = bound.keySet().iterator(); iterator.hasNext();) {
				try {
					vector.add(Integer.valueOf(Integer.parseInt((String) iterator.next())));
				} catch (Exception exception) {
				}
			}

			for(int i = 0; i < vector.size(); i++) {
				int j = ((Integer) vector.elementAt(i)).intValue();
				try {
					if(Keyboard.isKeyDown(j)) {
						if(keyboard.contains(Integer.valueOf(j))) {
							handleKeyDown(j);
						} else {
							handleKeyPress(j);
							keyboard.add(Integer.valueOf(j));
						}
					} else {
						keyboard.remove(new Integer(j));
					}
					continue;
				} catch (Exception exception1) {
					try {
						bound.remove((new StringBuilder()).append(j).append("").toString());
					} catch (Exception exception2) {
					}
					printStackTrace(exception1);
				}
			}

		}
	}

	public void afterUpdate() {
		if(ismultiplayer) {
			return;
		}
		if(!waterdamage) {
			ep.func_41003_g(300);
		}
		if(!damage) {
			ep.hurtTime = 0;
			ep.heartsLife = 0;
		}
		if(timeschedule != null) {
			int ai[] = getTime();
			if(lastrift != ai[2] && (ai[1] > timeschedule[2] || ai[1] >= timeschedule[2] && ai[0] > timeschedule[3])) {
				int l = 0;
				if(timeschedule[0] < timeschedule[2] || timeschedule[0] <= timeschedule[2] && timeschedule[1] < timeschedule[2]) {
					l = 1;
				}
				mc.theWorld.worldInfo.setWorldTime((ai[2] + l) * 24000 + (timeschedule[0] % 24) * 1000 + (int) (((timeschedule[1] % 60) / 60D) * 1000D));
				lastrift = ai[2];
				saveSettings();
			}
		}
		if(!dropitems || instantplant) {
			int i = 128;
			java.util.List list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(ep, AxisAlignedBB.getBoundingBox(ep.posX - i, ep.posY - i, ep.posZ - i, ep.posX + i, ep.posY + i, ep.posZ + i));
			for(int i1 = 0; i1 < list.size(); i1++) {
				Entity entity = (Entity) list.get(i1);
				if(!(entity instanceof EntityItem)) {
					continue;
				}
				EntityItem entityitem = (EntityItem) entity;
				if(!dropitems && entityitem.age >= 0) {
					mc.theWorld.setEntityDead(entityitem);
					continue;
				}
				if(!instantplant || entityitem.item.itemID != Block.sapling.blockID) {
					continue;
				}
				BlockSapling blocksapling = (BlockSapling) Block.sapling;
				if(!blocksapling.canPlaceBlockAt(mc.theWorld, (int) entity.posX, (int) entity.posY, (int) entity.posZ)) {
					continue;
				}
				mc.theWorld.setBlockWithNotify((int) entity.posX, (int) entity.posY, (int) entity.posZ, Block.sapling.blockID);
				if(instantgrow) {
					blocksapling.growTree(mc.theWorld, (int) entity.posX, (int) entity.posY, (int) entity.posZ, new Random());
				}
				mc.theWorld.setEntityDead(entityitem);
			}

		}
		if(mobsfrozen || !mobdamage) {
			for(int j = 0; j < mc.theWorld.loadedEntityList.size(); j++) {
				if(!(mc.theWorld.loadedEntityList.get(j) instanceof EntityLiving) || (mc.theWorld.loadedEntityList.get(j) instanceof EntityPlayer)) {
					continue;
				}
				EntityLiving entityliving = (EntityLiving) mc.theWorld.loadedEntityList.get(j);
				if(mobsfrozen) {
					entityliving.setPosition(entityliving.prevPosX, entityliving.prevPosY, entityliving.prevPosZ);
					entityliving.motionX = 0.0D;
					entityliving.motionY = 0.0D;
					entityliving.motionZ = 0.0D;
				}
				if((entityliving instanceof EntityCreature) && !(entityliving instanceof EntityPlayerSP)) {
					((EntityCreature) entityliving).attackTime = 20;
				}
				if(entityliving instanceof EntityCreeper) {
					((EntityCreeper) entityliving).timeSinceIgnited = -1;
				}
			}

		}
		if(timespeed != 0) {
			mc.theWorld.worldInfo.setWorldTime(mc.theWorld.worldInfo.getWorldTime() + timespeed);
		}
		if(infiniteitems) {
			for(int k = 0; k < ep.inventory.mainInventory.length; k++) {
				if(ep.inventory.mainInventory[k] != null) {
					ep.inventory.mainInventory[k].stackSize = ep.inventory.mainInventory[k].getMaxStackSize();
				}
			}

		}
		if(flying) {
			fly(-1);
			ep.distanceWalkedModified = walked;
		}
		PLUGIN_MANAGER.callPluginMethods(PLUGIN_ATUPDATE, new Object[0]);
	}

	public boolean growPlant(int i, int j, int k, Random random, WorldGenerator worldgenerator) {
		if(mc.theWorld.getBlockId(i, j, k) == Block.sapling.blockID) {
			((BlockSapling) Block.sapling).growTree(mc.theWorld, i, j, k, random);
			return true;
		}
		if(mc.theWorld.getBlockId(i, j, k) == Block.crops.blockID) {
			mc.theWorld.setBlockMetadataWithNotify(i, j, k, 7);
			return true;
		}
		if(mc.theWorld.getBlockId(i, j, k) == Block.cactus.blockID || mc.theWorld.getBlockId(i, j, k) == Block.reed.blockID) {
			int l = mc.theWorld.getBlockId(i, j, k);
			int i1 = 1;
			int j1;
			do {
				j1 = i1;
				if(mc.theWorld.getBlockId(i, j + i1, k) == l) {
					i1++;
				}
				if(mc.theWorld.getBlockId(i, j - i1, k) == l) {
					i1++;
				}
			} while(j1 != i1);
			if(i1 < 3) {
				for(int k1 = 0; k1 <= 3 - i1; k1++) {
					mc.theWorld.setBlockWithNotify(i, j + k1, k, l);
				}

				return true;
			}
		}
		return false;
	}

	public ItemStack getItemStack(String as[]) {
		if(as == null) {
			return null;
		}
		Object obj = null;
		try {
			if(as.length == 1) {
				ItemStack itemstack = getItem(as);
				Item item = itemstack.getItem();
				System.out.println(itemstack.getItemDamage());
				return new ItemStack(item, item.maxStackSize, itemstack.getItemDamage());
			}
			int j;
			try {
				String as1[] = as[0].split("(\\^|:)");
				int i = Integer.parseInt(as1[0]);
				int k = Item.itemsList[i].maxStackSize;
				int i1 = 0;
				if(as.length > 1) {
					k = Integer.parseInt(as[1]);
				}
				if(as1.length == 2) {
					i1 = Integer.parseInt(as1[1]);
				} else if(as.length > 2) {
					i1 = Integer.parseInt(as[2]);
				}
				return new ItemStack(Item.itemsList[i], k, i1);
			} catch (Exception exception) {
				j = 1;
			}
			int l = 0;
			try {
				if(as.length > 1) {
					Integer.parseInt(as[as.length - 1]);
					j = -1;
				}
				if(as.length > 2) {
					Integer.parseInt(as[as.length - 2]);
					l = -1;
				}
			} catch (Exception exception2) {
			}
			byte byte0 = 0;
			if(l == -1) {
				j = Integer.parseInt(as[as.length - 2]);
				l = Integer.parseInt(as[as.length - 1]);
				byte0 = 2;
			} else if(j == -1) {
				j = Integer.parseInt(as[as.length - 1]);
				byte0 = 1;
			}
			String s = "";
			for(int j1 = 0; j1 < as.length - byte0; j1++) {
				s = (new StringBuilder()).append(s).append(as[j1]).append(" ").toString();
			}

			String as2[] = s.split("(\\^|:)");
			int k1 = ITEMNAMES.indexOf(as2[0].trim().toLowerCase());
			if(k1 > -1) {
				Item item1 = Item.itemsList[k1];
				if(byte0 == 0) {
					j = item1.getItemStackLimit();
				}
				if(as2.length == 2) {
					l = Integer.parseInt(as2[1].trim());
				}
				return new ItemStack(Item.itemsList[k1], j, l);
			}
		} catch (Exception exception1) {
			return null;
		}
		return null;
	}

	public ItemStack getItem(String as[]) {
		if(as == null) {
			return null;
		}
		int i = 0;
		if(as.length == 1) {
			try {
				String as1[] = as[0].split("(\\^|:)");
				int j = Integer.parseInt(as1[0]);
				try {
					i = Integer.parseInt(as1[1]);
				} catch (Exception exception1) {
					i = 0;
				}
				return new ItemStack(Item.itemsList[j], 0, i);
			} catch (Exception exception) {
			}
		}
		String s = "";
		for(int k = 0; k < as.length; k++) {
			s = (new StringBuilder()).append(s).append(as[k]).append(" ").toString();
		}

		String as2[] = s.split("(\\^|:)");
		try {
			i = Integer.parseInt(as2[1].trim());
		} catch (Exception exception2) {
			i = 0;
		}
		int l = ITEMNAMES.indexOf(as2[0].trim().toLowerCase());
		if(l > -1) {
			return new ItemStack(Item.itemsList[l], 0, i);
		} else {
			return null;
		}
	}

	public static boolean copyDirectory(File file, File file1, IProgressUpdate iprogressupdate) {
		if(!file.isDirectory()) {
			return false;
		}
		if(!file1.exists()) {
			file1.mkdirs();
		}
		if(!file1.isDirectory()) {
			return false;
		}
		if(iprogressupdate != null) {
			iprogressupdate.displayLoadingString("Moving chunks");
		}
		try {
			File afile[] = file.listFiles();
			for(int i = 0; i < afile.length; i++) {
				if(afile[i].isDirectory()) {
					copyDirectory(afile[i], new File(file1, afile[i].getName()), null);
				} else if(afile[i].isFile()) {
					copyFile(afile[i], new File(file1, afile[i].getName()));
				}
				if(iprogressupdate != null) {
					iprogressupdate.setLoadingProgress((i * 100) / afile.length);
				}
			}

		} catch (Exception exception) {
			return false;
		}
		return true;
	}

	public static boolean copyFile(File file, File file1) {
		if(!file1.exists()) {
			try {
				file1.createNewFile();
			} catch (IOException ioexception) {
				return false;
			}
		}
		FileChannel filechannel = null;
		FileChannel filechannel1 = null;
		try {
			filechannel = (new FileInputStream(file)).getChannel();
			filechannel1 = (new FileOutputStream(file1)).getChannel();
			filechannel1.transferFrom(filechannel, 0L, filechannel.size());
		} catch (Exception exception) {
			boolean flag = false;
			return flag;
		} finally {
			try {
				if(filechannel != null) {
					filechannel.close();
				}
				if(filechannel1 != null) {
					filechannel1.close();
				}
			} catch (Exception exception2) {
			}
		}
		return true;
	}

	public void toggleLight(boolean flag) {
		WorldProvider worldprovider = ep.worldObj.worldProvider;
		if(flag) {
			for(int i = 0; i < worldprovider.lightBrightnessTable.length; i++) {
				worldprovider.lightBrightnessTable[i] = 1.0F;
			}

		} else {
			worldprovider.generateLightBrightnessTable();
		}
	}

	public void setItemMaxDamage() {
		for(int i = 0; i < Item.itemsList.length; i++) {
			if(Item.itemsList[i] == null || !(Item.itemsList[i] instanceof Item)) {
				continue;
			}
			if(Item.itemsList[i].getMaxDamage() > 0 && Item.itemsList[i].isDamageable()) {
				ITEM_MAX_DAMAGE[i] = Item.itemsList[i].getMaxDamage();
			}
			if(norepair && Item.itemsList[i].isDamageable()) {
				Item.itemsList[i].setMaxDamage(MAGICNUMBER);
			}
		}

	}

	public void destroyInventory() {
		for(int i = 0; i < ep.inventory.mainInventory.length; i++) {
			ep.inventory.mainInventory[i] = null;
		}

		for(int j = 0; j < ep.inventory.armorInventory.length; j++) {
			ep.inventory.armorInventory[j] = null;
		}

	}

	public int getBlockID(String s) {
		try {
			int i = Integer.parseInt(s);
			if(i >= 0 && i < Block.blocksList.length) {
				return i;
			}
		} catch (Exception exception) {
		}
		if(s.equalsIgnoreCase("air")) {
			return 0;
		}
		for(int j = 0; j < Block.blocksList.length; j++) {
			if(Block.blocksList[j] == null) {
				continue;
			}
			String s1 = Block.blocksList[j].getBlockName();
			s1 = s1.substring(s1.indexOf('.') + 1);
			if(s.equalsIgnoreCase(s1)) {
				return j;
			}
		}

		sendDebug("Block not found..");
		return -1;
	}

	public void changeSkin(EntityPlayer entityplayer, String s) {
		entityplayer.username = s;
		entityplayer.skinUrl = (new StringBuilder()).append("http://greenusercontent.net/mc/skins/").append(s).append(".png").toString();
		ep.worldObj.obtainEntitySkin(entityplayer);
		entityplayer.updateCloak();
	}

	public void sendBindList() {
		if(bound == null) {
			sendMessage("No key bindings found.");
			return;
		}
		Iterator iterator = bound.keySet().iterator();
		String s = "";
		while(iterator.hasNext()) {
			try {
				int i = Integer.parseInt((String) iterator.next());
				s = (new StringBuilder()).append(s).append(Keyboard.getKeyName(i)).append(", ").toString();
			} catch (Exception exception) {
			}
		}
		s = s.substring(0, s.length() - 2);
		if(!s.equalsIgnoreCase("")) {
			sendMessage("Key bindings:");
			sendMessage(s);
		} else {
			sendMessage("No key bindings found.");
		}
	}

	public String convertInput(String s) {
		if(s == null) {
			return s;
		}
		String as[] = s.split(" ");
		String s1 = as[0];
		if(alias.containsKey(as[0])) {
			s1 = alias.getString(as[0], as[0]);
		}
		return (new StringBuilder()).append(s1).append(" ").append(join(as, 1, as.length)).toString().trim();
	}

	public void addAlias(String s, String s1) {
		alias.set(s, s1);
		alias.save();
	}

	public void checkUpdateAvailable(Vector vector) {
		if(vector == null || vector.size() > 0) {
			updateAvailable(vector);
		} else {
			sendMessage("No new updates are available.");
		}
	}

	public void updateAvailable(Vector vector) {
		for(Iterator iterator = vector.iterator(); iterator.hasNext();) {
			HashMap hashmap = (HashMap) iterator.next();
			char c = textcolorrandom.charAt((new Random()).nextInt(textcolorrandom.length()));
			if(hashmap.get("message") == null || ((String) hashmap.get("message")).equalsIgnoreCase("")) {
				sendMessage((new StringBuilder()).append(hashmap.get("name")).append(" V").append(hashmap.get("version")).append(" now out! ").append(hashmap.get("website")).toString(), c);
			} else {
				sendMessage((String) hashmap.get("message"), c);
			}
		}

	}

	public boolean toggleMob(String s, boolean flag) {
		Class class1 = getEntity(s);
		if(class1 == null) {
			return false;
		}
		Field afield[] = (net.minecraft.src.BiomeGenBase.class).getDeclaredFields();
		Field afield1[] = afield;
		int i = afield1.length;
		label0: for(int j = 0; j < i; j++) {
			Field field = afield1[j];
			if(field == null) {
				continue;
			}
			try {
				field.setAccessible(true);
				field.get(null);
			} catch (Exception exception) {
				continue;
			}
			try {
				if(!(field.get(null) instanceof BiomeGenBase)) {
					continue;
				}
				BiomeGenBase biomegenbase = (BiomeGenBase) field.get(null);
				java.util.List alist[] = {biomegenbase.spawnableCreatureList, biomegenbase.spawnableMonsterList, biomegenbase.spawnableWaterCreatureList};
				java.util.List alist1[] = alist;
				int k = alist1.length;
				int l = 0;
				do {
					if(l >= k) {
						continue label0;
					}
					java.util.List list = alist1[l];
					if(flag) {
						Object obj;
						for(Iterator iterator = list.iterator(); iterator.hasNext(); System.out.println(((SpawnListEntry) obj).entityClass.getName())) {
							obj = iterator.next();
							if(!flag || !(obj instanceof SpawnListEntry)) {
								continue;
							}
							SpawnListEntry spawnlistentry = (SpawnListEntry) obj;
							if(spawnlistentry.entityClass == class1) {
								list.remove(obj);
							}
						}

					}
					l++;
				} while(true);
			} catch (Exception exception1) {
				exception1.printStackTrace();
			}
			return false;
		}

		return true;
	}

	public void noclip(boolean flag) {
		ep.fallDistance = 0.0F;
		if(flag) {
			prevflying = flying;
			prevdamage = damage;
			prevfiredamage = ep.isImmuneToFire;
			prevwaterdamage = waterdamage;
			prevfalldamage = falldamage;
			flying = true;
			damage = false;
			ep.isImmuneToFire = true;
			waterdamage = false;
			falldamage = false;
			ep.boundingBox.setBB(AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D));
		} else {
			flying = prevflying;
			damage = prevdamage;
			ep.isImmuneToFire = prevfiredamage;
			waterdamage = prevwaterdamage;
			falldamage = prevfalldamage;
			ep.setPosition(ep.posX, ep.posY, ep.posZ);
		}
	}

	static {
		try {
			PLUGIN_HANDLECOMMAND = (net.minecraft.src.SPCPlugin.class).getDeclaredMethod("handleCommand", new Class[]{java.lang.String[].class});
			PLUGIN_HANDLELEFTCLICK = (net.minecraft.src.SPCPlugin.class).getDeclaredMethod("handleLeftClick", new Class[]{net.minecraft.src.SPCObjectHit.class});
			PLUGIN_HANDLERIGHTCLICK = (net.minecraft.src.SPCPlugin.class).getDeclaredMethod("handleRightClick", new Class[]{net.minecraft.src.SPCObjectHit.class});
			PLUGIN_HANDLELEFTBUTTONDOWN = (net.minecraft.src.SPCPlugin.class).getDeclaredMethod("handleLeftButtonDown", new Class[]{net.minecraft.src.SPCObjectHit.class});
			PLUGIN_HANDLERIGHTBUTTONDOWN = (net.minecraft.src.SPCPlugin.class).getDeclaredMethod("handleRightButtonDown", new Class[]{net.minecraft.src.SPCObjectHit.class});
			PLUGIN_ATUPDATE = (net.minecraft.src.SPCPlugin.class).getDeclaredMethod("atUpdate", new Class[0]);
			PLUGIN_SETHELPER = (net.minecraft.src.SPCPlugin.class).getDeclaredMethod("setPlayerHelper", new Class[]{net.minecraft.src.PlayerHelper.class});
			PLUGIN_HANDLECUIEVENT = (net.minecraft.src.SPCPlugin.class).getDeclaredMethod("handleCUIEvent", new Class[]{java.lang.String.class, java.lang.String[].class});
		} catch (Throwable throwable) {
			printStackTrace(throwable);
		}
		CMDS = new HashMap();
		CMDS.put("=", new String[]{"Calculator command", "NUMBER OPERATOR NUMBER", "32 * 17 * 4 + 7"});
		CMDS.put("achievement", new String[]{"Achievement related commands", "<list|unlock>", "unlock"});
		CMDS.put("alias", new String[]{"Create an alias for a command", "<list|<ALIAS> {VALUE}>", "example msg hello world"});
		CMDS.put("ascend", new String[]{"Ascends the player to the platform above", "", ""});
		CMDS.put("atlantis", new String[]{"Makes your world like Atlantis, the legendary underwater city.", "", ""});
		CMDS.put("biome", new String[]{"Gets the current biome name", "", ""});
		CMDS.put("bind", new String[]{"Binds a keyboard key to a command", "<KEYNAME> <COMMAND>", "e /cannon 5"});
		CMDS.put("bindid", new String[]{"Binds a command to a keyboard key using the key id", "<ID> <COMMAND> {ARGS}", "22 /cannon 5"});
		CMDS.put("bring", new String[]{"Brings the specified entity to you.", "[ENTITY]", "item"});
		CMDS.put("calc", new String[]{"Calculator command", "NUMBER OPERATOR NUMBER", "32 * 17 * 4 + 7"});
		CMDS.put("cannon", new String[]{"Shoots TNT in the direction you are pointing, at the specified strength", "[strength]", "10"});
		CMDS.put("chest", new String[]{"Gives you access to chests.", "<drop|get|fill|swap|clear>", "get"});
		CMDS.put("clear", new String[]{"Clears the chat console.", "", ""});
		CMDS.put("clearwater", new String[]{"Toggles water clarity on/off", "", ""});
		CMDS.put("climb", new String[]{"Toggles climbing mode on/off", "[enable|disable]", "enable"});
		CMDS.put("clone", new String[]{"Clones the NPC which you are looking at", "[QUANTITY]", "10"});
		CMDS.put("config", new String[]{"Allows you to set the global configuration file", "<setglobal [reset]>", "setglobal"});
		CMDS.put("confusesuicide", new String[]{"Confuses nearby mobs to attack each other", "", ""});
		CMDS.put("clouds", new String[]{"Provides control on whether clouds are shown or not", "[enable|disable]", "disable"});
		CMDS.put("criticalhit", new String[]{"Turns critical hits always on", "[enable|disable]", "enable"});
		CMDS.put("cyclepainting", new String[]{"Cycles through the painting which you are pointing at", "", ""});
		CMDS.put("damage", new String[]{"Turns damage on/off", "", ""});
		CMDS.put("defuse", new String[]{"Defuses nearby TNT which has been primed", "[all]", "all"});
		CMDS.put("descend", new String[]{"Descends the player to the next platform below", "", ""});
		CMDS.put("destroy", new String[]{"Destroys the selected itemstack.", "[all]", ""});
		CMDS.put("diff", new String[]{"Sets the difficulty of the game. Valid values 0-3", "<VALUE>", "3"});
		CMDS.put("difficulty", new String[]{"Sets the difficulty of the game. Valid values 0-3", "<VALUE>", "3"});
		CMDS.put("drops", new String[]{"Turn block drops on or off.", "", ""});
		CMDS.put("dupe", new String[]{"Duplicates the selected itemstack.", "[all]", "all"});
		CMDS.put("duplicate", new String[]{"Duplicates the selected itemstack.", "[all]", "all"});
		CMDS.put("dropstore", new String[]{"Transfers everything in your inventory into a chest that it creates next to you.", "", ""});
		CMDS.put("effect", new String[]{"Configures potion effects on the player", "<list|remove TYPE|add TYPE [DURATION] [STRENGTH]", "add 1 100 1"});
		CMDS.put("enchant", new String[]{"Enchants the currently selected item", "<list|remove|add TYPE [LEVEL]>", "add protection 10"});
		CMDS.put("enderman", new String[]{"Enderman command to enable/disable block pickup", "pickup [enable|disable]", "pickup disable"});
		CMDS.put("explode", new String[]{"Creates an explosion at the players current location.", "<SIZE>", "10"});
		CMDS.put("ext", new String[]{"Extinguishes fires nearby", "[all]", "all"});
		CMDS.put("exterminate", new String[]{"Blows up the NPC which you are looking at", "[SIZE]", "10"});
		CMDS.put("extinguish", new String[]{"Extinguishes fires nearby", "[all]", "all"});
		CMDS.put("falldamage", new String[]{"Turns fall damage onoff", "", ""});
		CMDS.put("feed", new String[]{"Feeds the player the specified number of half points", "<HEAL>", "10"});
		CMDS.put("firedamage", new String[]{"Turns fire damage onoff", "", ""});
		CMDS.put("flammable", new String[]{"Sets the specified block at the flammability level", "<BLOCK> [CATCH] [SPREAD]", "stone 10 100"});
		CMDS.put("fly", new String[]{"Allows the player to fly. See /flymode for alternative flying modes", "[SPEED]", "5"});
		CMDS.put("flymode", new String[]{"Allows the player to fly using a standard fly mechanism.", "<standard|dynamic|minecraft|reset>", "standard"});
		CMDS.put("fog", new String[]{"Changes the render distance", "[tiny|small|normal|far]", "normal"});
		CMDS.put("freecam", new String[]{"Allows you to free cam arond the map", "", ""});
		CMDS.put("freeze", new String[]{"Toggles mobs to be frozen.", "", ""});
		CMDS.put("freezecam", new String[]{"Allows you to freeze the camera where it currently is", "", ""});
		CMDS.put("gamemode", new String[]{"Changes the game mode", "<0|1|creative|survival>", "creative"});
		CMDS.put("give", new String[]{"Gives player item, if quantity isn\u2019t specified maximum amount of that item", "<ITEMCODE|ITEMNAME> [QUANTITY] [DAMAGE]", "1"});
		CMDS.put("goto", new String[]{"Goto a waypoint", "<NAME>", "example"});
		CMDS.put("grow", new String[]{"Grows all saplingswheat on the map.", "[all]", ""});
		CMDS.put("h", new String[]{"Brings up a help message", "[COMMAND]", "give"});
		CMDS.put("hardcore", new String[]{"Configures the world to be in hardcore mode or not", "<enable|disable>", "enable"});
		CMDS.put("heal", new String[]{"Heals a player the specified number of points", "<HEALTH>", "10"});
		CMDS.put("health", new String[]{"Sets the health of a player to pre-defined figures", "<MIN|MAX|INFINITE>", "max"});
		CMDS.put("help", new String[]{"Brings up a help message", "[COMMAND]", "give"});
		CMDS.put("helmet", new String[]{"Specifies the helmet the player wears", "[ITEM] [QTY] [DAMAGE]", "10 1 1"});
		CMDS.put("home", new String[]{"Teleport to spawn point", "", ""});
		CMDS.put("hunger", new String[]{"Sets the players hunger level to pre-defined figures", "<empty|full|infinite|enable|disable>", "infinite"});
		CMDS.put("i", new String[]{"Gives player item, if quantity isn\u2019t specified maximum amount of that item", "<ITEMCODE|ITEMNAME> [QUANTITY] [DAMAGE]", "1"});
		CMDS.put("infiniteitems", new String[]{"Gives the player infinite items.", "", ""});
		CMDS.put("instantkill", new String[]{"Allows the player to kill enemies in one hit.", "", ""});
		CMDS.put("instantmine", new String[]{"Turns instant mining on/off", "", ""});
		CMDS.put("instantplant", new String[]{"Instantly plants saplings", "[grow]", "grow"});
		CMDS.put("item", new String[]{"Gives player item, if quantity isn\u2019t specified maximum amount of that item", "<ITEMCODE|ITEMNAME> [QUANTITY] [DAMAGE]", "1"});
		CMDS.put("itemdamage", new String[]{"Toggles item damage on and off", "", ""});
		CMDS.put("itemname", new String[]{"Discover the itemname and ID of your currently selected item.", "", ""});
		CMDS.put("itemstack", new String[]{"Gives the player the specified number of itemstacks", "<ID> [QTY] [DAMAGE]", "1 10 0"});
		CMDS.put("jump", new String[]{"Moves you from where you are to where your mouse is pointing", "", ""});
		CMDS.put("keepitems", new String[]{"Allows the player to keep items on death.", "", ""});
		CMDS.put("kill", new String[]{"Kills the current player", "", ""});
		CMDS.put("killall", new String[]{"Kills all of the specified mob type", "<MOBTYPE>", ""});
		CMDS.put("killnpc", new String[]{"Kills all living creatures around the player.", "[monster|animal|all]", "all"});
		CMDS.put("l", new String[]{"Lists all the waypoints currently configured.", "", ""});
		CMDS.put("light", new String[]{"Lights up the map.", "", ""});
		CMDS.put("listwaypoints", new String[]{"Lists all the waypoints currently configured.", "", ""});
		CMDS.put("longerlegs", new String[]{"Makes your legs longer so you can walk up 1 block high", "", ""});
		CMDS.put("macro", new String[]{"Loads and runs a file containing commands.", "<create <FILE>|edit <FILE>|list|folder|dir|delete <FILE>|FILENAME {ARGS}>", "miner arg1 arg2"});
		CMDS.put("maxstack", new String[]{"Changes the maximum stack size of the Item.", "[ITEMNAME|ITEMID] [QTY]", "stone 128"});
		CMDS.put("mobdamage", new String[]{"Toggles damage on and off", "", ""});
		CMDS.put("moveplayer", new String[]{"Moves the player in the specified direction.", "<DISTANCE> <DIRECTION>", "100 north"});
		CMDS.put("msg", new String[]{"This commands adds a message to the console.", "<MESSAGE>", "Hello world"});
		CMDS.put("music", new String[]{"Music configuration. Send a request to start music or set the volume.", "[play|pause|stop|skip|next|VOLUME]", "50"});
		CMDS.put("noclip", new String[]{"Allows the player to noclip", "", ""});
		CMDS.put("output", new String[]{"Toggles SPC output on/off", "", ""});
		CMDS.put("p", new String[]{"Gives current player position", "", ""});
		CMDS.put("phelp", new String[]{"Provides help for plugins", "[COMMAND]", "example"});
		CMDS.put("platform", new String[]{"Puts a one block glass platform beneath the players position", "", ""});
		CMDS.put("plugin", new String[]{"Provides plugin information and useful utilities", "<list|enable [MODNAME]|disable [MODNAME]>", "disable WorldEdit"});
		CMDS.put("pos", new String[]{"Gives current player position", "", ""});
		CMDS.put("ralias", new String[]{"Removes the specified alias", "<ALIAS|all>", "example"});
		CMDS.put("reach", new String[]{"Sets the reach distance of the player.", "<DISTANCE>", "10"});
		CMDS.put("rem", new String[]{"Removes the specified waypoint", "<NAME>", "example"});
		CMDS.put("removedrops", new String[]{"This command removes item drops from the world.", "[all]", "all"});
		CMDS.put("rename", new String[]{"Renames a specified command name to another name.", "<COMMAND> <NEWCOMMAND>", "goto warp"});
		CMDS.put("repair", new String[]{"Repairs the currently selected item, or all.", "[all]", "all"});
		CMDS.put("refill", new String[]{"Re-stocks your items in your inventory to the maximum ammount", "[all]", "all"});
		CMDS.put("repeat", new String[]{"Repeat the last command used", "", ""});
		CMDS.put("reset", new String[]{"Resets the settings to default", "", ""});
		CMDS.put("resize", new String[]{"Resizes the Minecraft window the size you want it", "[1080p|720p|480p|setdefault [WIDTH HEIGHT]|<WIDTH HEIGHT>]", "800 600"});
		CMDS.put("reskin", new String[]{"Reskins the NPC which you are pointing at to the specified skin", "", ""});
		CMDS.put("return", new String[]{"Moves the player to the last position before teleport", "", ""});
		CMDS.put("ride", new String[]{"Rides the entity you are pointing at", "", ""});
		CMDS.put("s", new String[]{"Mark a waypoint on the world", "<NAME>", "example"});
		CMDS.put("sc", new String[]{"Runs the specified script", "<FILENAME>", "test.js"});
		CMDS.put("search", new String[]{"Allows you to search for items using a name", "<SEARCHTERM>", "pick"});
		CMDS.put("set", new String[]{"Mark a waypoint on the world", "<NAME>", "example"});
		CMDS.put("setjump", new String[]{"Sets the height that you jump", "<HEIGHT|reset>", "3"});
		CMDS.put("setspawn", new String[]{"Sets the players spawn at their current location, or the one specified.", "[<X> <Y> <Z>]", "0 66 0"});
		CMDS.put("setspeed", new String[]{"Sets the speed that the player moves", "<SPEED|reset>", "3"});
		CMDS.put("skin", new String[]{"Allows the user to change their skin to any valid player's skin.", "<PLAYERNAME|reset>", "trunksbomb"});
		CMDS.put("slippery", new String[]{"Makes the specified block slippery", "<BLOCK> [SLIPPERYNESS]", "grass 1.5"});
		CMDS.put("spawn", new String[]{"Spawns the specified creature.", "<CREATURENAME> [QTY]", "zombie 10"});
		CMDS.put("spawner", new String[]{"Changes the mob spawner the player is pointing at", "<TYPE>", "Creeper"});
		CMDS.put("spawncontrol", new String[]{"Allows you to configure a list of spawnable creatures", "<all|animals|monsters>", "all"});
		CMDS.put("spawnportal", new String[]{"Spawns a portal nearby the player", "", ""});
		CMDS.put("spawnstack", new String[]{"Spawns the specified creature, multiple creatures will result in a stack.", "{CREATURENAME}", "spider creeper"});
		CMDS.put("sprinting", new String[]{"Turns sprinting on/off", "[enable|disable]", "enable"});
		CMDS.put("stackcombine", new String[]{"Combines all stacks in your inventory of the same type", "", ""});
		CMDS.put("stacklimit", new String[]{"Turn the itemstack limit on/off. Note: does not save stacksize above 128", "[on|off]", "off"});
		CMDS.put("startup", new String[]{"Sets the command to run on startup", "<COMMAND> {ARGS}", "macro startup"});
		CMDS.put("superheat", new String[]{"Superheats all the specified item in your inventory", "[ITEMID|ITEMNAME|all]", "stone"});
		CMDS.put("superpunch", new String[]{"Hit that NPC with a punch like no other", "[DISTANCE|reset]", "20"});
		CMDS.put("t", new String[]{"Teleport to X Y Z coordinates.", "<X> <Y> <Z>", "0 66 0"});
		CMDS.put("tele", new String[]{"Teleport to X Y Z coordinates.", "<X> <Y> <Z>", "0 66 0"});
		CMDS.put("textcolor", new String[]{"Configure the text which SPC outputs", "<<normal|error> <0-f|random>>|setrandom VALIDCOLORS|reset", "normal 9"});
		CMDS.put("time", new String[]{"Set and get the time within minecraft.", "[day|night|[set|get [minute|hour|day [TIME]]]|speed <SPEED>]", "set hour 16"});
		CMDS.put("timeschedule", new String[]{"Sets a time schedule which minecraft time will follow", "<TIME1> <TIME2>", "0:00 12:00"});
		CMDS.put("unbind", new String[]{"Unbinds a key which has been bound to a command", "<KEYNAME|all>", "e"});
		CMDS.put("unbindid", new String[]{"Unbinds a command from a keyboard key using the key id", "<ID>", "22"});
		CMDS.put("update", new String[]{"Configures the update warnings", "<enable|disable|check>", "enable"});
		CMDS.put("useportal", new String[]{"Instantly transfers you to the specified dimension", "<normal|nether|end|PORTAL>", "-1"});
		CMDS.put("waterdamage", new String[]{"Turns water damage on/off", "", ""});
		CMDS.put("watermovement", new String[]{"Turns water and lava slowdown and current effects off.", "", ""});
		CMDS.put("weather", new String[]{"Commands to toggle various weather on/off", "<rain|lightning|thunder|sun>", "rain"});
		CMDS.put("world", new String[]{"Various world related commands.", "<load FILENAME|save|new FILENAME [SEED]|seed [SEED]|exit|backup|name>", "load World10"});
		CMDS.put("xp", new String[]{"XP (player experience) related commands", "<add QTY|get|set XP>", "add 100"});
		System.out.println((new StringBuilder()).append("Commands: ").append(CMDS.size()).toString());
	}
}
