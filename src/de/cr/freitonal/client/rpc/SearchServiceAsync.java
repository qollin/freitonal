package de.cr.freitonal.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SearchServiceAsync {
	public void search(PieceSearchMask pieceSearchMask, AsyncCallback<SearchResult> callback);
}
