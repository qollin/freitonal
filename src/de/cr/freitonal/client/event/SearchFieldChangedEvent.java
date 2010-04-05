package de.cr.freitonal.client.event;

import com.google.gwt.event.shared.GwtEvent;

import de.cr.freitonal.client.models.Item;

public class SearchFieldChangedEvent extends GwtEvent<SearchFieldChangedHandler> {
	public static Type<SearchFieldChangedHandler> TYPE = new Type<SearchFieldChangedHandler>();
	private final Item selectedItem;

	public SearchFieldChangedEvent(Item selectedItem) {
		this.selectedItem = selectedItem;
	}

	@Override
	protected void dispatch(SearchFieldChangedHandler handler) {
		handler.onSearchFieldChanged(this);
	}

	@Override
	public Type<SearchFieldChangedHandler> getAssociatedType() {
		return TYPE;
	}

	/**
	 * @return the selectedItem
	 */
	public Item getSelectedItem() {
		return selectedItem;
	}

}
