package de.cr.freitonal.client.models;

import java.util.ArrayList;

import de.cr.freitonal.shared.models.Item;

public class MultiSourceItemSet extends ItemSet {

	private ArrayList<ItemSet> itemSets;

	private MultiSourceItemSet() {
	}

	public MultiSourceItemSet(ItemSet... itemSets) {
		this.itemSets = new ArrayList<ItemSet>();
		for (ItemSet itemSet : itemSets) {
			checkAndTransferSelection(itemSet);
			this.itemSets.add(itemSet);
		}
	}

	private void checkAndTransferSelection(ItemSet itemSet) {
		if (itemSet.getSelected() != null) {
			if (getSelected() == null) {
				setSelected(itemSet.getSelected());
			} else {
				throw new IllegalArgumentException("it makes no sense if more than itemSet has a selection");
			}
		}
	}

	@Override
	public ArrayList<Item> getItems() {
		ArrayList<Item> items = new ArrayList<Item>();
		for (ItemSet itemSet : itemSets) {
			items.addAll(itemSet.getItems());
		}

		return items;
	}

	public ItemSet getSelectedSource() {
		for (ItemSet itemSet : itemSets) {
			if (itemSet.contains(getSelected())) {
				return itemSet;
			}
		}

		throw new IllegalStateException("it must not be that a selected item cannot be found in of the itemSets");
	}
}
