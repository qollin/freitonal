package de.cr.freitonal.shared.models;

import com.google.gwt.user.client.rpc.IsSerializable;

public class VolatileItem implements IsSerializable {
	private String value;

	protected VolatileItem() {

	}

	public VolatileItem(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		return value.equals(((VolatileItem) other).value);
	}

	@Override
	public String toString() {
		return "VOLATILE: " + value;
	}

}
