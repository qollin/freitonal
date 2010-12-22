package de.cr.freitonal.client.widgets.base;

import com.google.gwt.event.shared.EventBus;

import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.models.MultiSourceItemSet;
import de.cr.freitonal.client.widgets.base.listbox.IListBoxView;

public class MultiSourceSearchFieldPresenter extends SearchFieldPresenter {

	public MultiSourceSearchFieldPresenter(EventBus eventBus, IListBoxView iListBoxView) {
		super(eventBus, iListBoxView);
	}

	public ItemSet getSelectedSource() {
		return ((MultiSourceItemSet) itemSet).getSelectedSource();
	}
}
