package de.cr.freitonal.unittests.client.models.factories;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import de.cr.freitonal.client.models.factories.AbstractFactory;
import de.cr.freitonal.client.rpc.dto.DTOArray;
import de.cr.freitonal.shared.models.Item;

public class AnAbstractFactoryShould {
	@Test
	public void ReturnAnEmptyListWhenGivenNullAsAParameter() {
		AbstractFactoryTester factory = new AbstractFactoryTester();
		ArrayList<Item> items = factory.createItemListFromRPCArray(null);
		assertEquals(0, items.size());
	}

	class AbstractFactoryTester extends AbstractFactory {
		@Override
		public ArrayList<Item> createItemListFromRPCArray(DTOArray array) {
			return super.createItemListFromRPCArray(array);
		}
	}
}
