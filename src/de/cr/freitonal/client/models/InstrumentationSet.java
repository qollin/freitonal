package de.cr.freitonal.client.models;

import java.util.ArrayList;

import de.cr.freitonal.shared.models.Item;

public class InstrumentationSet extends ItemSetMultiSelection {

	public InstrumentationSet(ArrayList<Item> instrumentItems) {
		super(instrumentItems);
	}

	public InstrumentationSet(Item... items) {
		super(items);
	}
}
