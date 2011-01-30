package de.cr.freitonal.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.cr.freitonal.shared.models.Catalog;
import de.cr.freitonal.shared.models.Instrumentation;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.Piece;
import de.cr.freitonal.shared.models.VolatileCatalog;
import de.cr.freitonal.shared.models.VolatileInstrumentation;
import de.cr.freitonal.shared.models.VolatileItem;
import de.cr.freitonal.shared.models.VolatilePiece;

@RemoteServiceRelativePath("create")
public interface CreateService extends RemoteService {
	Item createComposer(VolatileItem composer);

	Item createInstrument(VolatileItem instrument);

	Instrumentation createInstrumentation(VolatileInstrumentation instrumentation);

	Item createCatalogName(VolatileItem catalogName);

	Catalog createCatalog(VolatileCatalog catalog);

	Item createPieceType(VolatileItem pieceType);

	Piece createPiece(VolatilePiece piece);
}
