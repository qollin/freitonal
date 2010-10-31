package de.cr.freitonal.unittests.client.widgets.composer;

import static de.cr.freitonal.client.event.DisplayMode.Select;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Beethoven;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.createComposerSet;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.widgets.composer.ComposerPresenter;

public class AComposerPresenterInViewModeShould extends ComposerPresenterTest {

	private ComposerPresenter composerPresenter;

	@Before
	public void setupComposerPresenterInViewMode() {
		composerPresenter = new ComposerPresenter(eventBus, view);
		composerPresenter.setItems(createComposerSet());
		composerPresenter.fireOnNewItemSelected(Beethoven);
	}

	@Test
	public void SwitchToSelectModeWhenTheComposerIsDeselected() {
		composerPresenter.getListBoxPresenter().fireHandleClickEventOnCloseImage_TEST();
		assertEquals(Select, composerPresenter.getDisplayMode());
	}

	@Test
	public void ShowAllItemsWhenTheComposerIsDeselected() {
		composerPresenter.getListBoxPresenter().fireHandleClickEventOnCloseImage_TEST();
		assertEquals(createComposerSet().size(), composerPresenter.getItemCount());
	}

	@Test
	public void TriggerASearchEventWhenAComposerIsDeselected() {
		composerPresenter.getListBoxPresenter().fireHandleClickEventOnCloseImage_TEST();
		assertTrue(trace.contains("search"));
	}
}
