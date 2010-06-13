package de.cr.freitonal.unittests.client.widgets.catalog;

import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.NumberOfCatalogNames;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.NumberOfCatalogNumbers;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Opus27_1;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.AppController;
import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.widgets.catalog.CatalogPresenter;
import de.cr.freitonal.unittests.client.rpc.JSONTestCase;
import de.cr.freitonal.unittests.client.test.data.FullSearchInformation;
import de.cr.freitonal.unittests.client.widgets.piece.PieceViewMock;

public class CatalogPresenterTest extends JSONTestCase {
	private CatalogPresenter catalogPresenter;
	private CatalogSet catalogSet;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		appController = new AppController(new PieceViewMock(), null);

		catalogPresenter = appController.getPiecePresenter().getCatalogPresenter();
		onNextSearchReturn(resources.getFullSearchJSON().getText());
		appController.go();
	}

	@Test
	public void testInitialLoading() {
		assertEquals(NumberOfCatalogNames, catalogPresenter.getNameItemCount());
		assertEquals(NumberOfCatalogNumbers, catalogPresenter.getNumberItemCount());
	}

	@Test
	public void testNameSelection() {
		assertNull("Initially, no name should be selected", catalogPresenter.getNameListBoxPresenter().getSelectedItem());
		assertNull("Initially, no number should be selected", catalogPresenter.getNumberListBoxPresenter().getSelectedItem());
		assertFalse("Initially, the number dropdown should be disabled", catalogPresenter.getNumberListBoxPresenter().isEnabled());

		//select a name:
		catalogPresenter.getNameListBoxPresenter().fireOnNewItemSelected(FullSearchInformation.KV);

		assertNull("After selecting a name, no number should be selected", catalogPresenter.getNumberListBoxPresenter().getSelectedItem());
		assertTrue("After selecting a name, the number dropdown should be enabled", catalogPresenter.getNumberListBoxPresenter().isEnabled());
	}

	@Test
	public void testCreateMode() {
		appController.getPiecePresenter().fireAddPieceButtonClicked();
		onNextSearchFail();
		catalogPresenter.fireOnNewItemSelected(Opus27_1);
	}
}
