package de.cr.freitonal.client.models;

import java.util.ArrayList;

import de.cr.freitonal.shared.models.Item;

public class ComposerSet extends ItemSet {

	public ComposerSet(ArrayList<Item> items) {
		super(items);
	}

	public ComposerSet(Item... items) {
		super(items);
	}
}
