package de.cr.freitonal.client.event.dfa;

public interface TriggerParam {
	public boolean matches(Object[] transitionParameters);
}
