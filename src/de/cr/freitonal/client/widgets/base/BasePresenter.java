package de.cr.freitonal.client.widgets.base;

import java.util.logging.Logger;

import com.google.gwt.event.shared.EventBus;

public abstract class BasePresenter implements Presenter {

	private final EventBus eventBus;
	protected Logger logger;

	public BasePresenter(EventBus eventBus2) {
		this.eventBus = eventBus2;
		logger = Logger.getLogger("");
	}

	protected EventBus getEventBus() {
		return eventBus;
	}
}
