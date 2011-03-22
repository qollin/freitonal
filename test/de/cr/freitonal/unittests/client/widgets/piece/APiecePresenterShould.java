package de.cr.freitonal.unittests.client.widgets.piece;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.widgets.piece.PiecePresenter;

public class APiecePresenterShould extends PiecePresenterTest {
	private PiecePresenter piecePresenter;

	@Before
	public void setupPiecePresenterInCreationMode() {
		piecePresenter = new PiecePresenter(view, eventBus, rpcService);
	}

	@Test
	public void ReturnAPieceListPresenter() {
		assertNotNull(piecePresenter.getPieceListPresenter());
	}
}
