package de.cr.freitonal.unittests.client.widgets.piecetype;

import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.NumberOfPiecePlusInstrumentationTypes;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.NumberOfPieceTypes;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Quartett;
import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.widgets.piecetype.PieceTypePresenter;
import de.cr.freitonal.unittests.client.widgets.piece.PieceTestCase;

public class JSONPieceTypePresenterTest extends PieceTestCase {
	PieceTypePresenter pieceTypePresenter;

	@Before
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		super.setUp();
		pieceTypePresenter = piecePresenter.getPieceTypePresenter();
	}

	@Test
	public void testInitialLoading() {
		assertEquals(NumberOfPieceTypes + NumberOfPiecePlusInstrumentationTypes, pieceTypePresenter.getItemCount());
	}

	@Test
	public void testSearch() {
		onNextSearchReturn(resources.getSearchForQuartettJSON().getText());
		pieceTypePresenter.fireOnNewItemSelected(Quartett);

		assertEquals(1, pieceTypePresenter.getItemCount());
		assertEquals(Quartett, pieceTypePresenter.getSelectedItem());
	}
}
