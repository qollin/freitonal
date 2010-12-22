package de.cr.freitonal.unittests.client.widgets.catalog;

import de.cr.freitonal.client.widgets.base.listbox.IListBoxView;
import de.cr.freitonal.client.widgets.catalog.CatalogPresenter;
import de.cr.freitonal.unittests.client.widgets.base.listbox.ListBoxViewMock;

public class CatalogViewMock implements CatalogPresenter.View {
	private final IListBoxView nameListBoxView = new ListBoxViewMock();
	private final IListBoxView numberListBoxView = new ListBoxViewMock();

	public IListBoxView getNameListBoxView() {
		return nameListBoxView;
	}

	public IListBoxView getNumberListBoxView() {
		return numberListBoxView;
	}

}
