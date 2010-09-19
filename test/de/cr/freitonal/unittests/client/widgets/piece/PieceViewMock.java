package de.cr.freitonal.unittests.client.widgets.piece;

import com.google.gwt.event.dom.client.HasClickHandlers;

import de.cr.freitonal.client.widgets.base.scalar.ScalarPresenter;
import de.cr.freitonal.client.widgets.base.scalar.ScalarPresenter.View;
import de.cr.freitonal.client.widgets.catalog.CatalogPresenter;
import de.cr.freitonal.client.widgets.composer.ComposerPresenter;
import de.cr.freitonal.client.widgets.instrumentation.InstrumentationPresenter;
import de.cr.freitonal.client.widgets.piece.PiecePresenter;
import de.cr.freitonal.client.widgets.piecetype.PieceTypePresenter;
import de.cr.freitonal.client.widgets.pubdate.PublicationDatePresenter;
import de.cr.freitonal.client.widgets.subtitle.SubtitlePresenter;
import de.cr.freitonal.unittests.client.event.HasClickHandlersMock;
import de.cr.freitonal.unittests.client.widgets.base.BaseViewMock;
import de.cr.freitonal.unittests.client.widgets.catalog.CatalogViewMock;
import de.cr.freitonal.unittests.client.widgets.composer.ComposerViewMock;
import de.cr.freitonal.unittests.client.widgets.instrumentation.InstrumentationViewMock;
import de.cr.freitonal.unittests.client.widgets.piecetype.PieceTypeViewMock;
import de.cr.freitonal.unittests.client.widgets.pubdate.PublicationDateViewMock;
import de.cr.freitonal.unittests.client.widgets.subtitle.SubtitleViewMock;

public class PieceViewMock implements PiecePresenter.View {
	private final CatalogPresenter.View catalogView = new CatalogViewMock();
	private final ComposerPresenter.View composerView = new ComposerViewMock();
	private final InstrumentationPresenter.View instrumentationView = new InstrumentationViewMock();
	private final View musicKeyView = new BaseViewMock();
	private final View ordinalView = new BaseViewMock();
	private final PieceTypePresenter.View pieceTypeView = new PieceTypeViewMock();
	private final SubtitlePresenter.View subtitleView = new SubtitleViewMock();
	private final PublicationDatePresenter.View publicationDateView = new PublicationDateViewMock();
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

	public ScalarPresenter.View getMusicKeyView() {
		return musicKeyView;
	}

	public ScalarPresenter.View getOrdinalView() {
		return ordinalView;
	}

	public PieceTypePresenter.View getPieceTypeView() {
		return pieceTypeView;
	}

	public ScalarPresenter.View getSubtitleView() {
		return subtitleView;
	}

	public void setAddPieceButtonText(String text) {
	}

	public View getPublicationDateView() {
		return publicationDateView;
	}

}
