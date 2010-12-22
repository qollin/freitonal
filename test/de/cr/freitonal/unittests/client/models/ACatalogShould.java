package de.cr.freitonal.unittests.client.models;

import static de.cr.freitonal.unittests.client.test.data.TestData.CatalogOrdinal27_1;
import static de.cr.freitonal.unittests.client.test.data.TestData.Opus;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.cr.freitonal.shared.models.Catalog;

public class ACatalogShould {
	@Test
	public void BeEqualToAnotherCatalogWithTheSameID() {
		Catalog catalog1 = new Catalog(Opus, CatalogOrdinal27_1);
		Catalog catalog2 = new Catalog(Opus, CatalogOrdinal27_1);

		assertEquals(catalog1, catalog2);
	}
}
