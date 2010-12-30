package de.cr.freitonal.unittests.client.event.dfa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.event.dfa.AbstractTransitionAction;
import de.cr.freitonal.client.event.dfa.DeltaMap;
import de.cr.freitonal.client.event.dfa.Transition;
import de.cr.freitonal.client.event.dfa.TransitionAction;
import de.cr.freitonal.client.event.dfa.Trigger;
import de.cr.freitonal.client.event.dfa.TriggerParam;

public class ADeltaMapShould {
	private DeltaMap delta;
	private TriggerParam triggerParams;
	private Object[] transitionParams;
	private final ArrayList<String> trace = new ArrayList<String>();

	@Before
	public void createFreshDeltaMap() {
		delta = new DeltaMap();
		trace.clear();
	}

	@Before
	public void createTriggerParams() {
		transitionParams = new Object[] { "X" };
		triggerParams = new TriggerParam() {
			public boolean matches(Object[] transitionParameters) {
				return transitionParameters[0].equals("X");
			}
		};
	}

	@Test
	public void AcceptSimpleTransitions() {
		delta.addTransition("start", "trigger", "finish");
		assertEquals("finish", delta.getState("start", new Trigger("trigger")));
	}

	@Test
	public void ReturnTheAssociatedAction() {
		TransitionAction action = new AbstractTransitionAction() {
		};
		delta.addTransition("start", "trigger", "finish", action);
		assertEquals(action, delta.getAction("start", new Trigger("trigger")));
	}

	@Test
	public void AcceptTransitionsWithTriggerParams() {
		delta.addTransition("start", "trigger", triggerParams, "finish");
		String nextState = delta.getState("start", new Trigger("trigger", transitionParams));
		assertEquals("finish", nextState);
	}

	@Test
	public void AcceptTransitionsWithTriggerParamsAndTransitionAction() {
		TransitionAction action = new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				trace.add("onTransition");
			}
		};
		delta.addTransition("start", "trigger", new Transition(triggerParams, "finish", action));
		String nextState = delta.getState("start", new Trigger("trigger", transitionParams));
		TransitionAction returnedAction = delta.getAction("start", new Trigger("trigger", transitionParams));
		assertEquals("finish", nextState);
		assertSame(action, returnedAction);
	}

	@Test
	public void ContainAnyStateThatWasAddedToIt() {
		delta.addTransition("from", "trigger", null, "to");
		assertTrue(delta.containsState("from"));
		assertTrue(delta.containsState("to"));
	}
}
