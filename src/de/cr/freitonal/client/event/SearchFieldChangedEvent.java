package de.cr.freitonal.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SearchFieldChangedEvent extends GwtEvent<SearchFieldChangedHandler> {
	public static Type<SearchFieldChangedHandler> TYPE = new Type<SearchFieldChangedHandler>();

	public SearchFieldChangedEvent() {
	}

	@Override
	protected void dispatch(SearchFieldChangedHandler handler) {
		handler.onSearchFieldChanged(this);
	}

	@Override
	public Type<SearchFieldChangedHandler> getAssociatedType() {
		return TYPE;
	}
}
