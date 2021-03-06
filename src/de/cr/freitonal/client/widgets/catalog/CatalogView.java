package de.cr.freitonal.client.widgets.catalog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;

import de.cr.freitonal.client.widgets.base.listbox.EditableListBoxView;
import de.cr.freitonal.client.widgets.base.listbox.IEditableListBoxView;
import de.cr.freitonal.client.widgets.base.listbox.IListBoxView;
import de.cr.freitonal.client.widgets.base.listbox.ListBoxView;

public class CatalogView extends Composite implements CatalogPresenter.View {
	interface Binder extends UiBinder<HTMLPanel, CatalogView> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField
	ListBoxView nameList;

	@UiField
	EditableListBoxView numberList;

	@UiField
	Label label;

	@UiConstructor
	public CatalogView(String labelText) {
		HTMLPanel panel = binder.createAndBindUi(this);
		initWidget(panel);
		label.setText(labelText);
		nameList.setName(labelText);
		numberList.setName(labelText);
	}

	public IListBoxView getNameListBoxView() {
		return nameList;
	}

	public IEditableListBoxView getNumberListBoxView() {
		return numberList;
	}
}
