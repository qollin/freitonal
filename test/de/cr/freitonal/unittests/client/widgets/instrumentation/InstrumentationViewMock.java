package de.cr.freitonal.unittests.client.widgets.instrumentation;

import com.google.gwt.event.dom.client.HasClickHandlers;

import de.cr.freitonal.client.widgets.base.SearchFieldPresenter;
import de.cr.freitonal.client.widgets.instrumentation.InstrumentationPresenter;
import de.cr.freitonal.unittests.client.event.HasClickHandlersMock;
import de.cr.freitonal.unittests.client.widgets.base.ListBoxViewMock;

public class InstrumentationViewMock implements InstrumentationPresenter.View {
	private final HasClickHandlers addInstrumentButtonMock = new HasClickHandlersMock();

	public SearchFieldPresenter.View addInstrumentList() {
		return new ListBoxViewMock();
	}

	public HasClickHandlers getAddInstrumentButton() {
		return addInstrumentButtonMock;
	}
}
