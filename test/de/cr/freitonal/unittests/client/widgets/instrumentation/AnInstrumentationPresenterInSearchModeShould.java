package de.cr.freitonal.unittests.client.widgets.instrumentation;

import static de.cr.freitonal.client.event.DisplayMode.Create;
import static de.cr.freitonal.unittests.client.test.data.TestData.Piano;
import static de.cr.freitonal.unittests.client.test.data.TestData.createInstrumentationSet;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.event.PiecePlusInstrumentationTypeSelectedEvent;
import de.cr.freitonal.client.widgets.instrumentation.InstrumentationPresenter;
import de.cr.freitonal.shared.models.VolatileInstrumentation;

public class AnInstrumentationPresenterInSearchModeShould extends InstrumentationPresenterTest {
	private InstrumentationPresenter instrumentationPresenter;

	@Before
	public void setupInstrumentationPresenterInSearchState() {
		instrumentationPresenter = new InstrumentationPresenter(eventBus, view);
		instrumentationPresenter.setInstrumentations(createInstrumentationSet(2)); //go from Initial to Main
		instrumentationPresenter.getDFA().transition("instrumentSelected"); // go from Main to Search
	}

	@Test
	public void StayInSearchStateWhenGivenANewInstrumentationSet() {
		instrumentationPresenter.setInstrumentations(createInstrumentationSet(2));
		assertEquals("Search", instrumentationPresenter.getDFA().getState());
	}

	@Test
	public void CreateASecondDropdownBoxWhenASearchIsTriggered() {
		//trigger a search:
		instrumentationPresenter.getInstrumentPresenter(0).fireOnNewItemSelected(Piano);

		//as a result of the search new instrumentations will be set:
		instrumentationPresenter.setInstrumentations(createInstrumentationSet(2));

		assertEquals("There should be 2 dropdown boxes", 2, instrumentationPresenter.getNumberOfInstrumentPresenters());
		assertEquals("The first dropdown box should have 1 item", 1, instrumentationPresenter.getInstrumentPresenter(0).getItemCount());
		assertEquals("The first dropdown box should have searched for instrument selected", Piano, instrumentationPresenter.getInstrumentPresenter(0)
				.getSelectedItem());

		assertEquals("The second dropdown box should have two items", 2, instrumentationPresenter.getInstrumentPresenter(1).getItemCount());
		VolatileInstrumentation instrumentation = instrumentationPresenter.getSelectedItem();
		assertEquals("The volatile instrumentation should consist of one instrument", 1, instrumentation.getInstruments().size());
	}

	@Test
	public void ShowOnlyOneDropdownBoxWhenCreateModeIsEntered() {
		//trigger a search:
		instrumentationPresenter.getInstrumentPresenter(0).fireOnNewItemSelected(Piano);

		//as a result of the search new instrumentations will be set:
		instrumentationPresenter.setInstrumentations(createInstrumentationSet(2));

		instrumentationPresenter.setDisplayMode(Create);
		assertEquals(1, instrumentationPresenter.getNumberOfInstrumentPresenters());
	}

	@Test
	public void ShouldShowAllInstrumentsWhenCreateModeIsEntered() {
		//trigger a search:
		instrumentationPresenter.getInstrumentPresenter(0).fireOnNewItemSelected(Piano);

		//as a result of the search new instrumentations will be set:
		instrumentationPresenter.setInstrumentations(createInstrumentationSet(2));

		instrumentationPresenter.setDisplayMode(Create);
		assertEquals(2, instrumentationPresenter.getInstrumentPresenter(0).getItemCount());
	}

	@Test
	public void BeDisabledWhenAPiecePlusInstrumentationTypeSelectedEventIsSent() {
		assertTrue(instrumentationPresenter.isEnabled());

		eventBus.fireEvent(new PiecePlusInstrumentationTypeSelectedEvent());

		assertFalse("after an piecePlusInstrumentationType was selected, the instrumentation should be disabled",
					instrumentationPresenter.isEnabled());
	}

}
