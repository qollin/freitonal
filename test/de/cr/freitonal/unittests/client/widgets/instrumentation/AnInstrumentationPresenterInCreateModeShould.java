package de.cr.freitonal.unittests.client.widgets.instrumentation;

import static de.cr.freitonal.client.event.DisplayMode.Create;
import static de.cr.freitonal.unittests.client.test.data.TestData.Piano;
import static de.cr.freitonal.unittests.client.test.data.TestData.createInstrumentationSet;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.widgets.instrumentation.InstrumentationPresenter;

public class AnInstrumentationPresenterInCreateModeShould extends InstrumentationPresenterTest {
	private InstrumentationPresenter instrumentationPresenter;

	@Before
	public void setupInstrumentationPresenterInCreateMode() {
		instrumentationPresenter = new InstrumentationPresenter(eventBus, view);
		instrumentationPresenter.setInstrumentations(createInstrumentationSet(2)); //go from Initial to Main
		instrumentationPresenter.getDFA().transition("instrumentSelected"); // go from Main to Search
		instrumentationPresenter.setDisplayMode(Create); // go from Search to Create

		trace.clear();
	}

	@Test
	public void NotTriggerASearchWhenAnInstrumentIsSelected() {
		instrumentationPresenter.getInstrumentPresenter(0).fireOnNewItemSelected(Piano);
		assertFalse(trace.contains("search"));
	}

	@Test
	public void ShouldAddAnExtraDropdownWhenTheAddInstrumentButtonIsClicked() {
		instrumentationPresenter.fireAddInstrumentButtonClicked_TEST();
		assertEquals("the instrumentation presenter should show 2 dropdown boxes", 2, instrumentationPresenter.getNumberOfInstrumentPresenters());

	}

	@Test
	public void ShouldShowAllInstrumentsInTheNewDropdownWhenAddInstrumentButtonIsClicked() {
		instrumentationPresenter.fireAddInstrumentButtonClicked_TEST();
		assertEquals("the second dropdown box should show all instruments", 2, instrumentationPresenter.getInstrumentPresenter(1).getItemCount());
	}

}
