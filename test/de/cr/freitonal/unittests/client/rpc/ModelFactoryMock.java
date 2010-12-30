package de.cr.freitonal.unittests.client.rpc;

import static de.cr.freitonal.usertests.client.test.data.TestData.createPieceMask;
import de.cr.freitonal.client.rpc.ModelFactory;
import de.cr.freitonal.client.rpc.SearchResult;

public class ModelFactoryMock extends ModelFactory {
	public ModelFactoryMock() {
		super(null);
	}

	@Override
	public SearchResult createSearchResult(String jsonString) {
		SearchResult searchResult = new SearchResult();
		searchResult.setPieceSearchMask(createPieceMask());

		return searchResult;
	}
}
