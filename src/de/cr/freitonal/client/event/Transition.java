/**
 * 
 */
package de.cr.freitonal.client.event;

class Transition {
	private final String from, to;
	private final Trigger trigger;

	public Transition(String from, Trigger trigger, String to) {
		this.from = from;
		this.trigger = trigger;
		this.to = to;
	}

	@Override
	public String toString() {
		return from + " " + trigger + " " + to;
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