package de.cr.freitonal.unittests.client.rpc;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.client.rpc.RPCService;
import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.shared.models.Catalog;
import de.cr.freitonal.shared.models.Instrumentation;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.Piece;
import de.cr.freitonal.shared.models.VolatileCatalog;
import de.cr.freitonal.shared.models.VolatileInstrumentation;
import de.cr.freitonal.shared.models.VolatileItem;
import de.cr.freitonal.shared.models.VolatilePiece;

public class RPCServiceMock implements RPCService {
	private final ArrayList<String> trace;

	public RPCServiceMock() {
		trace = new ArrayList<String>();
	}

	public RPCServiceMock(ArrayList<String> trace) {
		this.trace = trace;
	}

	@Override
	public void search(PieceSearchMask searchMask, AsyncCallback<SearchResult> callback) {
		trace.add("RPCServiceMock: search");
	}

	@Override
	public void search(AsyncCallback<SearchResult> callback) {
		trace.add("RPCServiceMock: search");
	}

	@Override
	public void createComposer(VolatileItem composer, AsyncCallback<Item> callback) {
		trace.add("RPCServiceMock: createComposer");
	}

	@Override
	public void createInstrument(VolatileItem instrument, AsyncCallback<Item> callback) {
		trace.add("RPCServiceMock: createInstrument");
	}

	@Override
	public void createInstrumentation(VolatileInstrumentation instrumentation, AsyncCallback<Instrumentation> callback) {
		trace.add("RPCServiceMock: createInstrumententation");
	}

	@Override
	public void createCatalogName(VolatileItem catalogName, AsyncCallback<Item> callback) {
		trace.add("RPCServiceMock: createCatalogName");
	}

	@Override
	public void createCatalog(VolatileCatalog catalog, AsyncCallback<Catalog> callback) {
		trace.add("RPCServiceMock: createCatalog");
	}

	@Override
	public void createPieceType(VolatileItem pieceType, AsyncCallback<Item> callback) {
		trace.add("RPCServiceMock: createPieceType");
	}

	@Override
	public void createPiece(VolatilePiece piece, AsyncCallback<Piece> callback) {
		trace.add("RPCServiceMock: createPiece");
	}

}
