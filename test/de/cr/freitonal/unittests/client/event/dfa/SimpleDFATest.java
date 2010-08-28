package de.cr.freitonal.unittests.client.event.dfa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.event.dfa.AbstractTransitionAction;
import de.cr.freitonal.client.event.dfa.DFA;

public class SimpleDFATest {

	private DFA dfa;
	final ArrayList<String> trace = new ArrayList<String>();

	@Before
	public void setUp() {
		dfa = new DFA();
		trace.clear();
	}

	@Test
	public void testTwoStates() {
		dfa.addTransition("A", "transition1", "B");
		dfa.start("A");
		assertEquals("A", dfa.getState());
		dfa.transition("transition1");
		assertEquals("B", dfa.getState());
	}

	@Test
	public void testTransitionAction() {
		dfa.addTransition("A", "transition1", "B", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				trace.add("onTransition");
			}
		});
		dfa.start("A");
		dfa.transition("transition1");
		assertEquals("B", dfa.getState());
		assertEquals(1, trace.size());
		assertEquals("onTransition", trace.get(0));
	}

	@Test
	public void testTransitionActionWithParameter() {
		dfa.addTransition("A", "transition1", "B", new AbstractTransitionAction() {
			@Override
			public void onTransition(Object[] parameters) {
				trace.add(parameters[0].getClass().getSimpleName());
			}
		});
		dfa.start("A");
		ArrayList<String> parameter = new ArrayList<String>();
		dfa.transition("transition1", parameter);
		assertEquals("ArrayList", trace.get(0));
	}

	@Test
	public void testTransitionFromTerminalState() {
		dfa.addTransition("A", "t1", "B");
		dfa.start("A");
		dfa.transition("t1");
		try {
			dfa.transition("t2");
			fail();
		} catch (IllegalStateException e) {

		}
	}
}
