package de.cr.freitonal.client.widgets.base;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;

public class BaseView extends Composite implements BasePresenter.View {
	interface Binder extends UiBinder<HTMLPanel, BaseView> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField
	ListBoxView list;

	@UiField
	Label label;

	@UiConstructor
	public BaseView(String labelText) {
		HTMLPanel panel = binder.createAndBindUi(this);
		initWidget(panel);
		label.setText(labelText);
		list.setName(labelText);
	}

	public ListBoxPresenter.View getListBoxView() {
		return list;
	}

	public HasText getLabel() {
		return label;
	}

}
