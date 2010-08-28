package de.cr.freitonal.client.event;

public class EqualsTriggerParam implements TriggerParam {
	private final Object triggerParam;

	public EqualsTriggerParam(Object triggerParam) {
		this.triggerParam = triggerParam;
	}

	public boolean matches(Object[] transitionParameters) {
		if (transitionParameters == null) {
			return false;
		}
		return transitionParameters[0].equals(triggerParam);
	}

}
