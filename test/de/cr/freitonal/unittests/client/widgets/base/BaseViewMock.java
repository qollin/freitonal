package de.cr.freitonal.unittests.client.widgets.base;

import com.google.gwt.user.client.ui.HasText;

import de.cr.freitonal.client.widgets.base.SearchFieldPresenter;
import de.cr.freitonal.client.widgets.base.scalar.ScalarPresenter;
import de.cr.freitonal.unittests.client.widgets.base.listbox.ListBoxViewMock;

public class BaseViewMock implements ScalarPresenter.View {

	private final SearchFieldPresenter.View listBoxView = new ListBoxViewMock();

	public HasText getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	public SearchFieldPresenter.View getListBoxView() {
		return listBoxView;
	}

}
