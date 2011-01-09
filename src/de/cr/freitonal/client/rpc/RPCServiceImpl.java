package de.cr.freitonal.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.shared.models.Catalog;
import de.cr.freitonal.shared.models.Instrumentation;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.VolatileCatalog;
import de.cr.freitonal.shared.models.VolatileInstrumentation;
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

	@Override
	public void createInstrument(VolatileItem instrument, AsyncCallback<Item> callback) {
		createService.createInstrument(instrument, callback);
	}

	@Override
	public void createInstrumentation(VolatileInstrumentation instrumentation, AsyncCallback<Instrumentation> callback) {
		createService.createInstrumentation(instrumentation, callback);
	}

	@Override
	public void createCatalogName(VolatileItem catalogName, AsyncCallback<Item> callback) {
		createService.createCatalogName(catalogName, callback);
	}

	@Override
	public void createCatalog(VolatileCatalog catalog, AsyncCallback<Catalog> callback) {
		createService.createCatalog(catalog, callback);
	}

	@Override
	public void createPieceType(VolatileItem pieceType, AsyncCallback<Item> callback) {
		createService.createPieceType(pieceType, callback);
	}

}
