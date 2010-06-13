package de.cr.freitonal.client.models;

import java.util.ArrayList;

public class ItemSet {
	private final ArrayList<Item> items;
	private Item selected;

	public ItemSet(Item... items) {
		this.items = new ArrayList<Item>(items.length);
		for (Item item : items) {
			this.items.add(item);
		}
	}

	public ItemSet(ArrayList<Item> items) {
		this.items = items;
	}

	/**
	 * @return the items
	 */
	public ArrayList<Item> getItems() {
		return items;
	}

	public Item getSelected() {
		return selected;
	}

	public void setSelected(Item selected) {
		//TODO: potentially expensive
		if (!items.contains(selected)) {
			throw new IllegalArgumentException("unknown item: " + selected);
		}
		this.selected = selected;
	}

	public void copyItemSelectionTo(ItemSet target) {
		target.selected = selected;
	}

	public int size() {
		return items.size();
	}
}
