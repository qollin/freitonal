package de.cr.freitonal.client.rpc;

import java.util.ArrayList;

import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.models.ComposerSet;
import de.cr.freitonal.client.models.InstrumentationSet;
import de.cr.freitonal.client.models.Item;
import de.cr.freitonal.client.models.MusicKeySet;
import de.cr.freitonal.client.models.OrdinalSet;
import de.cr.freitonal.client.models.PieceTypeSet;
import de.cr.freitonal.client.models.SubtitleSet;
import de.cr.freitonal.client.rpc.dto.DTOArray;
import de.cr.freitonal.client.rpc.dto.DTOObject;
import de.cr.freitonal.client.rpc.dto.DTOParser;
import de.cr.freitonal.client.rpc.dto.DTOValue;

public class ModelFactory {
	private final DTOParser parser;

	public ModelFactory(DTOParser parser) {
		this.parser = parser;

	}

	public SearchResult createSearchResult(String jsonString) {
		DTOValue value = parser.parse(jsonString);

		SearchResult searchResult = new SearchResult();
		searchResult.setPieceSearchMask(createPieceSearchMask(value.isObject()));

		return searchResult;
	}

	private PieceSearchMask createPieceSearchMask(DTOObject rpcObject) {
		PieceSearchMask pieceSearchMask = new PieceSearchMask();

		pieceSearchMask.setComposers(createComposerSet(rpcObject.get("piece-composer").isArray()));
		pieceSearchMask
				.setCatalogs(createCatalogSet(rpcObject.get("piece-catalog__name").isArray(), rpcObject.get("piece-catalog__number").isArray()));
		pieceSearchMask.setPieceTypes(createPieceTypeSet(rpcObject.get("piece-piece_type").isArray()));
		pieceSearchMask.setInstrumentations(createInstrumentationSet(rpcObject.get("piece-instrumentations__instruments").isArray()));
		pieceSearchMask.setSubtitles(createSubtitleSet(rpcObject.get("piece-subtitle").isArray()));
		pieceSearchMask.setOrdinals(createOrdinalSet(rpcObject.get("piece-type_ordinal").isArray()));
		pieceSearchMask.setMusicKeys(createMusicKeySet(rpcObject.get("piece-music_key").isArray()));

		return pieceSearchMask;
	}

	private MusicKeySet createMusicKeySet(DTOArray array) {
		return new MusicKeySet(createItemListFromRPCArray(array));
	}

	private OrdinalSet createOrdinalSet(DTOArray array) {
		return new OrdinalSet(createItemListFromRPCArray(array));
	}

	private SubtitleSet createSubtitleSet(DTOArray array) {
		return new SubtitleSet(createItemListFromRPCArray(array));
	}

	private InstrumentationSet createInstrumentationSet(DTOArray array) {
		return new InstrumentationSet(createItemListFromRPCArray(array));
	}

	private PieceTypeSet createPieceTypeSet(DTOArray array) {
		return new PieceTypeSet(createItemListFromRPCArray(array));
	}

	private CatalogSet createCatalogSet(DTOArray nameArray, DTOArray numberArray) {
		return new CatalogSet(createItemListFromRPCArray(nameArray), createItemListFromRPCArray(numberArray));
	}

	private ComposerSet createComposerSet(DTOArray rpcArray) {
		return new ComposerSet(createItemListFromRPCArray(rpcArray));
	}

	private ArrayList<Item> createItemListFromRPCArray(DTOArray rpcArray) {
		ArrayList<Item> items = new ArrayList<Item>();

		for (int i = 0; i < rpcArray.size(); i++) {
			String id;
			if (rpcArray.get(i).isArray().get(0).isNumber() != null) {
				id = String.valueOf((int) rpcArray.get(i).isArray().get(0).isNumber().doubleValue());
			} else {
				id = rpcArray.get(i).isArray().get(0).isString().stringValue();
			}
			String name = rpcArray.get(i).isArray().get(1).isString().stringValue();
			items.add(new Item(id, name));
		}

		return items;
	}
}
