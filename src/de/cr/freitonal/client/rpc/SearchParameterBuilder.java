package de.cr.freitonal.client.rpc;

import java.util.ArrayList;

import de.cr.freitonal.shared.parameters.SearchParameters;

public class SearchParameterBuilder extends AbstractBuilder {
	private final SearchParameters parameters;

	public SearchParameterBuilder(PieceSearchMask searchMask) {
		super(searchMask);
		parameters = new SearchParameters();
		addParameters();
	}

	public SearchParameters getSearchParameters() {
		return parameters;
	}

	@Override
	protected void addHTTPParameter(String key, String value) {
		if (!parameters.containsKey(key)) {
			parameters.put(key, new ArrayList<String>());
		}
		parameters.get(key).add(value);

	}
}
