package net.minecraft.src;

import java.util.List;

public class TMIView implements _tmi_MgTooltipHandler {
	public static final String COPYRIGHT = "All of TooManyItems except for thesmall portion excerpted from the original Minecraft game is copyright 2011Marglyph. TooManyItems is free for personal use only. Do not redistributeTooManyItems, including in mod packs, and do not use TooManyItems' sourcecode or graphics in your own mods.";
	private _tmi_MgCanvas canvas;
	private TMIConfig config;
	private TMIController controller;
	private boolean widgetsCreated;
	private String activeTooltip;
	private _tmi_MgButton prevButton;
	private _tmi_MgButton nextButton;
	private _tmi_MgButton trashButton;
	private _tmi_MgButton stateButtons[];
	private _tmi_MgButton deleteButtons[];
	public _tmi_MgItemPanel itemPanel;
	public _tmi_MgButton rain;
	public _tmi_MgButton creative;
	public _tmi_MgButton delete;
	public _tmi_MgButton noon;
	public _tmi_MgButton dawn;
	public _tmi_MgButton dusk;
	public _tmi_MgButton midnight;
	public _tmi_MgButton difficulty;

	public TMIView(_tmi_MgCanvas _ptmi_mgcanvas, TMIConfig tmiconfig, TMIController tmicontroller) {
		widgetsCreated = false;
		activeTooltip = null;
		canvas = _ptmi_mgcanvas;
		config = tmiconfig;
		controller = tmicontroller;
		stateButtons = new _tmi_MgButton[config.getNumSaves()];
		deleteButtons = new _tmi_MgButton[config.getNumSaves()];
		createWidgets();
	}

	public void createWidgets() {
		prevButton = new _tmi_MgButton("", controller, "prev");
		prevButton.icon = new _tmi_MgImage(48, 12, 12, 12);
		prevButton.setOwnWidth(canvas);
		prevButton.height = 12;
		canvas.widgets.add(prevButton);
		nextButton = new _tmi_MgButton("", controller, "next");
		nextButton.icon = new _tmi_MgImage(36, 12, 12, 12);
		nextButton.setOwnWidth(canvas);
		nextButton.height = 12;
		canvas.widgets.add(nextButton);
		itemPanel = new _tmi_MgItemPanel(0, 0, 0, 0, 0, config.getItems(), controller);
		canvas.widgets.add(itemPanel);
		rain = new _tmi_MgButton("", controller, "rain");
		rain.showState = true;
		rain.icon = new _tmi_MgImage(0, 12, 12, 12);
		rain.setOwnWidth(canvas);
		rain.height = 14;
		canvas.widgets.add(rain);
		creative = new _tmi_MgButton("", controller, "creative");
		creative.showState = true;
		creative.icon = new _tmi_MgImage(12, 12, 12, 12);
		creative.setOwnWidth(canvas);
		creative.height = 14;
		canvas.widgets.add(creative);
		delete = new _tmi_MgButton("", controller, "deleteMode");
		delete.icon = new _tmi_MgImage(24, 12, 12, 12);
		delete.showState = true;
		delete.setOwnWidth(canvas);
		delete.height = 14;
		canvas.widgets.add(delete);
		noon = new _tmi_MgButton("", controller, "noon");
		noon.icon = new _tmi_MgImage(0, 24, 12, 12);
		noon.setOwnWidth(canvas);
		noon.height = 14;
		canvas.widgets.add(noon);
		dawn = new _tmi_MgButton("", controller, "dawn");
		dawn.icon = new _tmi_MgImage(12, 24, 12, 12);
		dawn.setOwnWidth(canvas);
		dawn.height = 14;
		canvas.widgets.add(dawn);
		dusk = new _tmi_MgButton("", controller, "dusk");
		dusk.icon = new _tmi_MgImage(24, 24, 12, 12);
		dusk.setOwnWidth(canvas);
		dusk.height = 14;
		canvas.widgets.add(dusk);
		midnight = new _tmi_MgButton("", controller, "midnight");
		midnight.icon = new _tmi_MgImage(36, 24, 12, 12);
		midnight.setOwnWidth(canvas);
		midnight.height = 14;
		canvas.widgets.add(midnight);
		difficulty = new _tmi_MgButton("", controller, "difficulty");
		difficulty.icon = new _tmi_MgImage(60, 24, 12, 12);
		difficulty.setOwnWidth(canvas);
		difficulty.height = 14;
		canvas.widgets.add(difficulty);
		delete.x = 2;
		delete.y = 2;
		canvas.arrangeHorizontally(1, 1001, new _tmi_MgWidget[]{delete, creative, rain, dawn, noon, dusk, midnight, difficulty});
		stateButtons = new _tmi_MgButton[config.getNumSaves()];

		for(int i = 0; i < config.getNumSaves(); i++) {
			stateButtons[i] = new _tmi_MgButton((new StringBuilder()).append("Save ").append(i + 1).toString(), controller, new TMIStateButtonData(i, 0));
			canvas.widgets.add(stateButtons[i]);
			deleteButtons[i] = new _tmi_MgButton("x", controller, new TMIStateButtonData(i, 1));
			canvas.widgets.add(deleteButtons[i]);
		}

		widgetsCreated = true;
	}

