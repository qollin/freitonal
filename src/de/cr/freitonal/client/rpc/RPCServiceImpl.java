package de.cr.freitonal.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.VolatileItem;
import de.cr.freitonal.shared.models.VolatilePiece;

public class RPCServiceImpl implements RPCService {
	private final SearchServiceAsync searchService = (SearchServiceAsync) GWT.create(SearchService.class);
	private final CreateServiceAsync createService = (CreateServiceAsync) GWT.create(CreateService.class);

	@Override
	public void search(PieceSearchMask searchMask, AsyncCallback<SearchResult> callback) {
		searchService.search(searchMask, callback);
	}

	public void search(AsyncCallback<SearchResult> callback) {
		search(null, callback);
	}

	public void save(VolatilePiece piece) {
		throw new IllegalStateException("not implemented yet");
	}

	@Override
	public void createComposer(VolatileItem composer, AsyncCallback<Item> callback) {
		createService.createComposer(composer, callback);
	}

}
