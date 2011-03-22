package de.cr.freitonal.usertests.client.test.data;

import java.util.ArrayList;
import java.util.Arrays;

import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.models.ComposerSet;
import de.cr.freitonal.client.models.InstrumentationSet;
import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.models.MusicKeySet;
import de.cr.freitonal.client.models.OrdinalSet;
import de.cr.freitonal.client.models.PieceTypeSet;
import de.cr.freitonal.client.models.PublicationDateSet;
import de.cr.freitonal.client.models.SubtitleSet;
import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.shared.models.Catalog;
import de.cr.freitonal.shared.models.Instrumentation;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.Piece;
import de.cr.freitonal.shared.models.VolatileInstrumentation;
import de.cr.freitonal.shared.models.VolatilePiece;

public class TestData {
	public static int NumberOfComposers = 3;
	public static int NumberOfCatalogNames = 3;
	public static int NumberOfCatalogNumbers = 319;
	public static int NumberOfPieceTypes = 34;
	public static int NumberOfPiecePlusInstrumentationTypes = 14;
	public static int NumberOfInstruments = 22;
	public static int NumberOfSubtitles = 83;
	public static int NumberOfOrdinals = 48;

	public static final Item Beethoven = new Item("1", "van Beethoven, Ludwig");
	public static final Item Mozart = new Item("2", "Mozart, Wolfgang Amadeus");

	public static final Item Piano = new Item("4", "Klavier");
	public static final Item Violin = new Item("1", "Violine");
	public static final Item Oboe = new Item("17", "Oboe");

	public static final Instrumentation InstrumentationPiano = new Instrumentation("13", "solo piano", Piano);
	public static final Instrumentation InstrumentationViolinPlusPiano = new Instrumentation("14", "violin + piano", Violin, Piano);

	public static final Item Ordinal4a = new Item("4a", "4a");

	public static final Item AMajor = new Item("31", "A-Dur");

	public static final Item Opus = new Item("1", "Opus");
	public static final Item KV = new Item("2", "KV");
	public static final Item CatalogOrdinal27_1 = new Item("110", "27-1");
	public static final Catalog Opus27_1 = new Catalog(Opus, CatalogOrdinal27_1);

	public static final Item Quartett = new Item("1", "Quartett");
	public static final Item Sonata = new Item("3", "Sonate");
	public static final Item Symphonie = new Item("8", "Symphonie");

	public static final Item StringQuartett = new Item("86", "Streichquartett");

	public static final Item Year1799 = new Item("1799", "1799");

	public static Item Eroica = new Item("Eroica", "Eroica");

	public static final VolatileInstrumentation VolatileInstrumentationPianoSolo = new VolatileInstrumentation("", Piano);

	public static VolatilePiece createVolatilePiece() {
		VolatilePiece vPiece = new VolatilePiece(Beethoven, InstrumentationPiano, Sonata, Opus27_1, AMajor);
		vPiece.setOrdinal(Ordinal4a);
		vPiece.setPieceType(Symphonie);
		vPiece.setPublicationDate(Year1799);
		vPiece.setSubtitle(Eroica);

		return vPiece;
	}

	public static Piece createPiece() {
		return new Piece("1", createVolatilePiece());
	}

	public static SearchResult createSearchResult() {
		SearchResult searchResult = new SearchResult();
		searchResult.setPieceSearchMask(createPieceMask());

		return searchResult;
	}

	public static PieceSearchMask createPieceMask() {
		PieceSearchMask pieceMask = new PieceSearchMask();
		pieceMask.setCatalogs(createCatalogSet());
		pieceMask.setComposers(createComposerSet());
		pieceMask.setInstrumentations(createInstrumentationSet(2));
		pieceMask.setMusicKeys(createMusicKeySet());
		pieceMask.setOrdinals(createOrdinalSet());
		pieceMask.setPieceTypes(createPieceTypeSet());
		pieceMask.setPublicationDates(createPublicationDateSet());
		pieceMask.setSubtitles(createSubtitleSet());

		return pieceMask;
	}

	public static OrdinalSet createOrdinalSet() {
		return new OrdinalSet(Ordinal4a);
	}

	public static InstrumentationSet createInstrumentationSet(int numberOfInstrumentations) {
		if (numberOfInstrumentations == 1) {
			return new InstrumentationSet(InstrumentationPiano);
		} else if (numberOfInstrumentations == 2) {
			return new InstrumentationSet(InstrumentationPiano, InstrumentationViolinPlusPiano);
		} else {
			throw new IllegalArgumentException("cannot handle " + numberOfInstrumentations + " instrumentations");
		}
	}

	public static MusicKeySet createMusicKeySet() {
		return new MusicKeySet(AMajor);
	}

	public static CatalogSet createCatalogSet() {
		return createCatalogSet(1);
	}

	public static CatalogSet createCatalogSet(int i) {
		switch (i) {
		case 1:
			return new CatalogSet(new ItemSet(Opus), new ItemSet(CatalogOrdinal27_1));
		default:
			throw new IllegalArgumentException("not implemented yet for i = " + i);
		}

	}

	public static PieceTypeSet createPieceTypeSet() {
		ArrayList<Item> pieceTypes = new ArrayList<Item>(Arrays.asList(new Item[] { Quartett, Sonata, Symphonie }));
		ArrayList<Item> piecePlusInstrumentationTypes = new ArrayList<Item>(Arrays.asList(new Item[] { StringQuartett }));
		return new PieceTypeSet(pieceTypes, piecePlusInstrumentationTypes);
	}

	public static PublicationDateSet createPublicationDateSet() {
		return new PublicationDateSet(Year1799);
	}

	public static SubtitleSet createSubtitleSet() {
		return new SubtitleSet(Eroica);
	}

	public static ComposerSet createComposerSet() {
		return new ComposerSet(Beethoven, Mozart);
	}

}
