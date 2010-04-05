package de.cr.freitonal.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RPCService {
	public void search(PieceSearchMask searchMask, AsyncCallback<SearchResult> callback);

	public void search(AsyncCallback<SearchResult> callback);
}
