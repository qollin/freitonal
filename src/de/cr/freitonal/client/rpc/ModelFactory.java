package de.cr.freitonal.client.rpc;

import java.util.ArrayList;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.models.ComposerSet;
import de.cr.freitonal.client.models.InstrumentationSet;
import de.cr.freitonal.client.models.Item;
import de.cr.freitonal.client.models.PieceTypeSet;

public class ModelFactory {
	public SearchResult createSearchResult(String jsonString) {
		JSONValue json = JSONParser.parse(jsonString);

		SearchResult searchResult = new SearchResult();
		searchResult.setPieceSearchMask(createPieceSearchMask(json.isObject()));

		return searchResult;
	}

	private PieceSearchMask createPieceSearchMask(JSONObject json) {
		PieceSearchMask pieceSearchMask = new PieceSearchMask();

		pieceSearchMask.setComposers(createComposerSet(json.get("piece-composer").isArray()));
		pieceSearchMask.setCatalogs(createCatalogSet(json.get("piece-catalog__name").isArray(), json.get("piece-catalog__number").isArray()));
		pieceSearchMask.setPieceTypes(createPieceTypeSet(json.get("piece-piece_type").isArray()));
		pieceSearchMask.setInstrumentations(createInstrumentationSet(json.get("piece-instrumentations__instruments").isArray()));

		return pieceSearchMask;
	}

	private InstrumentationSet createInstrumentationSet(JSONArray array) {
		return new InstrumentationSet(createItemListFromJSONArray(array));
	}

	private PieceTypeSet createPieceTypeSet(JSONArray array) {
		return new PieceTypeSet(createItemListFromJSONArray(array));
	}

	private CatalogSet createCatalogSet(JSONArray nameArray, JSONArray numberArray) {
		return new CatalogSet(createItemListFromJSONArray(nameArray), createItemListFromJSONArray(numberArray));
	}

	private ComposerSet createComposerSet(JSONArray array) {
		return new ComposerSet(createItemListFromJSONArray(array));
	}

	private ArrayList<Item> createItemListFromJSONArray(JSONArray array) {
		ArrayList<Item> items = new ArrayList<Item>();

		for (int i = 0; i < array.size(); i++) {
			int id = (int) array.get(i).isArray().get(0).isNumber().doubleValue();
			String name = array.get(i).isArray().get(1).isString().stringValue();
			items.add(new Item(String.valueOf(id), name));
		}

		return items;
	}
}
