package de.cr.freitonal.unittests.client.rpc;

import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.NumberOfCatalogNames;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.NumberOfCatalogNumbers;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.NumberOfComposers;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.NumberOfInstruments;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.NumberOfOrdinals;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.NumberOfPieceTypes;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.NumberOfSubtitles;
import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.rpc.ModelFactory;
import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.client.rpc.java.DTOParserJava;
import de.cr.freitonal.unittests.client.test.data.TestResources;

public class ModelFactoryTest {
	private TestResources resources;

	@Before
	public void setUp() {
		resources = new TestResources();
	}

	@Test
	public void testCreateSearchResult() {
		ModelFactory modelFactory = new ModelFactory(new DTOParserJava());
		SearchResult searchResult = modelFactory.createSearchResult(resources.getFullSearchJSON().getText());

		PieceSearchMask results = searchResult.getPieceSearchMask();
		assertEquals(NumberOfComposers, results.getComposers().getItems().size());
		assertEquals(NumberOfCatalogNames, results.getCatalogs().getNames().getItems().size());
		assertEquals(NumberOfCatalogNumbers, results.getCatalogs().getNumbers().getItems().size());
		assertEquals(NumberOfPieceTypes, results.getPieceTypes().getItems().size());
		assertEquals(NumberOfInstruments, results.getInstrumentations().getItems().size());
		assertEquals(NumberOfSubtitles, results.getSubtitles().getItems().size());
		assertEquals(NumberOfOrdinals, results.getOrdinals().getItems().size());
	}
}
