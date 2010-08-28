package de.cr.freitonal.unittests.client.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.event.EqualsTriggerParam;

public class AEqualsTriggerParamShould {

	private EqualsTriggerParam equalsTriggerParam;

	@Before
	public void createFreshEqualsTriggerParam() {
		equalsTriggerParam = new EqualsTriggerParam("param");
	}

	@Test
	public void HandleNullParametersForTheMatchesMethod() {
		try {
			equalsTriggerParam.matches(null);
		} catch (Throwable e) {
			fail();
		}
	}

	@Test
	public void ReturnFalseOnTheMatchesMethodWithNullParamater() {
		assertFalse(equalsTriggerParam.matches(null));
	}
}
