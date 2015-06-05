package net.minecraft.src;

import java.util.List;

class _tmi_MgItemPanel extends _tmi_MgWidget {
	public static final String COPYRIGHT = "All of TooManyItems except for thesmall portion excerpted from the original Minecraft game is copyright 2011Marglyph. TooManyItems is free for personal use only. Do not redistributeTooManyItems, including in mod packs, and do not use TooManyItems' sourcecode or graphics in your own mods.";
	private _tmi_MgItemHandler handler;
	private List items;
	public static int page = 0;
	public int numPages;
	public static final int SPACING = 18;
	private ItemStack hoverItem;
	private int marginLeft;
	private int marginTop;
	private int cols;
	private int rows;
	private int itemsPerPage;

	public _tmi_MgItemPanel(int i, int j, int k, int l, int i1, List list, _tmi_MgItemHandler _ptmi_mgitemhandler) {
		super(i, j, k, l, i1);
		numPages = 0;
		hoverItem = null;
		items = list;
		handler = _ptmi_mgitemhandler;
	}

	@Override
	public void resize() {
		marginLeft = x + (width % 18) / 2;
		marginTop = y + (height % 18) / 2;
		cols = width / 18;
		rows = height / 18;
		itemsPerPage = rows * cols;
		numPages = (int) Math.ceil((1.0F * items.size()) / itemsPerPage);
		page = page >= 0 ? page < numPages ? page : 0 : numPages - 1;
	}

	public ItemStack getHoverItem() {
		return hoverItem;
	}

	@Override
	public void draw(_tmi_MgCanvas _ptmi_mgcanvas, int i, int j) {
		if(!show) {
			return;
		}

		int k = page * itemsPerPage;
		int l = 0;
		int i1 = 0;
		hoverItem = null;
		_ptmi_mgcanvas.hardSetFlatMode(false);

		do {
			if(k >= itemsPerPage * (page + 1) || k >= items.size()) {
				break;
			}

			ItemStack itemstack = (ItemStack) items.get(k++);
			int j1 = marginLeft + l * 18;
			int k1 = marginTop + i1 * 18;

			if(i >= j1 && i < j1 + 18 && j >= k1 && j < k1 + 18) {
				hoverItem = itemstack;
				_ptmi_mgcanvas.drawRect(j1 - 1, k1 - 1, 18, 18, 0xee555555);
				_ptmi_mgcanvas.hardSetFlatMode(false);
			}

			_ptmi_mgcanvas.drawItem(j1, k1, itemstack);

			if(++l == cols) {
				l = 0;
				i1++;
			}
		} while(true);
	}

	@Override
	public boolean click(int i, int j, int k) {
		ItemStack itemstack = TMIUtils.getHeldItem();

		if(itemstack != null) {
			TMIUtils.deleteHeldItem();
			return false;
		}

		if(hoverItem != null) {
			return handler.onItemEvent(hoverItem, k);
		} else {
			return true;
		}
	}
}
