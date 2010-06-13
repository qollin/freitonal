package de.cr.freitonal.unittests.client.widgets.catalog;

import de.cr.freitonal.client.widgets.base.ListBoxPresenter;
import de.cr.freitonal.client.widgets.catalog.CatalogPresenter;
import de.cr.freitonal.unittests.client.widgets.base.ListBoxViewMock;

public class CatalogViewMock implements CatalogPresenter.View {
	private final ListBoxPresenter.View nameListBoxView = new ListBoxViewMock();
	private final ListBoxPresenter.View numberListBoxView = new ListBoxViewMock();

	public ListBoxPresenter.View getNameListBoxView() {
		return nameListBoxView;
	}

	public ListBoxPresenter.View getNumberListBoxView() {
		return numberListBoxView;
	}

}
