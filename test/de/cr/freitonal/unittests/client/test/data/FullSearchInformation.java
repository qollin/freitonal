package de.cr.freitonal.unittests.client.test.data;

import java.util.ArrayList;
import java.util.Arrays;

import de.cr.freitonal.client.models.Catalog;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.VolatileInstrumentation;

public class FullSearchInformation {
	public static int NumberOfComposers = 3;
	public static int NumberOfCatalogNames = 3;
	public static int NumberOfCatalogNumbers = 319;
	public static int NumberOfPieceTypes = 34;
	public static int NumberOfPiecePlusInstrumentationTypes = 14;
	public static int NumberOfInstruments = 22;
	public static int NumberOfSubtitles = 83;
	public static int NumberOfOrdinals = 48;

	public static Item Beethoven = new Item("1", "van Beethoven, Ludwig");
	public static Item Mozart = new Item("2", "Mozart, Wolfgang Amadeus");

	public static Item Piano = new Item("4", "Klavier");
	public static Item Violin = new Item("1", "Violine");

	public static Item Eroica = new Item("Eroica", "Eroica");

	public static Item Ordinal4a = new Item("4a", "4a");

	public static Item AMajor = new Item("31", "A-Dur");

	public static Catalog Opus27_1 = new Catalog(new Item("1", "Opus"), new Item("110", "27-1"));
	public static final Item KV = new Item("2", "KV");

	public static final Item Quartett = new Item("1", "Quartett");
	public static final Item Sonate = new Item("3", "Sonate");
	public static final Item Symphonie = new Item("8", "Symphonie");

	public static final VolatileInstrumentation InstrumentationPianoSolo = new VolatileInstrumentation("", new ArrayList<Item>(
			Arrays.asList(new Item[] { Piano })));
}
