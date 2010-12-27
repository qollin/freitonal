package de.cr.freitonal.unittests.client.widgets.base.scalar;

import static de.cr.freitonal.client.event.DisplayMode.DependendView;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.widgets.base.scalar.ScalarPresenter;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.unittests.client.widgets.PresenterTest;

public class AScalarPresenterAfterInitialLoadingShould extends PresenterTest {
	private ScalarViewMock view;
	private ScalarPresenter presenter;
	private ItemSet initialItems;

	@Before
	public void setupScalarPresenterInInitialLoading() {
		view = new ScalarViewMock(trace);
		presenter = new ScalarPresenter(eventBus, view);
		initialItems = new ItemSet(new Item("a", "a"), new Item("b", "b"));
		presenter.setItems(initialItems);
	}

	@Test
	public void SwitchToDependendViewModeWhenGivenASingleItemItemSet() {
		ItemSet items = new ItemSet(new Item("a", "a"));
		presenter.setItems(items);
		assertEquals(DependendView, presenter.getDisplayMode());
	}
}
