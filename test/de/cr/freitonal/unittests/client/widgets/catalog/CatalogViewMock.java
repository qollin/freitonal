package de.cr.freitonal.unittests.client.widgets.catalog;

import de.cr.freitonal.client.widgets.base.listbox.IEditableListBoxView;
import de.cr.freitonal.client.widgets.base.listbox.IListBoxView;
import de.cr.freitonal.client.widgets.catalog.CatalogPresenter;
import de.cr.freitonal.unittests.client.widgets.base.listbox.EditableListBoxViewMock;
import de.cr.freitonal.unittests.client.widgets.base.listbox.ListBoxViewMock;

public class CatalogViewMock implements CatalogPresenter.View {
	private final IListBoxView nameListBoxView = new ListBoxViewMock();
	private final IEditableListBoxView numberListBoxView = new EditableListBoxViewMock();

	public IListBoxView getNameListBoxView() {
		return nameListBoxView;
	}

	public IEditableListBoxView getNumberListBoxView() {
		return numberListBoxView;
	}

}
