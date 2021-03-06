package de.cr.freitonal.shared.models;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Item extends VolatileItem implements UID, IsSerializable {
	public static final Item NOT_AVAILABLE = new Item("<n/a>");
	public static final Item NULL_ITEM = new Item("<null>");
	private String id;

	@SuppressWarnings("unused")
	private Item() {
		//needed because of GWT Serialization
	}

	//only used to create constants
	private Item(String value) {
		super(value);
		id = "";
	}

	public Item(String id, String value) {
		super(value);
		if (id.equals("")) {
			throw new IllegalArgumentException("an Item ID must not be the empty String");
		}
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
