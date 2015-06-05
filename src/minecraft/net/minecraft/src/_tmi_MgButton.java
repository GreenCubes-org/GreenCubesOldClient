package net.minecraft.src;

public class _tmi_MgButton extends _tmi_MgWidget {
	public static final String COPYRIGHT = "All of TooManyItems except for thesmall portion excerpted from the original Minecraft game is copyright 2011Marglyph. TooManyItems is free for personal use only. Do not redistributeTooManyItems, including in mod packs, and do not use TooManyItems' sourcecode or graphics in your own mods.";
	public String label;
	public _tmi_MgButtonHandler controller;
	public Object data;
	public _tmi_MgImage icon;
	public boolean showState;
	public boolean state;
	public static final _tmi_MgImage stateOff = new _tmi_MgImage(48, 0, 8, 12);
	public static final _tmi_MgImage stateOn = new _tmi_MgImage(56, 0, 8, 12);

	public _tmi_MgButton(String s, _tmi_MgButtonHandler _ptmi_mgbuttonhandler, Object obj) {
		this(0, 0, 0, 0, 0, s, _ptmi_mgbuttonhandler, obj);
	}

	public _tmi_MgButton(int i, int j, int k, int l, int i1, String s, _tmi_MgButtonHandler _ptmi_mgbuttonhandler, Object obj) {
		super(i, j, k, l, i1);
		showState = false;
		state = false;
		label = s;
		controller = _ptmi_mgbuttonhandler;
		data = obj;
	}

	public void setOwnWidth(_tmi_MgCanvas _ptmi_mgcanvas) {
		width = contentWidth(_ptmi_mgcanvas) + getMargin();
	}

	public int contentWidth(_tmi_MgCanvas _ptmi_mgcanvas) {
		int i = _ptmi_mgcanvas.getTextWidth(label);

		if(icon != null) {
			i += icon.width;

			if(label != null && label.length() > 0) {
				i += 2;
			}
		}

		if(showState) {
			i += stateOff.width;

			if(label != null && label.length() > 0) {
				i++;
			}
		}

		return i;
	}

	public int getMargin() {
		return label == null || label.length() <= 0 ? 2 : 6;
	}

	@Override
	public void draw(_tmi_MgCanvas _ptmi_mgcanvas, int i, int j) {
		if(!show) {
			return;
		}

		_ptmi_mgcanvas.drawRect(x, y, width, height, contains(i, j) ? 0xee401008 : 0xee000000);
		int k = _ptmi_mgcanvas.getTextWidth(label);
		int l = contentWidth(_ptmi_mgcanvas);
		int i1 = x + (width - l) / 2;
		int j1 = y + (height - 8) / 2;
		_ptmi_mgcanvas.drawText(i1, j1, label, -1);

		if(icon != null) {
			int k1 = y + (height - icon.height) / 2;
			_ptmi_mgcanvas.drawChrome(i1, k1, icon);
			i1 += icon.width;

			if(k > 0) {
				i1 += 2;
			}
		}

		if(showState) {
			_tmi_MgImage _ltmi_mgimage = state ? stateOn : stateOff;
			int l1 = y + (height - _ltmi_mgimage.height) / 2;
			_ptmi_mgcanvas.drawChrome(i1, l1, _ltmi_mgimage);
		}
	}

	@Override
	public boolean click(int i, int j, int k) {
		if(k == 0) {
			return controller.onButtonPress(data);
		} else {
			return true;
		}
	}
}
