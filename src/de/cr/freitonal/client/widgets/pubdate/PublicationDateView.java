package de.cr.freitonal.client.widgets.pubdate;

import com.google.gwt.uibinder.client.UiConstructor;

import de.cr.freitonal.client.widgets.base.SimpleView;

public class PublicationDateView extends SimpleView implements PublicationDatePresenter.View {

	@UiConstructor
	public PublicationDateView(String labelText) {
		super(labelText);
	}

}
