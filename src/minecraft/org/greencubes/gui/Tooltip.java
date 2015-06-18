package org.greencubes.gui;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.GChat;

public class Tooltip {
	
	protected static final FancyGUI gui = FancyGUI.getInstance(); // Short-cut
	
	public final String string;
	private String[] lines;
	private int maxWidth;
	private int x;
	private int y;
	private int padding;
	private boolean followMouse = false;
	public int firstLinePadding = 0;
	
	public Tooltip(String string, int x, int y, int maxWidth, int padding) {
		this.string = string;
		this.x = x;
		this.y = y;
		this.maxWidth = maxWidth;
		this.padding = padding;
	}
	
	public Tooltip setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
		this.lines = null;
		return this;
	}
	
	public Tooltip setCoords(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	public Tooltip setPadding(int padding) {
		this.padding = padding;
		return this;
	}
	
	public Tooltip setFollowMouse(boolean fm) {
		this.followMouse = fm;
		return this;
	}
	
	public void render(FontRenderer fr, int mouseX, int mouseY, int screenWidth, int screenHeight) {
		if(lines == null)
			lines = GChat.wrapText(string, maxWidth, 0, false);
		int x = this.x;
		int y = this.y;
		if(followMouse) {
			x = mouseX;
			y = mouseY;
		}
		gui.renderScaledTooltip(x, y, lines, screenWidth, screenHeight, firstLinePadding, padding);
	}
	
}
