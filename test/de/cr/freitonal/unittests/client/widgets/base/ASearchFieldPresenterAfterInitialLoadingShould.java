package de.cr.freitonal.unittests.client.widgets.base;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.event.SearchFieldChangedEvent;
import de.cr.freitonal.client.event.SearchFieldChangedHandler;
import de.cr.freitonal.client.widgets.base.SearchFieldPresenter;
import de.cr.freitonal.unittests.client.widgets.base.listbox.ListBoxPresenterTest;

public class ASearchFieldPresenterAfterInitialLoadingShould extends ListBoxPresenterTest {

	private SearchFieldPresenter presenter;

	@Before
	public void setUp() {
		presenter = new SearchFieldPresenter(eventBus, view);
		presenter.setItemSet(oneElementItemSet);
	}

	@Test
	public void TriggerASearchEventWhenAnItemIsSelected() {
		eventBus.addHandler(SearchFieldChangedEvent.TYPE, new SearchFieldChangedHandler() {
			public void onSearchFieldChanged(SearchFieldChangedEvent event) {
				trace.add("onSearchFieldChanged");
			}
		});

		presenter.fireOnNewItemSelected(firstItem);
		assertTrue("a global search should have been triggered", trace.contains("onSearchFieldChanged"));
	}

}
