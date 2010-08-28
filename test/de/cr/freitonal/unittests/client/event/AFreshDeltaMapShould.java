package de.cr.freitonal.unittests.client.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.event.AbstractTransitionAction;
import de.cr.freitonal.client.event.DeltaMap;
import de.cr.freitonal.client.event.TransitionAction;
import de.cr.freitonal.client.event.Trigger;
import de.cr.freitonal.client.event.TriggerParam;

public class AFreshDeltaMapShould {
	private DeltaMap delta;

	@Before
	public void createFreshDeltaMap() {
		delta = new DeltaMap();
	}

	@Test
	public void AcceptSimpleTransitions() {
		delta.addTransition("start", "trigger", "finish");
		assertEquals("finish", delta.getState("start", "trigger"));
	}

	@Test
	public void ReturnTheAssociatedAction() {
		TransitionAction action = new AbstractTransitionAction() {
		};
		delta.addTransition("start", "trigger", "finish", action);
		assertEquals(action, delta.getAction("start", "trigger"));
	}

	@Test
	public void AcceptTransitionsWithTriggerParams() {
		final Object[] transitionParams = new Object[] { "X" };
		TriggerParam triggerParam = new TriggerParam() {
			public boolean matches(Object[] transitionParameters) {
				return transitionParameters[0].equals("X");
			}
		};
		delta.addTransition("start", "trigger", triggerParam, "finish");
		assertEquals("finish", delta.getState("start", new Trigger("trigger", transitionParams)));
	}

	@Test
	public void ContainAnyStateThatWasAddedToIt() {
		delta.addTransition("from", "trigger", null, "to");
		assertTrue(delta.containsState("from"));
		assertTrue(delta.containsState("to"));
	}
}
