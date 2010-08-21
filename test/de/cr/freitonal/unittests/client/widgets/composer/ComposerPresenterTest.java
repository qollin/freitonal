package de.cr.freitonal.unittests.client.widgets.composer;

import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Beethoven;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Mozart;
import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.event.SearchFieldChangedEvent;
import de.cr.freitonal.client.event.SearchFieldChangedHandler;
import de.cr.freitonal.client.models.ComposerSet;
import de.cr.freitonal.client.widgets.composer.ComposerPresenter;

public class ComposerPresenterTest {
	private ComposerPresenter composerPresenter;
	private ComposerSet composerSet;
	private HandlerManager eventBus;
	private ArrayList<String> trace;

	@Before
	public void setUp() {
		ComposerPresenter.View view = new ComposerViewMock();
		eventBus = new HandlerManager(null);
		trace = new ArrayList<String>();

		composerPresenter = new ComposerPresenter(eventBus, view);
		composerSet = new ComposerSet(Beethoven, Mozart);

		composerPresenter.setItems(composerSet);
	}

	@Test
	public void testInitialLoading() {
		assertEquals(2, composerPresenter.getItemCount());
	}

	@Test
	public void testSwitchBetweenSelectAndViewMode() {
		composerPresenter.getListBoxPresenter().fireOnNewItemSelected(Mozart);
		assertEquals("after selecting a composer, the item count should be one", 1, composerPresenter.getItemCount());

		eventBus.addHandler(SearchFieldChangedEvent.TYPE, new SearchFieldChangedHandler() {
			public void onSearchFieldChanged(SearchFieldChangedEvent event) {
				trace.add("onSearchFieldChanged");
			}
		});

		composerPresenter.getListBoxPresenter().fireHandleClickEventOnCloseImage_TEST();
		assertEquals("after returning to select mode, the item count should be two", 2, composerPresenter.getItemCount());
		assertEquals("after returning to select mode, a search event should have been sent", 1, trace.size());
		assertEquals("after returning to select mode, a search event should have been sent", "onSearchFieldChanged", trace.get(0));
	}
}
