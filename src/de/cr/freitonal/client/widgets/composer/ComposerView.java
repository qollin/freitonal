package de.cr.freitonal.client.widgets.composer;

import com.google.gwt.uibinder.client.UiConstructor;

import de.cr.freitonal.client.widgets.base.SimpleView;

public class ComposerView extends SimpleView implements ComposerPresenter.View {

	@UiConstructor
	public ComposerView(String labelText) {
		super(labelText);
	}
}
