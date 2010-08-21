package de.cr.freitonal.client.widgets.base;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;

public class SimpleView extends Composite implements SimplePresenter.View {
	interface Binder extends UiBinder<HTMLPanel, SimpleView> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField
	ListBoxView list;

	@UiField
	Label label;

	@UiConstructor
	public SimpleView(String labelText) {
		HTMLPanel panel = binder.createAndBindUi(this);
		initWidget(panel);
		label.setText(labelText);
		list.setName(labelText);
	}

	public SearchFieldPresenter.View getListBoxView() {
		return list;
	}

	public HasText getLabel() {
		return label;
	}

}
