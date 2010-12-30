package de.cr.freitonal.client.models;

import java.util.ArrayList;

import de.cr.freitonal.shared.models.Item;

public class OrdinalSet extends ItemSet {

	private OrdinalSet() {
	}

	public OrdinalSet(ArrayList<Item> items) {
		super(items);
	}

	public OrdinalSet(Item... items) {
		super(items);
	}

}
