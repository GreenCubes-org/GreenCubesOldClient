package net.minecraft.src;

import java.util.*;
import org.lwjgl.input.Keyboard;

public class TMIConfig {
	public static final String COPYRIGHT = "All of TooManyItems except for thesmall portion excerpted from the original Minecraft game is copyright 2011Marglyph. TooManyItems is free for personal use only. Do not redistributeTooManyItems, including in mod packs, and do not use TooManyItems' sourcecode or graphics in your own mods.";
	public static final String VERSION = "";
	public static final int NUM_SAVES = 7;
	public static final int INVENTORY_SIZE = 44;
	public static boolean isModloaderEnabled = false;
	private static TMIConfig instance;
	private static List items = new ArrayList();
	private static HashSet toolIds;
	private static HashSet nonUnlimitedIds;
	private Map settings;
	private static ItemStack states[][] = new ItemStack[7][44];
	private static boolean statesSaved[] = new boolean[7];

	public TMIConfig() {
		settings = new LinkedHashMap();
		settings.put("enable", "true");
		settings.put("enablemp", "true");
		settings.put("itemsonly", "false");
		settings.put("give-command", "/i {1},{3} {2}");
		settings.put("key", "o");

		for(int i = 0; i < getNumSaves(); i++) {
			settings.put((new StringBuilder()).append("save-name").append(i + 1).toString(), (new StringBuilder()).append("").append(i + 1).toString());
		}

		for(int j = 0; j < getNumSaves(); j++) {
			settings.put((new StringBuilder()).append("save").append(j + 1).toString(), "");
		}

		settings.put("spawner", "Pig");
		settings.put("include", "");
		settings.put("exclude", "");
		instance = this;
	}

	public static boolean isMultiplayer() {
		return TMIUtils.getMinecraft().theWorld.multiplayerWorld;
	}

	public static TMIConfig getInstance() {
		if(instance == null) {
			new TMIConfig();
		}

		return instance;
	}

	public Map getSettings() {
		return settings;
	}

	public List getItems() {
		return items;
	}

	public int getHotkey() {
		String s = (String) settings.get("key");
		int i = 0;
		i = Keyboard.getKeyIndex(s.toUpperCase());

		if(i == 0) {
			i = 24;
		}

		return i;
	}

	public int getNumSaves() {
		return 7;
	}

	public boolean isStateSaved(int i) {
		return statesSaved[i];
	}

	public ItemStack[] getState(int i) {
		return states[i];
	}

	public boolean getBooleanSetting(String s) {
		return Boolean.parseBoolean((String) settings.get(s));
	}

	public boolean isEnabled() {
		return isMultiplayer() && getBooleanSetting("enablemp") || !isMultiplayer() && getBooleanSetting("enable");
	}

	public void toggleEnabled() {
		String s = isMultiplayer() ? "enablemp" : "enable";
		settings.put(s, Boolean.toString(!getBooleanSetting(s)));
	}

	public void setEnabled(boolean flag) {
		String s = isMultiplayer() ? "enablemp" : "enable";
		settings.put(s, Boolean.toString(flag));
	}

	public static boolean isTool(Item item) {
		return toolIds.contains(Integer.valueOf(item.shiftedIndex));
	}

	public static boolean canItemBeUnlimited(Item item) {
		return !nonUnlimitedIds.contains(Integer.valueOf(item.shiftedIndex));
	}

	public boolean areDamageVariantsShown() {
		return true;
	}

	public void clearState(int i) {
		for(int j = 0; j < 44; j++) {
			states[i][j] = null;
			statesSaved[i] = false;
		}

		settings.put((new StringBuilder()).append("save").append(i + 1).toString(), "");
	}

	public void loadState(int i) {
		if(!statesSaved[i]) {
			return;
		}

		List list = TMIUtils.getMinecraft().thePlayer.inventorySlots.inventorySlots;

		for(int j = 0; j < 44; j++) {
			Slot slot = (Slot) list.get(j + 1);
			slot.putStack(null);
			ItemStack itemstack = TMIUtils.copyStack(states[i][j]);

			if(itemstack != null && itemstack.itemID >= 0 && itemstack.itemID < Item.itemsList.length && Item.itemsList[itemstack.itemID] != null) {
				slot.putStack(itemstack);
			}
		}
	}

