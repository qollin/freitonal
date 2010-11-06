package de.cr.freitonal.unittests.client.widgets.ordinal;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.models.OrdinalSet;
import de.cr.freitonal.client.widgets.ordinal.OrdinalPresenter;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.unittests.client.widgets.PresenterTest;

public class OrdinalPresenterTest extends PresenterTest {
	private OrdinalPresenter ordinalPresenter;

	private static final Item one = new Item("1", "1");
	private static final Item fourA = new Item("4a", "4a");

	@Before
	public void setUp() {
		OrdinalPresenter.View view = new OrdinalViewMock();
		ordinalPresenter = new OrdinalPresenter(eventBus, view);
		OrdinalSet ordinalSet = new OrdinalSet(one, fourA);
		ordinalPresenter.setItems(ordinalSet);
	}

	@Test
	public void testInitialLoading() {
		assertEquals("There should be 2 ordinals", 2, ordinalPresenter.getItemCount());
	}
}
