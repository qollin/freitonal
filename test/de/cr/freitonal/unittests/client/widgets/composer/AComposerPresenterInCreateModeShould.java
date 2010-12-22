package de.cr.freitonal.unittests.client.widgets.composer;

import static de.cr.freitonal.client.event.DisplayMode.Create;
import static de.cr.freitonal.unittests.client.test.data.TestData.Beethoven;
import static de.cr.freitonal.unittests.client.test.data.TestData.Mozart;
import static de.cr.freitonal.unittests.client.test.data.TestData.createComposerSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.widgets.composer.ComposerPresenter;

public class AComposerPresenterInCreateModeShould extends ComposerPresenterTest {

	private ComposerPresenter composerPresenter;

	@Before
	public void setupComposerPresenterInCreateMode() {
		composerPresenter = new ComposerPresenter(eventBus, view);
		composerPresenter.setItems(createComposerSet());
		composerPresenter.fireOnNewItemSelected(Mozart);
		composerPresenter.setDisplayMode(Create);

		trace.clear();
	}

	@Test
	public void ShowAllComposers() {
		assertEquals(createComposerSet().size(), composerPresenter.getItemCount());
	}

	@Test
	public void NotTriggerASearchWhenAComposerIsSelected() {
		composerPresenter.fireOnNewItemSelected(Mozart);
		assertFalse(trace.contains("search"));
	}

	@Test
	public void ShowAllComposersAfterAComposerHasBeenSelected() {
		composerPresenter.fireOnNewItemSelected(Beethoven);
		assertEquals(createComposerSet().size(), composerPresenter.getItemCount());
	}
}
