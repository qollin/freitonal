package de.cr.freitonal.shared.models;

public class VolatileCatalog {
	private final Item catalogName;
	private final String ordinal;

	public VolatileCatalog(Item catalogName, String ordinal) {
		this.catalogName = catalogName;
		this.ordinal = ordinal;
	}

	public Item getCatalogName() {
		return catalogName;
	}

	public String getOrdinal() {
		return ordinal;
	}

}
