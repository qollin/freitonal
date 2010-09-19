package de.cr.freitonal.client.widgets.pubdate;

import com.google.gwt.uibinder.client.UiConstructor;

import de.cr.freitonal.client.widgets.base.scalar.ScalarView;

public class PublicationDateView extends ScalarView implements PublicationDatePresenter.View {

	@UiConstructor
	public PublicationDateView(String labelText) {
		super(labelText);
	}

}
