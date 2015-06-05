package net.lahwran;

import net.lahwran.fevents.Cancellable;
import net.lahwran.fevents.Event;
import net.lahwran.fevents.HandlerList;
import net.lahwran.wecui.WorldEditCUI;

public class ChatEvent extends Event<ChatEvent> implements Cancellable {
	public String chat;
	public static final HandlerList<ChatEvent> handlers = new HandlerList();

	public ChatEvent(String chat) {
		this.chat = chat;
		WorldEditCUI.debug("chat message: " + chat);
	}

	@Override
	protected String getEventName() {
		return "ChatEvent";
	}

	@Override
	protected HandlerList<ChatEvent> getHandlers() {
		return handlers;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}