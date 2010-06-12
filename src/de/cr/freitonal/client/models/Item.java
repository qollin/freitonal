package de.cr.freitonal.client.models;

public class Item {
	public static final Item Empty = new Item("", "");
	public final String id;
	public final String value;

	public Item(String id, String value) {
		if (id == null || value == null) {
			throw new IllegalArgumentException("both arguments to the Item constructor must be non-null");
		}
		this.id = id;
		this.value = value;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		return id.equals(((Item) other).id) && value.equals(((Item) other).value);
	}

	@Override
	public String toString() {
		return id + ": " + value;
	}
}
