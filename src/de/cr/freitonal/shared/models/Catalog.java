package de.cr.freitonal.shared.models;

public class Catalog extends VolatileCatalog {
	private final String id;

	public Catalog(String id, Item catalogName, String ordinal) {
		super(catalogName, ordinal);
		this.id = id;
	}

	public Catalog(Item name, Item number) {
		this(number.getID(), name, number.getValue());
	}

	public String getID() {
		return id;
	}

	public Item getCatalogNumber() {
		return new Item(id, getOrdinal());
	}

	@Override
	public boolean equals(Object o) {
		Catalog other = (Catalog) o;
		return id.equals(other.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
