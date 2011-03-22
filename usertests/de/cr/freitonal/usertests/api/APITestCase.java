package de.cr.freitonal.usertests.api;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.client.rpc.PurgeService;
import de.cr.freitonal.client.rpc.PurgeServiceAsync;
import de.cr.freitonal.client.rpc.RPCService;
import de.cr.freitonal.client.rpc.RPCServiceImpl;
import de.cr.freitonal.shared.models.Catalog;
import de.cr.freitonal.shared.models.Instrumentation;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.Piece;
import de.cr.freitonal.shared.models.VolatileCatalog;
import de.cr.freitonal.shared.models.VolatileInstrumentation;
import de.cr.freitonal.shared.models.VolatileItem;
import de.cr.freitonal.shared.models.VolatilePiece;
import de.cr.freitonal.usertests.api.ScriptSequence.Script;

public class APITestCase extends GWTTestCase {
	private static final int NUMBER_OF_AUTOMATIC_SCRIPTS = 1;
	private ScriptSequence scripts;
	private RPCService rpcService;
	private PurgeServiceAsync purgeService;
	private static int counter = 0;

	public interface Future<T> {
		public T getObject();
	}

	@Override
	public String getModuleName() {
		return "de.cr.freitonal.FreitonalGUI";
	}

	@Override
	public void gwtSetUp() {
		Script onFailureScript = new Script() {
			@Override
			public void run(Object... parameters) {
				fail();
				finishTest();
			}
		};

		scripts = new ScriptSequence(onFailureScript);
		rpcService = new RPCServiceImpl();
		purgeService = (PurgeServiceAsync) GWT.create(PurgeService.class);
		addPurgeDBScript(); //an automatic script
	}

	private void purgeDB(AsyncCallback<Void> callback) {
		purgeService.purgeDB(callback);
	}

	private void addPurgeDBScript() {
		final String id = generateID();
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				purgeDB(scripts.createVoidCallback(id));
			}
		});
	}

	private Future<Item> createItemFuture(final String id) {
		return new Future<Item>() {
			@Override
			public Item getObject() {
				return (Item) scripts.getResult(id);
			}
		};
	}

	private Future<Piece> createPieceFuture(final String id) {
		return new Future<Piece>() {
			@Override
			public Piece getObject() {
				return (Piece) scripts.getResult(id);
			}
		};
	}

	protected Future<Item> createComposer(final VolatileItem composer) {
		final String id = generateID();
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				rpcService.createComposer(composer, scripts.createItemCallback(id));
			}
		});

		return createItemFuture(id);
	}

	protected Future<Item> createComposer(String composerName) {
		return createComposer(new VolatileItem(composerName));
	}

	private Future<Catalog> createCatalogFuture(final String id) {
		return new Future<Catalog>() {
			@Override
			public Catalog getObject() {
				return (Catalog) scripts.getResult(id);
			}
		};
	}

	private Future<Instrumentation> createInstrumentationFuture(final String id) {
		return new Future<Instrumentation>() {
			@Override
			public Instrumentation getObject() {
				return (Instrumentation) scripts.getResult(id);
			}
		};
	}

	protected Future<Item> createInstrument(final VolatileItem instrument) {
		final String id = generateID();
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				rpcService.createInstrument(instrument, scripts.createItemCallback(id));
			}
		});
		return createItemFuture(id);
	}

	protected Future<Item> createInstrument(String instrumentName) {
		return createInstrument(new VolatileItem(instrumentName));
	}

	protected Future<Instrumentation> createInstrumentation(final String nickname, final Future<Item>... instrumentFutures) {
		final String id = generateID();
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				Item[] instruments = new Item[instrumentFutures.length];
				for (int i = 0; i < instrumentFutures.length; i++) {
					instruments[i] = instrumentFutures[i].getObject();
				}
				VolatileInstrumentation instrumentation = new VolatileInstrumentation(nickname, instruments);
				rpcService.createInstrumentation(instrumentation, scripts.createInstrumentationCallback(id));
			}
		});

		return createInstrumentationFuture(id);
	}

	protected Future<Item> createCatalogName(final VolatileItem catalogName) {
		final String id = generateID();
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				rpcService.createCatalogName(catalogName, scripts.createItemCallback(id));
			}
		});
		return createItemFuture(id);
	}

	protected Future<Item> createPieceType(final VolatileItem pieceType) {
		final String id = generateID();
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				rpcService.createPieceType(pieceType, scripts.createItemCallback(id));
			}
		});
		return createItemFuture(id);
	}

	private VolatilePiece createVolatilePiece(final Future<Item> composer, final Future<Instrumentation> instrumentation,
			final Future<Catalog> catalog, final Future<Item> pieceType) {
		VolatilePiece vPiece = new VolatilePiece();
		vPiece.setComposer(composer.getObject());
		vPiece.setInstrumentation(instrumentation.getObject());
		vPiece.setCatalog(catalog.getObject());
		vPiece.setPieceType(pieceType.getObject());

		return vPiece;
	}

	protected Future<Piece> createPiece(final Future<Item> composer, final Future<Instrumentation> instrumentation, final Future<Catalog> catalog,
			final Future<Item> pieceType) {
		final String id = generateID();
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				VolatilePiece vPiece = createVolatilePiece(composer, instrumentation, catalog, pieceType);
				rpcService.createPiece(vPiece, scripts.createPieceCallback(id));
			}
		});
		return createPieceFuture(id);

	}

	protected Future<Item> createPieceType(String pieceType) {
		return createPieceType(new VolatileItem(pieceType));
	}

	protected Future<Item> createCatalogName(String catalogName) {
		return createCatalogName(new VolatileItem(catalogName));
	}

	protected Future<Catalog> createCatalog(final Future<Item> catalogName, final String ordinal) {
		final String id = generateID();
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				VolatileCatalog catalog = new VolatileCatalog(catalogName.getObject(), ordinal);
				rpcService.createCatalog(catalog, scripts.createCatalogCallback(id));
			}
		});
		return createCatalogFuture(id);
	}

	protected ScriptSequence getScriptSequence() {
		return scripts;
	}

	protected void addScript(Script script) {
		scripts.addScript(script);
	}

	protected void runNextScript() {
		scripts.runNextScript();
	}

	protected void go() {
		addFinishTestScript();
		runNextScript();
		delayTestFinish(1000 * 10);
	}

	private void addFinishTestScript() {
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				finishTest();
			}
		});
	}

	private String generateID() {
		return String.valueOf(++counter);
	}

}
