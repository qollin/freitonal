package de.cr.freitonal.unittests.client.models.factories;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.google.gson.JsonArray;

import de.cr.freitonal.client.models.factories.AbstractFactory;
import de.cr.freitonal.client.rpc.dto.DTOArray;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.unittests.client.rpc.java.DTOArrayJava;

public class AnAbstractFactoryShould {
	@Test
	public void ReturnAnEmptyListWhenGivenNullAsAParameter() {
		AbstractFactoryTester factory = new AbstractFactoryTester();
		ArrayList<Item> items = factory.createItemListFromRPCArray(null);
		assertEquals(0, items.size());
	}

	@Test
	public void ReturnANullItemWhenTheGivenRPCArrayContainsNull() {
		AbstractFactoryTester factory = new AbstractFactoryTester();
		DTOArray dtoArray = createDTOArrayWithANullItem();
		ArrayList<Item> items = factory.createItemListFromRPCArray(dtoArray);
		assertEquals(1, items.size());
		assertEquals(Item.NULL_ITEM, items.get(0));
	}

	private DTOArray createDTOArrayWithANullItem() {
		JsonArray jsonArray = new JsonArray();
		JsonArray jsonItem = new JsonArray();
		jsonItem.add(null);
		jsonItem.add(null);
		jsonArray.add(jsonItem);

		return new DTOArrayJava(jsonArray);
	}

	class AbstractFactoryTester extends AbstractFactory {
		@Override
		public ArrayList<Item> createItemListFromRPCArray(DTOArray array) {
			return super.createItemListFromRPCArray(array);
		}
	}
}
