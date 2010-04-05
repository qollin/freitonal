package de.cr.freitonal.client.widgets.composer;

import com.google.gwt.uibinder.client.UiConstructor;

import de.cr.freitonal.client.widgets.base.BaseView;

public class ComposerView extends BaseView implements ComposerPresenter.View {

	@UiConstructor
	public ComposerView(String labelText) {
		super(labelText);
	}
}
