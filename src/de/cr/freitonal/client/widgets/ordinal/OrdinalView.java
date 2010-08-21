package de.cr.freitonal.client.widgets.ordinal;

import com.google.gwt.uibinder.client.UiConstructor;

import de.cr.freitonal.client.widgets.base.SimpleView;

public class OrdinalView extends SimpleView implements OrdinalPresenter.View {
	@UiConstructor
	public OrdinalView(String labelText) {
		super(labelText);
	}

}
