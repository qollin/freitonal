package de.cr.freitonal.client.widgets.base;

import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.models.MultiSourceItemSet;

public class MultiSourceSearchFieldPresenter extends SearchFieldPresenter {

	public MultiSourceSearchFieldPresenter(HandlerManager eventBus, View view) {
		super(eventBus, view);
	}

	public ItemSet getSelectedSource() {
		return ((MultiSourceItemSet) itemSet).getSelectedSource();
	}
}
