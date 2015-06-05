package net.lahwran.fevents;

public abstract class Event<TEvent extends Event<TEvent>> {
	protected boolean cancelled = false;

	protected abstract HandlerList<TEvent> getHandlers();

	protected abstract String getEventName();

	@Override
	public String toString() {
		return getEventName() + " (" + getClass().getName() + ")";
	}

	protected void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public boolean isCancelled() {
		return this.cancelled;
	}
}