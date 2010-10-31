package de.cr.freitonal.client.widgets.pubdate;

import com.google.gwt.uibinder.client.UiConstructor;

import de.cr.freitonal.client.widgets.base.scalar.EditableScalarView;

public class PublicationDateView extends EditableScalarView implements PublicationDatePresenter.View {

	@UiConstructor
	public PublicationDateView(String labelText) {
		super(labelText);
	}

}
