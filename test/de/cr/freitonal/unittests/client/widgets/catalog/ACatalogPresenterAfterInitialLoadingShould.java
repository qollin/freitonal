package de.cr.freitonal.unittests.client.widgets.catalog;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.widgets.catalog.CatalogPresenter;

public class ACatalogPresenterAfterInitialLoadingShould extends CatalogPresenterTest {
	private CatalogPresenter catalogPresenter;

	@Before
	public void setupCatalogPresenterAfterInitialLoading() {
		catalogPresenter = new CatalogPresenter(eventBus, view);
		catalogPresenter.setCatalogs(catalogs);
	}

	@Test
	public void ShouldNotHaveAnythingSelected() {
		assertNull("Initially, no name should be selected", catalogPresenter.getNameListBoxPresenter().getSelectedItem());
		assertNull("Initially, no number should be selected", catalogPresenter.getNumberListBoxPresenter().getSelectedItem());
	}

	@Test
	public void ShouldHaveADisabledNumberDropdown() {
		assertFalse("Initially, the number dropdown should be disabled", catalogPresenter.getNumberListBoxPresenter().isEnabled());
	}

	@Test
	public void ShouldTriggerASearchAfterSelectingAName() {
		catalogPresenter.getNameListBoxPresenter().fireOnNewItemSelected(catalogs.getNames().getItem(0));
		assertEquals("a search should have triggered", 1, trace.size());
		assertEquals("a search should have triggered", "search", trace.get(0));
	}

	@Test
	public void ShouldEnableTheNumberDropdownAfterANameWasSelected() {
		catalogPresenter.getNameListBoxPresenter().fireOnNewItemSelected(catalogs.getNames().getItem(0));
		assertTrue("After selecting a name, the number dropdown should be enabled", catalogPresenter.getNumberListBoxPresenter().isEnabled());
	}
}
