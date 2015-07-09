package net.minecraft.src;

import java.util.*;
import org.lwjgl.opengl.GL11;

public class _tmi_MgCanvas {
	public static final String COPYRIGHT = "All of TooManyItems except for thesmall portion excerpted from the original Minecraft game is copyright 2011Marglyph. TooManyItems is free for personal use only. Do not redistributeTooManyItems, including in mod packs, and do not use TooManyItems' sourcecode or graphics in your own mods.";
	public int windowX;
	public int windowY;
	public GuiScreen window;
	private RenderItem drawItems;
	public List<_tmi_MgWidget> widgets;
	public static final int ALIGN_TOP = 1001;
	public static final int ALIGN_BOTTOM = 1002;
	public static final int ALIGN_LEFT = 1003;
	public static final int ALIGN_RIGHT = 1004;
	public static final int ALIGN_MIDDLE = 1005;
	public static final int WHITE = -1;
	public static final int SHADE = 0xee000000;
	public static final int RED_SHADE = 0xee401008;
	public static final int LIGHT_SHADE = 0xee555555;
	private boolean flatMode;

	public _tmi_MgCanvas(GuiScreen guiscreen, RenderItem renderitem) {
		windowX = 0;
		windowY = 0;
		widgets = new ArrayList<_tmi_MgWidget>();
		flatMode = false;
		window = guiscreen;
		drawItems = renderitem;
	}

	public void drawRect(int i, int j, int k, int l, int i1) {
		flatMode(true);
		window.drawGradientRect(i - windowX, j - windowY, (i + k) - windowX, (j + l) - windowY, i1, i1);
	}

	public void drawText(int i, int j, String s, int k) {
		drawText(i, j, s, k, 1.0F);
	}

	public void drawText(int i, int j, String s, int k, float f) {
		hardSetFlatMode(true);
		GL11.glPushMatrix();
		GL11.glScalef(f, f, 1.0F);
		window.fontRenderer.drawStringWithShadow(s, (int) ((i - windowX) / f), (int) ((j - windowY) / f), k);
		GL11.glPopMatrix();
	}

	public void drawTextCentered(int i, int j, int k, int l, String s, int i1, float f) {
		drawText(i + (k - getTextWidth(s, f)) / 2, j + (l - 8) / 2, s, i1, f);
	}

	public void drawTextCentered(int i, int j, int k, int l, String s, int i1) {
		drawTextCentered(i, j, k, l, s, i1, 1.0F);
	}

	public void drawText(int i, int j, String s) {
		drawText(i, j, s, -1);
	}

	public void drawTip(int i, int j, String s) {
		byte byte0 = 3;
		int k = i + 12;
		int l = j - 15;

		if(l < 0) {
			l = 0;
		}

		int i1 = getTextWidth(s) + byte0 * 2;

		if(k + i1 > window.width) {
			k -= (k + i1) - window.width;
		}

		drawRect(k, l, i1, 8 + byte0 * 2, 0xee000000);
		drawText(k + byte0, l + byte0, s, -1);
	}

	public void drawMultilineTip(int i, int j, List list) {
		if(list.size() > 0) {
			byte byte0 = 3;
			int k = 0;
			Iterator iterator = list.iterator();

			do {
				if(!iterator.hasNext()) {
					break;
				}

				Object obj = iterator.next();
				int j1 = getTextWidth((String) obj);

				if(j1 > k) {
					k = j1;
				}
			} while(true);

			int l = 8;

			if(list.size() > 1) {
				l += 2 + (list.size() - 1) * 10;
			}

			int i1 = k + byte0 * 2;
			int k1 = l + byte0 * 2;
			int l1 = i + 12;
			int i2 = j - 15;

			if(i2 < 0) {
				i2 = 0;
			}

			if(l1 + i1 > window.width) {
				l1 = window.width - i1;
			}

			if(i >= l1 && i <= l1 + i1 && j >= i2 && j <= i2 + k1) {
				i2 = j - k1 - 2;

				if(i2 < 0) {
					i2 = j + 2;
				}
			}

			drawRect(l1, i2, i1, k1, 0xee000000);
			l1 += byte0;
			i2 += byte0;
			boolean flag = true;

			for(Iterator iterator1 = list.iterator(); iterator1.hasNext();) {
				Object obj1 = iterator1.next();
				drawText(l1, i2, (String) obj1, -1);

				if(flag) {
					i2 += 2;
					flag = false;
				}

				i2 += 10;
			}
		}
	}

