package de.cr.freitonal.shared.models;

public class VolatileItem {
	private String value;

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
