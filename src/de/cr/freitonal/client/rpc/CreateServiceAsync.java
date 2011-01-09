package de.cr.freitonal.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.shared.models.Catalog;
import de.cr.freitonal.shared.models.Instrumentation;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.VolatileCatalog;
import de.cr.freitonal.shared.models.VolatileInstrumentation;
import de.cr.freitonal.shared.models.VolatileItem;

public interface CreateServiceAsync {
	void createComposer(VolatileItem composer, AsyncCallback<Item> callback);

	void createInstrument(VolatileItem instrument, AsyncCallback<Item> callback);

	void createInstrumentation(VolatileInstrumentation instrumentation, AsyncCallback<Instrumentation> callback);

	void createCatalogName(VolatileItem catalogName, AsyncCallback<Item> callback);

	void createCatalog(VolatileCatalog catalog, AsyncCallback<Catalog> callback);

	public void createPieceType(VolatileItem pieceType, AsyncCallback<Item> callback);
}
