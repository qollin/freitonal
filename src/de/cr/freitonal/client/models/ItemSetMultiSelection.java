package de.cr.freitonal.client.models;

import java.util.ArrayList;

import de.cr.freitonal.shared.models.Item;

public class ItemSetMultiSelection extends ItemSet {
	private ArrayList<Item> selected;

	private ItemSetMultiSelection() {
	}

	public ItemSetMultiSelection(Item... items) {
		super(items);
		selected = new ArrayList<Item>();
	}

	public ItemSetMultiSelection(ArrayList<Item> items) {
		super(items);
		selected = new ArrayList<Item>();
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
