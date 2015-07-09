package net.lahwran.fevents;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

public class HandlerList<TEvent extends Event<TEvent>> {
	
	private static ArrayList<HandlerList<?>> alllists = new ArrayList<HandlerList<?>>();
	
	Listener<TEvent>[][] handlers;
	int[] handlerids;
	private final EnumMap<Order, ArrayList<Listener<TEvent>>> handlerslots;
	private boolean baked = false;

	public static void bakeall() {
		for(HandlerList<?> h : alllists)
			h.bake();
	}

	public HandlerList() {
		this.handlerslots = new EnumMap<Order, ArrayList<Listener<TEvent>>>(Order.class);
		for(Order o : Order.values()) {
			this.handlerslots.put(o, new ArrayList<Listener<TEvent>>());
		}
		alllists.add(this);
	}

	public void register(Listener<TEvent> listener, Order order) {
		if(this.handlerslots.get(order).contains(listener))
			throw new IllegalStateException("This listener is already registered to order " + order.toString());
		this.baked = false;
		this.handlerslots.get(order).add(listener);
	}

	public void unregister(Listener<TEvent> listener) {
		for(Order o : Order.values())
			unregister(listener, o);
	}

	public void unregister(Listener<TEvent> listener, Order order) {
		if(this.handlerslots.get(order).contains(listener)) {
			this.baked = false;
			this.handlerslots.get(order).remove(listener);
		}
	}

	@SuppressWarnings("unchecked")
	void bake() {
		if(this.baked) {
			return;
		}
		ArrayList<Listener<TEvent>[]> handlerslist = new ArrayList<Listener<TEvent>[]>();
		ArrayList<Integer> handleridslist = new ArrayList<Integer>();
		for(Map.Entry<Order, ArrayList<Listener<TEvent>>> entry : this.handlerslots.entrySet()) {
			Order orderslot = (Order) entry.getKey();

			ArrayList<Listener<TEvent>> list = entry.getValue();

			int ord = orderslot.getIndex();
			handlerslist.add((Listener<TEvent>[]) list.toArray(new Listener[list.size()]));
			handleridslist.add(Integer.valueOf(ord));
		}
		this.handlers = (Listener<TEvent>[][]) handlerslist.toArray(new Listener[handlerslist.size()][]);
		this.handlerids = new int[handleridslist.size()];
		for(int i = 0; i < handleridslist.size(); i++) {
			this.handlerids[i] = handleridslist.get(i).intValue();
		}
		this.baked = true;
	}
}