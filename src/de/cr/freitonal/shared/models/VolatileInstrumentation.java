package de.cr.freitonal.shared.models;

import java.util.ArrayList;

public class VolatileInstrumentation {
	private ArrayList<Item> instruments = new ArrayList<Item>();
	private final String nickname;

	public VolatileInstrumentation(String nickname, ArrayList<Item> instruments) {
		this.nickname = nickname;
		this.instruments = instruments;
	}

	public String getNickname() {
		return nickname;
	}

	public ArrayList<Item> getInstruments() {
		return instruments;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		return nickname.equals(((VolatileInstrumentation) other).nickname) && instruments.equals(((VolatileInstrumentation) other).instruments);
	};
}