package de.cr.freitonal.usertests.gui;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.client.AppController;
import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.client.rpc.RPCService;
import de.cr.freitonal.client.rpc.RPCServiceImpl;
import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.client.widgets.base.listbox.EditableListBoxView;
import de.cr.freitonal.client.widgets.base.listbox.ListBoxPresenter;
import de.cr.freitonal.client.widgets.base.scalar.ScalarPresenter;
import de.cr.freitonal.client.widgets.piece.PieceView;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.Piece;
import de.cr.freitonal.shared.models.VolatilePiece;
import de.cr.freitonal.usertests.api.APITestCase;
import de.cr.freitonal.usertests.api.ScriptSequence.Script;

public class UserTestCase extends APITestCase {
	protected AppController appController;
	private PieceView pieceView;

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
			public void search(PieceSearchMask searchMask, AsyncCallback<SearchResult> callback) {
				AsyncCallback<SearchResult> callbackWrapper = getScriptSequence().createSearchResultCallbackWrapper(callback);
				super.search(searchMask, callbackWrapper);
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
	}

	private void enterText(ListBoxPresenter listBoxPresenter, String text) {
		((EditableListBoxView) listBoxPresenter.getView()).setEnteredText(text);
	}

	protected void selectComposer(final Future<Item> composer) {
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				setSelectedItem(appController.getPiecePresenter().getComposerPresenter(), composer);
				runNextScript();
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

	protected void enterCatalogNumber(final String number) {
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				enterText(appController.getPiecePresenter().getCatalogPresenter().getNumberListBoxPresenter(), number);
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

}
