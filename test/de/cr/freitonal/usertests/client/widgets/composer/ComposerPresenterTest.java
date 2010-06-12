package de.cr.freitonal.usertests.client.widgets.composer;

import static de.cr.freitonal.usertests.client.test.data.FullSearchInformation.Beethoven;
import static de.cr.freitonal.usertests.client.test.data.FullSearchInformation.Mozart;

import java.util.ArrayList;

import org.junit.Test;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.junit.client.GWTTestCase;

import de.cr.freitonal.client.event.SearchContext;
import de.cr.freitonal.client.event.SearchFieldChangedEvent;
import de.cr.freitonal.client.event.SearchFieldChangedHandler;
import de.cr.freitonal.client.models.ComposerSet;
import de.cr.freitonal.client.widgets.composer.ComposerPresenter;
import de.cr.freitonal.client.widgets.composer.ComposerView;

public class ComposerPresenterTest extends GWTTestCase {
	private ComposerPresenter composerPresenter;
	private ComposerSet composerSet;
	private HandlerManager eventBus;
	private ArrayList<String> trace;

	@Override
	public String getModuleName() {
		return "de.cr.freitonal.FreitonalGUI";
	}

	@Override
	public void gwtSetUp() {
		ComposerView view = new ComposerView("composer");
		eventBus = new HandlerManager(null);
		trace = new ArrayList<String>();

		composerPresenter = new ComposerPresenter(eventBus, view);
		composerSet = new ComposerSet(Beethoven, Mozart);

		composerPresenter.setItems(composerSet, SearchContext.IntialLoading);
	}

	@Test
	public void testInitialLoading() {
		assertEquals(2, composerPresenter.getItemCount());
	}

	@Test
	public void testSwitchBetweenSelectAndViewMode() {
		composerPresenter.getListBoxPresenter().fireOnNewItemSelected_TEST(Mozart);
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
