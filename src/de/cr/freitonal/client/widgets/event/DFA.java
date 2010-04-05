package de.cr.freitonal.client.widgets.event;

import java.util.HashMap;
import java.util.HashSet;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.GwtEvent.Type;

import de.cr.freitonal.client.event.SearchFieldChangedEvent;
import de.cr.freitonal.client.event.SearchFieldChangedHandler;
import de.cr.freitonal.client.event.TransitionHandler;

public class DFA {
	private final State initialState;
	private State currentState;
	private final HandlerManager eventBus;
	private final HashSet<Type<?>> registeredEventTypes = new HashSet<Type<?>>();
	private final HashMap<State, HashMap<Type<?>, State>> transitions = new HashMap<State, HashMap<Type<?>, State>>();

	public DFA(State initialState, HandlerManager eventBus) {
		this.initialState = initialState;
		this.eventBus = eventBus;
		currentState = initialState;
	}

	public void addTransition(final Type<SearchFieldChangedHandler> type, State from, State to) {
		addTransition(type, from, to, null);
	}

	public void addTransition(final Type<SearchFieldChangedHandler> type, State from, State to, final TransitionHandler handler) {
		if (!registeredEventTypes.contains(type)) {
			eventBus.addHandler(type, new SearchFieldChangedHandler() {
				public void onSearchFieldChanged(SearchFieldChangedEvent event) {
					transition(type, handler, event);
				}
			});
			registeredEventTypes.add(type);
		}
		if (!transitions.containsKey(from)) {
			transitions.put(from, new HashMap<Type<?>, State>());
		}
		transitions.get(from).put(type, to);
	}

	private void transition(Type<?> type, TransitionHandler handler, GwtEvent<?> event) {
		if (!transitions.containsKey(currentState)) {
			throw new IllegalStateException("I am in an unknown state: " + currentState);
		}
		if (!transitions.get(currentState).containsKey(type)) {
			throw new IllegalStateException("I have no transition of type " + type + " from " + currentState);
		}
		State to = transitions.get(currentState).get(type);
		currentState = to;

		if (handler != null) {
			handler.onTransition(event);
		}
	}

	public State getCurrentState() {
		return currentState;
	}

	public void reset() {
		currentState = initialState;
	}
}
