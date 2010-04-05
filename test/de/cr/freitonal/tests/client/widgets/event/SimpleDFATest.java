package de.cr.freitonal.tests.client.widgets.event;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.widgets.event.SimpleDFA;

public class SimpleDFATest {

	private SimpleDFA dfa;

	@Before
	public void setUp() {
		dfa = new SimpleDFA();
	}

	@Test
	public void testTwoStates() {
		dfa.addTransition("A", "transition1", "B");
		dfa.start("A");
		assertEquals("A", dfa.getState());
		dfa.transition("transition1");
		assertEquals("B", dfa.getState());
	}

}
