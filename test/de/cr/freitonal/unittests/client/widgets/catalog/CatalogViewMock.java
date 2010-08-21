package de.cr.freitonal.unittests.client.widgets.catalog;

import de.cr.freitonal.client.widgets.base.SearchFieldPresenter;
import de.cr.freitonal.client.widgets.catalog.CatalogPresenter;
import de.cr.freitonal.unittests.client.widgets.base.ListBoxViewMock;

public class CatalogViewMock implements CatalogPresenter.View {
	private final SearchFieldPresenter.View nameListBoxView = new ListBoxViewMock();
	private final SearchFieldPresenter.View numberListBoxView = new ListBoxViewMock();

	public SearchFieldPresenter.View getNameListBoxView() {
		return nameListBoxView;
	}

	public SearchFieldPresenter.View getNumberListBoxView() {
		return numberListBoxView;
	}

}
