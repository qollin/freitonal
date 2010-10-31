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
		throw new IllegalArgumentException(value + " is neither a string nor a number");
	}

	protected ArrayList<Item> createItemListFromRPCArray(DTOArray rpcArray) {
		ArrayList<Item> items = new ArrayList<Item>();

		for (int i = 0; i < rpcArray.size(); i++) {
			String id = forceString(rpcArray.get(i).isArray().get(0));
			String name = rpcArray.get(i).isArray().get(1).isString().stringValue();
			items.add(new Item(id, name));
		}

		return items;
	}

}