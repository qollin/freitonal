package de.cr.freitonal.client.models;

import java.util.ArrayList;

import de.cr.freitonal.shared.models.Item;

public class InstrumentationSetOld extends ItemSetMultiSelection {

	public InstrumentationSetOld(ArrayList<Item> instrumentItems) {
		super(instrumentItems);
	}

	public InstrumentationSetOld(Item... items) {
		super(items);
	}
}
