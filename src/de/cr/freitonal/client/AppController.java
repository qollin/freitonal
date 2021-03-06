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
import de.cr.freitonal.client.rpc.SearchParameterBuilder;
import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.client.widgets.piece.PiecePresenter;
import de.cr.freitonal.shared.parameters.SearchParameters;

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
		SearchParameters searchParameters = generateSearchParameters();
		rpcService.search(searchParameters, new AsyncCallback<SearchResult>() {

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}

			public void onSuccess(SearchResult result) {
				onReceivingData(result);
			}
		});
	}

	private SearchParameters generateSearchParameters() {
		SearchParameters searchParameters;
		if (currentPieceSearchMask != null) {
			searchParameters = new SearchParameterBuilder(currentPieceSearchMask).getSearchParameters();
		} else {
			searchParameters = new SearchParameters();
		}
		return searchParameters;
	}

	private void loadInitialData() {
		rpcService.search(new AsyncCallback<SearchResult>() {

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}

			public void onSuccess(SearchResult result) {
				onReceivingData(result);
			}

		});
	}

	private void onReceivingData(SearchResult result) {
		if (currentPieceSearchMask != null) {
			currentPieceSearchMask.copyItemSelectionTo(result.getPieceSearchMask());
		}
		currentPieceSearchMask = result.getPieceSearchMask();
		getPiecePresenter().setSearchData(result);
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
