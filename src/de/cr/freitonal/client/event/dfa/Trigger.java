package de.cr.freitonal.client.event.dfa;

public class Trigger {
	private final String triggerString;
	private final Object[] triggerParam;

	public Trigger(String triggerString, Object triggerParam) {
		this(triggerString, new Object[] { triggerParam });
	}

	public Trigger(String trigger, Object[] triggerParam) {
		this.triggerString = trigger;
		this.triggerParam = triggerParam;
	}

	public Trigger(String triggerString) {
		this(triggerString, (Object[]) null);
	}

	@Override
	public String toString() {
		return getTriggerString() + "-" + (getTriggerParam() != null ? getTriggerParam().toString() : "");
	};

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return toString().equals(obj.toString());
	}

	/**
	 * @return the triggerString
	 */
	public String getTriggerString() {
		return triggerString;
	}

	/**
	 * @return the triggerParam
	 */
	public Object[] getTriggerParam() {
		return triggerParam;
	}
}