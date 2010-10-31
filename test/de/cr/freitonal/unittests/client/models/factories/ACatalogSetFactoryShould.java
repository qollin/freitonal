package de.cr.freitonal.unittests.client.models.factories;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.models.factories.CatalogSetFactory;
import de.cr.freitonal.client.rpc.dto.DTOObject;
import de.cr.freitonal.shared.models.Item;

public class ACatalogSetFactoryShould extends FactoryTest {
	@Test
	public void ReturnACatalogSet() {
		DTOObject jsonObject = parse("{'piece-catalog__name': [['1', 'Opus']], 'piece-catalog__number': [['1', '13-b']]}");
		CatalogSetFactory factory = new CatalogSetFactory();
		CatalogSet catalogSet = factory.createCatalogSet(jsonObject);

		assertEquals(new Item("1", "Opus"), catalogSet.getNames().getItem(0));
		assertEquals(new Item("1", "13-b"), catalogSet.getNumbers().getItem(0));
	}
}
