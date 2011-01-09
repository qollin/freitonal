package de.cr.freitonal.client.rpc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapBuilder extends AbstractBuilder {
	private final Map<String, ArrayList<String>> parameters;

	public MapBuilder(PieceSearchMask searchMask) {
		super(searchMask);
		parameters = new HashMap<String, ArrayList<String>>();
		addParameters();
	}

	public Map<String, ArrayList<String>> getMap() {
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
