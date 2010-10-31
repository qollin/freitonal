package de.cr.freitonal.unittests.client.widgets.base.listbox;

import static de.cr.freitonal.client.event.DisplayMode.DependendView;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.cr.freitonal.client.widgets.base.listbox.ListBoxPresenter;

public class AListBoxPresenterShould extends ListBoxPresenterTest {
	@Test
	public void ReturnTheDependendViewDisplayModeWhenInThatState() {
		ListBoxPresenter listBoxPresenter = new ListBoxPresenter(null, view);

		listBoxPresenter.setItems(twoElementItemSet);
		listBoxPresenter.setItems(oneElementItemSet);

		assertEquals(DependendView, listBoxPresenter.getDisplayMode());
	}
}
