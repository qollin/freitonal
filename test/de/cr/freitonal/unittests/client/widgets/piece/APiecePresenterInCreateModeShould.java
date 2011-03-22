package de.cr.freitonal.unittests.client.widgets.piece;

import static de.cr.freitonal.client.event.DisplayMode.View;
import static de.cr.freitonal.usertests.client.test.data.TestData.createSearchResult;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.client.widgets.piece.PiecePresenter;
import de.cr.freitonal.shared.models.Piece;
import de.cr.freitonal.shared.models.VolatilePiece;
import de.cr.freitonal.unittests.client.rpc.RPCServiceMock;
import de.cr.freitonal.usertests.client.test.data.TestData;

public class APiecePresenterInCreateModeShould extends PiecePresenterTest {

	private PiecePresenter piecePresenter;

	@Before
	public void setupPiecePresenter() {
		piecePresenter = new PiecePresenter(view, eventBus, rpcService);
		piecePresenter.setSearchData(createSearchResult()); //go to select mode
		piecePresenter.fireAddPieceButtonClicked(); //go to create mode
	}

	@Test
	public void GiveEachPresenterOneItemToShow() {
		piecePresenter.setRPCService(new RPCServiceMock(trace) {
			@Override
			public void createPiece(VolatilePiece piece, AsyncCallback<Piece> callback) {
				callback.onSuccess(TestData.createPiece());
			}
		});

		piecePresenter.fireAddPieceButtonClicked(); //save

		assertEquals("The catalog presenter should be in view mode after a new piece was created.", View, piecePresenter.getCatalogPresenter()
				.getDisplayMode());
	}
}
