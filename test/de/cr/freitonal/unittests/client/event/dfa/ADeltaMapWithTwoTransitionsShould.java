package de.cr.freitonal.unittests.client.event.dfa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.event.dfa.DeltaMap;
import de.cr.freitonal.client.event.dfa.EqualsTriggerParam;
import de.cr.freitonal.client.event.dfa.Transition;
import de.cr.freitonal.client.event.dfa.Trigger;

public class ADeltaMapWithTwoTransitionsShould {

	private DeltaMap delta;

	@Before
	public void createDeltaMapWithTwoTransitions() {
		delta = new DeltaMap();

		delta.addTransition("start", "trigger", "finish");
		delta.addTransition("start", "trigger", new EqualsTriggerParam("param"), "finish");
	}

	@Test
	public void ReturnTheTransitionsInTheCorrectOrder() {
		ArrayList<Transition> transitions = delta.getTransitions("start", new Trigger("trigger"));
		assertEquals(2, transitions.size());
		assertNotNull(transitions.get(0).getTriggerParam());
		assertNull(transitions.get(1).getTriggerParam());
	}
}
