package de.cr.freitonal.unittests.client.widgets.instrumentation;

import static de.cr.freitonal.client.event.DisplayMode.Create;
import static de.cr.freitonal.usertests.client.test.data.TestData.createInstrumentationSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.event.DisplayMode;
import de.cr.freitonal.client.models.InstrumentationSet;
import de.cr.freitonal.client.widgets.instrumentation.InstrumentationPresenter;
import de.cr.freitonal.shared.models.Item;

public class AnInstrumentationPresenterInDependendViewModeShould extends InstrumentationPresenterTest {
	private InstrumentationPresenter instrumentationPresenter;
	private InstrumentationSet oneInstrumentation;

	@Before
	public void setupInstrumentationPresenterInDependendViewMode() {
		instrumentationPresenter = new InstrumentationPresenter(eventBus, view);
		//go to main mode
		instrumentationPresenter.setInstrumentations(createInstrumentationSet(2));

		oneInstrumentation = createInstrumentationSet(1);
		//go to dependend view mode
		instrumentationPresenter.setInstrumentations(oneInstrumentation);
	}

	@Test
	public void ShowTheGivenInstrumentation() {
		ArrayList<Item> items = extractDisplayItem(oneInstrumentation);
		assertTrue(trace.toString(), trace.contains("setItems:" + items));
	}

	@Test
	public void HideTheAddInstrumentationButton() {
		assertTrue(trace.toString(), trace.contains("setAddInstrumentButtonVisible:" + false));
	}

	@Test
	public void GoToSelectModeWhenSetInstrumentationsIsCalled() {
		instrumentationPresenter.setInstrumentations(createInstrumentationSet(2));
		assertEquals(DisplayMode.Select, instrumentationPresenter.getDisplayMode());
	}

	@Test
	public void ShowASingleDropDownBoxAfterSetInstrumentationsIsCalled() {
		instrumentationPresenter.setInstrumentations(createInstrumentationSet(2));
		assertEquals(1, instrumentationPresenter.getNumberOfInstrumentPresenters());
		assertEquals(DisplayMode.Select, instrumentationPresenter.getInstrumentPresenter(0).getDisplayMode());
	}

	@Test
	public void GoToCreateModeWhenSetDisplayModeWithCreateIsCalled() {
		instrumentationPresenter.setDisplayMode(Create);
		assertEquals(DisplayMode.Create, instrumentationPresenter.getDisplayMode());
	}

	private ArrayList<Item> extractDisplayItem(InstrumentationSet instrumentationSet) {
		String instrumentationTitle = instrumentationSet.getInstrumentations().get(0).toString();
		String instrumentationID = instrumentationSet.getInstrumentations().get(0).getID();
		Item displayItem = new Item(instrumentationID, instrumentationTitle);
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(displayItem);

		return items;
	}

}
