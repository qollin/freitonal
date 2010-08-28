package de.cr.freitonal.client.event.dfa;

public interface TransitionAction {
	public void onTransition();

	public void onTransition(Object[] parameter);
}
