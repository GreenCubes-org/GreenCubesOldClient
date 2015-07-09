package net.lahwran.fevents;

public class EventManager {
	public static <TEvent extends Event<TEvent>> void callEvent(TEvent event) {
		HandlerList<TEvent> handlerlist = event.getHandlers();
		handlerlist.bake();

		Listener<TEvent>[][] handlers = handlerlist.handlers;
		int[] handlerids = handlerlist.handlerids;

		for(int arrayidx = 0; arrayidx < handlers.length; arrayidx++) {
			if((event.isCancelled()) && ((handlerids[arrayidx] & 0x1) == 0)) {
				continue;
			}
			for(int handler = 0; handler < handlers[arrayidx].length; handler++)
				try {
					handlers[arrayidx][handler].onEvent(event);
				} catch (Throwable t) {
					System.err.println("Error while passing event " + event);
					t.printStackTrace();
				}
		}
	}
}