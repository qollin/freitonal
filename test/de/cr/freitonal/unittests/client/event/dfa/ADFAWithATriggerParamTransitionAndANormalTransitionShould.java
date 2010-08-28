package de.cr.freitonal.unittests.client.event.dfa;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.event.dfa.AbstractTransitionAction;
import de.cr.freitonal.client.event.dfa.DFA;
import de.cr.freitonal.client.event.dfa.EqualsTriggerParam;
import de.cr.freitonal.client.event.dfa.Trigger;

public class ADFAWithATriggerParamTransitionAndANormalTransitionShould {
	private final ArrayList<String> trace = new ArrayList<String>();
	private DFA dfa;

	@Before
	public void createDFA() {
		dfa = new DFA();
		trace.clear();

		dfa.addTransition("start", "trigger", "finish", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				trace.add("transition 1");
			}
		});
		dfa.addTransitionWithTriggerParam("start", "trigger", new EqualsTriggerParam("param"), "finish", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				trace.add("transition 2");
			}
		});

		dfa.start("start");
	}

	@Test
	public void ExecuteTheActionForTheNormalTransitionWhenTransitionIsCalled() {
		dfa.transition("trigger");

		assertEquals("finish", dfa.getState());
		assertEquals(1, trace.size());
		assertEquals("transition 1", trace.get(0));
	}

	@Test
	public void ExecuteTheActionForTheTriggerParamTransitionWhenTransitionWithTriggerParamIsCalled() {
		dfa.transition(new Trigger("trigger", "param"));

		assertEquals("finish", dfa.getState());
		assertEquals(1, trace.size());
		assertEquals("transition 2", trace.get(0));

	}
}
