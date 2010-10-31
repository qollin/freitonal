package de.cr.freitonal.client.rpc;

import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.models.ComposerSet;
import de.cr.freitonal.client.models.InstrumentationSet;
import de.cr.freitonal.client.models.MusicKeySet;
import de.cr.freitonal.client.models.OrdinalSet;
import de.cr.freitonal.client.models.PieceTypeSet;
import de.cr.freitonal.client.models.PublicationDateSet;
import de.cr.freitonal.client.models.SubtitleSet;
import de.cr.freitonal.client.models.factories.CatalogSetFactory;
import de.cr.freitonal.client.models.factories.ComposerSetFactory;
import de.cr.freitonal.client.models.factories.InstrumentationSetFactory;
import de.cr.freitonal.client.models.factories.MusicKeySetFactory;
import de.cr.freitonal.client.models.factories.OrdinalSetFactory;
import de.cr.freitonal.client.models.factories.PieceTypeSetFactory;
import de.cr.freitonal.client.models.factories.PublicationDateSetFactory;
import de.cr.freitonal.client.models.factories.SubtitleSetFactory;
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

		pieceSearchMask.setComposers(createComposerSet(rpcObject));
		pieceSearchMask.setCatalogs(createCatalogSet(rpcObject));
		pieceSearchMask.setPieceTypes(createPieceTypeSet(rpcObject));
		pieceSearchMask.setInstrumentations(createInstrumentationSet(rpcObject));
		pieceSearchMask.setSubtitles(createSubtitleSet(rpcObject));
		pieceSearchMask.setOrdinals(createOrdinalSet(rpcObject));
		pieceSearchMask.setMusicKeys(createMusicKeySet(rpcObject));
		pieceSearchMask.setPublicationDates(createPublicationDateSet(rpcObject));

		return pieceSearchMask;
	}

	private MusicKeySet createMusicKeySet(DTOObject jsonObject) {
		MusicKeySetFactory factory = new MusicKeySetFactory();
		return factory.createMusicKeySet(jsonObject);
	}

	private PublicationDateSet createPublicationDateSet(DTOObject jsonObject) {
		PublicationDateSetFactory factory = new PublicationDateSetFactory();
		return factory.createPublicationDateSet(jsonObject);
	}

	private OrdinalSet createOrdinalSet(DTOObject jsonObject) {
		OrdinalSetFactory factory = new OrdinalSetFactory();
		return factory.createOrdinalSet(jsonObject);
	}

	private SubtitleSet createSubtitleSet(DTOObject jsonObject) {
		SubtitleSetFactory factory = new SubtitleSetFactory();
		return factory.createSubtitleSet(jsonObject);
	}

	public InstrumentationSet createInstrumentationSet(DTOObject jsonObject) {
		InstrumentationSetFactory factory = new InstrumentationSetFactory();
		return factory.createInstrumentationSet(jsonObject);
	}

	private PieceTypeSet createPieceTypeSet(DTOObject jsonObject) {
		PieceTypeSetFactory factory = new PieceTypeSetFactory();
		return factory.createPieceTypeSet(jsonObject);
	}

	private CatalogSet createCatalogSet(DTOObject jsonObject) {
		CatalogSetFactory factory = new CatalogSetFactory();
		return factory.createCatalogSet(jsonObject);
	}

	private ComposerSet createComposerSet(DTOObject jsonObject) {
		ComposerSetFactory factory = new ComposerSetFactory();
		return factory.createComposerSet(jsonObject);
	}
}
