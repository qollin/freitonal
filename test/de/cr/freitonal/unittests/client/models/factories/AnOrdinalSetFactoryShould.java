package de.cr.freitonal.unittests.client.models.factories;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.cr.freitonal.client.models.OrdinalSet;
import de.cr.freitonal.client.models.factories.OrdinalSetFactory;
import de.cr.freitonal.client.rpc.dto.DTOObject;
import de.cr.freitonal.shared.models.Item;

public class AnOrdinalSetFactoryShould extends FactoryTest {

	@Test
	public void ReturnACorrectOrdinalSet() {
		DTOObject jsonObject = parse("{'piece-type_ordinal': [['17', '17']]}");
		OrdinalSetFactory factory = new OrdinalSetFactory();
		OrdinalSet ordinalSet = factory.createOrdinalSet(jsonObject);

		assertEquals(new Item("17", "17"), ordinalSet.getItem(0));
	}
}
