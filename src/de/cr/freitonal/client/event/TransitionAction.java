package de.cr.freitonal.client.event;

public interface TransitionAction {
	public void onTransition();

	public void onTransition(Object[] parameter);
}
