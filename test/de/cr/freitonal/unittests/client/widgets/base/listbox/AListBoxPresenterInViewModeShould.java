package de.cr.freitonal.unittests.client.widgets.base.listbox;

import static de.cr.freitonal.client.event.DisplayMode.Create;
import static de.cr.freitonal.client.event.DisplayMode.View;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.widgets.base.listbox.ListBoxPresenter;

public class AListBoxPresenterInViewModeShould extends ListBoxPresenterTest {
	private ListBoxPresenter listBoxPresenter;

	@Before
	public void setupListBoxPresenterInViewMode() {
		listBoxPresenter = new ListBoxPresenter(null, view);

		listBoxPresenter.setItemSet(twoElementItemSet);
		listBoxPresenter.fireOnNewItemSelected(secondItem);
	}

	@Test
	public void SwitchToCreateModeWhenTheSetDisplayModeIsCalled() {
		listBoxPresenter.setDisplayMode(Create);
		assertTrue(trace.contains("setDisplayMode:" + View));
	}

	@Test
	public void SaveAGivenItemSet() {
		listBoxPresenter.setItemSet(oneElementItemSet);
		assertEquals(oneElementItemSet, listBoxPresenter.getCurrentItemSet());
	}
}
