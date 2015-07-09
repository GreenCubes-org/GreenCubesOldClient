package org.greencubes.gui;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ChatAllowedCharacters;
import net.minecraft.src.FontRenderer;

public class InputField {
	
	protected static final FancyGUI gui = FancyGUI.getInstance(); // Short-cut
	protected static final FontRenderer fr = Minecraft.theMinecraft.fontRenderer;
	
	public int maxLength;
	public boolean isTyping = false;
	private int updateCounter = 0;
	public Tooltip toolTip;
	private StringBuilder string = new StringBuilder();
	private int cursorPosition = 0;
	private int width;
	private int x = -1;
	private int y = -1;
	
	public InputField(int maxLength, int width) {
		this.maxLength = maxLength;
		this.width = width;
	}
	
	public void keyTyped(char c, int key) {
		if(key == Keyboard.KEY_ESCAPE) {
			this.isTyping = false;
			return;
		}
		boolean flag = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
		if(ChatAllowedCharacters.allowedCharacters.indexOf(c) >= 0 && string.length() < maxLength) {
			string.insert(cursorPosition, c);
			if(fr.getStringWidth(string) > this.width - 4)
				string.deleteCharAt(cursorPosition);
			else
				cursorPosition++;
		} else if(key == Keyboard.KEY_BACK) {
			if(cursorPosition != 0 && string.length() > 0) {
				string.deleteCharAt(--cursorPosition);
				while(flag && cursorPosition > 0 && Character.isLetterOrDigit(string.charAt(cursorPosition - 1)))
					string.deleteCharAt(--cursorPosition);
			}
		}else if(key == Keyboard.KEY_LEFT) {
			if(cursorPosition == 0)
				return;
			for(cursorPosition--; flag && cursorPosition > 0 && Character.isLetterOrDigit(string.charAt(cursorPosition - 1)); cursorPosition--) {
			}
		} else if(key == Keyboard.KEY_RIGHT && cursorPosition < string.length()) {
			for(cursorPosition++; flag && cursorPosition < string.length() && Character.isLetterOrDigit(string.charAt(cursorPosition - 1)); cursorPosition++) {
			}
		} else if(key == Keyboard.KEY_DELETE && cursorPosition < string.length()) {
			string.deleteCharAt(cursorPosition);
		} else if(key == Keyboard.KEY_HOME) {
			cursorPosition = 0;
		} else if(key == Keyboard.KEY_END) {
			cursorPosition = string.length();
		}
	}
	
	public boolean mouseClicked(int x, int y, int key) {
		if(this.x == -1 && this.y == -1 || key != 0)
			return false;
		if(x > this.x && y > this.y && x < this.x + this.width && y < this.y + 13) {
			this.isTyping = true;
			return true;
		}
		this.isTyping = false;
		return false;
	}
	
	public void update() {
		this.updateCounter++;
	}
	
	public void setString(CharSequence cs) {
		string.setLength(0);
		string.append(cs);
		cursorPosition = 0;
	}
	
	public void render(GuiAdapter adapter, int x, int y, float scale) {
		this.x = x;
		this.y = y;
		gui.enableMode();
		gui.setScale(scale);
		gui.renderInterfaceNinePart(x, y, width, 13, 394, 257, gui.inputFieldNPI);
		gui.setScale(1f);
		if(string.length() > 0)
			fr.drawStringBuilderWithShadow(string, x + 2, y + 2, 0xFFFFFF);
		if(isTyping) {
			int pos = fr.getStringPartWidth(string, cursorPosition);
			if(updateCounter % 12 > 4) {
				fr.drawStringWithShadow("|", x + 2 + pos, y + 2, 0xFFFFFF);
			}
			if(toolTip != null)
				adapter.addTooltip(toolTip.setCoords(x + 2, y + 11));
		}
		gui.disableMode();
	}
	
}
