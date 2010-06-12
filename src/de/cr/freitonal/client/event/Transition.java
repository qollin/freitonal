/**
 * 
 */
package de.cr.freitonal.client.event;

class Transition {
	private final String from, to, transition;

	public Transition(String from, String transition, String to) {
		this.from = from;
		this.transition = transition;
		this.to = to;
	}

	@Override
	public String toString() {
		return from + " " + transition + " " + to;
	}

	@Override
	public boolean equals(Object obj) {
		return toString().equals(obj.toString());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}