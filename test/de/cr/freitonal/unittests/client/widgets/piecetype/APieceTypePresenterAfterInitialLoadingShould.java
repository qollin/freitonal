package de.cr.freitonal.unittests.client.widgets.piecetype;

import static de.cr.freitonal.client.event.DisplayMode.Create;
import static de.cr.freitonal.usertests.client.test.data.TestData.createPieceTypeSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.event.PiecePlusInstrumentationTypeSelectedEvent;
import de.cr.freitonal.client.event.PiecePlusInstrumentationTypeSelectedHandler;
import de.cr.freitonal.client.models.PieceTypeSet;
import de.cr.freitonal.client.widgets.piecetype.PieceTypePresenter;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.unittests.client.widgets.PresenterTest;

public class APieceTypePresenterAfterInitialLoadingShould extends PresenterTest {
	private PieceTypeViewMock view;
	private PieceTypePresenter presenter;
	private PieceTypeSet pieceTypeSet;

	@Before
	public void setupPieceTypePresenterAfterInitialLoading() {
		view = new PieceTypeViewMock();
		presenter = new PieceTypePresenter(eventBus, view);
		pieceTypeSet = createPieceTypeSet();
		presenter.setPieceTypes(pieceTypeSet);
	}

	@Test
	public void SwitchToCreateModeWhenSetDisplayModeWithCreateIsCalled() {
		presenter.setDisplayMode(Create);
		assertEquals(Create, presenter.getListBoxPresenter().getDisplayMode());
		presenter.fireOnNewItemSelected(pieceTypeSet.getPieceTypes().getItem(0));
	}

	@Test
	public void TriggerCorrectEventWhenAPiecePlusInstrumentationTypeIsSelected() {
		eventBus.addHandler(PiecePlusInstrumentationTypeSelectedEvent.TYPE, new PiecePlusInstrumentationTypeSelectedHandler() {
			public void onPiecePlusInstrumentationTypeSelected() {
				trace.add("onPiecePlusInstrumentationTypeSelected");
			}
		});
		presenter.fireOnNewItemSelected(pieceTypeSet.getPiecePlusInstrumentationTypes().getItem(0));
		assertTrue(trace.contains("onPiecePlusInstrumentationTypeSelected"));
	}

	@Test
	public void TriggerASearchWhenAnItemIsSelected() {
		Item pieceType = pieceTypeSet.getPieceTypes().getItem(0);
		presenter.fireOnNewItemSelected(pieceType);
		trace.contains("search");
		assertEquals(pieceType, pieceTypeSet.getAllTypesItemSet().getSelected());
	}
}
