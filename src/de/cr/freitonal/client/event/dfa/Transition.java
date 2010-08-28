package de.cr.freitonal.client.event.dfa;


public class Transition implements Comparable<Transition> {
	private final TriggerParam triggerParam;
	private final String targetState;
	private final TransitionAction action;

	public Transition(TriggerParam triggerParam, String targetState, TransitionAction action) {
		this.triggerParam = triggerParam;
		this.targetState = targetState;
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

	/**
	 * @return the targetState
	 */
	public String getTargetState() {
		return targetState;
	}

	/**
	 * @return the action
	 */
	public TransitionAction getAction() {
		return action;
	}

}