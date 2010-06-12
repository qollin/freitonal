package de.cr.freitonal.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.client.models.Piece;

public interface RPCService {
	public void search(PieceSearchMask searchMask, AsyncCallback<SearchResult> callback);

	public void search(AsyncCallback<SearchResult> callback);

	public void save(Piece piece);
}
