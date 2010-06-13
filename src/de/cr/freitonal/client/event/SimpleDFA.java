package de.cr.freitonal.client.event;

import java.util.HashMap;

public class SimpleDFA {
	private boolean debug;

	private final HashMap<String, HashMap<Trigger, String>> delta = new HashMap<String, HashMap<Trigger, String>>();
	private final HashMap<Transition, TransitionAction> actions = new HashMap<Transition, TransitionAction>();
	private String currentState;

	public void addTransition(String fromState, String trigger, String toState) {
		addTransition(fromState, trigger, toState, null);
	}

	public void addTransition(String fromState, String trigger, String toState, TransitionAction transitionAction) {
		addTransition(fromState, trigger, null, toState, transitionAction);
	}

	public void addTransition(String fromState, String triggerString, Object triggerParam, String toState, TransitionAction transitionAction) {
		if (!delta.containsKey(fromState)) {
			delta.put(fromState, new HashMap<Trigger, String>());
		}

		Trigger trigger = new Trigger(triggerString, triggerParam);
		delta.get(fromState).put(trigger, toState);

		if (transitionAction != null) {
			actions.put(new Transition(fromState, trigger, toState), transitionAction);
		}
	}

	public void addTransition(String[] fromStates, String trigger, String toState, AbstractTransitionAction transitionAction) {
		for (String fromState : fromStates) {
			addTransition(fromState, trigger, toState, transitionAction);
		}
	}

	public void start(String startState) {
		if (!delta.containsKey(startState)) {
			throw new IllegalStateException("unknown state: " + startState);
		}
		currentState = startState;
	}

	private TransitionAction moveToNextState(String triggerString, Object triggerParam) {
		Trigger trigger = new Trigger(triggerString, triggerParam);
		if (delta.get(currentState) == null || !delta.get(currentState).containsKey(trigger)) {
			throw new IllegalStateException(triggerString + " is not a transition away from " + currentState);
		}

		String nextState = delta.get(currentState).get(trigger);
		Transition transition = new Transition(currentState, trigger, nextState);
		currentState = nextState;

		if (actions.containsKey(transition)) {
			return actions.get(transition);
		}

		return null;
	}

	public void transitionWithTriggerParam(String trigger, Object triggerParam) {
		TransitionAction action = moveToNextState(trigger, triggerParam);

		if (action != null) {
			action.onTransition();
		}
	}

	public void transition(String trigger) {
		transitionWithTriggerParam(trigger, null);
	}

	public void transition(String trigger, Object... parameter) {
		String oldState = currentState;

		if (parameter == null) {
			throw new IllegalArgumentException("transition parameter must not be null");
		}
		TransitionAction action = moveToNextState(trigger, null);

		if (debug) {
			System.err.println("transitioning from " + oldState + " to " + currentState + " through " + trigger);
		}

		if (action != null) {
			action.onTransition(parameter);
		}
	}

	public String getState() {
		return currentState;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

}
