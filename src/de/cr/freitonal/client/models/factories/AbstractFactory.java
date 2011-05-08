package de.cr.freitonal.client.models.factories;

import java.util.ArrayList;

import de.cr.freitonal.client.rpc.dto.DTOArray;
import de.cr.freitonal.client.rpc.dto.DTOValue;
import de.cr.freitonal.shared.models.Item;

public abstract class AbstractFactory {

	protected String forceString(DTOValue value) {
		if (value.isString() != null) {
			return value.isString().stringValue();
		} else if (value.isNumber() != null) {
			return String.valueOf((int) value.isNumber().doubleValue());
		}
		return null;
	}

	protected ArrayList<Item> createItemListFromRPCArray(DTOArray rpcArray) {
		ArrayList<Item> items = new ArrayList<Item>();
		if (rpcArray == null) {
			return items;
		}

		for (int i = 0; i < rpcArray.size(); i++) {
			Item item = createItemFromTuplet(rpcArray.get(i).isArray());
			items.add(item);
		}

		return items;
	}

	private Item createItemFromTuplet(DTOArray tuplet) {
		String id = forceString(tuplet.get(0));
		Item item;
		if (id == null) {
			item = Item.NULL_ITEM;
		} else {
			String name = tuplet.get(1).isString().stringValue();
			item = new Item(id, name);
		}
		return item;
	}

}