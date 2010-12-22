package de.cr.freitonal.unittests.client.widgets.base.listbox;

import static de.cr.freitonal.client.event.DisplayMode.Create;
import static de.cr.freitonal.client.event.DisplayMode.DependendView;
import static de.cr.freitonal.client.event.DisplayMode.Select;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.widgets.base.listbox.ListBoxPresenter;

public class AListBoxPresenterInDependedViewModeShould extends ListBoxPresenterTest {
	private ListBoxPresenter listBoxPresenter;

	@Before
	public void setupListBoxPresenterInDependendViewMode() {
		listBoxPresenter = new ListBoxPresenter(null, view);

		listBoxPresenter.setItemSet(twoElementItemSet);
		listBoxPresenter.setItemSet(oneElementItemSet);
		trace.clear();
	}

	@Test
	public void HaveSetTheItemSelectionOnItsModel() {
		assertNotNull(oneElementItemSet.getSelected());
	}

	@Test
	public void SwitchToCreateModeWhenTheSetDisplayModeIsCalled() {
		listBoxPresenter.setDisplayMode(Create);
		assertTrue(trace.contains("setDisplayMode:" + Create));
	}

	@Test
	public void ShouldSwitchToSelectModeWhenSetItemsWithTwoItemsIsCalled() {
		listBoxPresenter.setItemSet(twoElementItemSet);
		assertEquals(Select, listBoxPresenter.getDisplayMode());
	}

	@Test
	public void ShouldCallSetDisplayModeOnItsViewWhenSetItemsWithTwoItemsIsCalled() {
		listBoxPresenter.setItemSet(twoElementItemSet);
		assertTrue(trace.contains("setDisplayMode:" + Select));
	}

	@Test
	public void ShouldNotCallSetDisplayModeOnItsViewWhenSetItemsWithOneItemIsCalled() {
		listBoxPresenter.setItemSet(oneElementItemSet);
		assertFalse(trace.contains("setDisplayMode:" + Select));
	}

	@Test
	public void ShouldCallSetItemsOnTheViewWhenSetItemsWithTwoItemsIsCalled() {
		listBoxPresenter.setItemSet(twoElementItemSet);
		assertTrue(trace.contains("setItems:" + twoElementItemSet.getItems()));
	}

	@Test
	public void ShouldStayInDependendViewModeWhenSetItemsWithOneItemIsCalled() {
		listBoxPresenter.setItemSet(oneElementItemSet);
		assertEquals(DependendView, listBoxPresenter.getDisplayMode());
	}

}
