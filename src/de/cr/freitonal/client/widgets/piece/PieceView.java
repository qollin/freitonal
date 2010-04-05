package de.cr.freitonal.client.widgets.piece;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

import de.cr.freitonal.client.widgets.base.BaseView;
import de.cr.freitonal.client.widgets.catalog.CatalogPresenter;
import de.cr.freitonal.client.widgets.catalog.CatalogView;
import de.cr.freitonal.client.widgets.composer.ComposerPresenter;
import de.cr.freitonal.client.widgets.composer.ComposerView;
import de.cr.freitonal.client.widgets.instrumentation.InstrumentationView;
import de.cr.freitonal.client.widgets.instrumentation.InstrumentationPresenter.View;
import de.cr.freitonal.client.widgets.piecetype.PieceTypePresenter;
import de.cr.freitonal.client.widgets.piecetype.PieceTypeView;

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
	BaseView subtitle;

	@UiField
	BaseView ordinal;

	@UiField
	BaseView key;

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

}
