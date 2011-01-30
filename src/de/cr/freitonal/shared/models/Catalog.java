package de.cr.freitonal.shared.models;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Catalog extends VolatileCatalog implements IsSerializable {
	private String id;

	@SuppressWarnings("unused")
	private Catalog() {
		//needed because of GWT serialization
	}

	public Catalog(String id, Item catalogName, String ordinal) {
		super(catalogName, ordinal);
		this.id = id;
	}

	public Catalog(Item name, Item number) {
		this(number.getID(), name, number.getValue());
	}

	public Catalog(String id, VolatileCatalog catalog) {
		this(id, catalog.getCatalogName(), catalog.getOrdinal());
	}

	public String getID() {
		return id;
	}

	@Override
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
