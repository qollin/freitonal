package de.cr.freitonal.unittests.client.widgets.piece;

import com.google.gwt.event.dom.client.HasClickHandlers;

import de.cr.freitonal.client.widgets.base.BasePresenter;
import de.cr.freitonal.client.widgets.base.BasePresenter.View;
import de.cr.freitonal.client.widgets.catalog.CatalogPresenter;
import de.cr.freitonal.client.widgets.composer.ComposerPresenter;
import de.cr.freitonal.client.widgets.instrumentation.InstrumentationPresenter;
import de.cr.freitonal.client.widgets.piece.PiecePresenter;
import de.cr.freitonal.unittests.client.event.HasClickHandlersMock;
import de.cr.freitonal.unittests.client.widgets.base.BaseViewMock;
import de.cr.freitonal.unittests.client.widgets.catalog.CatalogViewMock;
import de.cr.freitonal.unittests.client.widgets.composer.ComposerViewMock;
import de.cr.freitonal.unittests.client.widgets.instrumentation.InstrumentationViewMock;

public class PieceViewMock implements PiecePresenter.View {
	private final CatalogPresenter.View catalogView = new CatalogViewMock();
	private final ComposerPresenter.View composerView = new ComposerViewMock();
	private final InstrumentationPresenter.View instrumentationView = new InstrumentationViewMock();
	private final View musicKeyView = new BaseViewMock();
	private final View ordinalView = new BaseViewMock();
	private final View pieceTypeView = new BaseViewMock();
	private final View subtitleView = new BaseViewMock();
	private final HasClickHandlers addPieceButton = new HasClickHandlersMock();

	public HasClickHandlers getAddPieceButton() {
		return addPieceButton;
	}

	public CatalogPresenter.View getCatalogView() {
		return catalogView;
	}

	public ComposerPresenter.View getComposerView() {
		return composerView;
	}

	public InstrumentationPresenter.View getInstrumentationView() {
		return instrumentationView;
	}

	public BasePresenter.View getMusicKeyView() {
		return musicKeyView;
	}

	public BasePresenter.View getOrdinalView() {
		return ordinalView;
	}

	public BasePresenter.View getPieceTypeView() {
		return pieceTypeView;
	}

	public BasePresenter.View getSubtitleView() {
		return subtitleView;
	}

}
