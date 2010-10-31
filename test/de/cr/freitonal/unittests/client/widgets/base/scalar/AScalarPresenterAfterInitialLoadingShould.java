package de.cr.freitonal.unittests.client.widgets.base.scalar;

import static de.cr.freitonal.client.event.DisplayMode.DependendView;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.widgets.base.scalar.ScalarPresenter;
import de.cr.freitonal.shared.models.Item;

public class AScalarPresenterAfterInitialLoadingShould {
	@Test
	public void SwitchToDependendViewModeWhenGivenASingleItemItemSet() {
		ScalarViewMock view = new ScalarViewMock();
		ScalarPresenter presenter = new ScalarPresenter(null, view);
		ItemSet initialItems = new ItemSet(new Item("a", "a"), new Item("b", "b"));
		presenter.setItems(initialItems);

		ItemSet items = new ItemSet(new Item("a", "a"));
		presenter.setItems(items);
		assertEquals(DependendView, presenter.getDisplayMode());
	}
}
