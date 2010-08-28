package de.cr.freitonal.unittests.client.event;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.event.AbstractTransitionAction;
import de.cr.freitonal.client.event.EqualsTriggerParam;
import de.cr.freitonal.client.event.SimpleDFA;
import de.cr.freitonal.client.event.Trigger;

public class ADFAWithATriggerParamShould {
	private SimpleDFA dfa;
	final ArrayList<String> trace = new ArrayList<String>();

	@Before
	public void createFreshWithTriggerParam() {
		dfa = new SimpleDFA();
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
		dfa.transitionWithTriggerParam(new Trigger("trigger", "param"));
		assertEquals("finish", dfa.getState());
	}

	@Test
	public void ExecuteTheGivenAction() {
		dfa.transitionWithTriggerParam(new Trigger("trigger", "param"));
		assertEquals(1, trace.size());
		assertEquals("onTransition", trace.get(0));
	}

	@Test
	public void PassTheTransitionParameterToTheAction() {
		dfa.transitionWithTriggerParam(new Trigger("trigger", "param"), "actionParam");
		assertEquals(1, trace.size());
		assertEquals("actionParam", trace.get(0));
	}
}
