package de.cr.freitonal.usertests.client.widgets.catalog;

import java.util.ArrayList;

import org.junit.Test;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.junit.client.GWTTestCase;

import de.cr.freitonal.client.event.SearchContext;
import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.models.Item;
import de.cr.freitonal.client.widgets.catalog.CatalogPresenter;
import de.cr.freitonal.client.widgets.catalog.CatalogView;

public class CatalogPresenterTest extends GWTTestCase {
	private CatalogPresenter catalogPresenter;
	private CatalogSet catalogSet;

	@Override
	public String getModuleName() {
		return "de.cr.freitonal.FreitonalGUI";
	}

	@Override
	public void gwtSetUp() {
		CatalogView view = new CatalogView("catalog");
		catalogPresenter = new CatalogPresenter(new HandlerManager(null), view);
		ArrayList<Item> names = new ArrayList<Item>();
		ArrayList<Item> numbers = new ArrayList<Item>();

		names.add(new Item("1", "BWV"));
		names.add(new Item("2", "Opus"));
		numbers.add(new Item("1", "1-1"));
		numbers.add(new Item("2", "224A"));
		numbers.add(new Item("3", "1-1b"));
		catalogSet = new CatalogSet(names, numbers);

		catalogPresenter.setCatalogs(catalogSet, SearchContext.IntialLoading);
	}

	@Test
	public void testInitialLoading() {
		assertEquals(2, catalogPresenter.getNameItemCount());
		assertEquals(3, catalogPresenter.getNumberItemCount());
	}

	@Test
	public void testNameSelection() {
		assertNull("Initially, no name should be selected", catalogPresenter.getNameListBoxPresenter().getSelectedItem());
		assertNull("Initially, no number should be selected", catalogPresenter.getNumberListBoxPresenter().getSelectedItem());
		assertFalse("Initially, the number dropdown should be disabled", catalogPresenter.getNumberListBoxPresenter().isEnabled());

		//select a name:
		catalogPresenter.getNameListBoxPresenter().fireOnNewItemSelected_TEST(new Item("1", "BWV"));

		assertNull("After selecting a name, no number should be selected", catalogPresenter.getNumberListBoxPresenter().getSelectedItem());
		assertTrue("After selecting a name, the number dropdown should be enabled", catalogPresenter.getNumberListBoxPresenter().isEnabled());
	}
}
