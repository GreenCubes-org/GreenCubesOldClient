// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, EntityPlayerSP, GuiIngame, ChatAllowedCharacters

public class GuiChat extends GuiScreen {

	protected StringBuilder message = new StringBuilder();
	private int updateCounter;
	private static final String allowedCharacters = ChatAllowedCharacters.allowedCharacters;
	// GreenCubes Improved Chat start
	public int cursorPosition;

	// GreenCubes end

	public GuiChat(boolean isSlash) {
		updateCounter = 0;
		if(isSlash) {
			message.append('/');
			cursorPosition++;
		}
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void updateScreen() {
		updateCounter++;
	}

	@Override
	protected void keyTyped(char c, int i) {
		if(mc.ingameGUI.editingName) {
			mc.ingameGUI.keyForName(c, i);
			return;
		}
		if(mc.ingameGUI.inSettings)
			return;
		boolean process = false;
		// GreenCubes Improved Chat start
		boolean flag = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			if(cursorPosition == 0) {
				if(mc.chat.activeTab.sendPrefix != null && mc.chat.activeTab.sendPrefix.length() != 0) {
					message.insert(0, mc.chat.activeTab.sendPrefix);
					cursorPosition = mc.chat.activeTab.sendPrefix.length();
					mc.chat.activeTab.activeChannel = mc.chat.channels.get(1);
					mc.chat.activeTab.prefix = "";
					mc.chat.activeTab.sendPrefix = "";
				} else
					return;
			}
			for(cursorPosition--; flag && cursorPosition > 0 && Character.isLetterOrDigit(message.charAt(cursorPosition - 1)); cursorPosition--) {
			}
		} else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && cursorPosition < message.length()) {
			for(cursorPosition++; flag && cursorPosition < message.length() && Character.isLetterOrDigit(message.charAt(cursorPosition - 1)); cursorPosition++) {
			}
		} else if(Keyboard.isKeyDown(Keyboard.KEY_DELETE) && cursorPosition < message.length()) {
			message.deleteCharAt(cursorPosition);
			// message = (new StringBuilder(String.valueOf(message.substring(0,
			// cursorPosition)))).append(message.substring(cursorPosition +
			// 1)).toString();
		} else if(Keyboard.isKeyDown(Keyboard.KEY_PRIOR) && mc.chat.chatScroll <= mc.chat.activeTab.lines.size() - mc.chat.lines / 2) {
			mc.chat.chatScroll += mc.chat.lines / 2;
		} else if(Keyboard.isKeyDown(Keyboard.KEY_NEXT) && mc.chat.chatScroll > 0) {
			mc.chat.chatScroll -= mc.chat.lines / 2;
			if(mc.chat.chatScroll < 0)
				mc.chat.chatScroll = 0;
		} else if(Keyboard.isKeyDown(Keyboard.KEY_HOME)) {
			cursorPosition = 0;
		} else if(Keyboard.isKeyDown(Keyboard.KEY_END)) {
			cursorPosition = message.length();
		} else if(Keyboard.isKeyDown(Keyboard.KEY_UP) && mc.chat.commandScroll < mc.chat.pastCommands.size()) {
			mc.chat.commandScroll++;
			message = new StringBuilder(mc.chat.pastCommands.get(mc.chat.pastCommands.size() - mc.chat.commandScroll));
			cursorPosition = message.length();
		} else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN) && mc.chat.commandScroll > 0) {
			mc.chat.commandScroll--;
			if(mc.chat.commandScroll == 0) {
				message = new StringBuilder();
			} else {
				message = new StringBuilder(mc.chat.pastCommands.get(mc.chat.pastCommands.size() - mc.chat.commandScroll));
			}
			cursorPosition = message.length();
		} else if(flag && Keyboard.isKeyDown(Keyboard.KEY_TAB)) {
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
				mc.chat.previosTab();
			else
				mc.chat.nextTab();
		} else {
			process = true;
		}
		if(c == '\026') {
			String s = GChat.paste();
			StringBuilder s2 = new StringBuilder(s.length());
			for(int j = 0; j < s.length(); ++j) {
				char c2 = s.charAt(j);
				if(allowedCharacters.indexOf(c2) >= 0)
					s2.append(c2);
			}
			s = s2.toString();
			int j = 300 - message.length();
			if(j > s.length()) {
				j = s.length();
			}
			if(j > 0) {
				message.insert(cursorPosition, s.substring(0, j));
				cursorPosition += j;
			}
			checkcursor();
			return;
		}
		if(c == '\003') {
			GChat.copy(message.toString());
			return;
		}
		if(i == Keyboard.KEY_ESCAPE) {
			mc.displayGuiScreen(null);
			mc.chat.chatScroll = 0;
			return;
		}
		if(i == Keyboard.KEY_RETURN || i == Keyboard.KEY_NUMPADENTER) {
			mc.chat.pastCommands.add(message.toString());
			mc.chat.commandScroll = 0;
			mc.chat.processOutput(message.toString());
			mc.displayGuiScreen(null);
			mc.chat.chatScroll = 0;
			return;
		}
		if(i == Keyboard.KEY_BACK) {
			if(cursorPosition == 0) {
				if(mc.chat.activeTab.sendPrefix != null && mc.chat.activeTab.sendPrefix.length() != 0) {
					message.insert(0, mc.chat.activeTab.sendPrefix);
					cursorPosition = mc.chat.activeTab.sendPrefix.length();
				}
				mc.chat.activeTab.activeChannel = mc.chat.channels.get(1);
				mc.chat.activeTab.prefix = "";
				mc.chat.activeTab.sendPrefix = "";
				if(cursorPosition == 0)
					return;
			}
			if(message.length() > 0) {
				message.deleteCharAt(--cursorPosition);
				while(flag && cursorPosition > 0 && Character.isLetterOrDigit(message.charAt(cursorPosition - 1)))
					message.deleteCharAt(--cursorPosition);
			}
			process = false;
		}
		if(allowedCharacters.indexOf(c) >= 0 && message.length() + (mc.chat.activeTab.sendPrefix != null ? mc.chat.activeTab.sendPrefix.length() : 0) < 256) {
			message.insert(cursorPosition, c);
			cursorPosition++;
			process = false;
		}
		message = mc.chat.processKeyInput(message, cursorPosition);
		if(process && i == mc.gameSettings.keyBindChat.keyCode) {
			// close
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		// GreenCubes Improved Chat start
		checkcursor();

		int realCursor = cursorPosition;
		StringBuilder newMessage = new StringBuilder();
		if(mc.chat.activeTab.prefix != null && mc.chat.activeTab.prefix.length() > 0) {
			newMessage.append(mc.chat.activeTab.prefix).append(' ');
			realCursor += mc.chat.activeTab.prefix.length() + 1;
		}
		newMessage.append(message);
		List<StringBuilder> list = mc.chat.processChatDisplay(newMessage);
		int i1 = list.size();
		int j1 = mc.chat.bgColor;
		drawRect(2, height - 4 - i1 * 12, 2 + GChat.chatWidth, height - 2, j1);
		int passed = 0;
		boolean drawed = false;
		for(int n = 0; n < list.size(); ++n) {
			fontRenderer.drawStringBuilderWithShadow(list.get(n), 4, height - 12 * i1--, 0xe0e0e0);
			if(this.updateCounter % 12 > 4 && !drawed && !mc.ingameGUI.inSettings) {
				if(realCursor - passed <= list.get(n).length()) {
					drawed = true;
					int padding = mc.fontRenderer.getStringPartWidth(list.get(n), realCursor - passed);
					drawString(fontRenderer, "|", 3 + padding, height - 12 * i1 - 11, 0xe0e0e0);
				} else
					passed += list.get(n).length();
			}
		}

		if(!mc.ingameGUI.scroll()) {
			int k1 = Mouse.getDWheel();
			if(k1 > 0) {
				if(mc.chat.chatScroll <= mc.chat.activeTab.lines.size() - 11)
					mc.chat.chatScroll += 2;
			} else if(k1 < 0) {
				mc.chat.chatScroll -= 2;
				if(mc.chat.chatScroll < 0)
					mc.chat.chatScroll = 0;
			}
		}
		super.drawScreen(i, j, f);
	}

	@Override
	protected void mouseClicked(int i, int j, int k) {
		if(k != 0)
			if(mc.ingameGUI.field_933_a != null) {
				if(this.message.length() > 0 && this.message.charAt(this.message.length() - 1) != ' ') {
					this.message.append(' ');// += " ";
				}
				this.message.append(mc.ingameGUI.field_933_a);
				int c = 300;
				if(this.message.length() > c)
					this.message = new StringBuilder(this.message.substring(0, c));
			} else {
				super.mouseClicked(i, j, k);
			}
		mc.ingameGUI.mouseClicked(i, j, k);
	}

	@Override
	protected void mouseMovedOrUp(int x, int y, int key) {
		mc.ingameGUI.mouseMovedOrUp(x, y, key);
	}

	private void checkcursor() {
		if(cursorPosition > message.length())
			cursorPosition = message.length();
		if(cursorPosition < 0)
			cursorPosition = 0;
	}
}
