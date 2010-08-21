package de.cr.freitonal.unittests.client.widgets.piecetype;

import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Quartett;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Sonate;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Symphonie;
import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.event.PiecePlusInstrumentationTypeSelectedEvent;
import de.cr.freitonal.client.event.PiecePlusInstrumentationTypeSelectedHandler;
import de.cr.freitonal.client.models.PieceTypeSet;
import de.cr.freitonal.client.widgets.piecetype.PieceTypePresenter;
import de.cr.freitonal.shared.models.Item;

public class PieceTypePresenterTest {
	private PieceTypePresenter pieceTypePresenter;
	private HandlerManager eventBus;
	private ArrayList<String> trace;

	private static final Item StringQuartett = new Item("17", "Streichquartett");

	@Before
	public void setUp() {
		trace = new ArrayList<String>();

		eventBus = new HandlerManager(null);

		PieceTypePresenter.View view = new PieceTypeViewMock();
		pieceTypePresenter = new PieceTypePresenter(eventBus, view);

		ArrayList<Item> piecePlusInstrumentationTypes = new ArrayList<Item>();
		piecePlusInstrumentationTypes.add(StringQuartett);

		ArrayList<Item> pieceTypes = new ArrayList<Item>();
		pieceTypes.add(Quartett);
		pieceTypes.add(Sonate);
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
		assertEquals(1, trace.size());
		assertEquals("onPiecePlusInstrumentationTypeSelected", trace.get(0));
	}
}
