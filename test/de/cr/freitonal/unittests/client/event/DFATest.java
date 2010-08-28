package de.cr.freitonal.unittests.client.event;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.event.OldDFA;
import de.cr.freitonal.client.event.SearchFieldChangedEvent;
import de.cr.freitonal.client.event.State;

public class DFATest {
	private HandlerManager eventBus;
	private State a;
	private State b;
	private OldDFA oldDFA;

	@Before
	public void setUp() {
		eventBus = new HandlerManager(null);
		a = new State("A");
		b = new State("B");
		oldDFA = new OldDFA(a, eventBus);
	}

	@Test
	public void testSimpleDFA() {
		oldDFA.addTransition(SearchFieldChangedEvent.TYPE, a, b);
		assertTrue(oldDFA.getCurrentState() == a);
		eventBus.fireEvent(new SearchFieldChangedEvent());
		assertTrue(oldDFA.getCurrentState() == b);
		try {
			eventBus.fireEvent(new SearchFieldChangedEvent());
			fail("there are no transitions away from B");
		} catch (IllegalStateException e) {
		}
	}

	@Test
	public void testDFAWithTwoTransitions() {
		oldDFA.addTransition(SearchFieldChangedEvent.TYPE, a, b);
		oldDFA.addTransition(SearchFieldChangedEvent.TYPE, b, a);
		assertTrue("initial state is not a", oldDFA.getCurrentState() == a);
		eventBus.fireEvent(new SearchFieldChangedEvent());
		assertTrue("could not get from a -> b", oldDFA.getCurrentState() == b);
		eventBus.fireEvent(new SearchFieldChangedEvent());
		assertTrue("could not get from b -> a", oldDFA.getCurrentState() == a);
	}
}
