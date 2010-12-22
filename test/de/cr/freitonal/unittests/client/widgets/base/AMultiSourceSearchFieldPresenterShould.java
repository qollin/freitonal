package de.cr.freitonal.unittests.client.widgets.base;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.models.MultiSourceItemSet;
import de.cr.freitonal.client.widgets.base.MultiSourceSearchFieldPresenter;
import de.cr.freitonal.unittests.client.widgets.base.listbox.ListBoxPresenterTest;

public class AMultiSourceSearchFieldPresenterShould extends ListBoxPresenterTest {
	private MultiSourceSearchFieldPresenter presenter;

	@Before
	public void setupMultiSourceListBoxPresenter() {
		presenter = new MultiSourceSearchFieldPresenter(eventBus, view);
	}

	@Test
	public void RetainTheSourceOfTheSelectedItem() {
		presenter.setItemSet(new MultiSourceItemSet(oneElementItemSet, twoElementItemSet));

		presenter.fireOnNewItemSelected(secondItem);
		assertEquals(secondItem, presenter.getSelectedItem());
		assertEquals(twoElementItemSet, presenter.getSelectedSource());
	}
}
