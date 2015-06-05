package net.minecraft.src;

import java.util.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class TMIController implements _tmi_MgButtonHandler, _tmi_MgItemHandler {
	public static final String COPYRIGHT = "All of TooManyItems except for thesmall portion excerpted from the original Minecraft game is copyright 2011Marglyph. TooManyItems is free for personal use only. Do not redistributeTooManyItems, including in mod packs, and do not use TooManyItems' sourcecode or graphics in your own mods.";
	private GuiContainer window;
	private RenderItem drawItems;
	private TMIConfig config;
	private TMIView view;
	long lastKeyPressTime;
	long lastPrefsLoadTime;
	long deleteAllWaitUntil;
	public static final long KEY_DELAY = 200L;
	public static final long PREFS_DELAY = 2000L;
	public boolean deleteMode;
	private boolean wasInWindowLastFrame;
	private long windowLastFocused;
	private boolean haveReplacedItems;
	private _tmi_MgCanvas canvas;

	public TMIController(GuiContainer guicontainer, RenderItem renderitem) {
		window = null;
		drawItems = null;
		config = TMIConfig.getInstance();
		view = null;
		lastKeyPressTime = 0L;
		lastPrefsLoadTime = 0L;
		deleteAllWaitUntil = 0L;
		deleteMode = false;
		wasInWindowLastFrame = false;
		windowLastFocused = 0L;
		haveReplacedItems = false;
		window = guicontainer;
		drawItems = renderitem;
		canvas = new _tmi_MgCanvas(window, drawItems);
		view = new TMIView(canvas, config, this);
		TMIUtils.loadItems(config);
		TMIUtils.replaceCustomItems();
	}

	public void onEnterFrame(int i, int j, int k, int l) {
		try {
			boolean flag = Display.isActive();

			if(flag && !wasInWindowLastFrame) {
				windowLastFocused = System.currentTimeMillis();
			}

			wasInWindowLastFrame = flag;
			TMIUtils.suppressAchievementNotice();

			if(System.currentTimeMillis() - lastKeyPressTime > 200L) {
				if(Keyboard.isKeyDown(config.getHotkey())) {
					config.toggleEnabled();
					TMIUtils.savePreferences(config);
					lastKeyPressTime = System.currentTimeMillis();
				}

				TMIUtils.updateUnlimitedItems();
			}

			if(System.currentTimeMillis() - lastPrefsLoadTime > 2000L) {
				TMIUtils.loadPreferences(config);

				if(lastPrefsLoadTime == 0L) {
					TMIUtils.savePreferences(config);
				}

				lastPrefsLoadTime = System.currentTimeMillis();
			}

			if(config.isEnabled()) {
				view.layout(window.width, window.height, k, l);
				canvas.drawWidgets(i, j);
				view.determineTooltip(i, j);
			}
		} catch (Exception exception) {
			TMIUtils.safeReportException(exception);
			disable();
		}
	}

	public boolean allowRegularTip() {
		return !view.hasTooltip();
	}

	public void showToolTip(int i, int j) {
		try {
			view.showToolTip(i, j);
		} catch (Exception exception) {
			TMIUtils.safeReportException(exception);
			disable();
		}
	}

	public void handleScrollWheel(int i, int j) {
		if(view.itemPanel.contains(i, j)) {
			int k = Mouse.getEventDWheel();

			if(k != 0) {
				_tmi_MgItemPanel _tmp = view.itemPanel;
				_tmi_MgItemPanel.page += k >= 0 ? -1 : 1;
			}

			try {
				TMIPrivateFields.lwjglMouseEventDWheel.setInt(null, 0);
				TMIPrivateFields.lwjglMouseDWheel.setInt(null, 0);
			} catch (Exception exception) {
			}
		}
	}

	public void replacementClickHandler(int i, int j, int k, boolean flag, Slot slot, Minecraft minecraft, Container container) {
		if(windowLastFocused > System.currentTimeMillis() - 200L) {
			return;
		}

		if(!onClick(i, j, k)) {
			return;
		}

		int l = -1;

		if(slot != null) {
			l = slot.slotNumber;
		} else if(!flag) {
			l = -999;
			ItemStack itemstack = TMIUtils.getHeldItem();

			if(itemstack != null && (itemstack.stackSize < 0 || itemstack.stackSize > 64)) {
				itemstack.stackSize = 1;
			}
		}

		boolean flag1 = flag && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));

		if(k == 0 && deleteMode && l >= 0 && slot != null) {
			if(TMIUtils.shiftKey()) {
				ItemStack itemstack1 = slot.getStack();

				if(itemstack1 != null) {
					TMIUtils.deleteItemsOfType(itemstack1, window);
				}
			} else {
				slot.putStack((ItemStack) null);
			}
		} else if(!TMICompatibility.callConvenientInventoryHandler(l, k, flag1, minecraft, container) && (k != 2 || l < 0)) {
			if(l == 0 && k == 1 && isCrafting()) {
				for(int i1 = 0; i1 < 64; i1++) {
					window.handleMouseClick(slot, l, k, flag1);
				}
			} else if(l != -1) {
				if(isChest() && l >= 0 && !TMIConfig.isMultiplayer() && TMIUtils.shiftKey() && TMIUtils.getHeldItem() != null && TMIUtils.isValidItem(TMIUtils.getHeldItem())) {
					try {
						TMIUtils.fastTransfer(l, k, window.inventorySlots);
					} catch (Exception exception) {
						TMIUtils.safeReportException(exception);
					}
				} else {
					window.handleMouseClick(slot, l, k, flag1);
				}
			}
		}
	}

	public boolean onClick(int i, int j, int k) {
		try {
			if(this.config.isEnabled()) {
				this.canvas.sortByZOrder();
				for(_tmi_MgWidget local_tmi_MgWidget : this.canvas.widgets)
					if(local_tmi_MgWidget.contains(i, j))
						return local_tmi_MgWidget.click(i, j, k);
			}
		} catch (Exception localException) {
			TMIUtils.safeReportException(localException);
			disable();
		}
		return true;
	}

	@Override
	public boolean onButtonPress(Object obj) {
		if(obj instanceof TMIStateButtonData) {
			TMIStateButtonData tmistatebuttondata = (TMIStateButtonData) obj;

			if(tmistatebuttondata.action == 1) {
				config.clearState(tmistatebuttondata.state);
				TMIUtils.savePreferences(config);
			} else if(config.isStateSaved(tmistatebuttondata.state)) {
				config.loadState(tmistatebuttondata.state);
				TMIUtils.savePreferences(config);
			} else {
				config.saveState(tmistatebuttondata.state);
				TMIUtils.savePreferences(config);
			}

			return false;
		}

		if(obj instanceof String) {
			String s = (String) obj;

			if(s.equals("rain")) {
				TMIUtils.setRaining(!TMIUtils.isRaining());
			} else if(s.equals("creative")) {
				TMIUtils.setCreativeMode(!TMIUtils.isCreativeMode());
			} else if(s.equals("dawn")) {
				TMIUtils.setHourForward(0);
			} else if(s.equals("noon")) {
				TMIUtils.setHourForward(6);
			} else if(s.equals("dusk")) {
				TMIUtils.setHourForward(12);
			} else if(s.equals("midnight")) {
				TMIUtils.setHourForward(18);
			} else if(s.equals("next")) {
				_tmi_MgItemPanel _tmp = view.itemPanel;
				_tmi_MgItemPanel.page++;
			} else if(s.equals("prev")) {
				_tmi_MgItemPanel _tmp1 = view.itemPanel;
				_tmi_MgItemPanel.page--;
			} else if(s.equals("health")) {
				TMIUtils.setPlayerHealth(20);
			} else if(s.equals("difficulty")) {
				TMIUtils.incrementDifficulty();
			} else if(s.equals("deleteMode")) {
				ItemStack itemstack = TMIUtils.getHeldItem();

				if(itemstack != null) {
					if(TMIUtils.shiftKey()) {
						TMIUtils.deleteHeldItem();
						TMIUtils.deleteItemsOfType(itemstack, window);
						deleteAllWaitUntil = System.currentTimeMillis() + 1000L;
					} else {
						TMIUtils.deleteHeldItem();
					}
				} else if(TMIUtils.shiftKey()) {
					if(System.currentTimeMillis() > deleteAllWaitUntil) {
						for(int i = 0; i < window.inventorySlots.inventorySlots.size(); i++) {
							Slot slot = window.inventorySlots.inventorySlots.get(i);
							slot.putStack((ItemStack) null);
						}
					}
				} else {
					deleteMode = !deleteMode;
				}
			}

			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean onItemEvent(ItemStack itemstack, int i) {
		if(i == 0) {
			if(!TMIConfig.isMultiplayer() && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54))) {
				Item item = Item.itemsList[itemstack.itemID];

				if(TMIConfig.isTool(item)) {
					TMIUtils.giveStack(new ItemStack(itemstack.itemID, 1, -32000), config, 1);
					return false;
				}

				if(TMIConfig.canItemBeUnlimited(item)) {
					TMIUtils.giveStack(itemstack, config, -100);
					return false;
				} else {
					TMIUtils.giveStack(itemstack, config);
					return false;
				}
			} else {
				TMIUtils.giveStack(itemstack, config);
				return false;
			}
		}

		if(i == 1) {
			TMIUtils.giveStack(itemstack, config, 1);
			return false;
		} else {
			return true;
		}
	}

	public boolean handleKeypress(char c, int i) {
		return false;
	}

	public void onDestroy() {
	}

	public boolean isChest() {
		return window.inventorySlots instanceof ContainerChest;
	}

	public boolean isCrafting() {
		return (window.inventorySlots instanceof ContainerPlayer) || (window.inventorySlots instanceof ContainerWorkbench);
	}

	public void disable() {
		config.setEnabled(false);
	}

	public static void sendWindowClick(Container container, int i, int j, boolean flag) {
		Minecraft minecraft = TMIUtils.getMinecraft();
		minecraft.playerController.windowClick(container.windowId, i, j, flag, minecraft.thePlayer);
	}

	public void sortItemsAroundSlotNum(Container container, int i) {
		int j = container.inventorySlots.size();
		byte byte0 = 9;
		byte byte1 = 27;

		if((container instanceof ContainerChest) && i < j - byte0 - byte1) {
			sortItemsInContainer(container, 0, j - byte0 - byte1 - 1);
		} else if(i < j - byte0 && i >= j - byte0 - byte1) {
			sortItemsInContainer(container, j - byte0 - byte1, j - byte0 - 1);
		}
	}

	public void sortItemsInContainer(Container container, int i, int j) {
		HashMap hashmap = new HashMap();

		for(int k = i; k <= j; k++) {
			Slot slot = container.inventorySlots.get(k);

			if(slot == null) {
				continue;
			}

			ItemStack itemstack = slot.getStack();

			if(itemstack != null) {
				int i1 = TMIItemInfo.packItemIDAndDamage(itemstack.itemID, itemstack.getItemDamage());
				int j1 = itemstack.stackSize + (hashmap.containsKey(Integer.valueOf(i1)) ? ((Integer) hashmap.get(Integer.valueOf(i1))).intValue() : 0);
				hashmap.put(Integer.valueOf(i1), Integer.valueOf(j1));
			}
		}

		System.out.println(hashmap.values());
		HashMap hashmap1 = new HashMap();
		int l = i;
		ArrayList arraylist = new ArrayList(hashmap.keySet());
		Collections.sort(arraylist);

		for(Iterator iterator = arraylist.iterator(); iterator.hasNext();) {
			int k1 = ((Integer) iterator.next()).intValue();
			int l1 = ((Integer) hashmap.get(Integer.valueOf(k1))).intValue();
			hashmap1.put(Integer.valueOf(k1), Integer.valueOf(l));
			l = (int) (l + Math.ceil((float) l1 / (float) TMIUtils.maxStackSize(TMIItemInfo.unpackItemID(k1))));
		}

		System.out.println(hashmap1.values());
		BitSet bitset = new BitSet();
		bitset.set(i, j + 1, true);

		do {
			if(bitset.isEmpty() && TMIUtils.getHeldItem() == null) {
				break;
			}

			System.out.println((new StringBuilder()).append("").append(!bitset.isEmpty()).append(TMIUtils.getHeldItem() != null).toString());
			ItemStack itemstack1 = TMIUtils.getHeldItem();

			if(itemstack1 == null) {
				int i2 = bitset.nextSetBit(i);
				Slot slot1 = container.inventorySlots.get(i2);

				if(slot1 == null) {
					bitset.set(i2, false);
				} else {
					ItemStack itemstack2 = slot1.getStack();

					if(itemstack2 == null) {
						bitset.set(i2, false);
					} else {
						int l2 = TMIItemInfo.packItemIDAndDamage(itemstack2.itemID, itemstack2.getItemDamage());
						sendWindowClick(container, i2, 0, false);
						bitset.set(i2, false);
						int i3 = ((Integer) hashmap1.get(Integer.valueOf(l2))).intValue();
						Slot slot3 = container.inventorySlots.get(i3);
						sendWindowClick(container, i3, 0, false);
						bitset.set(i3, false);
						ItemStack itemstack4 = TMIUtils.getHeldItem();

						if(itemstack4 != null) {
							int k3 = TMIItemInfo.packItemIDAndDamage(itemstack4.itemID, itemstack4.getItemDamage());

							if(k3 == l2) {
								hashmap1.put(Integer.valueOf(l2), Integer.valueOf(((Integer) hashmap1.get(Integer.valueOf(l2))).intValue() + 1));
							}
						}
					}
				}
			} else {
				int j2 = TMIItemInfo.packItemIDAndDamage(itemstack1.itemID, itemstack1.getItemDamage());
				int k2 = ((Integer) hashmap1.get(Integer.valueOf(j2))).intValue();
				Slot slot2 = container.inventorySlots.get(k2);
				sendWindowClick(container, k2, 0, false);
				bitset.set(k2, false);
				ItemStack itemstack3 = TMIUtils.getHeldItem();

				if(itemstack3 != null) {
					int j3 = TMIItemInfo.packItemIDAndDamage(itemstack3.itemID, itemstack3.getItemDamage());

					if(j3 == j2) {
						hashmap1.put(Integer.valueOf(j2), Integer.valueOf(((Integer) hashmap1.get(Integer.valueOf(j2))).intValue() + 1));
					}
				}
			}
		} while(true);
	}
}
