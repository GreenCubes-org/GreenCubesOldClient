package net.lahwran.fevents;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

public class HandlerList<TEvent extends Event<TEvent>> {
	Listener<TEvent>[][] handlers;
	int[] handlerids;
	private final EnumMap<Order, ArrayList<Listener<TEvent>>> handlerslots;
	private boolean baked = false;

	private static ArrayList<HandlerList> alllists = new ArrayList();

	public static void bakeall() {
		for(HandlerList h : alllists)
			h.bake();
	}

	public HandlerList() {
		this.handlerslots = new EnumMap(Order.class);
		for(Order o : Order.values()) {
			this.handlerslots.put(o, new ArrayList());
		}
		alllists.add(this);
	}

	public void register(Listener<TEvent> listener, Order order) {
		if(((ArrayList) this.handlerslots.get(order)).contains(listener))
			throw new IllegalStateException("This listener is already registered to order " + order.toString());
		this.baked = false;
		((ArrayList) this.handlerslots.get(order)).add(listener);
	}

	public void unregister(Listener<TEvent> listener) {
		for(Order o : Order.values())
			unregister(listener, o);
	}

	public void unregister(Listener<TEvent> listener, Order order) {
		if(((ArrayList) this.handlerslots.get(order)).contains(listener)) {
			this.baked = false;
			((ArrayList) this.handlerslots.get(order)).remove(listener);
		}
	}

	void bake() {
		if(this.baked) {
			return;
		}
		ArrayList handlerslist = new ArrayList();
		ArrayList handleridslist = new ArrayList();
		for(Map.Entry entry : this.handlerslots.entrySet()) {
			Order orderslot = (Order) entry.getKey();

			ArrayList list = (ArrayList) entry.getValue();

			int ord = orderslot.getIndex();
			handlerslist.add(list.toArray(new Listener[list.size()]));
			handleridslist.add(Integer.valueOf(ord));
		}
		this.handlers = ((Listener[][]) handlerslist.toArray(new Listener[handlerslist.size()][]));
		this.handlerids = new int[handleridslist.size()];
		for(int i = 0; i < handleridslist.size(); i++) {
			this.handlerids[i] = ((Integer) handleridslist.get(i)).intValue();
		}
		this.baked = true;
	}
}