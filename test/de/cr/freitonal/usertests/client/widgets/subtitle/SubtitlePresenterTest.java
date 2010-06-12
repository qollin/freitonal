package de.cr.freitonal.usertests.client.widgets.subtitle;

import org.junit.Test;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.junit.client.GWTTestCase;

import de.cr.freitonal.client.event.SearchContext;
import de.cr.freitonal.client.models.Item;
import de.cr.freitonal.client.models.SubtitleSet;
import de.cr.freitonal.client.widgets.subtitle.SubtitlePresenter;
import de.cr.freitonal.client.widgets.subtitle.SubtitleView;

public class SubtitlePresenterTest extends GWTTestCase {
	private SubtitlePresenter subtitlePresenter;

	private static final Item Eroica = new Item("Eroica", "Eroica");
	private static final Item Waldstein = new Item("Waldsteinsonate", "Waldsteinsonate");
	private static final Item Rasumovsky = new Item("Rasumovsky 1", "Rasumovsky 1");

	@Override
	public String getModuleName() {
		return "de.cr.freitonal.FreitonalGUI";
	}

	@Override
	public void gwtSetUp() {
		SubtitleView view = new SubtitleView("Subtitle");
		subtitlePresenter = new SubtitlePresenter(new HandlerManager(null), view);
		SubtitleSet subtitleSet = new SubtitleSet(Eroica, Waldstein, Rasumovsky);
		subtitlePresenter.setItems(subtitleSet, SearchContext.IntialLoading);
	}

	@Test
	public void testInitialLoading() {
		assertEquals("There should be 3 subtitles", 3, subtitlePresenter.getItemCount());
	}
}
