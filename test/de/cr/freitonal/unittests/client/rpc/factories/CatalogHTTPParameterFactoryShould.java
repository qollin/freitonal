package de.cr.freitonal.unittests.client.rpc.factories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.rpc.URLEncoder;
import de.cr.freitonal.client.rpc.factories.CatalogHTTPParameterFactory;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.unittests.client.rpc.URLEncoderMock;
import de.cr.freitonal.unittests.client.test.data.TestData;

public class CatalogHTTPParameterFactoryShould {
	private URLEncoder urlEncoder;
	private CatalogHTTPParameterFactory factory;
	private CatalogSet catalogSet;
	private Item name;
	private Item number;

	@Before
	public void setupCatalogHTTPParameterFactory() {
		urlEncoder = new URLEncoderMock();
		factory = new CatalogHTTPParameterFactory(urlEncoder);
		catalogSet = TestData.createCatalogSet();
		name = catalogSet.getNames().getItem(0);
		number = catalogSet.getNumbers().getItem(0);
	}

	@Test
	public void UsesTheCorrectParameterNamesWhenOnlyANameIsSelected() {
		catalogSet.getNames().setSelected(name);

		ArrayList<String> parameters = factory.createHTTPParameters(catalogSet);
		assertTrue(parameters.contains("piece-catalog__name=" + name.getID()));
		assertEquals(1, parameters.size());
	}

	@Test
	public void UsesTheCorrectParameterNamesWhenNameAndNumbeAreSelected() {
		catalogSet.getNames().setSelected(name);
		catalogSet.getNumbers().setSelected(number);

		ArrayList<String> parameters = factory.createHTTPParameters(catalogSet);
		assertTrue(parameters.contains("piece-catalog=" + number.getID()));
		assertEquals(1, parameters.size());
	}
}
