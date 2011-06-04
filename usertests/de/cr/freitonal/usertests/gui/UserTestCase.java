package de.cr.freitonal.usertests.gui;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.client.AppController;
import de.cr.freitonal.client.rpc.RPCService;
import de.cr.freitonal.client.rpc.RPCServiceImpl;
import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.client.widgets.base.listbox.EditableListBoxView;
import de.cr.freitonal.client.widgets.base.listbox.ListBoxPresenter;
import de.cr.freitonal.client.widgets.base.scalar.ScalarPresenter;
import de.cr.freitonal.client.widgets.piece.PieceView;
import de.cr.freitonal.shared.models.Catalog;
import de.cr.freitonal.shared.models.Instrumentation;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.Piece;
import de.cr.freitonal.shared.models.VolatilePiece;
import de.cr.freitonal.shared.parameters.SearchParameters;
import de.cr.freitonal.usertests.api.APITestCase;
import de.cr.freitonal.usertests.api.ScriptSequence.Script;

public class UserTestCase extends APITestCase {
	protected static final boolean RUN_NEXT_SCRIPT = true;
	protected static final boolean DO_NOT_RUN_NEXT_SCRIPT = false;
	protected AppController appController;
	private PieceView pieceView;
	protected Future<Item> mozart;
	protected Future<Item> piano;
	protected Future<Instrumentation> soloPiano;
	protected Future<Item> opus;
	protected Future<Catalog> opus52;
	protected Future<Item> sonata;

	@Override
	public String getModuleName() {
		return "de.cr.freitonal.FreitonalGUI";
	}

	@Override
	public void gwtSetUp() {
		super.gwtSetUp();
		pieceView = new PieceView();
		appController = new AppController(pieceView, null);

		RPCService rpcService = new RPCServiceImpl() {
			@Override
			public void search(SearchParameters searchParameters, AsyncCallback<SearchResult> callback) {
				AsyncCallback<SearchResult> callbackWrapper = getScriptSequence().createSearchResultCallbackWrapper(callback);
				super.search(searchParameters, callbackWrapper);
			}

			@Override
			public void createPiece(VolatilePiece piece, AsyncCallback<Piece> callback) {
				AsyncCallback<Piece> callbackWrapper = getScriptSequence().createPieceCallbackWrapper(callback);
				super.createPiece(piece, callbackWrapper);
			};
		};
		appController.setRPCService(rpcService);
	}

	protected void runApplication() {
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				appController.go();
			}
		});
	}

	private void setSelectedItem(ScalarPresenter presenter, Future<Item> item) {
		setSelectedItem(presenter.getListBoxPresenter(), item);
	}

	private void setSelectedItem(ListBoxPresenter listBoxPresenter, Future<Item> item) {
		listBoxPresenter.getView().setSelectedItem(item.getObject());
		listBoxPresenter.fireOnNewItemSelected(item.getObject());
	}

	private void enterText(ListBoxPresenter listBoxPresenter, String text) {
		((EditableListBoxView) listBoxPresenter.getView()).setEnteredText(text);
	}

	protected void selectComposer(final Future<Item> composer) {
		selectComposer(composer, RUN_NEXT_SCRIPT);
	}

	protected void selectComposer(final Future<Item> composer, final boolean runNextScript) {
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				setSelectedItem(appController.getPiecePresenter().getComposerPresenter(), composer);
				if (runNextScript) {
					runNextScript();
				}
			}
		});
	}

	protected void selectInstrument(final Future<Item> instrument) {
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				setSelectedItem(appController.getPiecePresenter().getInstrumentationPresenter().getInstrumentPresenter(0), instrument);
				runNextScript();
			}
		});
	}

	protected void selectCatalogName(final Future<Item> catalogName) {
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				setSelectedItem(appController.getPiecePresenter().getCatalogPresenter().getNameListBoxPresenter(), catalogName);
				runNextScript();
			}
		});
	}

	protected void selectPieceType(final Future<Item> pieceType) {
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				setSelectedItem(appController.getPiecePresenter().getPieceTypePresenter().getListBoxPresenter(), pieceType);
				runNextScript();
			}
		});
	}

	private String getCatalogNumber(Future<Catalog> catalog) {
		return catalog.getObject().getCatalogNumber().getValue();
	}

	protected void enterCatalogNumber(final Future<Catalog> catalog) {
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				enterText(appController.getPiecePresenter().getCatalogPresenter().getNumberListBoxPresenter(), getCatalogNumber(catalog));
				runNextScript();
			}
		});
	}

	protected void clickAddPieceButton() {
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				appController.getPiecePresenter().fireAddPieceButtonClicked();
				runNextScript();
			}
		});
	}

	/**
	 * The same as clickAddPieceButton, but does not run the next script. The
	 * next script is run by the overriden RPCService (see gwtSetUp)
	 */
	protected void clickSavePieceButton() {
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				appController.getPiecePresenter().fireAddPieceButtonClicked();
			}
		});
	}

	@SuppressWarnings("unchecked")
	protected void createTestObjects() {
		mozart = createComposer("Mozart");
		piano = createInstrument("Piano");
		soloPiano = createInstrumentation("solo-piano", piano);
		opus = createCatalogName("Opus");
		opus52 = createCatalog(opus, "52");
		sonata = createPieceType("Sonata");
	}

}
