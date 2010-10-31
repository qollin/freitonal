package de.cr.freitonal.unittests.client.widgets.base.listbox;

import static de.cr.freitonal.client.event.DisplayMode.Create;
import static de.cr.freitonal.client.event.DisplayMode.DependendView;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.widgets.base.listbox.ListBoxPresenter;

public class AListBoxPresenterInDependedViewModeShould extends ListBoxPresenterTest {
	private ListBoxPresenter listBoxPresenter;

	@Before
	public void setupListBoxPresenterInDependendViewMode() {
		listBoxPresenter = new ListBoxPresenter(null, view);

		listBoxPresenter.setItems(twoElementItemSet);
		listBoxPresenter.setItems(oneElementItemSet);
	}

	@Test
	public void SwitchToCreateModeWhenTheSetDisplayModeIsCalled() {
		listBoxPresenter.setDisplayMode(Create);
		assertTrue(trace.contains("setDisplayMode:" + DependendView));
	}

}
