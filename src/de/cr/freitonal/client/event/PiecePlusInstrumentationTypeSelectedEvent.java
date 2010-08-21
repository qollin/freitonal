package de.cr.freitonal.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class PiecePlusInstrumentationTypeSelectedEvent extends GwtEvent<PiecePlusInstrumentationTypeSelectedHandler> {
	public static Type<PiecePlusInstrumentationTypeSelectedHandler> TYPE = new Type<PiecePlusInstrumentationTypeSelectedHandler>();

	@Override
	public Type<PiecePlusInstrumentationTypeSelectedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PiecePlusInstrumentationTypeSelectedHandler handler) {
		handler.onPiecePlusInstrumentationTypeSelected();
	}

}
