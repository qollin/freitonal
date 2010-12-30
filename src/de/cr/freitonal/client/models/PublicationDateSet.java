package de.cr.freitonal.client.models;

import java.util.ArrayList;

import de.cr.freitonal.shared.models.Item;

public class PublicationDateSet extends ItemSet {

	private PublicationDateSet() {
	}

	public PublicationDateSet(ArrayList<Item> items) {
		super(items);
	}

	public PublicationDateSet(Item... items) {
		super(items);
	}
}
