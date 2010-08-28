package de.cr.freitonal.client.event;

public class SimpleDFA {
	private boolean debug;

	private final DeltaMap delta = new DeltaMap();
	private String currentState;

	public void addTransition(String fromState, String trigger, String toState) {
		addTransition(fromState, trigger, toState, null);
	}

	public void addTransition(String fromState, String trigger, String toState, TransitionAction transitionAction) {
		addTransitionWithTriggerParam(fromState, trigger, null, toState, transitionAction);
	}

	public void addTransitionWithTriggerParam(String fromState, String triggerString, TriggerParam triggerParam, String toState,
			TransitionAction transitionAction) {
		delta.addTransition(fromState, triggerString, triggerParam, toState, transitionAction);
	}

	public void addTransitionWithTriggerParam(String fromState, String triggerString, TriggerParam triggerParam, String toState) {
		addTransitionWithTriggerParam(fromState, triggerString, triggerParam, toState, null);
	}

	public void addTransition(String[] fromStates, String trigger, String toState, AbstractTransitionAction transitionAction) {
		for (String fromState : fromStates) {
			addTransition(fromState, trigger, toState, transitionAction);
		}
	}

	public void start(String startState) {
		if (!delta.containsState(startState)) {
			throw new IllegalStateException("unknown state: " + startState);
		}
		currentState = startState;
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

	public void transitionWithTriggerParam(Trigger trigger) {
		transitionWithTriggerParam(trigger, (Object[]) null);
	}

	public void transitionWithTriggerParam(Trigger trigger, Object... parameters) {
		TransitionAction action = moveToNextState(trigger);

		if (action != null) {
			if (parameters != null) {
				action.onTransition(parameters);
			} else {
				action.onTransition();
			}
		}
	}

	public void transition(String trigger) {
		transitionWithTriggerParam(new Trigger(trigger));
	}

	public void transition(String trigger, Object... parameter) {
		String oldState = currentState;

		if (parameter == null) {
			throw new IllegalArgumentException("transition parameter must not be null");
		}
		TransitionAction action = moveToNextState(new Trigger(trigger));

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
