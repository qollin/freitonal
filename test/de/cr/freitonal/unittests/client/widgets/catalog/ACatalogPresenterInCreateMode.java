package de.cr.freitonal.unittests.client.widgets.catalog;

import static de.cr.freitonal.client.event.DisplayMode.Create;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.widgets.catalog.CatalogPresenter;

public class ACatalogPresenterInCreateMode extends CatalogPresenterTest {
	private CatalogPresenter catalogPresenter;

	@Before
	public void setupCatalogPresenterInCreateMode() {
		catalogPresenter = new CatalogPresenter(eventBus, view);
		catalogPresenter.setCatalogs(catalogs);
		catalogPresenter.setDisplayMode(Create);
	}

	@Test
	public void ShouldNotHaveAnythingSelected() {
		assertNull("Initially, no name should be selected", catalogPresenter.getNameListBoxPresenter().getSelectedItem());
		assertNull("Initially, no number should be selected", catalogPresenter.getNumberListBoxPresenter().getSelectedItem());
	}

	@Test
	public void ShouldNotTriggerASearchWhenANameIsSelected() {
		catalogPresenter.getNameListBoxPresenter().fireOnNewItemSelected(catalogs.getNames().getItem(0));
		assertEquals(0, trace.size());
	}

	@Test
	public void ShouldNotSelectANumberWhenANameWasSelected() {
		catalogPresenter.getNameListBoxPresenter().fireOnNewItemSelected(catalogs.getNames().getItem(0));
		assertNull("After selecting a name, no number should be selected", catalogPresenter.getNumberListBoxPresenter().getSelectedItem());
	}

	@Test
	public void ShouldEnableTheNumberDropdownAfterANameWasSelected() {
		catalogPresenter.getNameListBoxPresenter().fireOnNewItemSelected(catalogs.getNames().getItem(0));
		assertTrue("After selecting a name, the number dropdown should be enabled", catalogPresenter.getNumberListBoxPresenter().isEnabled());
	}

}
