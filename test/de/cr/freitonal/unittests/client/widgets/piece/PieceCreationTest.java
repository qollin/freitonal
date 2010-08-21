package de.cr.freitonal.unittests.client.widgets.piece;

import static de.cr.freitonal.client.event.DisplayMode.Create;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.AMajor;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Beethoven;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Eroica;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.InstrumentationPianoSolo;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.NumberOfComposers;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.NumberOfInstruments;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Opus27_1;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Ordinal4a;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Piano;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Quartett;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.AppController;
import de.cr.freitonal.client.rpc.RPCServiceImpl;
import de.cr.freitonal.client.widgets.composer.ComposerPresenter;
import de.cr.freitonal.client.widgets.instrumentation.InstrumentationPresenter;
import de.cr.freitonal.client.widgets.piece.PiecePresenter;
import de.cr.freitonal.shared.models.VolatilePiece;
import de.cr.freitonal.unittests.client.rpc.JSONTestCase;

public class PieceCreationTest extends JSONTestCase {
	private PiecePresenter piecePresenter;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		PiecePresenter.View view = new PieceViewMock();
		appController = new AppController(view, null);
		piecePresenter = appController.getPiecePresenter();

		onNextSearchReturn(resources.getFullSearchJSON().getText());
		appController.go();

	}

	@Test
	public void testSwitchToCreateModeComposer() {
		ComposerPresenter composerPresenter = piecePresenter.getComposerPresenter();
		onNextSearchReturn(resources.getSearchForBeethovenJSON().getText());
		composerPresenter.getListBoxPresenter().fireOnNewItemSelected(Beethoven);

		assertEquals("after selecting a composer, the item count should be one", 1, composerPresenter.getItemCount());

		piecePresenter.fireAddPieceButtonClicked();

		assertEquals("the composer presenter should be in creation mode", Create, composerPresenter.getDisplayMode());
		assertEquals("the composer presenter should show all composers", NumberOfComposers, composerPresenter.getItemCount());

		//check that the no search is triggered in creation mode when a composer is selected:
		onNextSearchFail();
		piecePresenter.getComposerPresenter().fireOnNewItemSelected(Beethoven);
		assertEquals("the composer presenter should show all composers", NumberOfComposers, composerPresenter.getItemCount());
	}

	@Test
	public void testSwitchToCreateModeInstrumentation() {
		InstrumentationPresenter instrumentationPresenter = piecePresenter.getInstrumentationPresenter();
		onNextSearchReturn(resources.getSearchForPianoJSON().getText());
		instrumentationPresenter.getInstrumentPresenter(0).fireOnNewItemSelected(Piano);

		assertEquals("after selecting an instrument, the item count should be one", 1, instrumentationPresenter.getInstrumentPresenter(0)
				.getItemCount());
		assertEquals("after selecting an instrument, there should be 2 dropdown boxes", 2, instrumentationPresenter.getNumberOfInstrumentPresenters());

		piecePresenter.fireAddPieceButtonClicked();

		assertEquals("the instrumentation presenter should show 1 dropdown box", 1, instrumentationPresenter.getNumberOfInstrumentPresenters());
		assertEquals("the instrumentation presenter should show all instruments", NumberOfInstruments, instrumentationPresenter
				.getInstrumentPresenter(0).getItemCount());

		//check that in creation mode no search is triggered and no new dropdown box is created:
		onNextSearchFail();
		instrumentationPresenter.getInstrumentPresenter(0).fireOnNewItemSelected(Piano);
		assertEquals("the instrumentation presenter should show 1 dropdown box", 1, instrumentationPresenter.getNumberOfInstrumentPresenters());

		//check that the + button also works in creation mode
		instrumentationPresenter.fireAddInstrumentButtonClicked_TEST();
		assertEquals("the instrumentation presenter should show 2 dropdown boxes", 2, instrumentationPresenter.getNumberOfInstrumentPresenters());
		assertEquals("the second dropdown box should show all instruments", NumberOfInstruments, instrumentationPresenter.getInstrumentPresenter(1)
				.getItemCount());
	}

	@Test
	public void testSavePiece() {
		//switch to creation mode
		piecePresenter.fireAddPieceButtonClicked();

		//select elements for the new piece:
		piecePresenter.getComposerPresenter().fireOnNewItemSelected(Beethoven);
		piecePresenter.getCatalogPresenter().fireOnNewItemSelected(Opus27_1);
		piecePresenter.getMusicKeyPresenter().fireOnNewItemSelected(AMajor);
		piecePresenter.getPieceTypePresenter().fireOnNewItemSelected(Quartett);
		piecePresenter.getSubtitlePresenter().fireOnNewItemSelected(Eroica);
		piecePresenter.getOrdinalPresenter().fireOnNewItemSelected(Ordinal4a);
		piecePresenter.getInstrumentationPresenter().getInstrumentPresenter(0).fireOnNewItemSelected(Piano);

		final ArrayList<String> trace = new ArrayList<String>();
		appController.setRPCService(new RPCServiceImpl(parser, urlEncoder) {
			@Override
			public void save(VolatilePiece piece) {
				assertNotNull("the piece to save should not be null", piece);
				assertEquals("the saved composer should be Beethoven", Beethoven, piece.getComposer());
				assertEquals("the saved catalog should be Opus 27-1", Opus27_1, piece.catalog);
				assertEquals("the saved musickey should be A major", AMajor, piece.musicKey);
				assertEquals("the saved piece type should be Quartett", Quartett, piece.getPieceType());
				assertEquals("the saved subtitle should be Eroica", Eroica, piece.subtitle);
				assertEquals("the saved ordinal should be 4a", Ordinal4a, piece.ordinal);
				assertEquals("the saved instrumentation should be piano", InstrumentationPianoSolo, piece.getInstrumentation());

				trace.add("save");
			}
		});

		//save the piece
		piecePresenter.fireAddPieceButtonClicked();
		assertEquals("there should be one traced call", 1, trace.size());
		assertEquals("RPCService.save should have been called", "save", trace.get(0));
	}

}
