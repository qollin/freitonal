package de.cr.freitonal.unittests.client.widgets.instrumentation;

import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.createInstrumentationSet;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.widgets.instrumentation.InstrumentationPresenter;

public class AnInstrumentationPresenterInMainModeShould extends InstrumentationPresenterTest {
	private InstrumentationPresenter instrumentationPresenter;

	@Before
	public void setupInstrumentationPresenterInMainState() {
		instrumentationPresenter = new InstrumentationPresenter(eventBus, view);
		instrumentationPresenter.setInstrumentations(createInstrumentationSet(2));
	}

	@Test
	public void StayInMainStateWhenGivenANewInstrumentationSetWithTwoInstrumentations() {
		instrumentationPresenter.setInstrumentations(createInstrumentationSet(2));
		assertEquals("Main", instrumentationPresenter.getDFA().getState());
	}

	@Test
	public void SwitchToDependendViewModeWhenGivenAnInstrumentationSetWithOneInstrumentation() {
		instrumentationPresenter.setInstrumentations(createInstrumentationSet(1));
		assertEquals("DependendView", instrumentationPresenter.getDFA().getState());
	}

}
