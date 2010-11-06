package de.cr.freitonal.client.widgets.base;

import com.google.gwt.event.shared.EventBus;

public abstract class BasePresenter implements Presenter {

	private final EventBus eventBus;

	public BasePresenter(EventBus eventBus2) {
		this.eventBus = eventBus2;
	}

	protected EventBus getEventBus() {
		return eventBus;
	}
}
