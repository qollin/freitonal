package de.cr.freitonal.client.widgets.ordinal;

import com.google.gwt.uibinder.client.UiConstructor;

import de.cr.freitonal.client.widgets.base.scalar.ScalarView;

public class OrdinalView extends ScalarView implements OrdinalPresenter.View {
	@UiConstructor
	public OrdinalView(String labelText) {
		super(labelText);
	}

}
