package de.cr.freitonal.client.widgets.piece;

import static de.cr.freitonal.client.event.DisplayMode.Create;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;

import de.cr.freitonal.client.event.dfa.AbstractTransitionAction;
import de.cr.freitonal.client.event.dfa.DFA;
import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.client.rpc.RPCService;
import de.cr.freitonal.client.widgets.base.Presenter;
import de.cr.freitonal.client.widgets.catalog.CatalogPresenter;
import de.cr.freitonal.client.widgets.composer.ComposerPresenter;
import de.cr.freitonal.client.widgets.instrumentation.InstrumentationPresenter;
import de.cr.freitonal.client.widgets.musickey.MusicKeyPresenter;
import de.cr.freitonal.client.widgets.ordinal.OrdinalPresenter;
import de.cr.freitonal.client.widgets.piecetype.PieceTypePresenter;
import de.cr.freitonal.client.widgets.pubdate.PublicationDatePresenter;
import de.cr.freitonal.client.widgets.subtitle.SubtitlePresenter;
import de.cr.freitonal.shared.models.VolatilePiece;

public class PiecePresenter {
	private final ComposerPresenter composerPresenter;
	private final CatalogPresenter catalogPresenter;
	private final PieceTypePresenter pieceTypePresenter;
	private final InstrumentationPresenter instrumentationPresenter;
	private final SubtitlePresenter subtitlePresenter;
	private final OrdinalPresenter ordinalPresenter;
	private final MusicKeyPresenter musicKeyPresenter;
	private final PublicationDatePresenter publicationDatePresenter;
	private final View view;
	private final DFA dfa = new DFA();

	private final ArrayList<Presenter> presenters = new ArrayList<Presenter>();
	private RPCService rpcService;

	public interface View {
		public ComposerPresenter.View getComposerView();

		public CatalogPresenter.View getCatalogView();

		public PieceTypePresenter.View getPieceTypeView();

		public InstrumentationPresenter.View getInstrumentationView();

		public SubtitlePresenter.View getSubtitleView();

		public OrdinalPresenter.View getOrdinalView();

		public MusicKeyPresenter.View getMusicKeyView();

		public HasClickHandlers getAddPieceButton();

		public void setAddPieceButtonText(String text);

		public PublicationDatePresenter.View getPublicationDateView();
	}

	public PiecePresenter(View view, EventBus eventBus, RPCService rpcService) {
		this.view = view;
		this.rpcService = rpcService;

		composerPresenter = new ComposerPresenter(eventBus, view.getComposerView());
		presenters.add(composerPresenter);

		catalogPresenter = new CatalogPresenter(eventBus, view.getCatalogView());
		presenters.add(catalogPresenter);

		pieceTypePresenter = new PieceTypePresenter(eventBus, view.getPieceTypeView());
		presenters.add(pieceTypePresenter);

		instrumentationPresenter = new InstrumentationPresenter(eventBus, view.getInstrumentationView());
		presenters.add(instrumentationPresenter);

		subtitlePresenter = new SubtitlePresenter(eventBus, view.getSubtitleView());
		presenters.add(subtitlePresenter);

		ordinalPresenter = new OrdinalPresenter(eventBus, view.getOrdinalView());
		presenters.add(ordinalPresenter);

		musicKeyPresenter = new MusicKeyPresenter(eventBus, view.getMusicKeyView());
		presenters.add(musicKeyPresenter);

		publicationDatePresenter = new PublicationDatePresenter(eventBus, view.getPublicationDateView());
		presenters.add(publicationDatePresenter);

		bind();
		initializeDFA();
	}

	private void initializeDFA() {
		dfa.addTransition("Main", "fireAddPieceButtonClicked", "Create", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				view.setAddPieceButtonText("save piece");
				for (Presenter presenter : presenters) {
					presenter.setDisplayMode(Create);
				}
			}
		});
		dfa.addTransition("Create", "fireAddPieceButtonClicked", "Save", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				save();
			}
		});
		dfa.start("Main");
	}

	private void bind() {
		view.getAddPieceButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				fireAddPieceButtonClicked();
			}
		});
	}

	public void fireAddPieceButtonClicked() {
		dfa.transition("fireAddPieceButtonClicked");
	}

	private void save() {
		VolatilePiece piece = new VolatilePiece();
		piece.setComposer(composerPresenter.getSelectedItem());
		piece.setCatalog(catalogPresenter.getEnteredItem());
		piece.musicKey = musicKeyPresenter.getSelectedItem();
		piece.ordinal = ordinalPresenter.getSelectedItem();
		piece.subtitle = subtitlePresenter.getSelectedItem();
		piece.setPieceType(pieceTypePresenter.getSelectedItem());
		piece.setInstrumentation(instrumentationPresenter.getSelectedItem());
		rpcService.save(piece);
	}

	public void setSearchData(PieceSearchMask pieceSearchMask) {
		composerPresenter.setItems(pieceSearchMask.getComposers());
		catalogPresenter.setCatalogs(pieceSearchMask.getCatalogs());
		pieceTypePresenter.setPieceTypes(pieceSearchMask.getPieceTypes());
		instrumentationPresenter.setInstrumentations(pieceSearchMask.getInstrumentations());
		subtitlePresenter.setItems(pieceSearchMask.getSubtitles());
		ordinalPresenter.setItems(pieceSearchMask.getOrdinals());
		musicKeyPresenter.setItems(pieceSearchMask.getMusicKeys());
		publicationDatePresenter.setItems(pieceSearchMask.getPublicationDates());
	}

	/**
	 * @return the composerPresenter
	 */
	public ComposerPresenter getComposerPresenter() {
		return composerPresenter;
	}

	public PieceTypePresenter getPieceTypePresenter() {
		return pieceTypePresenter;
	}

	public CatalogPresenter getCatalogPresenter() {
		return catalogPresenter;
	}

	public InstrumentationPresenter getInstrumentationPresenter() {
		return instrumentationPresenter;
	}

	public SubtitlePresenter getSubtitlePresenter() {
		return subtitlePresenter;
	}

	public OrdinalPresenter getOrdinalPresenter() {
		return ordinalPresenter;
	}

	public MusicKeyPresenter getMusicKeyPresenter() {
		return musicKeyPresenter;
	}

	public void setRPCService(RPCService rpcService) {
		this.rpcService = rpcService;
	}
}
