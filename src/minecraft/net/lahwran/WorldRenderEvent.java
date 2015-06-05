package net.lahwran;

import net.lahwran.fevents.Event;
import net.lahwran.fevents.HandlerList;

public class WorldRenderEvent extends Event<WorldRenderEvent> {
	
	private static final WorldRenderEvent singleton = new WorldRenderEvent();
	public float partialTick;
	public static final HandlerList<WorldRenderEvent> handlers = new HandlerList();

	public static WorldRenderEvent update(float partialTick) {
		singleton.partialTick = partialTick;
		return singleton;
	}

	@Override
	protected String getEventName() {
		return "Worldedit CUI Communication event";
	}

	@Override
	protected HandlerList<WorldRenderEvent> getHandlers() {
		return handlers;
	}
}