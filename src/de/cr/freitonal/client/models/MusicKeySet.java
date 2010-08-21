package de.cr.freitonal.client.models;

import java.util.ArrayList;

import de.cr.freitonal.shared.models.Item;

public class MusicKeySet extends ItemSet {
	public MusicKeySet(ArrayList<Item> items) {
		super(items);
	}

	public MusicKeySet(Item... items) {
		super(items);
	}

}
