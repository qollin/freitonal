package de.cr.freitonal.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.cr.freitonal.shared.parameters.SearchParameters;

@RemoteServiceRelativePath("search")
public interface SearchService extends RemoteService {
	public SearchResult search(SearchParameters searchParameters);

	public SearchResult search();
}
