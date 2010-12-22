package de.cr.freitonal.unittests.client.widgets.composer;

import com.google.gwt.user.client.ui.HasText;

import de.cr.freitonal.client.widgets.base.listbox.IListBoxView;
import de.cr.freitonal.client.widgets.composer.ComposerPresenter;
import de.cr.freitonal.unittests.client.widgets.base.listbox.ListBoxViewMock;

public class ComposerViewMock implements ComposerPresenter.View {
	private final IListBoxView listBoxView = new ListBoxViewMock();

	public HasText getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	public IListBoxView getListBoxView() {
		return listBoxView;
	}

}
