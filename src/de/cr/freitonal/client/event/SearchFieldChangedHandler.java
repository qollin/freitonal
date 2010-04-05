package de.cr.freitonal.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface SearchFieldChangedHandler extends EventHandler {
	public void onSearchFieldChanged(SearchFieldChangedEvent event);
}
