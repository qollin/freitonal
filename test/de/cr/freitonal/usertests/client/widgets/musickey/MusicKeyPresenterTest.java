package de.cr.freitonal.usertests.client.widgets.musickey;

import org.junit.Test;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.junit.client.GWTTestCase;

import de.cr.freitonal.client.event.SearchContext;
import de.cr.freitonal.client.models.Item;
import de.cr.freitonal.client.models.MusicKeySet;
import de.cr.freitonal.client.widgets.musickey.MusicKeyPresenter;
import de.cr.freitonal.client.widgets.musickey.MusicKeyView;

public class MusicKeyPresenterTest extends GWTTestCase {
	private MusicKeyPresenter musicKeyPresenter;

	private static final Item AMajor = new Item("31", "A-Dur");

	@Override
	public String getModuleName() {
		return "de.cr.freitonal.FreitonalGUI";
	}

	@Override
	public void gwtSetUp() {
		MusicKeyView view = new MusicKeyView("Key");
		musicKeyPresenter = new MusicKeyPresenter(new HandlerManager(null), view);
		MusicKeySet musicKeySet = new MusicKeySet(AMajor);
		musicKeyPresenter.setItems(musicKeySet, SearchContext.IntialLoading);
	}

	@Test
	public void testInitialLoading() {
		assertEquals("There should be 1 musicKey", 1, musicKeyPresenter.getItemCount());
	}
}
