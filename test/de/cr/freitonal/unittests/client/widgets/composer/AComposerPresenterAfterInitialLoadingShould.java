package de.cr.freitonal.unittests.client.widgets.composer;

import static de.cr.freitonal.client.event.DisplayMode.View;
import static de.cr.freitonal.usertests.client.test.data.TestData.Beethoven;
import static de.cr.freitonal.usertests.client.test.data.TestData.Mozart;
import static de.cr.freitonal.usertests.client.test.data.TestData.createComposerSet;
import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.widgets.composer.ComposerPresenter;

public class AComposerPresenterAfterInitialLoadingShould extends ComposerPresenterTest {
	private ComposerPresenter composerPresenter;

	@Before
	public void setupComposerPresenterAfterInitialLoading() {
		composerPresenter = new ComposerPresenter(eventBus, view);
		composerPresenter.setItems(createComposerSet());
	}

	@Test
	public void ShowTheCorrectNumberOfComposers() {
		assertEquals(createComposerSet().size(), composerPresenter.getItemCount());
	}

	@Test
	public void ShowOnlyOneComposerAfterSelectingOne() {
		composerPresenter.getListBoxPresenter().fireOnNewItemSelected(Mozart);
		assertEquals(1, composerPresenter.getItemCount());
	}

	@Test
	public void ReturnTheSelectedComposerAfterSelectingOne() {
		composerPresenter.getListBoxPresenter().fireOnNewItemSelected(Beethoven);
		assertEquals(Beethoven, composerPresenter.getSelectedItem());
	}

	@Test
	public void EnterViewModeAfterSelectingAComposer() {
		composerPresenter.getListBoxPresenter().fireOnNewItemSelected(Mozart);
		assertEquals(View, composerPresenter.getDisplayMode());
	}
}
