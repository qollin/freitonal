package de.cr.freitonal.usertests.api;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.shared.models.Item;

public class ScriptSequence {
	private final Script onFailure;
	private int nextScriptIndex = 0;
	private final ArrayList<Script> scripts = new ArrayList<Script>();

	public interface Script {
		public void run(Object... parameters);
	}

	public ScriptSequence(Script onFailure) {
		this.onFailure = onFailure;
	}

	public void addScript(Script script) {
		scripts.add(script);
	}

	public AsyncCallback<Void> createVoidCallback() {
		return new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				onFailure.run((Object) null);
			}

			@Override
			public void onSuccess(Void result) {
				getNextScript().run((Object) null);
			}
		};
	}

	public AsyncCallback<Item> createItemCallback() {
		return new AsyncCallback<Item>() {
			@Override
			public void onFailure(Throwable caught) {
				onFailure.run((Object) null);
			}

			@Override
			public void onSuccess(Item result) {
				getNextScript().run(result);
			}
		};
	}

	public AsyncCallback<SearchResult> createSearchResultCallbackWrapper(final AsyncCallback<SearchResult> originalCallback) {
		return new AsyncCallback<SearchResult>() {
			public void onFailure(Throwable caught) {
				onFailure.run((Object) null);
			}

			@Override
			public void onSuccess(SearchResult result) {
				originalCallback.onSuccess(result);
				getNextScript().run(result);
			}
		};
	}

	private Script getNextScript() {
		return scripts.get(nextScriptIndex++);
	}

	public void run() {
		getNextScript().run((Object) null);
	}

}
