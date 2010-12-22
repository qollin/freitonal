package de.cr.freitonal.unittests.client.widgets.base.scalar;

import com.google.gwt.user.client.ui.HasText;

import de.cr.freitonal.client.widgets.base.listbox.IListBoxView;
import de.cr.freitonal.client.widgets.base.scalar.ScalarPresenter;
import de.cr.freitonal.unittests.client.widgets.base.listbox.ListBoxViewMock;

public class ScalarViewMock implements ScalarPresenter.View {
	private final ListBoxViewMock listBoxView;

	public ScalarViewMock() {
		listBoxView = new ListBoxViewMock();
	}

	public IListBoxView getListBoxView() {
		return listBoxView;
	}

	public HasText getLabel() {
		return null;
	}

}
