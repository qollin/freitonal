package de.cr.freitonal.client.widgets.base;

import com.google.gwt.event.shared.EventBus;

import de.cr.freitonal.client.event.SearchFieldChangedEvent;
import de.cr.freitonal.client.widgets.base.listbox.ListBoxPresenter;

public class SearchFieldPresenter extends ListBoxPresenter {
	public SearchFieldPresenter(EventBus eventBus, View view) {
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
