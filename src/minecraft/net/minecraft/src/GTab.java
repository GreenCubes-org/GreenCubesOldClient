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
