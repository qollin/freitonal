package de.cr.freitonal.client.event.dfa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class DeltaMap {
	private final HashMap<String, HashMap<String, ArrayList<Transition>>> map;
	private final HashSet<String> states;

	public DeltaMap() {
		map = new HashMap<String, HashMap<String, ArrayList<Transition>>>();
		states = new HashSet<String>();
	}

	public void addTransition(String fromState, String trigger, String toState) {
		addTransition(fromState, trigger, null, toState);
	}

	public void addTransition(String fromState, String trigger, TriggerParam triggerParam, String toState) {
		addTransition(fromState, trigger, new Transition(triggerParam, toState, null));
	}

	public void addTransition(String fromState, String trigger, String toState, TransitionAction action) {
		addTransition(fromState, trigger, new Transition(null, toState, action));
	}

	public void addTransition(String fromState, String trigger, Transition transition) {
		prepareAddingAState(fromState);
		prepareAddingATrigger(fromState, trigger);

		map.get(fromState).get(trigger).add(transition);

		states.add(fromState);
		states.add(transition.getTargetState());
	}

	private void prepareAddingAState(String fromState) {
		if (!map.containsKey(fromState)) {
			map.put(fromState, new HashMap<String, ArrayList<Transition>>());
		}
	}

	private void prepareAddingATrigger(String fromState, String trigger) {
		if (!map.get(fromState).containsKey(trigger)) {
			map.get(fromState).put(trigger, new ArrayList<Transition>());
		}
	}

	public String getState(String fromState, Trigger trigger) {
		Transition transition = getTransition(fromState, trigger);
		if (transition != null) {
			return transition.getTargetState();
		}

		return null;
	}

	public TransitionAction getAction(String fromState, Trigger trigger) {
		Transition transition = getTransition(fromState, trigger);

		if (transition != null) {
			return transition.getAction();
		}

		return null;
	}

	private Transition getTransition(String fromState, Trigger trigger) {
		ArrayList<Transition> transitions = getTransitions(fromState, trigger);
		if (transitions == null) {
			return null;
		}

		for (Transition transition : transitions) {
			if (transition.getTriggerParam() == null || //no trigger param means it matches
					transition.getTriggerParam().matches(trigger.getTriggerParam())) {
				return transition;
			}
		}
		return null;
	}

	public ArrayList<Transition> getTransitions(String fromState, Trigger trigger) {
		if (map.containsKey(fromState) && map.get(fromState).containsKey(trigger.getTriggerString())) {
			ArrayList<Transition> transitions = map.get(fromState).get(trigger.getTriggerString());
			Collections.sort(transitions);

			return transitions;
		}

		return null;
	}

	public boolean containsState(String state) {
		return states.contains(state);
	}

}
