package de.cr.freitonal.shared.models;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.IsSerializable;

import de.cr.freitonal.shared.renderer.InstrumentationRenderer;

public class Instrumentation extends VolatileInstrumentation implements UID, IsSerializable {
	private String id;
	private HashMap<Item, Integer> instrumentCounts;

	@SuppressWarnings("unused")
	private Instrumentation() {
		//needed because of GWT serialization
	}

	public Instrumentation(String id, String nickname, Item... instruments) {
		super(nickname, instruments);
		this.id = id;
		instrumentCounts = new HashMap<Item, Integer>();
	}

	public Instrumentation(String id, String nickname, ArrayList<Item> instruments) {
		super(nickname, instruments);
		this.id = id;
		instrumentCounts = new HashMap<Item, Integer>();
	}

	public Instrumentation(String id, VolatileInstrumentation vol) {
		this(id, vol.getNickname(), vol.getInstruments());
	}

	public String getID() {
		return id;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		return id.equals(((Instrumentation) other).id);
	};

	@Override
	public String toString() {
		return new InstrumentationRenderer().render(this);
	}

	public void setInstrumentCount(Item instrument, int count) {
		instrumentCounts.put(instrument, count);
	}

	public int getInstrumentCount(Item instrument) {
		if (instrumentCounts.containsKey(instrument)) {
			return instrumentCounts.get(instrument);
		} else {
			return 1;
		}
	}
}
