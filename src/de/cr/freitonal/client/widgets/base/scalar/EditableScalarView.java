package de.cr.freitonal.client.widgets.base.scalar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;

import de.cr.freitonal.client.widgets.base.listbox.EditableListBoxView;
import de.cr.freitonal.client.widgets.base.listbox.IListBoxView;

public class EditableScalarView extends Composite implements ScalarPresenter.View {
	interface Binder extends UiBinder<HTMLPanel, EditableScalarView> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField
	EditableListBoxView list;

	@UiField
	Label label;

	@UiConstructor
	public EditableScalarView(String labelText) {
		HTMLPanel panel = binder.createAndBindUi(this);
		initWidget(panel);
		label.setText(labelText);
		list.setName(labelText);
	}

	public IListBoxView getListBoxView() {
		return list;
	}

	public HasText getLabel() {
		return label;
	}

}
