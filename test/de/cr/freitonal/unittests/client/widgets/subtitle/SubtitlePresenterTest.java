package de.cr.freitonal.unittests.client.widgets.subtitle;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.event.SearchContext;
import de.cr.freitonal.client.models.Item;
import de.cr.freitonal.client.models.SubtitleSet;
import de.cr.freitonal.client.widgets.subtitle.SubtitlePresenter;

public class SubtitlePresenterTest {
	private SubtitlePresenter subtitlePresenter;

	private static final Item Eroica = new Item("Eroica", "Eroica");
	private static final Item Waldstein = new Item("Waldsteinsonate", "Waldsteinsonate");
	private static final Item Rasumovsky = new Item("Rasumovsky 1", "Rasumovsky 1");

	@Before
	public void setUp() {
		SubtitlePresenter.View view = new SubtitleViewMock();
		subtitlePresenter = new SubtitlePresenter(new HandlerManager(null), view);
		SubtitleSet subtitleSet = new SubtitleSet(Eroica, Waldstein, Rasumovsky);
		subtitlePresenter.setItems(subtitleSet, SearchContext.IntialLoading);
	}

	@Test
	public void testInitialLoading() {
		assertEquals("There should be 3 subtitles", 3, subtitlePresenter.getItemCount());
	}
}
