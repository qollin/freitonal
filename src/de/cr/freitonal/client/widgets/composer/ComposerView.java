package de.cr.freitonal.client.widgets.composer;

import com.google.gwt.uibinder.client.UiConstructor;

import de.cr.freitonal.client.widgets.base.scalar.ScalarView;

public class ComposerView extends ScalarView implements ComposerPresenter.View {

	@UiConstructor
	public ComposerView(String labelText) {
		super(labelText);
	}
}
