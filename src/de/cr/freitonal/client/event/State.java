package de.cr.freitonal.client.event;

public class State {
	private final String name;

	public State(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

}
