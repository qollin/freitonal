package de.cr.freitonal.unittests.client.widgets.instrumentation;

import static de.cr.freitonal.usertests.client.test.data.TestData.createInstrumentationSet;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.widgets.instrumentation.InstrumentationPresenter;

public class AnInstrumentationPresenterShould extends InstrumentationPresenterTest {
	private InstrumentationPresenter instrumentationPresenter;

	@Before
	public void setupInstrumentationPresenter() {
		instrumentationPresenter = new InstrumentationPresenter(eventBus, view);
	}

	@Test
	public void GoIntoMainStateWhenGivenTheInitialInstrumentationSet() {
		instrumentationPresenter.setInstrumentations(createInstrumentationSet(2));
		assertEquals("Main", instrumentationPresenter.getDFA().getState());
	}
}
