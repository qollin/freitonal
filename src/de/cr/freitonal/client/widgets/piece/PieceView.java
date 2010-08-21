package de.cr.freitonal.client.widgets.piece;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

import de.cr.freitonal.client.widgets.catalog.CatalogPresenter;
import de.cr.freitonal.client.widgets.catalog.CatalogView;
import de.cr.freitonal.client.widgets.composer.ComposerPresenter;
import de.cr.freitonal.client.widgets.composer.ComposerView;
import de.cr.freitonal.client.widgets.instrumentation.InstrumentationPresenter.View;
import de.cr.freitonal.client.widgets.instrumentation.InstrumentationView;
import de.cr.freitonal.client.widgets.musickey.MusicKeyPresenter;
import de.cr.freitonal.client.widgets.musickey.MusicKeyView;
import de.cr.freitonal.client.widgets.ordinal.OrdinalPresenter;
import de.cr.freitonal.client.widgets.ordinal.OrdinalView;
import de.cr.freitonal.client.widgets.piecetype.PieceTypePresenter;
import de.cr.freitonal.client.widgets.piecetype.PieceTypeView;
import de.cr.freitonal.client.widgets.pubdate.PublicationDatePresenter;
import de.cr.freitonal.client.widgets.pubdate.PublicationDateView;
import de.cr.freitonal.client.widgets.subtitle.SubtitlePresenter;
import de.cr.freitonal.client.widgets.subtitle.SubtitleView;

public class PieceView extends Composite implements PiecePresenter.View {
	interface Binder extends UiBinder<HTMLPanel, PieceView> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField
	ComposerView composer;

	@UiField
	CatalogView catalog;

	@UiField
	PieceTypeView pieceType;

	@UiField
	InstrumentationView instrumentations;

	@UiField
	SubtitleView subtitle;

	@UiField
	OrdinalView ordinal;

	@UiField
	MusicKeyView musicKey;

	@UiField
	PublicationDateView publicationDate;

	@UiField
	Button addPieceButton;

	public PieceView() {
		HTMLPanel panel = binder.createAndBindUi(this);
		initWidget(panel);
	}

	public ComposerPresenter.View getComposerView() {
		return composer;
	}

	public CatalogPresenter.View getCatalogView() {
		return catalog;
	}

	public PieceTypePresenter.View getPieceTypeView() {
		return pieceType;
	}

	public View getInstrumentationView() {
		return instrumentations;
	}

	public SubtitlePresenter.View getSubtitleView() {
		return subtitle;
	}

	public OrdinalPresenter.View getOrdinalView() {
		return ordinal;
	}

	public MusicKeyPresenter.View getMusicKeyView() {
		return musicKey;
	}

	public HasClickHandlers getAddPieceButton() {
		return addPieceButton;
	}

	public void setAddPieceButtonText(String text) {
		addPieceButton.setText(text);
	}

	public PublicationDatePresenter.View getPublicationDateView() {
		return publicationDate;
	}

}
