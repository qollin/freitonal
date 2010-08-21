package de.cr.freitonal.client.widgets.base;

import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.event.SearchFieldChangedEvent;

public class SearchFieldPresenter extends ListBoxPresenter {
	public SearchFieldPresenter(HandlerManager eventBus, View view) {
		super(eventBus, view);
	}

	@Override
	protected void initializeDFA() {
		super.initializeDFA();
	}

	@Override
	protected void fireListBoxChangedEvent() {
		if (!dfa.getState().equals("Create")) {
			getEventBus().fireEvent(new SearchFieldChangedEvent());
		}
		super.fireListBoxChangedEvent();
	}
}
