package de.cr.freitonal.unittests.client.widgets.piecetype;

import static de.cr.freitonal.client.event.DisplayMode.Create;
import static de.cr.freitonal.unittests.client.test.data.TestData.createPieceTypeSet;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.cr.freitonal.client.models.PieceTypeSet;
import de.cr.freitonal.client.widgets.piecetype.PieceTypePresenter;
import de.cr.freitonal.unittests.client.widgets.PresenterTest;

public class APieceTypePresenterAfterInitialLoadingShould extends PresenterTest {
	@Test
	public void SwitchToCreateModeWhenSetDisplayModeWithCreateIsCalled() {
		PieceTypeViewMock view = new PieceTypeViewMock();
		PieceTypePresenter presenter = new PieceTypePresenter(eventBus, view);
		PieceTypeSet pieceTypeSet = createPieceTypeSet();
		presenter.setPieceTypes(pieceTypeSet);
		presenter.setDisplayMode(Create);
		assertEquals(Create, presenter.getListBoxPresenter().getDisplayMode());
		presenter.fireOnNewItemSelected(pieceTypeSet.getPieceTypes().getItem(0));
	}
}
