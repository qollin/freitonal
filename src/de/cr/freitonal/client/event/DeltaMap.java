package de.cr.freitonal.client.event;

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
		addTransition(fromState, trigger, triggerParam, toState, null);
	}

	public void addTransition(String fromState, String trigger, String toState, TransitionAction action) {
		addTransition(fromState, trigger, null, toState, action);
	}

	public void addTransition(String fromState, String trigger, TriggerParam triggerParam, String toState, TransitionAction action) {
		prepareAddingAState(fromState);
		prepareAddingATrigger(fromState, trigger);

		map.get(fromState).get(trigger).add(new Transition(triggerParam, toState, action));

		states.add(fromState);
		states.add(toState);
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

	public String getState(String fromState, String triggerString) {
		return getState(fromState, new Trigger(triggerString));
	}

	public String getState(String fromState, Trigger trigger) {
		Transition transition = getTransition(fromState, trigger);
		if (transition != null) {
			return transition.targetState;
		}

		return null;
	}

	public TransitionAction getAction(String fromState, String triggerString) {
		return getAction(fromState, new Trigger(triggerString));
	}

	public TransitionAction getAction(String fromState, Trigger trigger) {
		Transition transition = getTransition(fromState, trigger);

		if (transition != null) {
			return transition.action;
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

	public static class Transition implements Comparable<Transition> {
		private final TriggerParam triggerParam;
		String targetState;
		TransitionAction action;

		public Transition(TriggerParam triggerParam, String state, TransitionAction action) {
			this.triggerParam = triggerParam;
			this.targetState = state;
			this.action = action;
		}

		public int compareTo(Transition other) {
			if (other.getTriggerParam() == null && getTriggerParam() != null) {
				return -1;
			} else if (other.getTriggerParam() != null && getTriggerParam() == null) {
				return 1;
			} else {
				return 0;
			}
		}

		/**
		 * @return the triggerParam
		 */
		public TriggerParam getTriggerParam() {
			return triggerParam;
		}

	}

}
