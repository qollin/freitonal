package de.cr.freitonal.usertests.gui;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.client.AppController;
import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.client.rpc.RPCService;
import de.cr.freitonal.client.rpc.RPCServiceImpl;
import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.client.widgets.piece.PieceView;
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
}
