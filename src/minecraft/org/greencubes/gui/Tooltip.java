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
