package de.cr.freitonal.client.widgets.piece;

import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.client.rpc.RPCService;
import de.cr.freitonal.client.widgets.base.BasePresenter;
import de.cr.freitonal.client.widgets.catalog.CatalogPresenter;
import de.cr.freitonal.client.widgets.composer.ComposerPresenter;
import de.cr.freitonal.client.widgets.instrumentation.InstrumentationPresenter;
import de.cr.freitonal.client.widgets.piecetype.PieceTypePresenter;

public class PiecePresenter {
	private final ComposerPresenter composerPresenter;
	private final CatalogPresenter catalogPresenter;
	private final PieceTypePresenter pieceTypePresenter;
	private final InstrumentationPresenter instrumentationPresenter;

	public interface View {
		public ComposerPresenter.View getComposerView();

		public CatalogPresenter.View getCatalogView();

		public PieceTypePresenter.View getPieceTypeView();

		public InstrumentationPresenter.View getInstrumentationView();
	}

	public PiecePresenter(View view, HandlerManager eventBus, RPCService rpcService) {
		composerPresenter = new ComposerPresenter(eventBus, view.getComposerView());
		catalogPresenter = new CatalogPresenter(eventBus, view.getCatalogView());
		pieceTypePresenter = new PieceTypePresenter(eventBus, view.getPieceTypeView());
		instrumentationPresenter = new InstrumentationPresenter(eventBus, view.getInstrumentationView());
	}

	public void setSearchData(PieceSearchMask pieceSearchMask) {
		composerPresenter.setItems(pieceSearchMask.getComposers());
		catalogPresenter.setCatalogs(pieceSearchMask.getCatalogs());
		pieceTypePresenter.setItems(pieceSearchMask.getPieceTypes());
		instrumentationPresenter.setInstrumentations(pieceSearchMask.getInstrumentations());
	}

	/**
	 * @return the composerPresenter
	 */
	public ComposerPresenter getComposerPresenter() {
		return composerPresenter;
	}

	public BasePresenter getPieceTypePresenter() {
		return pieceTypePresenter;
	}

	public CatalogPresenter getCatalogPresenter() {
		return catalogPresenter;
	}

	public InstrumentationPresenter getInstrumentationPresenter() {
		return instrumentationPresenter;
	}
}
