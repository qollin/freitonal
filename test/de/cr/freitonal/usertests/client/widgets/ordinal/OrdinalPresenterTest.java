package de.cr.freitonal.usertests.client.widgets.ordinal;

import org.junit.Test;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.junit.client.GWTTestCase;

import de.cr.freitonal.client.event.SearchContext;
import de.cr.freitonal.client.models.Item;
import de.cr.freitonal.client.models.OrdinalSet;
import de.cr.freitonal.client.widgets.ordinal.OrdinalPresenter;
import de.cr.freitonal.client.widgets.ordinal.OrdinalView;

public class OrdinalPresenterTest extends GWTTestCase {
	private OrdinalPresenter ordinalPresenter;

	private static final Item one = new Item("1", "1");
	private static final Item fourA = new Item("4a", "4a");

	@Override
	public String getModuleName() {
		return "de.cr.freitonal.FreitonalGUI";
	}

	@Override
	public void gwtSetUp() {
		OrdinalView view = new OrdinalView("Ordinal");
		ordinalPresenter = new OrdinalPresenter(new HandlerManager(null), view);
		OrdinalSet ordinalSet = new OrdinalSet(one, fourA);
		ordinalPresenter.setItems(ordinalSet, SearchContext.IntialLoading);
	}

	@Test
	public void testInitialLoading() {
		assertEquals("There should be 2 ordinals", 2, ordinalPresenter.getItemCount());
	}
}
