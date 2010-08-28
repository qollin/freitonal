package de.cr.freitonal.unittests.client.event.dfa;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.event.dfa.AbstractTransitionAction;
import de.cr.freitonal.client.event.dfa.DFA;
import de.cr.freitonal.client.event.dfa.EqualsTriggerParam;
import de.cr.freitonal.client.event.dfa.Trigger;

public class ADFAWithATriggerParamShould {
	private DFA dfa;
	final ArrayList<String> trace = new ArrayList<String>();

	@Before
	public void createFreshWithTriggerParam() {
		dfa = new DFA();
		trace.clear();

		dfa.addTransitionWithTriggerParam("start", "trigger", new EqualsTriggerParam("param"), "finish", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				trace.add("onTransition");
			}

			@Override
			public void onTransition(Object[] parameters) {
				for (Object parameter : parameters) {
					trace.add(parameter.toString());
				}
			}
		});

		dfa.start("start");
	}

	@Test
	public void TransitionWhenTheParamMatches() {
		dfa.transition(new Trigger("trigger", "param"));
		assertEquals("finish", dfa.getState());
	}

	@Test
	public void ExecuteTheGivenAction() {
		dfa.transition(new Trigger("trigger", "param"));
		assertEquals(1, trace.size());
		assertEquals("onTransition", trace.get(0));
	}

	@Test
	public void PassTheTransitionParameterToTheAction() {
		dfa.transition(new Trigger("trigger", "param"), "actionParam");
		assertEquals(1, trace.size());
		assertEquals("actionParam", trace.get(0));
	}
}
