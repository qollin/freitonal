package de.cr.freitonal.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.shared.models.VolatilePiece;

public interface RPCService extends CreateServiceAsync, SearchServiceAsync {
	public void search(AsyncCallback<SearchResult> callback);

	public void save(VolatilePiece piece);
}
