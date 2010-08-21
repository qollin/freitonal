package de.cr.freitonal.unittests.client.widgets.piece;

import org.junit.Before;

import de.cr.freitonal.client.AppController;
import de.cr.freitonal.client.widgets.piece.PiecePresenter;
import de.cr.freitonal.unittests.client.rpc.JSONTestCase;

public class PieceTestCase extends JSONTestCase {
	protected PiecePresenter.View pieceView;
	protected PiecePresenter piecePresenter;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		pieceView = new PieceViewMock();
		appController = new AppController(pieceView, null);
		piecePresenter = appController.getPiecePresenter();

		onNextSearchReturn(resources.getFullSearchJSON().getText());
		appController.go();

	}
}
