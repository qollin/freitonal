package de.cr.freitonal.client.event;

public class Trigger {
	private final String trigger;
	private final Object triggerParam;

	public Trigger(String trigger, Object triggerParam) {
		this.trigger = trigger;
		this.triggerParam = triggerParam;
	}

	@Override
	public String toString() {
		return trigger + "-" + (triggerParam != null ? triggerParam.toString() : "");
	};

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return toString().equals(obj.toString());
	}
}