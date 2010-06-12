package de.cr.freitonal.client.models;

import java.util.ArrayList;

public class ItemSetMultiSelection extends ItemSet {
	private final ArrayList<Item> selected = new ArrayList<Item>();

	public ItemSetMultiSelection(Item... items) {
		super(items);
	}

	public ItemSetMultiSelection(ArrayList<Item> items) {
		super(items);
	}

	@Override
	public void setSelected(Item item) {
		throw new IllegalStateException("you must call setSelectedList on an ItemSetMultiSelection");
	}

	public void setSelectedList(Item... items) {
		selected.clear();
		for (Item item : items) {
			selected.add(item);
		}
	}

	public void setSelectedList(ArrayList<Item> selectedItems) {
		setSelectedList(selectedItems.toArray(new Item[selectedItems.size()]));
	}

	@Override
	public Item getSelected() {
		throw new IllegalStateException("you must call getSelectedList on an ItemSetMultiSelection");
	}

	public ArrayList<Item> getSelectedList() {
		return selected;
	}
}
