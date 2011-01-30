package de.cr.freitonal.server.services;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import de.cr.freitonal.client.rpc.CreateService;
import de.cr.freitonal.shared.models.Catalog;
import de.cr.freitonal.shared.models.Instrumentation;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.Piece;
import de.cr.freitonal.shared.models.VolatileCatalog;
import de.cr.freitonal.shared.models.VolatileInstrumentation;
import de.cr.freitonal.shared.models.VolatileItem;
import de.cr.freitonal.shared.models.VolatilePiece;

@SuppressWarnings("serial")
public class CreateServiceImpl extends ClojureServlet implements CreateService {

	@Override
	public void init(ServletConfig servletConfig) {
		setClojureNamespace("de.cr.freitonal.server.insert-frontend");
		super.init(servletConfig);
	}

	@Override
	public void init() throws ServletException {
		setClojureNamespace("de.cr.freitonal.server.insert-frontend");
		super.init();
	}

	@Override
	public Item createComposer(VolatileItem composer) {
		return createItem(composer, "doCreateComposer");
	}

	@Override
	public Item createInstrument(VolatileItem instrument) {
		return createItem(instrument, "doCreateInstrument");
	}

	private Item createItem(VolatileItem item, String functionName) {
		return (Item) runFunction(functionName, item);
	}

	@Override
	public Instrumentation createInstrumentation(VolatileInstrumentation instrumentation) {
		return (Instrumentation) runFunction("doCreateInstrumentation", instrumentation);
	}

	@Override
	public Item createCatalogName(VolatileItem catalogName) {
		return createItem(catalogName, "doCreateCatalogName");
	}

	@Override
	public Catalog createCatalog(VolatileCatalog catalog) {
		return (Catalog) runFunction("doCreateCatalog", catalog);
	}

	@Override
	public Item createPieceType(VolatileItem pieceType) {
		return createItem(pieceType, "doCreatePieceType");
	}

	@Override
	public Piece createPiece(VolatilePiece piece) {
		return (Piece) runFunction("doCreatePiece", piece);
	}

}
