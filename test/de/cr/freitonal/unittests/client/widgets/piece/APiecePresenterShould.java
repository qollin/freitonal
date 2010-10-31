package de.cr.freitonal.unittests.client.widgets.piece;

import org.junit.Before;

import de.cr.freitonal.client.widgets.piece.PiecePresenter;

public class APiecePresenterShould extends PiecePresenterTest {

	private PiecePresenter piecePresenter;

	@Before
	public void setupPiecePresenter() {
		piecePresenter = new PiecePresenter(view, eventBus, rpcService);
	}
}
