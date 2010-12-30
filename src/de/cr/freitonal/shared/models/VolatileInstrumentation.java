package de.cr.freitonal.shared.models;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gwt.user.client.rpc.IsSerializable;

public class VolatileInstrumentation implements IsSerializable {
	protected ArrayList<Item> instruments = new ArrayList<Item>();
	protected String nickname;

	protected VolatileInstrumentation() {
	}

	public VolatileInstrumentation(String nickname, ArrayList<Item> instruments) {
		this.nickname = nickname;
		this.instruments = instruments;
	}

	public VolatileInstrumentation(String nickname, Item... instruments) {
		this.nickname = nickname;
		this.instruments = new ArrayList<Item>(Arrays.asList(instruments));
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
