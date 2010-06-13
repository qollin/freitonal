package de.cr.freitonal.unittests.client.widgets.composer;

import com.google.gwt.user.client.ui.HasText;

import de.cr.freitonal.client.widgets.base.ListBoxPresenter;
import de.cr.freitonal.client.widgets.composer.ComposerPresenter;
import de.cr.freitonal.unittests.client.widgets.base.ListBoxViewMock;

public class ComposerViewMock implements ComposerPresenter.View {
	private final ListBoxPresenter.View listBoxView = new ListBoxViewMock();

	public HasText getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	public ListBoxPresenter.View getListBoxView() {
		return listBoxView;
	}

}
