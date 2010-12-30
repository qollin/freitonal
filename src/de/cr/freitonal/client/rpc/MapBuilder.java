package de.cr.freitonal.client.rpc;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder extends AbstractBuilder {
	private final Map<String, String> parameters;

	public MapBuilder(PieceSearchMask searchMask) {
		super(searchMask);
		parameters = new HashMap<String, String>();
		addParameters();
	}

	public Map<String, String> getMap() {
		return parameters;
	}

	@Override
	protected void addHTTPParameter(String key, String value) {
		parameters.put(key, value);
	}

}
