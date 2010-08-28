package de.cr.freitonal.client.event.dfa;


public abstract class AbstractTransitionAction implements TransitionAction {

	public void onTransition() {
		throw new IllegalStateException("please override me!");
	}

	public void onTransition(Object[] parameters) {
		throw new IllegalStateException("please override me!");
	}

}
