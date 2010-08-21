package de.cr.freitonal.client.widgets.base;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;

public class ListBoxAndEntryView extends ListBoxView {
	interface Binder extends UiBinder<HTMLPanel, ListBoxAndEntryView> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField
	TextBox entry;

	@UiConstructor
	public ListBoxAndEntryView() {
		super(false);
		HTMLPanel panel = binder.createAndBindUi(this);
		init(panel);
	}

	@Override
	protected void switchToSelectMode() {
		super.switchToSelectMode();
		entry.setVisible(false);
		list.setVisible(true);
	}

	@Override
	protected void switchToCreateMode() {
		super.switchToCreateMode();
		list.setVisible(false);
		entry.setVisible(true);
	}
}
