package de.cr.freitonal.usertests;

import de.cr.freitonal.client.rpc.SearchResult;

public interface Verifier {
	void verify(SearchResult result);
}