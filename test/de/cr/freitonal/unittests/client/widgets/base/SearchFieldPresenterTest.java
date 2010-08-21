package de.cr.freitonal.unittests.client.widgets.base;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.widgets.base.SearchFieldPresenter;

public class SearchFieldPresenterTest extends ListBoxPresenterTest {

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Override
	@Test
	public void testConstructor() {
		SearchFieldPresenter presenter = new SearchFieldPresenter(eventBus, view);
		presenter.setItems(new ItemSet(emptyItem));
		presenter.fireOnNewItemSelected(emptyItem);
		assertTrue("a global search should have been triggered", trace.contains("onSearchFieldChanged"));
	}

}
