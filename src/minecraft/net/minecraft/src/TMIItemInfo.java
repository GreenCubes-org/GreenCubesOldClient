package net.minecraft.src;

import java.util.*;

public class TMIItemInfo {
	public static final String COPYRIGHT = "All of TooManyItems except for thesmall portion excerpted from the original Minecraft game is copyright 2011Marglyph. TooManyItems is free for personal use only. Do not redistributeTooManyItems, including in mod packs, and do not use TooManyItems' sourcecode or graphics in your own mods.";
	private static final Map fallbackNames = new HashMap();
	private static final Map maxDamageExceptions = new HashMap();
	private static final Set hideItems = new HashSet();
	private static final Set showItems = new HashSet();
	private static final Set tools = new HashSet();
	private static final Set nonUnlimited = new HashSet();
	public static List potionValues;
	public static int itemOffset = addItemOffset(0);

	public TMIItemInfo() {
	}

	public static void hideItem(int i) {
		hideItems.add(Integer.valueOf(i));
	}

	public static boolean isHidden(int i) {
		return hideItems.contains(Integer.valueOf(i));
	}

	public static void addFallbackName(int i, int j, String s) {
		fallbackNames.put(Integer.valueOf(packItemIDAndDamage(i, j)), s);
	}

	public static boolean hasFallbackName(int i, int j) {
		return fallbackNames.containsKey(Integer.valueOf(packItemIDAndDamage(i, j)));
	}

	public static String getFallbackName(int i, int j) {
		int k = packItemIDAndDamage(i, j);

		if(fallbackNames.containsKey(Integer.valueOf(k))) {
			return (String) fallbackNames.get(Integer.valueOf(k));
		} else {
			return "Unnamed";
		}
	}

	public static void setMaxDamageException(int i, int j) {
		maxDamageExceptions.put(Integer.valueOf(i), Integer.valueOf(j));
	}

	public static int getMaxDamageException(int i) {
		if(maxDamageExceptions.containsKey(Integer.valueOf(i))) {
			return ((Integer) maxDamageExceptions.get(Integer.valueOf(i))).intValue();
		} else {
			return -1;
		}
	}

	public static int packItemIDAndDamage(int i, int j) {
		return (i << 8) + j;
	}

	public static int unpackItemID(int i) {
		return i >> 8;
	}

	public static int unpackDamage(int i) {
		return i & 0xff;
	}

	public static void showItemWithDamage(int i, int j) {
		showItems.add(Integer.valueOf(packItemIDAndDamage(i, j)));
	}

	public static void showItemWithDamageRange(int i, int j, int k) {
		for(int l = j; l <= k; l++) {
			showItemWithDamage(i, l);
		}
	}

	public static boolean isShown(int i, int j) {
		return showItems.contains(Integer.valueOf(packItemIDAndDamage(i, j)));
	}

	public static int addItemOffset(int i) {
		return i + Item.shovelSteel.shiftedIndex;
	}

	static {
		potionValues = new ArrayList();
		int ai[] = {0, 16, 32, 64, 8192, 8193, 8194, 8195, 8196, 8197, 8200, 8201, 8202, 8204, 8225, 8226, 8227, 8228, 8229, 8232, 8233, 8234, 8236, 8257, 8258, 8259, 8260, 8261, 8264, 8265, 8266, 8268, 16384, 16385, 16386, 16387, 16388, 16389, 16392, 16393, 16394, 16396, 16417, 16418, 16419, 16420, 16421, 16424, 16425, 16426, 16428, 16449, 16450, 16451, 16452, 16453, 16456, 16457, 16458, 16460};
		int ai1[] = ai;
		int i = ai1.length;

		for(int j = 0; j < i; j++) {
			int k = ai1[j];
			potionValues.add(Integer.valueOf(k));
		}

		showItemWithDamageRange(Block.CARPET_1.blockID, 0, 15);
		showItemWithDamageRange(6, 0, 3);
		showItemWithDamageRange(18, 0, 3);
		showItemWithDamageRange(Item.FRAME.shiftedIndex, 0, 63);
		showItemWithDamageRange(Item.FRAME_PARTS.shiftedIndex, 0, 19);
		showItemWithDamageRange(Block.COLORED_GLASS_1.blockID, 0, 15);
		showItemWithDamageRange(Block.COLORED_GLASS_HALF_BLOCK_1.blockID, 0, 15);
		showItemWithDamageRange(Block.COLORED_GLASS_STEP_1.blockID, 0, 15);
		showItemWithDamageRange(Block.COLORED_GLASS_STEP_UP_1.blockID, 0, 15);
		showItemWithDamageRange(Block.COLORED_GLASS_STAIRS_1.blockID, 0, 15);
		showItemWithDamageRange(Block.COLORED_GLASS_PANE_1.blockID, 0, 15);
		setMaxDamageException(Item.PACKED_ITEM.shiftedIndex, 9);
		setMaxDamageException(Block.COLORED_GLASS_1.blockID, 15);
		setMaxDamageException(Block.COLORED_GLASS_HALF_BLOCK_1.blockID, 15);
		setMaxDamageException(Block.COLORED_GLASS_STEP_1.blockID, 15);
		setMaxDamageException(Block.COLORED_GLASS_STEP_UP_1.blockID, 15);
		setMaxDamageException(Block.COLORED_GLASS_STAIRS_1.blockID, 15);
		setMaxDamageException(Block.COLORED_GLASS_PANE_1.blockID, 15);
		setMaxDamageException(Block.CARPET_1.blockID, 15);
		setMaxDamageException(Item.FRAME_PARTS.shiftedIndex, 19);
		setMaxDamageException(Item.FRAME.shiftedIndex, 63);
		setMaxDamageException(Item.trammel.shiftedIndex, 0);
		setMaxDamageException(Item.SET_BOX.shiftedIndex, 5);
		setMaxDamageException(Item.COUNTER_MOD.shiftedIndex, 100);
		setMaxDamageException(Block.blockAppleTreeLeaves.blockID, 0);
		setMaxDamageException(Block.blockAppleTreeLeavesPlayer.blockID, 0);
		setMaxDamageException(Item.GLOWING_MOD.shiftedIndex, 4);
		setMaxDamageException(Item.COLLECTIBLES.shiftedIndex, 256);
		showItemWithDamageRange(43, 0, 5);
		//addFallbackName(97, 0, "Silverfish Stone");
		addFallbackName(43, 0, "Double Slab");
		addFallbackName(110, 0, "Mycelium");
		addFallbackName(111, 0, "Lily Pad");
		addFallbackName(119, 0, "End Portal");
		addFallbackName(120, 0, "End Portal Frame");
		addFallbackName(121, 0, "White Stone");
		hideItem(9);
		hideItem(11);
		hideItem(43);
		hideItem(63);
		hideItem(64);
		hideItem(68);
		hideItem(71);
		hideItem(74);
		hideItem(59);
		hideItem(83);
		hideItem(55);
		hideItem(26);
		hideItem(93);
		hideItem(94);
		hideItem(34);
		hideItem(36);
		hideItem(92);
		hideItem(97);
		hideItem(99);
		hideItem(100);
		hideItem(104);
		hideItem(105);
		hideItem(110);
		hideItem(116);
		hideItem(117);
		hideItem(118);
		hideItem(119);
		hideItem(120);
		hideItem(122);
		hideItem(addItemOffset(117));
		hideItem(1245);
		hideItem(3079);
	}
}
