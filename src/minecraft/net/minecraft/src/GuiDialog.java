package net.minecraft.src;

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiDialog extends GuiScreen {

	private static final int maxHeight = 450;
	private static final int minHeight = 20 * 5;
	private static final int windowWidth = 440;
	private static final int lineHeight = 20;
	private static final int paragraphPadding = 26;
	private static final RenderContent BR = new RenderContent(RenderContentType.BR);
	private static final RenderContent PAGE = new RenderContent(RenderContentType.PAGE);
	//private static final Pattern itemPattern = Pattern.compile("\\{ITEM\\}");

	private static RenderItem itemRenderer = RenderItem.getInstance();

	public boolean showOk = true;
	public boolean showCancel = true;

	private int height = 0;
	private int startContent = 0;
	private int nextContent = 0;
	private String title;
	private FontRenderer font;
	private int titleWidth;
	private int tw;
	private int titleTextWidth;
	private int mouseOnButton = 0;
	private boolean buttonsOnAllPages = false;
	private String accept = "�������";
	private String decline = "����������";
	private int timer = -1;
	private List<RenderContent> content = new ArrayList<RenderContent>();
	private TIntList prevContent = new TIntArrayList();

	public GuiDialog(Packet209Dialog dialog) {
		font = Minecraft.theMinecraft.fontRenderer;
		showOk = dialog.ok;
		showCancel = dialog.cancel;
		title = dialog.title;
		titleTextWidth = titleWidth = font.getStringWidth(title);
		titleWidth *= 2;
		titleWidth += (titleWidth % 2) + 12;
		if(titleWidth < 160)
			titleWidth = 160;
		tw = (480 - 24 * 2 - 19 * 2 - titleWidth) / 2;
		if(tw % 2 != 0) {
			titleWidth += 2;
			tw -= 1;
		}
		NBTTagCompound data = dialog.data;
		if(data.hasKey("Accept"))
			accept = data.getString("Accept");
		if(data.hasKey("Decline"))
			decline = data.getString("Decline");
		if(data.hasKey("TimerOk")) {
			timer = data.getInteger("TimerOk");
			showOk = false;
		}
		if(data.hasKey("AllPagesButtons"))
			buttonsOnAllPages = data.getBoolean("AllPagesButtons");
		NBTTagList pages = data.getTagList("Pages");
		int maxPageHeight = 0;
		int currentPageHeight = 0;
		boolean lastItem = false;
		int itemLine = 0;
		for(int i = 0; i < pages.size(); ++i) {
			if(i != 0)
				content.add(PAGE);
			lastItem = false;
			itemLine = 0;
			NBTTagList page = (NBTTagList) pages.get(i);
			for(int i1 = 0; i1 < page.size(); ++i1) {
				NBTTagCompound element = (NBTTagCompound) page.get(i1);
				switch(element.getByte("T")) {
				case 0:
					lastItem = false;
					itemLine = 0;
					String[] paragraphs = element.getString("V").split("\n\n");
					for(int i2 = 0; i2 < paragraphs.length; ++i2) {
						if(i2 != 0 || !element.getBoolean("NBR"))
							content.add(BR);
						String[] lines = GChat.wrapText(paragraphs[i2], windowWidth / 2, paragraphPadding / 2, false);
						List<String> renderLines = new ArrayList<String>();
						for(int i3 = 0; i3 < lines.length; ++i3) {
							if(currentPageHeight + 20 > maxHeight) {
								RenderContent rc = new RenderContent(RenderContentType.TEXT);
								rc.content = renderLines;
								rc.heightPosition = currentPageHeight - renderLines.size() * lineHeight;
								content.add(rc);
								renderLines = new ArrayList<String>();
								content.add(PAGE);
								if(maxPageHeight < currentPageHeight)
									maxPageHeight = currentPageHeight;
								currentPageHeight = 0;
							}
							renderLines.add(lines[i3]);
							currentPageHeight += lineHeight;
						}
						if(renderLines != null && renderLines.size() > 0) {
							RenderContent rc = new RenderContent(RenderContentType.TEXT);
							rc.content = renderLines;
							rc.heightPosition = currentPageHeight - renderLines.size() * lineHeight;
							content.add(rc);
						}
					}
					break;
				case 1:
					itemLine = 0;
					boolean wereLastItem = lastItem;
					if(!lastItem) {
						if(currentPageHeight + 50 > maxHeight) {
							content.add(PAGE);
							wereLastItem = false;
							if(maxPageHeight < currentPageHeight)
								maxPageHeight = currentPageHeight;
							currentPageHeight = 0;
						}
						lastItem = true;
					} else
						lastItem = false;
					ItemStack item = ItemStack.loadItemStackFromNBT(element);
					String itemDescr = element.getString("V");
					RenderContent rc = new RenderContent(RenderContentType.ITEM);
					rc.content = item;
					rc.content2 = itemDescr != null ? GChat.wrapText(itemDescr, 166 / 2, 0, true) : null;
					rc.heightPosition = wereLastItem ? currentPageHeight - 50 : currentPageHeight;
					if(!wereLastItem)
						currentPageHeight += 50;
					content.add(rc);
					break;
				case 2:
					itemLine = 0;
					lastItem = false;
					int texture = Minecraft.theMinecraft.renderEngine.getTexture(element.getString("V"));
					int height = element.getShort("H");
					int width = element.getShort("W");
					if(currentPageHeight + 50 > maxHeight) {
						content.add(PAGE);
						if(maxPageHeight < currentPageHeight)
							maxPageHeight = currentPageHeight;
						currentPageHeight = 0;
					}
					rc = new RenderContent(RenderContentType.IMAGE);
					rc.content = texture;
					rc.content2 = new HW(height, width);
					rc.heightPosition = currentPageHeight;
					currentPageHeight += height + 3;
					content.add(rc);
					break;
				case 3:
					lastItem = false;
					if(itemLine == 6)
						itemLine = 0;

					break;
				}
			}
			if(currentPageHeight > maxPageHeight)
				maxPageHeight = currentPageHeight;
			currentPageHeight = 0;
		}
		height = maxPageHeight;
		if(height < minHeight)
			height = minHeight;
		else
			height += (height - 4) % 3;
	}

	@Override
	public void updateScreen() {
		if(timer != -1)
			if(timer-- == 0)
				showOk = true;
	}

	@Override
	public void drawScreen(int x, int y, float f) {
		ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1, 1, 1, 1f);
		GL11.glScalef((float) sr.scaledWidthD / mc.displayWidth, (float) sr.scaledHeightD / mc.displayHeight, 0);
		int guiWidth = mc.displayWidth;
		int guiHeight = mc.displayHeight;
		float scaleFactorW = mc.displayWidth / (float) sr.scaledWidthD;
		float scaleFactorH = mc.displayHeight / (float) sr.scaledHeightD;
		GL11.glTranslatef((guiWidth - 480) / 2F, (guiHeight - 63 - 43 - height) / 2F, 0);
		Tessellator t = Tessellator.instance;

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/gc_images/gui/quests_t1.png"));
		t.startDrawingQuads();
		GuiIngame.drawQuestSquare2(24, 43 - 24, tw, 24, 0, 0, 0, tw / 2 * 512, 512);
		GuiIngame.drawQuestSquare2(480 - 24 - tw, 43 - 24, tw, 24, 0, 0, 0, tw / 2 * 512, 512);
		t.draw();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/gc_images/gui/quests_t2.png"));
		GuiIngame.drawNotifySquare(24 + 19 + tw, 0, titleWidth, 43, 0, 0, 0, titleWidth / 2 * 512, 512);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/gc_images/gui/quests_t3.png"));
		GuiIngame.drawNotifySquare(0, 43, 480, height - 4, 0, 0, 0, 512, height / 3 * 512);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/gc_images/gui/quests.png"));
		t.startDrawingQuads();
		GuiIngame.drawQuestSquare2(0, 43 - 24, 24, 24, 0, 0, 149, 24, 24);
		GuiIngame.drawQuestSquare2(480 - 24, 43 - 24, 24, 24, 0, 24, 149, -24, 24);
		GuiIngame.drawQuestSquare2(24 + tw, 0, 19, 43, 0, 52, 149, 19, 43);
		GuiIngame.drawQuestSquare2(24 + 19 + tw + titleWidth, 0, 19, 43, 0, 52 + 19, 149, -19, 43);
		GuiIngame.drawQuestSquare2(0, 43 + height - 4, 480, 62, 0, 0, 84, 480, 62);
		boolean renderItems = false;
		boolean renderImages = false;
		boolean hasItem = false;
		for(int i = startContent; i < content.size(); ++i) {
			RenderContent c = content.get(i);
			if(c == PAGE) {
				nextContent = i + 1;
				break;
			}
			if(c.type == RenderContentType.ITEM) {
				GuiIngame.drawQuestSquare2(hasItem ? 246 : 23, 43 + c.heightPosition, 213, 42, 0, 0, 40, 213, 42);
				hasItem = !hasItem;
				renderItems = true;
			}
			if(c.type == RenderContentType.IMAGE) {
				renderImages = true;
			}
		}
		int mx = Mouse.getX() - (guiWidth - 480) / 2;
		int my = guiHeight - Mouse.getY() - (guiHeight - 63 - 43 - height) / 2;
		boolean b = Mouse.isButtonDown(0) && my > 43 + height - 4 + 15 && my < 43 + height - 4 + 15 + 39;
		if(nextContent > startContent) {
			boolean pressed = b && mx > 433 && mx < 433 + 39;
			GuiIngame.drawQuestSquare2(433, 43 + height - 4 + 15, 39, 39, 0, pressed ? 0 : 413, 0, 39, 39);
			GuiIngame.drawQuestSquare2(433 + 14, 43 + height - 4 + 15 + 10, 13, 19, 0, 467, 0, 13, 19);
		}
		if(buttonsOnAllPages || nextContent <= startContent) {
			if(showOk || timer != -1) {
				boolean pressed = showOk && b && mx > 243 && mx < 243 + 185;
				if(showOk) {
					GuiIngame.drawQuestSquare2(243, 43 + height - 4 + 15, 185, 39, 0, pressed ? 227 : 41, 0, 185, 39);
					GuiIngame.drawQuestSquare2(243 + 10, 43 + height - 4 + 15 + 10, 19, 19, 0, 473, 20, 19, 19);
				} else {
					GuiIngame.drawQuestSquare2(243, 43 + height - 4 + 15, 185, 39, 0, 227, 39, 185, 39);
				}
			}
			if(showCancel) {
				boolean pressed = b && mx > 52 && mx < 52 + 185;
				GuiIngame.drawQuestSquare2(52, 43 + height - 4 + 15, 185, 39, 0, pressed ? 227 : 41, 0, 185, 39);
				GuiIngame.drawQuestSquare2(52 + 10, 43 + height - 4 + 15 + 10, 19, 19, 0, 453, 20, 19, 19);
			}
		}
		if(prevContent.size() > 0) {
			boolean pressed = b && mx > 8 && mx < 8 + 39;
			GuiIngame.drawQuestSquare2(8, 43 + height - 4 + 15, 39, 39, 0, pressed ? 0 : 413, 0, 39, 39);
			GuiIngame.drawQuestSquare2(8 + 12, 43 + height - 4 + 15 + 10, 13, 19, 0, 453, 0, 13, 19);
		}
		t.draw();
		if(renderImages) {
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glColor4f(1, 1, 1, 1f);
			for(int i = startContent; i < content.size(); ++i) {
				RenderContent c = content.get(i);
				if(c == PAGE)
					break;
				if(c.type == RenderContentType.IMAGE) {
					int texture = ((Integer) c.content).intValue();
					HW hw = (HW) c.content2;
					t.startDrawingQuads();
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
					GuiIngame.drawQuestSquare2(23, 43 + c.heightPosition, hw.w, hw.h, 0, 0, 0, 512, 512);
					t.draw();
				}
			}
		}
		GL11.glScalef(2, 2, 0);
		if(renderItems) {
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			RenderHelper.func_41089_c();
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			itemRenderer.zLevel = 100;
			hasItem = false;
			for(int i = startContent; i < content.size(); ++i) {
				RenderContent c = content.get(i);
				if(c == PAGE)
					break;
				if(c.type == RenderContentType.ITEM) {
					ItemStack item = (ItemStack) c.content;
					if(item != null) {
						int w = ((hasItem ? 245 : 22) + 6) / 2;
						int h = (48 + c.heightPosition) / 2;
						itemRenderer.renderItemIntoGUI(font, mc.renderEngine, item, w, h);
						itemRenderer.renderItemOverlayIntoGUI(font, mc.renderEngine, item, w, h);
					}
					hasItem = !hasItem;
				}
			}
			itemRenderer.zLevel = 0;
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			RenderHelper.disableStandardItemLighting();
			GL11.glPopMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDisable(GL11.GL_BLEND);
		}
		font.drawStringWithShadow(title, -titleTextWidth / 2 + (24 + 21 + tw + titleWidth / 2) / 2, 3, 0xefefef);
		hasItem = false;
		boolean isParagraph = true;
		for(int i = startContent; i < content.size(); ++i) {
			RenderContent c = content.get(i);
			if(c == PAGE)
				break;
			if(c.type == RenderContentType.ITEM) {
				String[] lines = (String[]) c.content2;
				if(lines != null)
					for(int i1 = 0; i1 < lines.length; ++i1) {
						font.drawString(lines[i1], (int) (((hasItem ? 246 : 23) + 42) / 2F), (int) ((43 - 4 + c.heightPosition + 2) / 2F + i1 * 10 + 2), 0);
					}
				hasItem = !hasItem;
			} else if(c == BR) {
				isParagraph = true;
			} else if(c.type == RenderContentType.TEXT) {
				@SuppressWarnings("unchecked")
				List<String> lines = (List<String>) c.content;
				for(int i1 = 0; i1 < lines.size(); ++i1) {
					float space = 0.0f;
					int spaces = 0;
					/*if(i1 != lines.size() - 1) {
						int width = font.getStringWidth(lines.get(i1));
						int reqWidth = 220 - (isParagraph ? 13 : 0);
						spaces = GCUtil.count(lines.get(i1), ' ');
						space = (float) (reqWidth - width) / spaces;
					}*/
					font.renderString(lines.get(i1), (isParagraph ? 22 + paragraphPadding : 22) / 2, (43 - 4 + c.heightPosition + 2) / 2 + i1 * 10, 0, false, space, spaces);
					isParagraph = false;
				}
			}
		}
		if(buttonsOnAllPages || nextContent <= startContent) {
			if(showOk || timer != -1)
				font.drawString(accept, (243 + 12 + 27) / 2, (43 + height - 4 + 15 + 11) / 2, 0x292929);
			if(showCancel)
				font.drawString(decline, (52 + 12 + 26) / 2, (43 + height - 4 + 15 + 11) / 2, 0x292929);
		}

		GL11.glPopMatrix();

	}

	@Override
	protected void mouseClicked(int i, int j, int k) {
		if(k == 0) {
			mouseOnButton = 0;
			int mx = Mouse.getX() - (mc.displayWidth - 480) / 2;
			int my = mc.displayHeight - Mouse.getY() - (mc.displayHeight - 63 - 43 - height) / 2;
			if(nextContent > startContent) {
				boolean pressed = Mouse.isButtonDown(0) && mx > 433 && mx < 433 + 39 && my > 43 + height - 4 + 15 && my < 43 + height - 4 + 15 + 39;
				if(pressed)
					mouseOnButton = 4;
			}
			if(buttonsOnAllPages || nextContent <= startContent) {
				if(showOk) {
					boolean pressed = Mouse.isButtonDown(0) && mx > 243 && mx < 243 + 185 && my > 43 + height - 4 + 15 && my < 43 + height - 4 + 15 + 39;
					if(pressed)
						mouseOnButton = 3;
				}
				if(showCancel) {
					boolean pressed = Mouse.isButtonDown(0) && mx > 52 && mx < 52 + 185 && my > 43 + height - 4 + 15 && my < 43 + height - 4 + 15 + 39;
					if(pressed)
						mouseOnButton = 2;
				}
			}
			if(prevContent.size() > 0) {
				boolean pressed = Mouse.isButtonDown(0) && mx > 8 && mx < 8 + 39 && my > 43 + height - 4 + 15 && my < 43 + height - 4 + 15 + 39;
				if(pressed)
					mouseOnButton = 1;
			}
		}
	}

	@Override
	protected void mouseMovedOrUp(int i, int j, int k) {
		if(k == 0 && mouseOnButton != 0 && !Mouse.isButtonDown(0)) {
			int mx = Mouse.getX() - (mc.displayWidth - 480) / 2;
			int my = mc.displayHeight - Mouse.getY() - (mc.displayHeight - 63 - 43 - height) / 2;
			if(nextContent > startContent) {
				boolean pressed = mx > 433 && mx < 433 + 39 && my > 43 + height - 4 + 15 && my < 43 + height - 4 + 15 + 39;
				if(pressed && mouseOnButton == 4)
					right();
			}
			if(buttonsOnAllPages || nextContent <= startContent) {
				if(showOk) {
					boolean pressed = mx > 243 && mx < 243 + 185 && my > 43 + height - 4 + 15 && my < 43 + height - 4 + 15 + 39;
					if(pressed && mouseOnButton == 3)
						ok();
				}
				if(showCancel) {
					boolean pressed = mx > 52 && mx < 52 + 185 && my > 43 + height - 4 + 15 && my < 43 + height - 4 + 15 + 39;
					if(pressed && mouseOnButton == 2)
						cancel();
				}
			}
			if(prevContent.size() > 0) {
				boolean pressed = mx > 8 && mx < 8 + 39 && my > 43 + height - 4 + 15 && my < 43 + height - 4 + 15 + 39;
				if(pressed && mouseOnButton == 1)
					left();
			}
			mouseOnButton = 0;
		}
	}

	private void right() {
		prevContent.add(startContent);
		startContent = nextContent;
	}

	private void ok() {
		if(mc.getSendQueue() != null)
			mc.getSendQueue().addToSendQueue(new Packet210DialogAnswer(1));
		close();
	}

	private void cancel() {
		if(mc.getSendQueue() != null)
			mc.getSendQueue().addToSendQueue(new Packet210DialogAnswer(2));
		close();
	}

	private void close() {
		mc.displayGuiScreen(null);
		mc.setIngameFocus();
	}

	@Override
	protected void keyTyped(char c, int i) {
	}

	private void left() {
		nextContent = startContent;
		if(prevContent.size() > 0) {
			int prev = prevContent.get(prevContent.size() - 1);
			prevContent.removeAt(prevContent.size() - 1);
			startContent = prev;
		} else
			startContent = 0;
	}

	private static enum RenderContentType {
		TEXT, ITEM, BR, PAGE, IMAGE;
	}

	private static class RenderContent {
		public RenderContentType type;
		public Object content;
		public Object content2;
		public int heightPosition;

		public RenderContent(RenderContentType type) {
			this.type = type;
		}
	}

	private static class HW {
		public final int h;
		public final int w;

		public HW(int h, int w) {
			this.h = h;
			this.w = w;
		}
	}
}
