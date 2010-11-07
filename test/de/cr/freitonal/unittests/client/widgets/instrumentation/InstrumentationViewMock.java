package de.cr.freitonal.unittests.client.widgets.instrumentation;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.HasClickHandlers;

import de.cr.freitonal.client.widgets.base.SearchFieldPresenter;
import de.cr.freitonal.client.widgets.instrumentation.InstrumentationPresenter;
import de.cr.freitonal.unittests.client.event.HasClickHandlersMock;
import de.cr.freitonal.unittests.client.widgets.base.listbox.ListBoxViewMock;

public class InstrumentationViewMock implements InstrumentationPresenter.View {
	private final HasClickHandlers addInstrumentButtonMock = new HasClickHandlersMock();
	private final ArrayList<String> trace;

	public InstrumentationViewMock(ArrayList<String> trace) {
		this.trace = trace;
	}

	public InstrumentationViewMock() {
		this.trace = new ArrayList<String>();
	}

	public SearchFieldPresenter.View addInstrumentList() {
		return new ListBoxViewMock(trace);
	}

	public HasClickHandlers getAddInstrumentButton() {
		return addInstrumentButtonMock;
	}
}
