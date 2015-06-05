package net.lahwran;

import net.lahwran.fevents.Event;
import net.lahwran.fevents.HandlerList;
import net.lahwran.wecui.WorldEditCUI;

public class WECUIEvent extends Event<WECUIEvent> {
	public String type;
	public String[] params;
	private boolean handled = false;

	public static final HandlerList<WECUIEvent> handlers = new HandlerList();

	public WECUIEvent(String type, String[] params) {
		this.type = type;
		this.params = params;
		String debugmsg = "CUI event: " + type + " ";
		for(int i = 0; i < params.length; i++) {
			debugmsg = debugmsg + params[i] + " ";
		}
		WorldEditCUI.debug(debugmsg);
	}

	public void markHandled() {
		this.handled = true;
	}

	public void markInvalid(String reason) {
		String debugmsg = "WARNING - INVALID WECUIEvent " + this.type;
		for(int i = 0; i < this.params.length; i++) {
			debugmsg = debugmsg + "|" + this.params[i];
		}
		debugmsg = debugmsg + " because " + reason;
		WorldEditCUI.debug(debugmsg);
		markHandled();
	}

	public float getFloat(int index) {
		return Float.parseFloat(this.params[index]);
	}

	public int getInt(int index) {
		return (int) Float.parseFloat(this.params[index]);
	}

	public boolean isHandled() {
		return this.handled;
	}

	@Override
	public boolean isCancelled() {
		return isHandled();
	}

	@Override
	protected String getEventName() {
		return "Worldedit CUI Communication event";
	}

	@Override
	protected HandlerList<WECUIEvent> getHandlers() {
		return handlers;
	}
}