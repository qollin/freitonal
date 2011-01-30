package de.cr.freitonal.unittests.client.widgets.piecetype;

import static de.cr.freitonal.usertests.client.test.data.TestData.Quartett;
import static de.cr.freitonal.usertests.client.test.data.TestData.Sonata;
import static de.cr.freitonal.usertests.client.test.data.TestData.Symphonie;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.event.PiecePlusInstrumentationTypeSelectedEvent;
import de.cr.freitonal.client.event.PiecePlusInstrumentationTypeSelectedHandler;
import de.cr.freitonal.client.models.PieceTypeSet;
import de.cr.freitonal.client.widgets.piecetype.PieceTypePresenter;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.unittests.client.widgets.PresenterTest;

public class PieceTypePresenterTest extends PresenterTest {
	private PieceTypePresenter pieceTypePresenter;

	private static final Item StringQuartett = new Item("17", "Streichquartett");

	@Before
	public void setUp() {
		PieceTypePresenter.View view = new PieceTypeViewMock();
		pieceTypePresenter = new PieceTypePresenter(eventBus, view);

		ArrayList<Item> piecePlusInstrumentationTypes = new ArrayList<Item>();
		piecePlusInstrumentationTypes.add(StringQuartett);

		ArrayList<Item> pieceTypes = new ArrayList<Item>();
		pieceTypes.add(Quartett);
		pieceTypes.add(Sonata);
		pieceTypes.add(Symphonie);

		PieceTypeSet pieceTypeSet = new PieceTypeSet(pieceTypes, piecePlusInstrumentationTypes);
		pieceTypePresenter.setPieceTypes(pieceTypeSet);
	}

	@Test
	public void testInitialLoading() {
		assertEquals("There should be 4 piece types", 4, pieceTypePresenter.getItemCount());
		assertEquals("The piece+instrumentation type should be at the top of the list", StringQuartett, pieceTypePresenter.getItem(0));
	}

	@Test
	public void testSelectionOfAPiecePlusInstrumentationType() {
		eventBus.addHandler(PiecePlusInstrumentationTypeSelectedEvent.TYPE, new PiecePlusInstrumentationTypeSelectedHandler() {
			public void onPiecePlusInstrumentationTypeSelected() {
				trace.add("onPiecePlusInstrumentationTypeSelected");
			}
		});
		pieceTypePresenter.fireOnNewItemSelected(StringQuartett);
		assertTrue(trace.contains("onPiecePlusInstrumentationTypeSelected"));
	}
}
