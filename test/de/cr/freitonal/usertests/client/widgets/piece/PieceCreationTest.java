package de.cr.freitonal.usertests.client.widgets.piece;

import static de.cr.freitonal.client.event.DisplayMode.Create;
import static de.cr.freitonal.usertests.client.test.data.FullSearchInformation.Beethoven;
import static de.cr.freitonal.usertests.client.test.data.FullSearchInformation.NumberOfComposers;
import static de.cr.freitonal.usertests.client.test.data.FullSearchInformation.NumberOfInstruments;
import static de.cr.freitonal.usertests.client.test.data.FullSearchInformation.Opus27_1;
import static de.cr.freitonal.usertests.client.test.data.FullSearchInformation.Piano;

import java.util.ArrayList;

import org.junit.Test;

import de.cr.freitonal.client.AppController;
import de.cr.freitonal.client.models.Piece;
import de.cr.freitonal.client.rpc.RPCServiceImpl;
import de.cr.freitonal.client.rpc.gwt.DTOParserGWT;
import de.cr.freitonal.client.widgets.composer.ComposerPresenter;
import de.cr.freitonal.client.widgets.instrumentation.InstrumentationPresenter;
import de.cr.freitonal.client.widgets.piece.PiecePresenter;
import de.cr.freitonal.client.widgets.piece.PieceView;
import de.cr.freitonal.usertests.client.rpc.JSONTestCase;

public class PieceCreationTest extends JSONTestCase {
	private PiecePresenter piecePresenter;

	@Override
	public String getModuleName() {
		return "de.cr.freitonal.FreitonalGUI";
	}

	@Override
	public void gwtSetUp() {
		super.gwtSetUp();
		PieceView view = new PieceView();
		appController = new AppController(view, null);
		piecePresenter = appController.getPiecePresenter();

		onNextSearchReturn(resources.getFullSearchJSON().getText());
		appController.go();

	}

	@Test
	public void testSwitchToCreateModeComposer() {
		ComposerPresenter composerPresenter = piecePresenter.getComposerPresenter();
		onNextSearchReturn(resources.getSearchForBeethovenJSON().getText());
		composerPresenter.getListBoxPresenter().fireOnNewItemSelected_TEST(Beethoven);

		assertEquals("after selecting a composer, the item count should be one", 1, composerPresenter.getItemCount());

		piecePresenter.fireAddPieceButtonClicked();

		assertEquals("the composer presenter should be in creation mode", Create, composerPresenter.getDisplayMode());
		assertEquals("the composer presenter should show all composers", NumberOfComposers, composerPresenter.getItemCount());
	}

	@Test
	public void testSwitchToCreateModeInstrumentation() {
		InstrumentationPresenter instrumentationPresenter = piecePresenter.getInstrumentationPresenter();
		onNextSearchReturn(resources.getSearchForPianoJSON().getText());
		instrumentationPresenter.getInstrumentPresenter(0).fireOnNewItemSelected_TEST(Piano);

		assertEquals("after selecting an instrument, the item count should be one", 1, instrumentationPresenter.getInstrumentPresenter(0)
				.getItemCount());
		assertEquals("after selecting an instrument, there should be 2 dropdown boxes", 2, instrumentationPresenter.getNumberOfInstrumentPresenters());

		piecePresenter.fireAddPieceButtonClicked();

		assertEquals("the instrumentation presenter should show 1 dropdown box", 1, instrumentationPresenter.getNumberOfInstrumentPresenters());
		assertEquals("the instrumentation presenter should show all instruments", NumberOfInstruments, instrumentationPresenter
				.getInstrumentPresenter(0).getItemCount());

		//check that in creation mode no search is triggered and no new dropdown box is created:
		onNextSearchFail();
		instrumentationPresenter.getInstrumentPresenter(0).fireOnNewItemSelected_TEST(Piano);
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
		piecePresenter.getComposerPresenter().fireOnNewItemSelected_TEST(Beethoven);
		piecePresenter.getCatalogPresenter().fireOnNewItemSelected_TEST(Opus27_1);

		final ArrayList<String> trace = new ArrayList<String>();
		appController.setRPCService(new RPCServiceImpl(new DTOParserGWT()) {
			@Override
			public void save(Piece piece) {
				assertNotNull("the piece to save should not be null", piece);
				assertEquals("the saved composer should be Beethoven", Beethoven, piece.composer);
				assertEquals("the saved catalog should be Opus 27-1", Opus27_1, piece.catalog);
				trace.add("save");
			}
		});

		//save the piece
		piecePresenter.fireAddPieceButtonClicked();
		assertEquals("there should be one traced call", 1, trace.size());
		assertEquals("RPCService.save should have been called", "save", trace.get(0));
	}

}
