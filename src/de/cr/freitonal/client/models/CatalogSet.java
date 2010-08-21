package de.cr.freitonal.client.models;

import java.util.ArrayList;

import de.cr.freitonal.shared.models.Item;

public class CatalogSet {

	private final ItemSet names;
	private final ItemSet numbers;

	public CatalogSet(ItemSet names, ItemSet numbers) {
		this.names = names;
		this.numbers = numbers;
	}

	public CatalogSet(ArrayList<Item> names, ArrayList<Item> numbers) {
		this(new ItemSet(names), new ItemSet(numbers));
	}

	/**
	 * @return the names
	 */
	public ItemSet getNames() {
		return names;
	}

	/**
	 * @return the numbers
	 */
	public ItemSet getNumbers() {
		return numbers;
	}

	public void copyItemSelectionTo(CatalogSet target) {
		names.copyItemSelectionTo(target.names);
		numbers.copyItemSelectionTo(target.numbers);
	}
}
