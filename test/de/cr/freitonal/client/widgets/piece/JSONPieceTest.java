package de.cr.freitonal.client.widgets.piece;

import static de.cr.freitonal.client.test.data.FullSearchInformation.Beethoven;
import static de.cr.freitonal.client.test.data.FullSearchInformation.NumberOfCatalogNames;
import static de.cr.freitonal.client.test.data.FullSearchInformation.NumberOfCatalogNumbers;
import static de.cr.freitonal.client.test.data.FullSearchInformation.NumberOfComposers;
import static de.cr.freitonal.client.test.data.FullSearchInformation.NumberOfInstruments;
import static de.cr.freitonal.client.test.data.FullSearchInformation.NumberOfPieceTypes;
import static de.cr.freitonal.client.test.data.FullSearchInformation.Piano;

import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.client.AppController;
import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.client.rpc.RPCServiceImpl;
import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.client.test.data.TestResources;
import de.cr.freitonal.client.widgets.composer.ComposerPresenter;
import de.cr.freitonal.client.widgets.instrumentation.InstrumentationPresenter;

public class JSONPieceTest extends GWTTestCase {
	private TestResources resources;
	private PieceView pieceView;
	private AppController appController;
	private PiecePresenter piecePresenter;

	@Override
	public void gwtSetUp() {
		resources = GWT.create(TestResources.class);
		pieceView = new PieceView();
		appController = new AppController(pieceView, null);
		piecePresenter = appController.getPiecePresenter();

		onNextSearchReturn(resources.getFullSearchJSON().getText());

	}

	@Test
	public void testFullSearch() {
		appController.go();
		assertEquals(NumberOfComposers, piecePresenter.getComposerPresenter().getItemCount());
		assertEquals(NumberOfCatalogNames, piecePresenter.getCatalogPresenter().getNameItemCount());
		assertEquals(NumberOfCatalogNumbers, piecePresenter.getCatalogPresenter().getNumberItemCount());
		assertEquals(NumberOfPieceTypes, piecePresenter.getPieceTypePresenter().getItemCount());
		assertEquals(NumberOfInstruments, piecePresenter.getInstrumentationPresenter().getItemCount(0));
	}

	@Test
	public void testComposerSearch() {
		ComposerPresenter composerPresenter = piecePresenter.getComposerPresenter();

		appController.go();

		onNextSearchReturn(resources.getSearchForBeethovenJSON().getText());
		composerPresenter.getListBoxPresenter().fireOnNewItemSelected(Beethoven);

		assertEquals(1, composerPresenter.getItemCount());
		assertEquals(Beethoven, composerPresenter.getListBoxPresenter().getSelectedItem());
	}

	@Test
	public void testInstrumentationSearch() {
		InstrumentationPresenter instrumentationPresenter = piecePresenter.getInstrumentationPresenter();

		appController.go();

		onNextSearchReturn(resources.getSearchForPianoJSON().getText());
		instrumentationPresenter.getListBoxPresenter(0).fireOnNewItemSelected(Piano);

		assertEquals(1, instrumentationPresenter.getItemCount(0));
		assertEquals(Piano, instrumentationPresenter.getListBoxPresenter(0).getSelectedItem());
	}

	private void onNextSearchReturn(final String jsonString) {
		appController.setRPCService(new RPCServiceImpl() {
			@Override
			public void search(PieceSearchMask searchMask, AsyncCallback<SearchResult> callback) {
				callback.onSuccess(createSearchResult(searchMask, jsonString));
			}
		});
	}

	@Override
	public String getModuleName() {
		return "de.cr.freitonal.FreitonalGUI";
	}
}
