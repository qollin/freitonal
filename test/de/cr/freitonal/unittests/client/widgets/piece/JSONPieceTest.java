package de.cr.freitonal.unittests.client.widgets.piece;

import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.AMajor;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Beethoven;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Eroica;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.NumberOfCatalogNames;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.NumberOfCatalogNumbers;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.NumberOfComposers;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.NumberOfInstruments;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.NumberOfPieceTypes;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Ordinal4a;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Piano;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Violin;
import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.AppController;
import de.cr.freitonal.client.widgets.composer.ComposerPresenter;
import de.cr.freitonal.client.widgets.instrumentation.InstrumentationPresenter;
import de.cr.freitonal.client.widgets.musickey.MusicKeyPresenter;
import de.cr.freitonal.client.widgets.ordinal.OrdinalPresenter;
import de.cr.freitonal.client.widgets.piece.PiecePresenter;
import de.cr.freitonal.client.widgets.subtitle.SubtitlePresenter;
import de.cr.freitonal.unittests.client.rpc.JSONTestCase;

public class JSONPieceTest extends JSONTestCase {
	private PiecePresenter.View pieceView;
	private PiecePresenter piecePresenter;

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

	@Test
	public void testFullSearch() {
		assertEquals(NumberOfComposers, piecePresenter.getComposerPresenter().getItemCount());
		assertEquals(NumberOfCatalogNames, piecePresenter.getCatalogPresenter().getNameItemCount());
		assertEquals(NumberOfCatalogNumbers, piecePresenter.getCatalogPresenter().getNumberItemCount());
		assertEquals(NumberOfPieceTypes, piecePresenter.getPieceTypePresenter().getItemCount());
		assertEquals(NumberOfInstruments, piecePresenter.getInstrumentationPresenter().getInstrumentPresenter(0).getItemCount());
	}

	@Test
	public void testComposerSearch() {
		ComposerPresenter composerPresenter = piecePresenter.getComposerPresenter();
		onNextSearchReturn(resources.getSearchForBeethovenJSON().getText());
		composerPresenter.getListBoxPresenter().fireOnNewItemSelected(Beethoven);

		assertEquals(1, composerPresenter.getItemCount());
		assertEquals(Beethoven, composerPresenter.getListBoxPresenter().getSelectedItem());
	}

	@Test
	public void testInstrumentationSearch() {
		InstrumentationPresenter instrumentationPresenter = piecePresenter.getInstrumentationPresenter();
		onNextSearchReturn(resources.getSearchForPianoJSON().getText());
		instrumentationPresenter.getInstrumentPresenter(0).fireOnNewItemSelected(Piano);

		assertEquals("searching for one instrument should create a second search box", 2, instrumentationPresenter.getNumberOfInstrumentPresenters());
		assertEquals(1, instrumentationPresenter.getInstrumentPresenter(0).getItemCount());
		assertEquals(Piano, instrumentationPresenter.getInstrumentPresenter(0).getSelectedItem());
		assertEquals(12, instrumentationPresenter.getInstrumentPresenter(1).getItemCount());

		onNextSearchReturn(resources.getSearchForPianoAndViolinJSON().getText());
		instrumentationPresenter.getInstrumentPresenter(1).fireOnNewItemSelected(Violin);

		assertEquals("searching for two instruments should create a third search box", 3, instrumentationPresenter.getNumberOfInstrumentPresenters());
		assertEquals(1, instrumentationPresenter.getInstrumentPresenter(0).getItemCount());
		assertEquals(Piano, instrumentationPresenter.getInstrumentPresenter(0).getSelectedItem());
		assertEquals(1, instrumentationPresenter.getInstrumentPresenter(1).getItemCount());
		assertEquals(Violin, instrumentationPresenter.getInstrumentPresenter(1).getSelectedItem());
		assertEquals(4, instrumentationPresenter.getInstrumentPresenter(2).getItemCount());
	}

	@Test
	public void testSubtitleSearch() {
		SubtitlePresenter subtitlePresenter = piecePresenter.getSubtitlePresenter();
		onNextSearchReturn(resources.getSearchForSubtitleJSON().getText());
		subtitlePresenter.getListBoxPresenter().fireOnNewItemSelected(Eroica);
	}

	@Test
	public void testOrdinalSearch() {
		OrdinalPresenter ordinalPresenter = piecePresenter.getOrdinalPresenter();
		onNextSearchReturn(resources.getSearchForOrdinal4aJSON().getText());
		ordinalPresenter.getListBoxPresenter().fireOnNewItemSelected(Ordinal4a);
	}

	@Test
	public void testMusicKeySearch() {
		MusicKeyPresenter musicKeyPresenter = piecePresenter.getMusicKeyPresenter();
		onNextSearchReturn(resources.getSearchForAMajorJSON().getText());
		musicKeyPresenter.getListBoxPresenter().fireOnNewItemSelected(AMajor);
	}
}