	public void drawItem(int i, int j, ItemStack itemstack) {
		try {
			drawItems.zLevel = 200F;
			drawItems.renderItemIntoGUI(window.fontRenderer, window.mc.renderEngine, itemstack, i - windowX, j - windowY);
			drawItems.zLevel = 0.0F;
		} catch (Exception exception) {
			drawItems.renderItemIntoGUI(window.fontRenderer, window.mc.renderEngine, new ItemStack(51, 1, 0), i - windowX, j - windowY);
		} catch (LinkageError linkageerror) {
			drawItems.renderItemIntoGUI(window.fontRenderer, window.mc.renderEngine, new ItemStack(51, 1, 0), i - windowX, j - windowY);
		}
	}

	public void drawChrome(int i, int j, int k, int l, int i1, int j1) {
		hardSetFlatMode(true);
		window.mc.renderEngine.bindTexture(window.mc.renderEngine.getTexture("/tmi.png"));
		window.drawTexturedModalRect(i - windowX, j - windowY, k, l, i1, j1);
	}

	public void drawChrome(int i, int j, _tmi_MgImage _ptmi_mgimage) {
		hardSetFlatMode(true);
		window.mc.renderEngine.bindTexture(window.mc.renderEngine.getTexture(_ptmi_mgimage.filename));
		window.drawTexturedModalRect(i - windowX, j - windowY, _ptmi_mgimage.x, _ptmi_mgimage.y, _ptmi_mgimage.width, _ptmi_mgimage.height);
	}

	public void sortByZOrder() {
		Collections.sort(widgets, _tmi_MgWidget.getComparator());
	}

	public void drawWidgets(int i, int j) {
		sortByZOrder();
		_tmi_MgWidget _ltmi_mgwidget;

		for(Iterator iterator = widgets.iterator(); iterator.hasNext(); _ltmi_mgwidget.draw(this, i, j)) {
			_ltmi_mgwidget = (_tmi_MgWidget) iterator.next();
		}
	}

	public int getTextWidth(String s) {
		return getTextWidth(s, 1.0F);
	}

	public int getTextWidth(String s, float f) {
		if(s == null || s.equals("")) {
			return 0;
		} else {
			return (int) (window.fontRenderer.getStringWidth(s) * f);
		}
	}

	public void flatMode(boolean flag) {
		if(flag && !flatMode) {
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
		} else if(!flag && flatMode) {
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		}

		flatMode = flag;
	}

	public void hardSetFlatMode(boolean flag) {
		if(flag) {
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
		} else {
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		}

		flatMode = flag;
	}

	public void arrangeHorizontally(int i, int j, _tmi_MgWidget a_ptmi_mgwidget[]) {
		if(a_ptmi_mgwidget.length > 1) {
			int k = a_ptmi_mgwidget[0].x;
			int l = a_ptmi_mgwidget[0].y;

			if(j == 1002) {
				l += a_ptmi_mgwidget[0].height;
			} else if(j == 1005) {
				l += a_ptmi_mgwidget[0].height / 2;
			}

			_tmi_MgWidget a_ltmi_mgwidget[] = a_ptmi_mgwidget;
			int i1 = a_ltmi_mgwidget.length;

			for(int j1 = 0; j1 < i1; j1++) {
				_tmi_MgWidget _ltmi_mgwidget = a_ltmi_mgwidget[j1];
				_ltmi_mgwidget.x = k;

				switch(j) {
				case 1001:
					_ltmi_mgwidget.y = l;
					break;

				case 1002:
					_ltmi_mgwidget.y = l - _ltmi_mgwidget.height;
					break;

				case 1005:
					_ltmi_mgwidget.y = l - _ltmi_mgwidget.height / 2;
					break;
				}

				k += i + _ltmi_mgwidget.width;
			}
		}
	}
}
