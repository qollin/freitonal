package de.cr.freitonal.unittests.client.widgets.base;

import com.google.gwt.user.client.ui.HasText;

import de.cr.freitonal.client.widgets.base.BasePresenter;
import de.cr.freitonal.client.widgets.base.ListBoxPresenter;

public class BaseViewMock implements BasePresenter.View {

	private final ListBoxPresenter.View listBoxView = new ListBoxViewMock();

	public HasText getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	public ListBoxPresenter.View getListBoxView() {
		return listBoxView;
	}

}
