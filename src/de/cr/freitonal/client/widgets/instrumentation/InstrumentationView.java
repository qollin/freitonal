package de.cr.freitonal.client.widgets.instrumentation;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;

import de.cr.freitonal.client.widgets.base.listbox.IListBoxView;
import de.cr.freitonal.client.widgets.base.listbox.ListBoxView;

public class InstrumentationView extends Composite implements InstrumentationPresenter.View {
	interface Binder extends UiBinder<HTMLPanel, InstrumentationView> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField
	Button addInstrumentButton;

	ArrayList<ListBoxView> instrumentLists = new ArrayList<ListBoxView>();

	@UiField
	Label label;

	HTMLPanel panel;

	private final String labelText;

	@UiConstructor
	public InstrumentationView(String labelText) {
		this.labelText = labelText;
		panel = binder.createAndBindUi(this);
		initWidget(panel);
		label.setText(labelText);
	}

	public IListBoxView addInstrumentList() {
		ListBoxView instrumentList = new ListBoxView();
		instrumentList.setName(labelText);
		instrumentLists.add(instrumentList);
		panel.add(instrumentList, "piece-instrumentation-instruments");

		return instrumentList;
	}

	public IListBoxView getInstrumentView(int i) {
		return instrumentLists.get(i);
	}

	public HasClickHandlers getAddInstrumentButton() {
		return addInstrumentButton;
	}

	public void setAddInstrumentButtonVisible(boolean visible) {
		addInstrumentButton.setVisible(visible);
	}
}
