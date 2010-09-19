package de.cr.freitonal.client.widgets.piecetype;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.event.PiecePlusInstrumentationTypeSelectedEvent;
import de.cr.freitonal.client.models.PieceTypeSet;
import de.cr.freitonal.client.widgets.base.MultiSourceSearchFieldPresenter;
import de.cr.freitonal.client.widgets.base.scalar.ScalarPresenter;

public class PieceTypePresenter extends ScalarPresenter {
	private PieceTypeSet pieceTypes;

	public PieceTypePresenter(HandlerManager eventBus, View view) {
		super(eventBus, view);
		MultiSourceSearchFieldPresenter searchFieldPresenter = new MultiSourceSearchFieldPresenter(eventBus, view.getListBoxView());
		setListBoxPresenter(searchFieldPresenter);
		bind();
	}

	private void bind() {
		final MultiSourceSearchFieldPresenter searchFieldPresenter = (MultiSourceSearchFieldPresenter) getListBoxPresenter();

		searchFieldPresenter.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if (searchFieldPresenter.getSelectedSource() == pieceTypes.getPiecePlusInstrumentationTypes()) {
					getEventBus().fireEvent(new PiecePlusInstrumentationTypeSelectedEvent());
				}
			}
		});
	}

	public void setPieceTypes(PieceTypeSet pieceTypes) {
		this.pieceTypes = pieceTypes;
		setItems(pieceTypes.getAllTypesItemSet());
	}
}
