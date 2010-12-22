package de.cr.freitonal.unittests.client.widgets.instrumentation;

import static de.cr.freitonal.client.event.DisplayMode.DependendView;
import static de.cr.freitonal.unittests.client.test.data.TestData.Piano;
import static de.cr.freitonal.unittests.client.test.data.TestData.createInstrumentationSet;
import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.widgets.base.listbox.ListBoxPresenter;
import de.cr.freitonal.client.widgets.instrumentation.InstrumentationPresenter;

public class AnInstrumentationPresenterAfterInitialLoadingShould extends InstrumentationPresenterTest {
	private InstrumentationPresenter instrumentationPresenter;

	@Before
	public void setupInstrumentationPresenterAfterInitialLoading() {
		instrumentationPresenter = new InstrumentationPresenter(eventBus, view);
		instrumentationPresenter.setInstrumentations(createInstrumentationSet(2));
	}

	@Test
	public void SelectOneInstrumentWhenAnInstrumentIsSelected() {
		instrumentationPresenter.getInstrumentPresenter(0).fireOnNewItemSelected(Piano);
		assertEquals(1, instrumentationPresenter.getInstrumentPresenter(0).getItemCount());
	}

	@Test
	public void SwitchItsListBoxPresentersToDependendViewModeWhenGivenAOneElementItemSet() {
		instrumentationPresenter.setInstrumentations(createInstrumentationSet(1));
		for (int i = 0; i < instrumentationPresenter.getNumberOfInstrumentPresenters(); i++) {
			ListBoxPresenter instrumentPresenter = instrumentationPresenter.getInstrumentPresenter(i);
			assertEquals(DependendView, instrumentPresenter.getDisplayMode());
		}
	}

}
