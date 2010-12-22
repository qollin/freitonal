package de.cr.freitonal.unittests.client.widgets.piece;

import static de.cr.freitonal.unittests.client.test.data.TestData.AMajor;
import static de.cr.freitonal.unittests.client.test.data.TestData.Beethoven;
import static de.cr.freitonal.unittests.client.test.data.TestData.Eroica;
import static de.cr.freitonal.unittests.client.test.data.TestData.Opus27_1;
import static de.cr.freitonal.unittests.client.test.data.TestData.Ordinal4a;
import static de.cr.freitonal.unittests.client.test.data.TestData.Piano;
import static de.cr.freitonal.unittests.client.test.data.TestData.Quartett;
import static de.cr.freitonal.unittests.client.test.data.TestData.VolatileInstrumentationPianoSolo;
import static de.cr.freitonal.unittests.client.test.data.TestData.createPieceMask;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.widgets.piece.PiecePresenter;
import de.cr.freitonal.shared.models.VolatilePiece;
import de.cr.freitonal.unittests.client.rpc.RPCServiceMock;

public class APiecePresenterInCreationModeShould extends PiecePresenterTest {
	private PiecePresenter piecePresenter;

	@Before
	public void setupPiecePresenterInCreationMode() {
		piecePresenter = new PiecePresenter(view, eventBus, rpcService);
		piecePresenter.setSearchData(createPieceMask());
		piecePresenter.fireAddPieceButtonClicked(); //switch to create mode
	}

	@Test
	public void CallSaveWithAllSelectedItems() {
		piecePresenter.getComposerPresenter().fireOnNewItemSelected(Beethoven);
		piecePresenter.getCatalogPresenter().fireOnNewItemSelected(Opus27_1);
		piecePresenter.getMusicKeyPresenter().fireOnNewItemSelected(AMajor);
		piecePresenter.getPieceTypePresenter().fireOnNewItemSelected(Quartett);
		piecePresenter.getSubtitlePresenter().fireOnNewItemSelected(Eroica);
		piecePresenter.getOrdinalPresenter().fireOnNewItemSelected(Ordinal4a);
		piecePresenter.getInstrumentationPresenter().getInstrumentPresenter(0).fireOnNewItemSelected(Piano);

		piecePresenter.setRPCService(new RPCServiceMock() {
			@Override
			public void save(VolatilePiece piece) {
				assertNotNull("the piece to save should not be null", piece);
				assertEquals("the saved composer should be Beethoven", Beethoven, piece.getComposer());
				assertEquals("the saved catalog should be Opus 27-1", Opus27_1, piece.getCatalog());
				assertEquals("the saved musickey should be A major", AMajor, piece.musicKey);
				assertEquals("the saved piece type should be Quartett", Quartett, piece.getPieceType());
				assertEquals("the saved subtitle should be Eroica", Eroica, piece.subtitle);
				assertEquals("the saved ordinal should be 4a", Ordinal4a, piece.ordinal);
				assertEquals("the saved instrumentation should be piano", VolatileInstrumentationPianoSolo, piece.getInstrumentation());

				trace.add("save");
			}
		});

		piecePresenter.fireAddPieceButtonClicked();
		assertTrue(trace.contains("save"));
	}
}