package de.cr.freitonal.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.client.event.OldDFA;
import de.cr.freitonal.client.event.SearchFieldChangedEvent;
import de.cr.freitonal.client.event.State;
import de.cr.freitonal.client.event.TransitionHandler;
import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.client.rpc.RPCService;
import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.client.widgets.piece.PiecePresenter;

public class AppController {
	private final PiecePresenter piecePresenter;
	private RPCService rpcService;
	private final EventBus eventBus = new SimpleEventBus();
	private OldDFA oldDFA;
	private PieceSearchMask currentPieceSearchMask;

	public AppController(PiecePresenter.View pieceView, final RPCService rpcService) {
		this.rpcService = rpcService;
		piecePresenter = new PiecePresenter(pieceView, eventBus, rpcService);

		createDFA();
	}

	private void createDFA() {
		State searchState = new State("Search");
		oldDFA = new OldDFA(searchState, eventBus);
		oldDFA.addTransition(SearchFieldChangedEvent.TYPE, searchState, searchState, new TransitionHandler() {
			public void onTransition(GwtEvent<?> event) {
				search((SearchFieldChangedEvent) event);
			}
		});
	}

	private void search(SearchFieldChangedEvent event) {
		rpcService.search(currentPieceSearchMask, new AsyncCallback<SearchResult>() {

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}

			public void onSuccess(SearchResult result) {
				currentPieceSearchMask = result.getPieceSearchMask();
				getPiecePresenter().setSearchData(currentPieceSearchMask);
			}
		});
	}

	private void loadInitialData() {
		rpcService.search(new AsyncCallback<SearchResult>() {

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}

			public void onSuccess(SearchResult result) {
				currentPieceSearchMask = result.getPieceSearchMask();
				getPiecePresenter().setSearchData(currentPieceSearchMask);
			}

		});
	}

	public void go() {
		loadInitialData();
	}

	/**
	 * @return the piecePresenter
	 */
	public PiecePresenter getPiecePresenter() {
		return piecePresenter;
	}

	public OldDFA getDFA() {
		return oldDFA;
	}

	public void setRPCService(RPCService rpcService) {
		this.rpcService = rpcService;
		this.piecePresenter.setRPCService(rpcService);
	}
}
