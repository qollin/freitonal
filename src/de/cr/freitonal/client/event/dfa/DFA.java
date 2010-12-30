package de.cr.freitonal.client.event.dfa;

public class DFA {
	private final DeltaMap delta = new DeltaMap();
	private String currentState;

	public void addTransition(String[] fromStates, String trigger, String toState, AbstractTransitionAction transitionAction) {
		for (String fromState : fromStates) {
			addTransition(fromState, trigger, toState, transitionAction);
		}
	}

	public void addTransition(String fromState, String trigger, String toState) {
		addTransition(fromState, trigger, toState, null);
	}

	public void addTransition(String fromState, String trigger, String toState, TransitionAction transitionAction) {
		addTransitionWithTriggerParam(fromState, trigger, new Transition(null, toState, transitionAction));
	}

	public void addTransitionWithTriggerParam(String[] fromStates, String triggerString, Transition transition) {
		for (String fromState : fromStates) {
			addTransitionWithTriggerParam(fromState, triggerString, transition);
		}
	}

	public void addTransitionWithTriggerParam(String fromState, String triggerString, Transition transition) {
		delta.addTransition(fromState, triggerString, transition);
	}

	public void addTransitionWithTriggerParam(String[] fromStates, String triggerString, TriggerParam triggerParam, String toState) {
		for (String fromState : fromStates) {
			addTransitionWithTriggerParam(fromState, triggerString, triggerParam, toState);
		}
	}

	public void addTransitionWithTriggerParam(String fromState, String triggerString, TriggerParam triggerParam, String toState) {
		addTransitionWithTriggerParam(fromState, triggerString, new Transition(triggerParam, toState, null));
	}

	public void start(String startState) {
		if (!delta.containsState(startState)) {
			throw new IllegalStateException("unknown state: " + startState);
		}
		currentState = startState;
	}

	public void transition(Trigger trigger) {
		transition(trigger, (Object[]) null);
	}

	public void transition(String trigger) {
		transition(new Trigger(trigger));
	}

	public void transition(String trigger, Object... parameters) {
		transition(new Trigger(trigger), parameters);
	}

	public void transition(Trigger trigger, Object... parameters) {
		TransitionAction action = moveToNextState(trigger);

		if (action != null) {
			if (parameters != null) {
				action.onTransition(parameters);
			} else {
				action.onTransition();
			}
		}
	}

	private TransitionAction moveToNextState(Trigger trigger) {
		String nextState = delta.getState(currentState, trigger);

		if (nextState == null) {
			throw new IllegalStateException(trigger.getTriggerString() + " is not a transition away from " + currentState);
		}

		TransitionAction action = delta.getAction(currentState, trigger);
		currentState = nextState;

		return action;
	}

	public String getState() {
		return currentState;
	}
}
