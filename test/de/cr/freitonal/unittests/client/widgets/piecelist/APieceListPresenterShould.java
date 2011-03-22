package de.cr.freitonal.unittests.client.widgets.piecelist;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.client.widgets.piecelist.PieceListPresenter;
import de.cr.freitonal.shared.models.PieceList;
import de.cr.freitonal.shared.models.PieceSkeleton;
import de.cr.freitonal.unittests.client.widgets.PresenterTest;

public class APieceListPresenterShould extends PresenterTest {
	private PieceListPresenter pieceListPresenter;
	private PieceListPresenter.View view;

	@Before
	public void setupPieceListPresenter() {
		view = new PieceListViewMock(trace);
		pieceListPresenter = new PieceListPresenter(eventBus, view);
	}

	@Test
	public void ConvertAllSkeletonPiecesToPiecesAndPassThemToTheView() {
		PieceList pieceList = new PieceList();
		PieceSkeleton pieceSkeleton = new PieceSkeleton("bla");
		pieceList.add(pieceSkeleton);

		PieceSearchMask pieceSearchMask = new PieceSearchMask();

		pieceListPresenter.setPieceList(pieceList, pieceSearchMask);
		assertTrue(trace.contains("setPieceList"));
	}
}
