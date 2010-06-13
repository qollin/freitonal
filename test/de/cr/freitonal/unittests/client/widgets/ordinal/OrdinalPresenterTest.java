package de.cr.freitonal.unittests.client.widgets.ordinal;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.event.SearchContext;
import de.cr.freitonal.client.models.Item;
import de.cr.freitonal.client.models.OrdinalSet;
import de.cr.freitonal.client.widgets.ordinal.OrdinalPresenter;

public class OrdinalPresenterTest {
	private OrdinalPresenter ordinalPresenter;

	private static final Item one = new Item("1", "1");
	private static final Item fourA = new Item("4a", "4a");

	@Before
	public void setUp() {
		OrdinalPresenter.View view = new OrdinalViewMock();
		ordinalPresenter = new OrdinalPresenter(new HandlerManager(null), view);
		OrdinalSet ordinalSet = new OrdinalSet(one, fourA);
		ordinalPresenter.setItems(ordinalSet, SearchContext.IntialLoading);
	}

	@Test
	public void testInitialLoading() {
		assertEquals("There should be 2 ordinals", 2, ordinalPresenter.getItemCount());
	}
}
