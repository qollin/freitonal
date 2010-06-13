package de.cr.freitonal.unittests.client.widgets.musickey;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.event.SearchContext;
import de.cr.freitonal.client.models.Item;
import de.cr.freitonal.client.models.MusicKeySet;
import de.cr.freitonal.client.widgets.musickey.MusicKeyPresenter;

public class MusicKeyPresenterTest {
	private MusicKeyPresenter musicKeyPresenter;

	private static final Item AMajor = new Item("31", "A-Dur");

	@Before
	public void setUp() {
		MusicKeyPresenter.View view = new MusicKeyViewMock();
		musicKeyPresenter = new MusicKeyPresenter(new HandlerManager(null), view);
		MusicKeySet musicKeySet = new MusicKeySet(AMajor);
		musicKeyPresenter.setItems(musicKeySet, SearchContext.IntialLoading);
	}

	@Test
	public void testInitialLoading() {
		assertEquals("There should be 1 musicKey", 1, musicKeyPresenter.getItemCount());
	}
}
