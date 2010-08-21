package de.cr.freitonal.client.models;

import de.cr.freitonal.shared.models.Item;

public class Catalog {
	public Item name;
	public Item number;

	public Catalog(Item name, Item number) {
		this.name = name;
		this.number = number;
	}

	@Override
	public boolean equals(Object obj) {
		Catalog other = (Catalog) obj;
		if (other == null) {
			return false;
		}

		if (name == null && other.name != null) {
			return false;
		}
		if (number == null && other.number != null) {
			return false;
		}
		if (other.name == null && name != null) {
			return false;
		}
		if (other.number == null && number != null) {
			return false;
		}

		return name.equals(other.name) && number.equals(other.number);
	}
}
