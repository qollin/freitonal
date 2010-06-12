package de.cr.freitonal.client.widgets.ordinal;

import com.google.gwt.uibinder.client.UiConstructor;

import de.cr.freitonal.client.widgets.base.BaseView;

public class OrdinalView extends BaseView implements OrdinalPresenter.View {
	@UiConstructor
	public OrdinalView(String labelText) {
		super(labelText);
	}

}
