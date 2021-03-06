/*
 * Copyright 2015 Eiren 'Eirenliel' Rain and GreenCubes.org
 * authors
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package net.minecraft.src;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiBook extends GuiScreen {

	private final static int NORMAL_BOOK = 1;
	private final static int GOLDEN_BOOK = 2;
	private final static int DIAMOND_BOOK = 3;

	private final static int OBSIDIAN_BOOK = 10;

	private StringBuilder fullText;
	private ArrayList<BookLine> prevLines = new ArrayList<BookLine>();
	private StringBuilder nextReversed;
	private StringBuilder title;
	private String subTitle;
	private int page = 1;
	private int cursorLine = -1;
	private int cursorPosition = 0;
	private ArrayList<BookLine> currentLines = new ArrayList<BookLine>();
	private int cursorCycle = 0;
	private int type;

	private final static int lineWidth = 110;
	private final static int lineHeight = 10;
	private final static int leftPageHeight = 12;
	private final static int rightPageHeight = 14;
	private final static int paddingTop = 5;
	private final static int paddingLeft = 5;
	private final static int paddingRight = 10;
	private final static int paddingHeader = 21;
	private final static int paddingSubTitle = 11;

	private final static int cursorColor = 0xff0000;
	private final static int cursorPaddingLeft = 0;
	private final static int cursorPaddingTop = 1;
	private final static String cursor = "_";

	private int maxCharacters = 4095;

	private final boolean isEditable;
	private int characters;

	private int renderList = -1;
	private int savedPage = -1;
	private int savedHeight = -1;
	private int savedWidth = -1;
	private boolean typed = false;

	public GuiBook(Packet222TextMessage packet) {
		title = new StringBuilder(packet.title);
		isEditable = packet.editable;
		fullText = new StringBuilder(packet.message);
		type = packet.type;
		subTitle = packet.subtitle;
		if(DIAMOND_BOOK == type || type == OBSIDIAN_BOOK)
			maxCharacters *= 2;
		Minecraft.theMinecraft.renderEngine.getTexture("/gc_images/gui/guibook.png");
		Minecraft.theMinecraft.renderEngine.getTexture("/gc_images/gui/guigoldenbook.png");
		Minecraft.theMinecraft.renderEngine.getTexture("/gc_images/gui/guidiamondbook.png");
		Minecraft.theMinecraft.renderEngine.getTexture("/gc_images/gui/guiobsidianbook.png");
	}

	@Override
	public void initGui() {
		typed = true;
		controlList.clear();
		controlList.add(new GuiButton(0, (this.width - 254) / 2, this.height - 30, 100, 20, "�������"));
		if(isEditable) {
			controlList.add(new GuiButton(1, (this.width - 254) / 2 + 154, this.height - 30, 100, 20, "���������"));
			Keyboard.enableRepeatEvents(true);
		}
		if(fullText == null)
			return;
		for(int i = 0; i < fullText.length(); ++i) {
			char c = fullText.charAt(i);
			if(c == '\n')
				continue;
			if(ChatAllowedCharacters.allowedCharacters.indexOf(c) == -1)
				fullText.deleteCharAt(i--);
		}
		BookLine currentLine = new BookLine();
		float width = 0;
		int cutted = 0;
		for(int i = 0; i < fullText.length(); ++i) {
			char c = fullText.charAt(i);
			if(c != '\247') {
				if(c == '\n') {
					//currentLine.width = width;
					currentLines.add(currentLine);
					currentLine = new BookLine();
					if(currentLines.size() >= leftPageHeight + rightPageHeight) {
						cutted++;
						break;
					}
					currentLine.isNewLine = true;
					width = 0;
					cutted++;
					continue;
				} else {
					int id = ChatAllowedCharacters.allowedCharacters.indexOf(c);
					if(id == -1) {
						cutted++;
						continue;
					}
					float newWidth = width + fontRenderer.charWidth[id + 32];
					if(newWidth > lineWidth) {
						BookLine prevLine = currentLine;
						//currentLine.width = width;
						currentLines.add(currentLine);
						currentLine = new BookLine();
						if(currentLines.size() >= leftPageHeight + rightPageHeight)
							break;
						width = fontRenderer.charWidth[id];
					} else
						width = newWidth;
				}
			} else {
				i++;
				currentLine.text.append(fullText.charAt(i));
				cutted++;
			}
			cutted++;
			//if(!currentLine.isNewLine || currentLine.text.length() > 0 || c != ' ')
			currentLine.text.append(c);
		}
		if(currentLine.text.length() > 0 || currentLines.size() == 0)
			currentLines.add(currentLine);
		if(fullText.length() > 0) {
			nextReversed = new StringBuilder(fullText.substring(cutted));
			nextReversed.reverse();
		} else {
			nextReversed = new StringBuilder();
		}
		/*System.out.println("== Shit we've got: ==");
		System.out.println("== Full Text: " + fullText.toString());
		System.out.println("== Next Reversed Text: " + nextReversed.toString());
		for(int i = 0; i < currentLines.size(); ++i) {
			System.out.println("== Line " + i + ": " + currentLines.get(i).text.toString());
		}
		System.out.println("== END OF SHIT ==");*/
		characters = fullText.length();
		fullText = null;
	}

	@Override
	public void onGuiClosed() {
		if(isEditable)
			Keyboard.enableRepeatEvents(false);
		if(renderList >= 0)
			GL11.glDeleteLists(renderList, 1);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if(button.id == 0) {
			mc.displayGuiScreen(null);
			mc.setIngameFocus();
			mc.getSendQueue().addToSendQueue(new Packet222TextMessage(Packet222TextMessage.STATE_CLOSE));
		} else if(button.id == 1 && isEditable) {
			// Building book and sending it
			StringBuilder fullText = new StringBuilder();
			for(int i = 0; i < prevLines.size(); ++i) {
				BookLine l = prevLines.get(i);
				if(l.isNewLine)
					fullText.append('\n');
				fullText.append(l.text);
			}
			for(int i = 0; i < currentLines.size(); ++i) {
				BookLine l = currentLines.get(i);
				if(l.isNewLine)
					fullText.append('\n');
				fullText.append(l.text);
			}
			fullText.append(nextReversed.reverse());
			String text;
			if(fullText.length() > maxCharacters) {
				text = fullText.substring(0, maxCharacters);
			} else
				text = fullText.toString();
			mc.getSendQueue().addToSendQueue(new Packet222TextMessage(Packet222TextMessage.STATE_EDITED, title.toString(), text));
			mc.displayGuiScreen(null);
			mc.setIngameFocus();
		}
	}

	@Override
	protected void keyTyped(char c, int i) {
		if(isEditable) {
			if(c == '\026') {
				String s = GChat.paste();
				for(int i1 = 0; i1 < s.length(); ++i1) {
					char c1 = s.charAt(i1);
					keyTyped(c1, -1);
				}
				return;
			}
			if(i == Keyboard.KEY_ESCAPE) {
				if(!isEditable) {
					mc.displayGuiScreen(null);
					mc.setIngameFocus();
					mc.getSendQueue().addToSendQueue(new Packet222TextMessage(0));
				}
			} else if(i == Keyboard.KEY_UP) {
				if(cursorLine > 0) {
					int offset = fontRenderer.getStringPartWidth(currentLines.get(cursorLine).text, cursorPosition);
					cursorLine--;
					cursorPosition = fontRenderer.getClosestSymbolByPadding(currentLines.get(cursorLine).text, offset);
				} else if(cursorLine == 0) {
					cursorLine = -1;
					cursorPosition = 0;
				}
			} else if(i == Keyboard.KEY_DOWN) {
				if(cursorLine == -1) {
					cursorLine = 0;
					cursorPosition = 0;
				} else if(cursorLine == currentLines.size() - 1) {
					nextPage();
				} else if(cursorLine < currentLines.size() - 1) {
					int offset = fontRenderer.getStringPartWidth(currentLines.get(cursorLine).text, cursorPosition);
					cursorLine++;
					cursorPosition = fontRenderer.getClosestSymbolByPadding(currentLines.get(cursorLine).text, offset);
				}
			} else if(i == Keyboard.KEY_LEFT) {
				if(cursorPosition == 0) {
					if(cursorLine > 0) {
						cursorLine--;
						cursorPosition = currentLines.get(cursorLine).text.length() - 1;
					} else {
						if(prevPage()) {
							cursorLine = currentLines.size() - 1;
							cursorPosition = currentLines.get(cursorLine).text.length() - 1;
						}
					}
				} else
					cursorPosition--;
			} else if(i == Keyboard.KEY_RIGHT) {
				if(cursorLine == -1) {
					if(cursorPosition < title.length())
						cursorPosition++;
				} else {
					if(cursorLine >= 0 && cursorPosition >= currentLines.get(cursorLine).text.length()) {
						if(cursorLine == currentLines.size() - 1) {
							nextPage();
						} else {
							cursorLine++;
							cursorPosition = 0;
						}
					} else
						cursorPosition++;
				}
			} else if(i == Keyboard.KEY_RETURN) {
				if(cursorLine == -1) {
					cursorLine = 0;
					cursorPosition = 0;
				} else {
					if(characters == maxCharacters) {
						return;
					}
					characters++;
					if(cursorLine == leftPageHeight + rightPageHeight - 1) {
						nextReversed.append('\n');
						nextPage();
						return;
					}
					BookLine nextLine = currentLines.get(cursorLine);
					String left;
					if(nextLine.text.length() == 0 || cursorPosition == 0 || cursorPosition == nextLine.text.length())
						left = "";
					else {
						left = nextLine.text.substring(cursorPosition);
						nextLine.text = new StringBuilder(nextLine.text.substring(0, cursorPosition));
					}
					nextLine = new BookLine();
					nextLine.text.append(left);
					nextLine.isNewLine = true;
					currentLines.add(cursorLine + 1, nextLine);
					while(currentLines.size() > leftPageHeight + rightPageHeight) {
						BookLine reset = currentLines.remove(currentLines.size() - 1);
						nextReversed.append(reset.text.reverse());
					}
					cursorPosition = 0;
					cursorLine++;
					if(cursorLine == currentLines.size() - 1)
						nextPage();
				}
				typed = true;
			} else if(i == Keyboard.KEY_DELETE) {
				if(cursorLine == -1) {
					if(cursorPosition < title.length())
						title.deleteCharAt(cursorPosition);
				} else {
					BookLine line = currentLines.get(cursorLine);
					if(cursorPosition < line.text.length()) {
						line.text.deleteCharAt(cursorPosition);
						refitLines(cursorLine);
						characters--;
					} else if(cursorLine < currentLines.size() - 1 && currentLines.size() > 0) {
						line = currentLines.get(cursorLine + 1);
						if(line.isNewLine) {
							line.isNewLine = false;
							refitLines(cursorLine);
							characters--;
						} else {
							line.text.deleteCharAt(0);
							refitLines(cursorLine + 1);
							characters--;
						}
					}
				}
				typed = true;
			} else if(i == Keyboard.KEY_BACK) {
				if(cursorLine == -1) {
					if(cursorPosition > 0) {
						title.deleteCharAt(cursorPosition - 1);
						cursorPosition--;
					}
				} else {
					BookLine line = currentLines.get(cursorLine);
					if(cursorPosition > 0) {
						line.text.deleteCharAt(cursorPosition - 1);
						refitLines(cursorLine);
						cursorPosition--;
						characters--;
					} else if(cursorLine > 0) {
						if(currentLines.get(cursorLine).text.length() == 0) {
							currentLines.remove(cursorLine);
							refitLines(cursorLine - 1);
							cursorLine--;
							cursorPosition = currentLines.get(cursorLine).text.length();
							if(line.isNewLine)
								characters--;
						} else {
							line = currentLines.get(cursorLine - 1);
							if(line.text.length() > 0)
								line.text.deleteCharAt(line.text.length() - 1);
							else
								currentLines.remove(cursorLine - 1);
							refitLines(cursorLine - 1);
							cursorLine--;
							cursorPosition = currentLines.get(cursorLine).text.length();
							characters--;
						}
					}
				}
				typed = true;
			} else if(ChatAllowedCharacters.allowedCharacters.indexOf(c) >= 0) {
				if(cursorLine == -1) {
					if(title.length() < 40) {
						title.insert(cursorPosition, c);
						cursorPosition++;
					}
				} else {
					if(characters == maxCharacters)
						return;
					characters++;
					BookLine line = currentLines.get(cursorLine);
					line.text.insert(cursorPosition, c);
					int width = fontRenderer.getStringWidth(line.text);
					if(width > lineWidth)
						fitLine(cursorLine);
					cursorPosition++;
					if(cursorLine >= 0 && cursorPosition > currentLines.get(cursorLine).text.length()) {
						if(cursorLine == currentLines.size() - 1) {
							nextPage();
						} else {
							cursorLine++;
							cursorPosition = 1;
						}
					}
				}
				typed = true;
			}
		}
	}

	private void refitLines(int lineN) {
		BookLine currentLine = currentLines.get(lineN);
		float width = fontRenderer.getStringWidth(currentLine.text);
		if(lineN == currentLines.size() - 1) {
			// Last line on page
			if(nextReversed.length() > 0) {
				while(true) {
					if(nextReversed.length() == 0)
						return;
					char c = nextReversed.charAt(nextReversed.length() - 1);
					int id = ChatAllowedCharacters.allowedCharacters.indexOf(c);
					if(id == -1) {
						if(c == '\n') {
							if(currentLines.size() < leftPageHeight + rightPageHeight) {
								nextReversed.deleteCharAt(nextReversed.length() - 1);
								BookLine newLine = new BookLine();
								newLine.isNewLine = true;
								currentLines.add(newLine);
								refitLines(currentLines.size() - 1);
								break;
							} else
								break;
						} else {
							nextReversed.deleteCharAt(nextReversed.length() - 1);
							currentLine.text.append(c);
						}
					} else {
						float newWidth = width + fontRenderer.charWidth[id + 32];
						if(newWidth > lineWidth) {
							if(currentLines.size() < leftPageHeight + rightPageHeight) {
								BookLine newLine = new BookLine();
								currentLines.add(newLine);
								refitLines(currentLines.size() - 1);
							}
							break;
						} else {
							width = newWidth;
							nextReversed.deleteCharAt(nextReversed.length() - 1);
							currentLine.text.append(c);
						}
					}
				}
			}
		} else {
			BookLine nextLine = currentLines.get(lineN + 1);
			if(nextLine.isNewLine) {
				refitLines(lineN + 1);
				return;
			}
			while(true) {
				if(nextLine.text.length() == 0) {
					currentLines.remove(lineN + 1);
					refitLines(lineN);
					return;
				}
				char c = nextLine.text.charAt(0);
				int id = ChatAllowedCharacters.allowedCharacters.indexOf(c);
				if(id == -1) {
					nextLine.text.deleteCharAt(0);
					currentLine.text.append(c);
				} else {
					float newWidth = width + fontRenderer.charWidth[id + 32];
					if(newWidth <= lineWidth) {
						width = newWidth;
						currentLine.text.append(c);
						nextLine.text.deleteCharAt(0);
					} else
						break;
				}
			}
			refitLines(lineN + 1);
		}
	}

	private void fitLine(int lineN) {
		BookLine line = currentLines.get(lineN);
		int width = fontRenderer.getStringWidth(line.text);
		StringBuilder onNewLine = new StringBuilder(3);
		while(width > lineWidth) {
			onNewLine.append(line.text.charAt(line.text.length() - 1));
			line.text.deleteCharAt(line.text.length() - 1);
			width = fontRenderer.getStringWidth(line.text);
		}
		if(onNewLine.length() > 0) {
			if(lineN == currentLines.size() - 1) {
				if(currentLines.size() == leftPageHeight + rightPageHeight)
					nextReversed.append(onNewLine);
				else {
					BookLine newLine = new BookLine();
					newLine.text.append(onNewLine.reverse());
					currentLines.add(newLine);
				}
			} else {
				BookLine nextLine = currentLines.get(lineN + 1);
				if(nextLine.isNewLine) {
					nextLine = new BookLine();
					for(int i = 0; i < onNewLine.length(); ++i) {
						nextLine.text.insert(0, onNewLine.charAt(i));
					}
					currentLines.add(lineN + 1, nextLine);
					while(currentLines.size() > leftPageHeight + rightPageHeight) {
						BookLine reset = currentLines.remove(currentLines.size() - 1);
						nextReversed.append(reset.text.reverse());
					}
				} else {
					for(int i = 0; i < onNewLine.length(); ++i) {
						nextLine.text.insert(0, onNewLine.charAt(i));
					}
					fitLine(lineN + 1);
				}
			}
		}
	}

	private boolean nextPage() {
		if(nextReversed.length() > 0) {
			if(cursorLine != -1) {
				cursorLine = 0;
				cursorPosition = 0;
			}
			page++;
			// Compacting current page to prev text
			for(int i = 0; i < currentLines.size(); ++i) {
				BookLine line = currentLines.get(i);
				prevLines.add(line);
			}
			currentLines.clear();
			BookLine currentLine = new BookLine();
			float width = 0;
			int cutted = 0;
			for(int i = nextReversed.length() - 1; i >= 0; --i) {
				char c = nextReversed.charAt(i);
				if(c != '\247') {
					if(c == '\n') {
						//currentLine.width = width;
						currentLines.add(currentLine);
						currentLine = new BookLine();
						if(currentLines.size() >= leftPageHeight + rightPageHeight) {
							cutted++;
							break;
						}
						currentLine.isNewLine = true;
						width = 0;
						cutted++;
						continue;
					} else {
						int id = ChatAllowedCharacters.allowedCharacters.indexOf(c);
						if(id == -1) {
							cutted++;
							continue;
						}
						float newWidth = width + fontRenderer.charWidth[id + 32];
						if(newWidth > lineWidth) {
							BookLine prevLine = currentLine;
							//currentLine.width = width;
							currentLines.add(currentLine);
							currentLine = new BookLine();
							if(currentLines.size() >= leftPageHeight + rightPageHeight)
								break;
							width = fontRenderer.charWidth[id];
						} else
							width = newWidth;
					}
				} else {
					i--;
					currentLine.text.append(nextReversed.charAt(i));
					cutted++;
				}
				cutted++;
				currentLine.text.append(c);
			}
			if(currentLine.text.length() > 0)
				currentLines.add(currentLine);
			nextReversed = new StringBuilder(nextReversed.substring(0, Math.max(0, nextReversed.length() - cutted)));
			return true;
		}
		return false;
	}

	private boolean prevPage() {
		if(prevLines.size() > 0) {
			if(cursorLine != -1) {
				cursorLine = 0;
				cursorPosition = 0;
			}
			page--;
			for(int i = currentLines.size() - 1; i >= 0; --i) {
				BookLine line = currentLines.get(i);
				nextReversed.append(line.text.reverse());
				if(line.isNewLine)
					nextReversed.append('\n');
			}
			currentLines.clear();
			for(int i = Math.max(0, prevLines.size() - leftPageHeight - rightPageHeight); i < prevLines.size(); ++i) {
				BookLine line = prevLines.remove(i--);
				currentLines.add(line);
			}
			return true;
		}
		return false;
	}

	@Override
	protected void mouseClicked(int x, int y, int button) {
		if(button == 0) {
			// Next Page button
			if(x < (width - 254) / 2 + 251 && x > (width - 254) / 2 + 251 - 17) {
				if(y < (height - 171) / 2 + 163 && y > (height - 171) / 2 + 147) {
					nextPage();
					super.mouseClicked(x, y, button);
					return;
				}
			}
			// Prev Page button
			if(x > (width - 254) / 2 + 3 && x < (width - 254) / 2 + 3 + 17) {
				if(y < (height - 171) / 2 + 163 && y > (height - 171) / 2 + 147) {
					prevPage();
					super.mouseClicked(x, y, button);
					return;
				}
			}
			// Moving cursor
			if(isEditable) {
				// Header click
				if(y >= (height - 171) / 2 - paddingHeader - 1 && y <= (height - 171) / 2 - paddingHeader + lineHeight + 1) {
					int titleWidth = fontRenderer.getStringWidth(title);
					int xTitle = (width - titleWidth) / 2;
					if(x >= xTitle && x <= xTitle + titleWidth) {
						cursorLine = -1;
						cursorPosition = fontRenderer.getClosestSymbolByPadding(title, x - xTitle);
					}
				} else
				// Book click left
				if(x >= (width - 254) / 2 + paddingLeft && x <= width / 2 - paddingRight) {
					int line = (int) Math.ceil((y - (height - 171) / 2 - paddingTop - lineHeight * 2) / lineHeight);
					if(line >= 0 && line < leftPageHeight) {
						if(line >= currentLines.size())
							line = currentLines.size() - 1;
						BookLine bl = currentLines.get(line);
						if(bl != null) {
							cursorLine = line;
							cursorPosition = fontRenderer.getClosestSymbolByPadding(bl.text, x - (width - 254) / 2 - paddingLeft);
							super.mouseClicked(x, y, button);
							return;
						}
					}
				}
				// Book click right
				if(x >= width / 2 + paddingRight && x <= (width - 254) / 2 + 254) {
					int line = (int) Math.ceil((y - (height - 171) / 2 - paddingTop) / lineHeight);
					if(line >= 0 && line < rightPageHeight) {
						line += leftPageHeight;
						if(line >= currentLines.size())
							line = currentLines.size() - 1;
						BookLine bl = currentLines.get(line);
						if(bl != null) {
							cursorLine = line;
							cursorPosition = fontRenderer.getClosestSymbolByPadding(bl.text, x - width / 2 - paddingRight);
							super.mouseClicked(x, y, button);
							return;
						}
					}
				}
			}
		}
		super.mouseClicked(x, y, button);
	}

	@Override
	public void updateScreen() {
		cursorCycle++;
	}

	private void drawCursor(int x, int y) {
		if((cursorCycle / 10) % 2 == 0)
			fontRenderer.drawString(cursor, x + cursorPaddingLeft, y + cursorPaddingTop, cursorColor);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		/*if(renderList >= 0 && savedPage == page && !typed && savedHeight == height && savedWidth == width && (cursorCycle % 1000) != 0) {
			GL11.glCallList(renderList);
			if(isEditable)
				cursor();
			super.drawScreen(i, j, f);
			return;
		}
		if(renderList < 0)
			renderList = GL11.glGenLists(1);
		savedPage = page;
		savedHeight = height;
		savedWidth = width;
		typed = false;
		GL11.glNewList(renderList, GL11.GL_COMPILE_AND_EXECUTE);*/
		drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if(type == NORMAL_BOOK)
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/gc_images/gui/guibook.png"));
		else if(type == GOLDEN_BOOK)
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/gc_images/gui/guigoldenbook.png"));
		else if(type == DIAMOND_BOOK)
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/gc_images/gui/guidiamondbook.png"));
		else if(type == OBSIDIAN_BOOK)
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/gc_images/gui/guiobsidianbook.png"));
		zLevel = -90F;
		int xCorner = (width - 254) / 2;
		int yCorner = (height - 171) / 2;
		drawTexturedModalRect(xCorner, yCorner, 0, 0, 254, 171);
		// Rendering title
		int titleWidth = fontRenderer.getStringWidth(title);
		fontRenderer.drawStringWithShadow(title.toString(), (width - titleWidth) / 2, yCorner - paddingHeader, 0xffffff);
		if(subTitle.length() > 0) {
			int subtitleWidth = fontRenderer.getStringWidth(subTitle);
			fontRenderer.drawStringWithShadow(subTitle, (width - subtitleWidth) / 2, yCorner - paddingSubTitle, 0xffffff);
		}

		// Rendering current page
		xCorner += paddingLeft;
		yCorner += paddingTop;
		fontRenderer.drawString(new StringBuilder("���.: ").append(page).append(", ����.: ").append(characters >= maxCharacters ? "\247c" : "").append(characters).toString(), xCorner, yCorner, 0x555555);
		yCorner += lineHeight;
		yCorner += lineHeight;
		for(int n = 0; n < leftPageHeight; ++n) {
			if(currentLines.size() <= n)
				break;
			BookLine line = currentLines.get(n);
			fontRenderer.drawString(line.text.toString(), xCorner, yCorner, 0);
			yCorner += lineHeight;
		}
		yCorner = (height - 171) / 2;
		yCorner += paddingTop;
		xCorner = width / 2 + paddingRight;
		for(int n = 0; n < rightPageHeight; ++n) {
			int k = n + leftPageHeight;
			if(currentLines.size() <= k)
				break;
			BookLine line = currentLines.get(k);
			fontRenderer.drawString(line.text.toString(), xCorner, yCorner, 0);
			yCorner += lineHeight;
		}
		// Rendering cursor
		if(isEditable) {
			//GL11.glEndList();
			cursor();
		}// else
			//	GL11.glEndList();
		super.drawScreen(i, j, f);
	}

	private void cursor() {
		if(cursorLine == -1) {
			int symbolPadding = fontRenderer.getStringPartWidth(title, cursorPosition);
			drawCursor((width - fontRenderer.getStringWidth(title)) / 2 + symbolPadding, (height - 171) / 2 - paddingHeader);
		} else if(cursorLine < leftPageHeight) {
			// Render it on left
			BookLine line = currentLines.get(cursorLine);
			int symbolPadding = fontRenderer.getStringPartWidth(line.text, cursorPosition);
			int x = (width - 254) / 2 + paddingLeft + symbolPadding;
			int y = (height - 171) / 2 + paddingTop + lineHeight * (2 + cursorLine);
			drawCursor(x, y);
		} else if(cursorLine < leftPageHeight + rightPageHeight) {
			// And on right
			BookLine line = currentLines.get(cursorLine);
			int symbolPadding = fontRenderer.getStringPartWidth(line.text, cursorPosition);
			int x = width / 2 + paddingRight + symbolPadding;
			int y = (height - 171) / 2 + paddingTop + lineHeight * (cursorLine - leftPageHeight);
			drawCursor(x, y);
		}
	}
}
