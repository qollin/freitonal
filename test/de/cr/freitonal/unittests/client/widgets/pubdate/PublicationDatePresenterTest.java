package de.cr.freitonal.unittests.client.widgets.pubdate;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.models.PublicationDateSet;
import de.cr.freitonal.client.widgets.pubdate.PublicationDatePresenter;
import de.cr.freitonal.shared.models.Item;

public class PublicationDatePresenterTest {
	private PublicationDatePresenter publicationDatePresenter;

	private static final Item Date1880 = new Item("1880", "1880");
	private static final Item Date1900 = new Item("1900", "1900");

	@Before
	public void setUp() {
		PublicationDatePresenter.View view = new PublicationDateViewMock();
		publicationDatePresenter = new PublicationDatePresenter(new HandlerManager(null), view);
		PublicationDateSet dates = new PublicationDateSet(Date1880, Date1900);
		publicationDatePresenter.setItems(dates);
	}

	@Test
	public void testInitialLoading() {
		assertEquals("There should be 2 dates", 2, publicationDatePresenter.getItemCount());
	}
}
