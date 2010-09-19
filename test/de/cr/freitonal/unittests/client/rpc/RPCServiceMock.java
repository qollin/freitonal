package de.cr.freitonal.unittests.client.rpc;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.client.rpc.RPCService;
import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.shared.models.VolatilePiece;

public class RPCServiceMock implements RPCService {
	private final ArrayList<String> trace;

	public RPCServiceMock() {
		trace = new ArrayList<String>();
	}

	public RPCServiceMock(ArrayList<String> trace) {
		this.trace = trace;
	}

	public void search(PieceSearchMask searchMask, AsyncCallback<SearchResult> callback) {
		trace.add("RPCServiceMock: search");
	}

	public void search(AsyncCallback<SearchResult> callback) {
		trace.add("RPCServiceMock: search");
	}

	public void save(VolatilePiece piece) {
		trace.add("RPCServiceMock: save");
	}

}
