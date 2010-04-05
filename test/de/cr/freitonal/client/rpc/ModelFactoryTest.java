package de.cr.freitonal.client.rpc;

import static de.cr.freitonal.client.test.data.FullSearchInformation.NumberOfCatalogNames;
import static de.cr.freitonal.client.test.data.FullSearchInformation.NumberOfCatalogNumbers;
import static de.cr.freitonal.client.test.data.FullSearchInformation.NumberOfComposers;
import static de.cr.freitonal.client.test.data.FullSearchInformation.NumberOfInstruments;
import static de.cr.freitonal.client.test.data.FullSearchInformation.NumberOfPieceTypes;

import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;

import de.cr.freitonal.client.test.data.TestResources;

public class ModelFactoryTest extends GWTTestCase {
	private TestResources resources;

	@Override
	public void gwtSetUp() {
		resources = GWT.create(TestResources.class);
	}

	@Test
	public void testCreateSearchResult() {
		ModelFactory modelFactory = new ModelFactory();
		SearchResult searchResult = modelFactory.createSearchResult(resources.getFullSearchJSON().getText());

		PieceSearchMask results = searchResult.getPieceSearchMask();
		assertEquals(NumberOfComposers, results.getComposers().getItems().size());
		assertEquals(NumberOfCatalogNames, results.getCatalogs().getNames().getItems().size());
		assertEquals(NumberOfCatalogNumbers, results.getCatalogs().getNumbers().getItems().size());
		assertEquals(NumberOfPieceTypes, results.getPieceTypes().getItems().size());
		assertEquals(NumberOfInstruments, results.getInstrumentations().getItems().size());
	}

	@Override
	public String getModuleName() {
		return "de.cr.freitonal.FreitonalGUI";
	}

}
