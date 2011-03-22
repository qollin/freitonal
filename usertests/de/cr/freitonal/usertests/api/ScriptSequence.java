package de.cr.freitonal.usertests.api;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.shared.models.Catalog;
import de.cr.freitonal.shared.models.Instrumentation;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.Piece;

public class ScriptSequence {
	protected static final String DO_NOT_SAVE_RESULT = "doNotSaveResult";
	private final Script onFailure;
	private int nextScriptIndex = 0;
	private final ArrayList<Script> scripts = new ArrayList<Script>();
	private final HashMap<String, Object> results = new HashMap<String, Object>();

	public interface Script {
		public void run(Object... parameters);
	}

	public ScriptSequence(Script onFailure) {
		this.onFailure = onFailure;
	}

	public void addScript(Script script) {
		scripts.add(script);
	}

	private void processResult(Object result, String id) {
		if (!id.equals(DO_NOT_SAVE_RESULT)) {
			results.put(id, result);
		}
		getNextScript().run(result);
	}

	public Object getResult(String id) {
		return results.get(id);
	}

	public AsyncCallback<Void> createVoidCallback(final String id) {
		return new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				onFailure.run(caught);
			}

			@Override
			public void onSuccess(Void result) {
				processResult(result, id);
			}
		};
	}

	public AsyncCallback<Item> createItemCallback(final String id) {
		return new AsyncCallback<Item>() {
			@Override
			public void onFailure(Throwable caught) {
				onFailure.run(caught);
			}

			@Override
			public void onSuccess(Item result) {
				processResult(result, id);
			}
		};
	}

	public AsyncCallback<SearchResult> createSearchResultCallbackWrapper(final AsyncCallback<SearchResult> originalCallback) {
		return new AsyncCallback<SearchResult>() {
			public void onFailure(Throwable caught) {
				onFailure.run(caught);
			}

			@Override
			public void onSuccess(SearchResult result) {
				originalCallback.onSuccess(result);
				processResult(result, DO_NOT_SAVE_RESULT);
			}
		};
	}

	public AsyncCallback<Piece> createPieceCallbackWrapper(final AsyncCallback<Piece> originalCallback) {
		return new AsyncCallback<Piece>() {
			public void onFailure(Throwable caught) {
				onFailure.run(caught);
			}

			@Override
			public void onSuccess(Piece result) {
				originalCallback.onSuccess(result);
				processResult(result, DO_NOT_SAVE_RESULT);
			}
		};
	}

	private Script getNextScript() {
		return scripts.get(nextScriptIndex++);
	}

	public void runNextScript() {
		getNextScript().run((Object) null);
	}

	public AsyncCallback<Instrumentation> createInstrumentationCallback(final String id) {
		return new AsyncCallback<Instrumentation>() {
			@Override
			public void onFailure(Throwable caught) {
				onFailure.run(caught);
			}

			@Override
			public void onSuccess(Instrumentation result) {
				processResult(result, id);
			}
		};
	}

	public AsyncCallback<Catalog> createCatalogCallback(final String id) {
		return new AsyncCallback<Catalog>() {
			@Override
			public void onFailure(Throwable caught) {
				onFailure.run(caught);
			}

			@Override
			public void onSuccess(Catalog result) {
				processResult(result, id);
			}
		};
	}

	public AsyncCallback<Piece> createPieceCallback(final String id) {
		return new AsyncCallback<Piece>() {

			@Override
			public void onFailure(Throwable caught) {
				onFailure.run(caught);
			}

			@Override
			public void onSuccess(Piece result) {
				processResult(result, id);
			}
		};
	}
}
