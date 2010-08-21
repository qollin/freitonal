package de.cr.freitonal.shared.models;

public class Item extends VolatileItem {
	private final String id;

	public Item(String id, String value) {
		super(value);
		this.id = id;
	}

	public Item(String id, VolatileItem vol) {
		this(id, vol.getValue());
	}

	public String getID() {
		return id;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		return id.equals(((Item) other).id);
	}

	@Override
	public String toString() {
		return id + ": " + getValue();
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}