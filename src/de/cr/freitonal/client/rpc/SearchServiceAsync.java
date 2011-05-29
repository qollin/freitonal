package de.cr.freitonal.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.shared.parameters.SearchParameters;

public interface SearchServiceAsync {
	public void search(SearchParameters searchParameters, AsyncCallback<SearchResult> callback);

	public void search(AsyncCallback<SearchResult> callback);
}
