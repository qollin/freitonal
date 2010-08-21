package de.cr.freitonal.unittests.client.widgets.base;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.models.MultiSourceItemSet;
import de.cr.freitonal.client.widgets.base.MultiSourceSearchFieldPresenter;
import de.cr.freitonal.shared.models.Item;

public class MultiSourceListBoxPresenterTest extends ListBoxPresenterTest {
	private static final ItemSet itemSet1 = new ItemSet(new Item("1", "1"), new Item("2", "2"));
	private static final ItemSet itemSet2 = new ItemSet(new Item("a", "a"), new Item("b", "b"));

	@Test
	public void testSetItems() {
		MultiSourceSearchFieldPresenter presenter = new MultiSourceSearchFieldPresenter(eventBus, view);
		presenter.setItems(new MultiSourceItemSet(itemSet1, itemSet2));

		Item itemA = itemSet2.getItems().get(0);

		presenter.fireOnNewItemSelected(itemA);
		assertTrue("a global search should have been triggered", trace.contains("onSearchFieldChanged"));
		assertEquals(itemA, presenter.getSelectedItem());
		assertEquals(itemSet2, presenter.getSelectedSource());
	}
}