	public void layout(int i, int j, int k, int l) {
		int i1 = (i - k) / 2;

		if(!widgetsCreated) {
			createWidgets();
		}

		itemPanel.x = (i + k) / 2 + 5;
		itemPanel.y = 20;
		itemPanel.width = i - itemPanel.x - 2;
		itemPanel.height = j - itemPanel.y;
		itemPanel.resize();
		rain.state = TMIUtils.isRaining();
		creative.state = TMIUtils.isCreativeMode();
		delete.state = controller.deleteMode;
		nextButton.y = prevButton.y = 2;
		nextButton.x = i - nextButton.width - 2;
		prevButton.x = nextButton.x - prevButton.width - 2;
		canvas.drawRect(0, 0, i, 18, 0xee000000);
		_tmi_MgItemPanel _tmp = itemPanel;
		String s = (new StringBuilder()).append("").append(_tmi_MgItemPanel.page + 1).append("/").append(itemPanel.numPages).toString();
		int j1 = prevButton.x - 4 - canvas.getTextWidth(s);
		canvas.drawText(2, j - 13, (new StringBuilder()).append("TooManyItems 12w17a 2012-04-26   ModLoader ").append(TMIConfig.isModloaderEnabled ? "ON" : "OFF").toString(), -1);
		canvas.drawText(j1, 4, s, -1);
		rain.show = TMIConfig.canChangeWeather();
		creative.show = TMIConfig.canChangeCreativeMode();
		delete.show = TMIConfig.canDelete();
		dawn.show = noon.show = dusk.show = midnight.show = TMIConfig.canChangeTime();
		difficulty.show = TMIConfig.canChangeDifficulty();
		boolean flag = TMIConfig.canRestoreSaves();
		int k1 = 0;

		for(int l1 = 0; l1 < config.getNumSaves(); l1++) {
			deleteButtons[l1].x = -1000;
			stateButtons[l1].y = 30 + l1 * 22;
			stateButtons[l1].height = 20;
			String s1 = (String) config.getSettings().get((new StringBuilder()).append("save-name").append(l1 + 1).toString());

			if(s1 == null) {
				s1 = (new StringBuilder()).append("").append(l1 + 1).toString();
			}

			if(config.isStateSaved(l1)) {
				stateButtons[l1].label = (new StringBuilder()).append("Load ").append(s1).toString();
			} else {
				stateButtons[l1].label = (new StringBuilder()).append("Save ").append(s1).toString();
			}

			int j2 = canvas.getTextWidth(stateButtons[l1].label) + 26;

			if(j2 + 2 + 20 > i1) {
				j2 = i1 - 20 - 2;
			}

			if(j2 > k1) {
				k1 = j2;
			}
		}

		for(int i2 = 0; i2 < config.getNumSaves(); i2++) {
			stateButtons[i2].width = k1;
			stateButtons[i2].show = flag;
			deleteButtons[i2].show = flag;

			if(config.isStateSaved(i2)) {
				deleteButtons[i2].x = stateButtons[i2].width + 2;
				deleteButtons[i2].y = stateButtons[i2].y;
				deleteButtons[i2].width = 20;
				deleteButtons[i2].height = 20;
			}
		}
	}

	public void determineTooltip(int i, int j) {
		setTooltip(null);

		if(rain != null && rain.contains(i, j)) {
			setTooltip((new StringBuilder()).append("Rain/snow is ").append(rain.state ? "ON" : "OFF").toString());
		} else if(creative != null && creative.contains(i, j)) {
			setTooltip((new StringBuilder()).append("Creative mode is ").append(creative.state ? "ON" : "OFF").toString());
		} else if(noon != null && noon.contains(i, j)) {
			setTooltip("Set time to noon");
		} else if(dawn != null && dawn.contains(i, j)) {
			setTooltip("Set time to sunrise");
		} else if(dusk != null && dusk.contains(i, j)) {
			setTooltip("Set time to sunset");
		} else if(midnight != null && midnight.contains(i, j)) {
			setTooltip("Set time to midnight");
		} else if(difficulty != null && difficulty.contains(i, j)) {
			setTooltip(TMIUtils.getDifficultyString());
		} else if(delete != null && delete.contains(i, j)) {
			ItemStack itemstack = TMIUtils.getHeldItem();

			if(itemstack == null) {
				if(TMIUtils.shiftKey()) {
					setTooltip("DELETE ALL ITEMS from current inventory screen");
				} else {
					setTooltip((new StringBuilder()).append("Delete mode is ").append(delete.state ? "ON" : "OFF").toString());
				}
			} else if(TMIUtils.shiftKey()) {
				setTooltip((new StringBuilder()).append("DELETE ALL ").append(TMIUtils.itemDisplayName(itemstack)).toString());
			} else {
				setTooltip((new StringBuilder()).append("DELETE ").append(TMIUtils.itemDisplayName(itemstack)).toString());
			}
		} else if(itemPanel.contains(i, j)) {
			ItemStack itemstack1 = TMIUtils.getHeldItem();

			if(itemstack1 == null) {
				ItemStack itemstack2 = itemPanel.getHoverItem();

				if(itemstack2 != null) {
					List list = TMIUtils.itemDisplayNameMultiline(itemstack2, true);
					canvas.drawMultilineTip(i, j, list);
				}
			} else {
				setTooltip((new StringBuilder()).append("DELETE ").append(TMIUtils.itemDisplayName(itemstack1)).toString());
			}
		}
	}

	public boolean isInitialized() {
		return widgetsCreated;
	}

	@Override
	public void setTooltip(String s) {
		activeTooltip = s;
	}

	public String getTooltip(String s) {
		return activeTooltip;
	}

	public boolean hasTooltip() {
		return activeTooltip != null;
	}

	public void showToolTip(int i, int j) {
		if(activeTooltip != null) {
			canvas.drawTip(i, j, activeTooltip);
		}
	}
}
