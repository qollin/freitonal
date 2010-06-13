package de.cr.freitonal.unittests.client.event;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

public class HasClickHandlersMock implements HasClickHandlers {
	private final ArrayList<ClickHandler> clickHandlers = new ArrayList<ClickHandler>();

	public HandlerRegistration addClickHandler(ClickHandler handler) {
		clickHandlers.add(handler);
		return null;
	}

	public void fireEvent(GwtEvent<?> event) {
		for (ClickHandler handler : clickHandlers) {
			handler.onClick(null);
		}
	}

}
