package de.cr.freitonal.client;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.client.event.DFA;
import de.cr.freitonal.client.event.SearchContext;
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
	private final HandlerManager eventBus = new HandlerManager(null);
	private DFA dfa;
	private PieceSearchMask currentPieceSearchMask;

	public AppController(PiecePresenter.View pieceView, final RPCService rpcService) {
		this.rpcService = rpcService;
		piecePresenter = new PiecePresenter(pieceView, eventBus, rpcService);
		createDFA();
	}

	private void createDFA() {
		State searchState = new State("Search");
		dfa = new DFA(searchState, eventBus);
		dfa.addTransition(SearchFieldChangedEvent.TYPE, searchState, searchState, new TransitionHandler() {
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
				getPiecePresenter().setSearchData(currentPieceSearchMask, SearchContext.FieldSearch);
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
				getPiecePresenter().setSearchData(currentPieceSearchMask, SearchContext.IntialLoading);
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

	public DFA getDFA() {
		return dfa;
	}

	public void setRPCService(RPCService rpcService) {
		this.rpcService = rpcService;
		this.piecePresenter.setRPCService(rpcService);
	}
}
