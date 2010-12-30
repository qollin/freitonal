package de.cr.freitonal.client.models;

import java.util.ArrayList;
import java.util.HashSet;

import com.google.gwt.user.client.rpc.IsSerializable;

import de.cr.freitonal.shared.models.Item;

public class ItemSet implements IsSerializable {
	private ArrayList<Item> items;
	private Item selected;

	private ItemSet() {
	}

	public ItemSet(Item... items) {
		this.items = new ArrayList<Item>(items.length);
		for (Item item : items) {
			this.items.add(item);
		}
	}

	public ItemSet(ArrayList<Item> items) {
		if (items == null) {
			throw new IllegalArgumentException("items must not be null");
		}
		this.items = items;
	}

	public ItemSet(HashSet<Item> instruments) {
		this(new ArrayList<Item>(instruments));
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
		if (!getItems().contains(selected)) {
			throw new IllegalArgumentException("unknown item: " + selected);
		}
		this.selected = selected;
	}

	public void copyItemSelectionTo(ItemSet target) {
		target.selected = selected;
	}

	public int size() {
		return getItems().size();
	}

	public boolean contains(Item item) {
		return getItems().contains(item);
	}

	public Item getItem(int index) {
		return getItems().get(index);
	}

	public void removeSelection() {
		selected = null;
	}
}
