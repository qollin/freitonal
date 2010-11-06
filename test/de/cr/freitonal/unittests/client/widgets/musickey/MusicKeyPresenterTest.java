package de.cr.freitonal.unittests.client.widgets.musickey;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.models.MusicKeySet;
import de.cr.freitonal.client.widgets.musickey.MusicKeyPresenter;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.unittests.client.widgets.PresenterTest;

public class MusicKeyPresenterTest extends PresenterTest {
	private MusicKeyPresenter musicKeyPresenter;

	private static final Item AMajor = new Item("31", "A-Dur");

	@Before
	public void setUp() {
		MusicKeyPresenter.View view = new MusicKeyViewMock();
		musicKeyPresenter = new MusicKeyPresenter(eventBus, view);
		MusicKeySet musicKeySet = new MusicKeySet(AMajor);
		musicKeyPresenter.setItems(musicKeySet);
	}

	@Test
	public void testInitialLoading() {
		assertEquals("There should be 1 musicKey", 1, musicKeyPresenter.getItemCount());
	}
}
