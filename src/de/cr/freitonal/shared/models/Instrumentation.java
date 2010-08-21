package de.cr.freitonal.shared.models;

import java.util.ArrayList;

public class Instrumentation extends VolatileInstrumentation {
	private final String id;

	public Instrumentation(String id, String nickname, ArrayList<Item> instruments) {
		super(nickname, instruments);
		this.id = id;
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
}
