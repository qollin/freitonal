package de.cr.freitonal.shared.models;

import com.google.gwt.user.client.rpc.IsSerializable;

public class VolatileCatalog implements IsSerializable {
	private Item catalogName;
	private String ordinal;

	/**
	 * needed because of GWT serialization: DO NO USE
	 */
	protected VolatileCatalog() {
	}

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

	public Item getCatalogNumber() {
		throw new IllegalStateException("a volatile catalog does not have number yet. Use getOrdinal()");
	}
}
