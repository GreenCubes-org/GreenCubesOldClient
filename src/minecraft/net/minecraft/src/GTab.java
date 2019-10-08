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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GTab {

	private final GChat chat;

	public boolean isSystem;
	public String name;
	public Map<Integer, GChannel> attachedChannels = new HashMap<Integer, GChannel>();
	public List<GLine> lines = new LinkedList<GLine>();
	public int nameWidth;
	public GChannel activeChannel;
	public String prefix = "";
	public String sendPrefix = "";
	public boolean hasNewMessage = false;
	public List<Integer> readyToJoin = new ArrayList<Integer>();
	public List<Integer> generalLeaved = new ArrayList<Integer>();

	public GTab(GChat chat, String name) {
		this.chat = chat;
		this.name = name;
		nameWidth = GChat.mc.fontRenderer.getStringWidth(name);
	}

	public void setName(String name) {
		if(this.name.equals(name))
			return;
		this.name = name;
		nameWidth = GChat.mc.fontRenderer.getStringWidth(name);
	}

	public GLine addNewLine(String message) {
		GLine line = new GLine(message);
		lines.add(0, line);
		if(chat.activeTab != this)
			hasNewMessage = true;
		if(lines.size() > chat.maxHistoryPerTab)
			lines.remove(lines.size() - 1);
		return line;
	}
}
