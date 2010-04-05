package de.cr.freitonal.client.widgets.event;

import java.util.HashMap;

public class SimpleDFA {
	private final HashMap<String, HashMap<String, String>> delta = new HashMap<String, HashMap<String, String>>();
	private String currentState;

	public void addTransition(String fromState, String trigger, String toState) {
		if (!delta.containsKey(fromState)) {
			delta.put(fromState, new HashMap<String, String>());
		}
		delta.get(fromState).put(trigger, toState);
	}

	public void start(String startState) {
		if (!delta.containsKey(startState)) {
			throw new IllegalStateException("unknown state: " + startState);
		}
		currentState = startState;
	}

	public void transition(String trigger) {
		if (!delta.get(currentState).containsKey(trigger)) {
			throw new IllegalStateException(trigger + " is not a transition away from " + currentState);
		}
		currentState = delta.get(currentState).get(trigger);
	}

	public String getState() {
		return currentState;
	}
}
