package de.cr.freitonal.client.widgets.base;

import com.google.gwt.event.shared.HandlerManager;


public abstract class BasePresenter implements Presenter {

	private final HandlerManager eventBus;

	public BasePresenter(HandlerManager eventBus) {
		this.eventBus = eventBus;
	}

	protected HandlerManager getEventBus() {
		return eventBus;
	}
}