	public void saveState(int i) {
		List list = TMIUtils.getMinecraft().thePlayer.inventorySlots.inventorySlots;

		for(int j = 0; j < 44; j++) {
			states[i][j] = TMIUtils.copyStack(((Slot) list.get(j + 1)).getStack());
		}

		settings.put((new StringBuilder()).append("save").append(i + 1).toString(), encodeState(i));
		statesSaved[i] = true;
	}

	public String encodeState(int i) {
		StringBuilder stringbuilder = new StringBuilder();

		for(int j = 0; j < 44; j++) {
			if(states[i][j] != null) {
				stringbuilder.append(states[i][j].itemID);
				stringbuilder.append(":");
				stringbuilder.append(states[i][j].stackSize);
				stringbuilder.append(":");
				stringbuilder.append(states[i][j].getItemDamage());
				List list = TMIUtils.getEnchantments(states[i][j]);
				int k;
				int l;

				for(Iterator iterator = list.iterator(); iterator.hasNext(); stringbuilder.append((new StringBuilder()).append(":").append(k).append(":").append(l).toString())) {
					int ai[] = (int[]) iterator.next();
					k = ai[0];
					l = ai[1];
				}
			}

			stringbuilder.append(",");
		}

		return stringbuilder.toString();
	}

	public void decodeState(int i, String s) {
		if(s.trim().equals("")) {
			statesSaved[i] = false;
		} else {
			String as[] = s.split(",", 0);

			for(int j = 0; j < as.length && j < states[i].length; j++) {
				String as1[] = as[j].split(":");

				if(as1.length < 3) {
					continue;
				}

				try {
					states[i][j] = new ItemStack(Integer.parseInt(as1[0]), Integer.parseInt(as1[1]), Integer.parseInt(as1[2]));

					for(int k = 3; k < as1.length - 1; k += 2) {
						int l = Integer.parseInt(as1[k]);
						int i1 = Integer.parseInt(as1[k + 1]);
						TMIUtils.addEnchantment(states[i][j], l, i1);
					}
				} catch (Exception exception) {
					System.out.println(exception);
				}
			}

			statesSaved[i] = true;
		}
	}

	public static boolean canDelete() {
		return !isMultiplayer();
	}

	public static boolean canChangeWeather() {
		return !getInstance().getBooleanSetting("itemsonly");
	}

	public static boolean canChangeCreativeMode() {
		return !getInstance().getBooleanSetting("itemsonly");
	}

	public static boolean canChangeTime() {
		return !getInstance().getBooleanSetting("itemsonly");
	}

	public static boolean canChangeHealth() {
		return !isMultiplayer() && !getInstance().getBooleanSetting("itemsonly");
	}

	public static boolean canRestoreSaves() {
		return !isMultiplayer();
	}

	public static boolean canChangeDifficulty() {
		return !isMultiplayer();
	}

	static {
		toolIds = new HashSet();

		for(int i = 0; i <= 3; i++) {
			toolIds.add(Integer.valueOf(TMIItemInfo.addItemOffset(i)));
		}

		for(int j = 11; j <= 23; j++) {
			toolIds.add(Integer.valueOf(TMIItemInfo.addItemOffset(j)));
		}

		for(int k = 27; k <= 30; k++) {
			toolIds.add(Integer.valueOf(TMIItemInfo.addItemOffset(k)));
		}

		for(int l = 34; l <= 38; l++) {
			toolIds.add(Integer.valueOf(TMIItemInfo.addItemOffset(l)));
		}

		for(int i1 = 42; i1 <= 61; i1++) {
			toolIds.add(Integer.valueOf(TMIItemInfo.addItemOffset(i1)));
		}

		toolIds.add(Integer.valueOf(TMIItemInfo.addItemOffset(103)));
		toolIds.add(Integer.valueOf(TMIItemInfo.addItemOffset(90)));
		toolIds.add(Integer.valueOf(TMIItemInfo.addItemOffset(5)));
		nonUnlimitedIds = new HashSet();
		nonUnlimitedIds.add(Integer.valueOf(TMIItemInfo.addItemOffset(102)));
		nonUnlimitedIds.add(Integer.valueOf(TMIItemInfo.addItemOffset(130)));
	}
}
