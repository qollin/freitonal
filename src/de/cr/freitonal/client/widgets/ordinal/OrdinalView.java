package de.cr.freitonal.client.widgets.ordinal;

import com.google.gwt.uibinder.client.UiConstructor;

import de.cr.freitonal.client.widgets.base.scalar.EditableScalarView;

public class OrdinalView extends EditableScalarView implements OrdinalPresenter.View {
	@UiConstructor
	public OrdinalView(String labelText) {
		super(labelText);
	}

}
