package de.cr.freitonal.client.widgets.piecetype;

import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.widgets.base.BasePresenter;

public class PieceTypePresenter extends BasePresenter {
	public PieceTypePresenter(HandlerManager eventBus, View pieceTypeView) {
		super(eventBus, pieceTypeView);
	}

	public interface View extends BasePresenter.View {

	}
}
