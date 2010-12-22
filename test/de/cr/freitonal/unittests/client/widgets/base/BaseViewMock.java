package de.cr.freitonal.unittests.client.widgets.base;

import com.google.gwt.user.client.ui.HasText;

import de.cr.freitonal.client.widgets.base.listbox.IListBoxView;
import de.cr.freitonal.client.widgets.base.scalar.ScalarPresenter;
import de.cr.freitonal.unittests.client.widgets.base.listbox.ListBoxViewMock;

public class BaseViewMock implements ScalarPresenter.View {

	private final IListBoxView listBoxView = new ListBoxViewMock();

	public HasText getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	public IListBoxView getListBoxView() {
		return listBoxView;
	}

}
