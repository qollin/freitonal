package de.cr.freitonal.tests.client.widgets.event;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.event.SearchFieldChangedEvent;
import de.cr.freitonal.client.widgets.event.DFA;
import de.cr.freitonal.client.widgets.event.State;

public class DFATest {
	private HandlerManager eventBus;
	private State a;
	private State b;
	private DFA dfa;

	@Before
	public void setUp() {
		eventBus = new HandlerManager(null);
		a = new State("A");
		b = new State("B");
		dfa = new DFA(a, eventBus);
	}

	@Test
	public void testSimpleDFA() {
		dfa.addTransition(SearchFieldChangedEvent.TYPE, a, b);
		assertTrue(dfa.getCurrentState() == a);
		eventBus.fireEvent(new SearchFieldChangedEvent(null));
		assertTrue(dfa.getCurrentState() == b);
		try {
			eventBus.fireEvent(new SearchFieldChangedEvent(null));
			fail("there are no transitions away from B");
		} catch (IllegalStateException e) {
		}
	}

	@Test
	public void testDFAWithTwoTransitions() {
		dfa.addTransition(SearchFieldChangedEvent.TYPE, a, b);
		dfa.addTransition(SearchFieldChangedEvent.TYPE, b, a);
		assertTrue("initial state is not a", dfa.getCurrentState() == a);
		eventBus.fireEvent(new SearchFieldChangedEvent(null));
		assertTrue("could not get from a -> b", dfa.getCurrentState() == b);
		eventBus.fireEvent(new SearchFieldChangedEvent(null));
		assertTrue("could not get from b -> a", dfa.getCurrentState() == a);
	}
}
