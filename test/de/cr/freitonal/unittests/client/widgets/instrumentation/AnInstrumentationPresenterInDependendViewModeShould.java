package de.cr.freitonal.unittests.client.widgets.instrumentation;

import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.createInstrumentationSet;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import de.cr.freitonal.client.models.InstrumentationSet;
import de.cr.freitonal.client.widgets.instrumentation.InstrumentationPresenter;
import de.cr.freitonal.shared.models.Item;

public class AnInstrumentationPresenterInDependendViewModeShould extends InstrumentationPresenterTest {
	private InstrumentationPresenter instrumentationPresenter;

	@Test
	public void ShowTheGivenInstrumentation() {
		instrumentationPresenter = new InstrumentationPresenter(eventBus, view);
		//go to main mode
		instrumentationPresenter.setInstrumentations(createInstrumentationSet(2));

		InstrumentationSet oneInstrumentation = createInstrumentationSet(1);
		ArrayList<Item> items = extractDisplayItem(oneInstrumentation);
		//go to dependend view mode
		instrumentationPresenter.setInstrumentations(oneInstrumentation);

		assertTrue(trace.toString(), trace.contains("setItems:" + items));
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
