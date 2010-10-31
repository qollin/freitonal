package de.cr.freitonal.unittests.client.event.dfa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.event.dfa.AbstractTransitionAction;
import de.cr.freitonal.client.event.dfa.DFA;
import de.cr.freitonal.client.event.dfa.Trigger;
import de.cr.freitonal.client.event.dfa.TriggerParam;

public class ADFAShould {
	private final ArrayList<String> trace = new ArrayList<String>();
	private DFA dfa;
	private TriggerParam alwaysTrue;

	@Before
	public void createAndPrepareDFA() {
		dfa = new DFA();
		alwaysTrue = new TriggerParam() {
			public boolean matches(Object[] transitionParameters) {
				return true;
			}
		};
	}

	@Before
	public void emptyTrace() {
		trace.clear();
	}

	@Test
	public void AcceptTransitionsWithTriggerParamsAndMultipleSourceStates() {
		dfa.addTransitionWithTriggerParam(new String[] { "a", "b" }, "trigger", alwaysTrue, "c");
		dfa.start("a");
		dfa.transition(new Trigger("trigger", null));
		assertEquals("c", dfa.getState());

		dfa.start("b");
		dfa.transition(new Trigger("trigger", null));
		assertEquals("c", dfa.getState());
	}

	@Test
	public void AcceptTransitionsWithTriggerParamsAndActionsAndMultipleSourceStates() {
		dfa.addTransitionWithTriggerParam(new String[] { "a", "b" }, "trigger", alwaysTrue, "c", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				trace.add("onTransition");
			}
		});

		dfa.start("a");
		dfa.transition(new Trigger("trigger", null));
		assertEquals("c", dfa.getState());
		assertTrue(trace.contains("onTransition"));

		trace.clear();
		dfa.start("b");
		dfa.transition(new Trigger("trigger", null));
		assertEquals("c", dfa.getState());
		assertTrue(trace.contains("onTransition"));
	}

}
