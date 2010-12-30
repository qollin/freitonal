package de.cr.freitonal.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.VolatileItem;
import de.cr.freitonal.shared.models.VolatilePiece;

public interface RPCService {
	public void search(PieceSearchMask searchMask, AsyncCallback<SearchResult> callback);

	public void search(AsyncCallback<SearchResult> callback);

	public void save(VolatilePiece piece);

	public void createComposer(VolatileItem composer, AsyncCallback<Item> callback);
}
