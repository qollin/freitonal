package de.cr.freitonal.unittests.client.rpc.factories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.rpc.Parameter;
import de.cr.freitonal.client.rpc.URLEncoder;
import de.cr.freitonal.client.rpc.factories.CatalogHTTPParameterFactory;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.usertests.client.test.data.TestData;

public class ACatalogHTTPParameterFactoryShould {
	private URLEncoder urlEncoder;
	private CatalogHTTPParameterFactory factory;
	private CatalogSet catalogSet;
	private Item name;
	private Item number;

	@Before
	public void setupCatalogHTTPParameterFactory() {
		factory = new CatalogHTTPParameterFactory();
		catalogSet = TestData.createCatalogSet();
		name = catalogSet.getNames().getItem(0);
		number = catalogSet.getNumbers().getItem(0);
	}

	@Test
	public void UseTheCorrectParameterNamesWhenOnlyANameIsSelected() {
		catalogSet.getNames().setSelected(name);

		ArrayList<Parameter> parameters = factory.createHTTPParameters(catalogSet);
		assertTrue(parameters.contains(new Parameter("piece-catalog__name", name.getID())));
		assertEquals(1, parameters.size());
	}

	@Test
	public void UseTheCorrectParameterNamesWhenNameAndNumbeAreSelected() {
		catalogSet.getNames().setSelected(name);
		catalogSet.getNumbers().setSelected(number);

		ArrayList<Parameter> parameters = factory.createHTTPParameters(catalogSet);
		assertTrue(parameters.contains(new Parameter("piece-catalog", number.getID())));
		assertEquals(1, parameters.size());
	}
}
